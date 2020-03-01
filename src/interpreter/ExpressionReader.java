package interpreter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import exception.EndOfExpressionException;
import exception.EndOfFileException;
import exception.LackOfParentheseException;

/**
 * 把表达式从字符串形式转化成分层次的字符串
 * 比如(A,B,(C)) --> List(A,B,List(c))
 * 使用List来代替括号的层次效果
 * @author 90946
 *
 */
public final class ExpressionReader {
//比较麻烦的是读完一个表达式之后同时还需要把之后的 空白字符 全部读完
//否则就会I/O阻塞，导致不能输出结果

//在读取文件时需要判断读到文件尾
//Reader.read() == -1可以判断
	
	
	private LineNumberReader reader = DEFAULT_READER;
	
	private static final LineNumberReader DEFAULT_READER = new LineNumberReader(
				new BufferedReader(
					new InputStreamReader(System.in)));
	
	//下一个字符
	private char nextChar;
	//下一个token
	private StringBuilder nextToken = new StringBuilder();
	
	//一个文件是否读取完
	private boolean endOfFile = false;
	
	//判断一个表达式是否读取完
	private int parentheseStack=0;
	private boolean endOfExpression = false;
	
	//控制台模式会假设一个表达式最后只有'\n'
	//只会调用一次 nextChar()
	private boolean consoleMode = true;
	
	//有效的字符集
	private static final Set<Character> validCharacters = new HashSet<>();
	static {
		//运算符
			//算术
		validCharacters.add('+');
		validCharacters.add('-');
		validCharacters.add('*');
		validCharacters.add('/');
			//比较
		validCharacters.add('>');
		validCharacters.add('<');
		validCharacters.add('=');
		//谓词提示符
		validCharacters.add('?');
		//赋值提示符
		validCharacters.add('!');
		//字符串提示符
		validCharacters.add('\'');
		validCharacters.add('"');
		//数字
		validCharacters.add('!');
		validCharacters.add('1');
		validCharacters.add('2');
		validCharacters.add('3');
		validCharacters.add('4');
		validCharacters.add('5');
		validCharacters.add('6');
		validCharacters.add('7');
		validCharacters.add('8');
		validCharacters.add('9');
		validCharacters.add('0');
		//小数
		validCharacters.add('.');
		//注释
		validCharacters.add(';');
		//
		validCharacters.add('_');
	}
	
	//设置新的库
	public void setReader(LineNumberReader reader) {
		nextChar = ' ';
		endOfFile = false;
		endOfExpression = false;
		//
		consoleMode = false;
		this.reader = reader;
	}
	
	//默认从system.in中读取
	public void resetReader() {
		nextChar = ' ';
		endOfFile = false;
		endOfExpression = false;
		//
		consoleMode = true;
		this.reader = DEFAULT_READER;
	}
	
	/**
	 * @return {@code nextChar}是否是左括号
	 */
	private boolean isLeftParenthese() {
		return nextChar == '(';
	}
	
	/**
	 * @return {@code nextChar}是否是右括号
	 */
	private boolean isRightParenthese() {
		return nextChar == ')';
	}
	
	/**
	 * @return {@code nextChar}是否是有效的字符
	 */
	private boolean isToken() {
		return Character.isLetter(nextChar) || validCharacters.contains(nextChar);
	}
	
	private boolean isQuoteExpression() {
		return nextChar == '\'';
	}
	
	
	/**
	 * 从{@code nextChar}开始判断是否是字母字符，
	 * 一直读取字母进入nextToken,直到遇到非字母字符
	 * @throws IOException 
	 * @throws EndOfFileException 
	 * @throws EndOfExpressionException 
	 */
	private ExpressionReader token() throws IOException, EndOfFileException, EndOfExpressionException {
		nextToken = new StringBuilder();
		//提供这种字符串的格式"..."
		boolean stringMode = false;
		if(nextChar == '"') {
			stringMode = true;
		}
		while(isToken() || stringMode) {
			nextToken.append(nextChar);
			nextChar();
			if(nextChar == '"') {
				stringMode = false;
			}
		}
		return this;
	}
	
	/**
	 * 先判断{@code nextChar}是否是'('或')'或token，若是，则不读取空白；
	 * 否则，从下一个字符开始判断是否是空白字符，
	 * 跳过所有空白字符，最后停在非空白字符
	 * @throws IOException 
	 * @throws EndOfFileException 
	 * @throws EndOfExpressionException 
	 */
	private ExpressionReader white() throws IOException, EndOfFileException, EndOfExpressionException {
		if(isLeftParenthese() || isToken() || isRightParenthese())
			return this;
		nextChar();
		while(Character.isWhitespace(nextChar)) {
			nextChar();
		}
		return this;
	}
	
