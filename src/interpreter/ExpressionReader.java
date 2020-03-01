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
 * �ѱ��ʽ���ַ�����ʽת���ɷֲ�ε��ַ���
 * ����(A,B,(C)) --> List(A,B,List(c))
 * ʹ��List���������ŵĲ��Ч��
 * @author 90946
 *
 */
public final class ExpressionReader {
//�Ƚ��鷳���Ƕ���һ�����ʽ֮��ͬʱ����Ҫ��֮��� �հ��ַ� ȫ������
//����ͻ�I/O���������²���������

//�ڶ�ȡ�ļ�ʱ��Ҫ�ж϶����ļ�β
//Reader.read() == -1�����ж�
	
	
	private LineNumberReader reader = DEFAULT_READER;
	
	private static final LineNumberReader DEFAULT_READER = new LineNumberReader(
				new BufferedReader(
					new InputStreamReader(System.in)));
	
	//��һ���ַ�
	private char nextChar;
	//��һ��token
	private StringBuilder nextToken = new StringBuilder();
	
	//һ���ļ��Ƿ��ȡ��
	private boolean endOfFile = false;
	
	//�ж�һ�����ʽ�Ƿ��ȡ��
	private int parentheseStack=0;
	private boolean endOfExpression = false;
	
	//����̨ģʽ�����һ�����ʽ���ֻ��'\n'
	//ֻ�����һ�� nextChar()
	private boolean consoleMode = true;
	
	//��Ч���ַ���
	private static final Set<Character> validCharacters = new HashSet<>();
	static {
		//�����
			//����
		validCharacters.add('+');
		validCharacters.add('-');
		validCharacters.add('*');
		validCharacters.add('/');
			//�Ƚ�
		validCharacters.add('>');
		validCharacters.add('<');
		validCharacters.add('=');
		//ν����ʾ��
		validCharacters.add('?');
		//��ֵ��ʾ��
		validCharacters.add('!');
		//�ַ�����ʾ��
		validCharacters.add('\'');
		validCharacters.add('"');
		//����
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
		//С��
		validCharacters.add('.');
		//ע��
		validCharacters.add(';');
		//
		validCharacters.add('_');
	}
	
	//�����µĿ�
	public void setReader(LineNumberReader reader) {
		nextChar = ' ';
		endOfFile = false;
		endOfExpression = false;
		//
		consoleMode = false;
		this.reader = reader;
	}
	
	//Ĭ�ϴ�system.in�ж�ȡ
	public void resetReader() {
		nextChar = ' ';
		endOfFile = false;
		endOfExpression = false;
		//
		consoleMode = true;
		this.reader = DEFAULT_READER;
	}
	
	/**
	 * @return {@code nextChar}�Ƿ���������
	 */
	private boolean isLeftParenthese() {
		return nextChar == '(';
	}
	
	/**
	 * @return {@code nextChar}�Ƿ���������
	 */
	private boolean isRightParenthese() {
		return nextChar == ')';
	}
	
	/**
	 * @return {@code nextChar}�Ƿ�����Ч���ַ�
	 */
	private boolean isToken() {
		return Character.isLetter(nextChar) || validCharacters.contains(nextChar);
	}
	
	private boolean isQuoteExpression() {
		return nextChar == '\'';
	}
	
	
	/**
	 * ��{@code nextChar}��ʼ�ж��Ƿ�����ĸ�ַ���
	 * һֱ��ȡ��ĸ����nextToken,ֱ����������ĸ�ַ�
	 * @throws IOException 
	 * @throws EndOfFileException 
	 * @throws EndOfExpressionException 
	 */
	private ExpressionReader token() throws IOException, EndOfFileException, EndOfExpressionException {
		nextToken = new StringBuilder();
		//�ṩ�����ַ����ĸ�ʽ"..."
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
	 * ���ж�{@code nextChar}�Ƿ���'('��')'��token�����ǣ��򲻶�ȡ�հף�
	 * ���򣬴���һ���ַ���ʼ�ж��Ƿ��ǿհ��ַ���
	 * �������пհ��ַ������ͣ�ڷǿհ��ַ�
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
	 * �ж�{@code nextChar}�Ƿ���'(',
	 * ��ȡ�ַ�
	 * @throws IOException
	 * @throws LackOfParentheseException
	 * @throws EndOfFileException 
	 * @throws EndOfExpressionException 
	 */
	private ExpressionReader leftParenthese() throws LackOfParentheseException, IOException, EndOfFileException, EndOfExpressionException {
		if(nextChar != '(') {
			throw new LackOfParentheseException(reader.getLineNumber()+1, "������");
		}
		if(parentheseStack == 0) {
			endOfExpression = false;
		}
		parentheseStack++;
		nextChar();
		return this;
	}
	
	
	/**
	 * �ж�{@code nextChar}�Ƿ���')',
	 * ��ȡ�ַ�
	 * @throws IOException
	 * @throws LackOfParentheseException
	 * @throws EndOfFileException 
	 * @throws EndOfExpressionException 
	 */
	private ExpressionReader rightParenthese() throws LackOfParentheseException, IOException, EndOfFileException, EndOfExpressionException {
		if(nextChar != ')') {
			throw new LackOfParentheseException(reader.getLineNumber()+1, "������");
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
		//�������ʽβ
		if(consoleMode && endOfExpression) {
			endOfExpression = false;
			throw new EndOfExpressionException();	
		}
		int tChar = reader.read();
		//�����ļ�β
		if(tChar == -1) {
			endOfFile = true;
			throw new EndOfFileException();
		}
		nextChar = (char)tChar;
	}
	
	/**
	 * ���ж�{@code nextChar}�Ƿ���'('��token�����ǣ��򲻶�ȡ')'�Ϳհף�
	 * ���򣬴���һ���ַ���ʼ�ж��Ƿ���')'�Ϳհ��ַ���
	 * ��������')'�Ϳհף����ͣ�ڷ�')'�ͷǿհ��ַ�
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
	 * ����ע��
	 * ע�͸�ʽΪ;.........
	 * ����;֮�����һ��
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
				
				//�����հ�
				if(Character.isWhitespace(nextChar)) {
					nextChar();
					white();
				}
			}
			// )
			rightParenthese();
		}catch (EndOfExpressionException e) {
			// TODO: handle exception
			//��ȡ�ļ�ʱ����һ�����ʽ���һ���հ�
			//��ʱconsoleMode = false
			
			//�� consoleMode = true
			//ֻ������һ���հ�
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