	/**
	 * 判断{@code nextChar}是否是'(',
	 * 读取字符
	 * @throws IOException
	 * @throws LackOfParentheseException
	 * @throws EndOfFileException 
	 * @throws EndOfExpressionException 
	 */
	private ExpressionReader leftParenthese() throws LackOfParentheseException, IOException, EndOfFileException, EndOfExpressionException {
		if(nextChar != '(') {
			throw new LackOfParentheseException(reader.getLineNumber()+1, "左括号");
		}
		if(parentheseStack == 0) {
			endOfExpression = false;
		}
		parentheseStack++;
		nextChar();
		return this;
	}
	
	
	/**
	 * 判断{@code nextChar}是否是')',
	 * 读取字符
	 * @throws IOException
	 * @throws LackOfParentheseException
	 * @throws EndOfFileException 
	 * @throws EndOfExpressionException 
	 */
	private ExpressionReader rightParenthese() throws LackOfParentheseException, IOException, EndOfFileException, EndOfExpressionException {
		if(nextChar != ')') {
			throw new LackOfParentheseException(reader.getLineNumber()+1, "右括号");
		}
		parentheseStack--;
		nextChar();
		if(parentheseStack == 0) {
			endOfExpression = true;
			throw new EndOfExpressionException();
		}
		return this;
	}
	
	
	private void nextChar() throws IOException, EndOfFileException, EndOfExpressionException {
		//读到表达式尾
		if(consoleMode && endOfExpression) {
			endOfExpression = false;
			throw new EndOfExpressionException();	
		}
		int tChar = reader.read();
		//读到文件尾
		if(tChar == -1) {
			endOfFile = true;
			throw new EndOfFileException();
		}
		nextChar = (char)tChar;
	}
	
	/**
	 * 先判断{@code nextChar}是否是'('或token，若是，则不读取')'和空白；
	 * 否则，从下一个字符开始判断是否是')'和空白字符，
	 * 跳过所有')'和空白，最后停在非')'和非空白字符
	 * @throws IOException 
	 * @throws EndOfFileException 
	 * @throws EndOfExpressionException 
	 */
	private ExpressionReader endOfExpression() throws IOException, EndOfFileException, EndOfExpressionException {
		if(isLeftParenthese() || isToken())
			return this;
		nextChar();
		while(nextChar == ')' || Character.isWhitespace(nextChar)) {
			nextChar();
		}
		return this;
	}
	
	/**
	 * 跳过注释
	 * 注释格式为;.........
	 * 跳过;之后的那一行
	 * @return
	 * @throws IOException
	 * @throws EndOfFileException
	 * @throws EndOfExpressionException
	 */
	private ExpressionReader comment() throws IOException, EndOfFileException, EndOfExpressionException {
		white();
		if(nextChar == ';') {
			reader.readLine();
			nextChar();
		}
		return this;
	}
	
	public Object read() {
		try {
			white();
			if(isLeftParenthese()) {
				return readExpression();
			}else {
				token();
				return nextToken.toString();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (EndOfFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (EndOfExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	

	public Object readExpression() {		
		List<Object> list = new LinkedList<>();	
		try {
			comment();
			// (
			white().leftParenthese().white();
						
			while(!isRightParenthese()) {
				if(isLeftParenthese()  || isQuoteExpression()){
					if(nextChar == '\'') {
						//'() --> (quote (...))
						nextChar();
						List<Object> subList = new LinkedList<>();
						subList.add("quote");
						if(isLeftParenthese()) {
							//'(...)
							subList.add(readExpression());
						}else {
							//'string
							token();
							subList.add(nextToken.toString());
						}
						list.add(subList);
					}else if(isLeftParenthese()) {
						//(...)
						list.add(readExpression());
					}
					
				}else {
					//token
					token();
					list.add(nextToken.toString());
				}	
				
				//跳过空白
				if(Character.isWhitespace(nextChar)) {
					nextChar();
					white();
				}
			}
			// )
			rightParenthese();
		}catch (EndOfExpressionException e) {
			// TODO: handle exception
			//读取文件时跳过一个表达式后的一串空白
			//此时consoleMode = false
			
			//若 consoleMode = true
			//只会跳过一个空白
			try {
				comment();
				endOfExpression();
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}catch (EndOfFileException e) {
			// TODO: handle exception
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	public boolean hasNextExpression() {
		// TODO Auto-generated method stub
		return !endOfFile;
	}
	
	public static void main(String[] args) {
		ExpressionReader eTest = new ExpressionReader();
		while(true) {
			System.out.println(eTest.readExpression());
		}
	}

}
