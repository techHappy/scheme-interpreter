package interpreter;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public final class ExpressionReader_deprecated {

	private static final Scanner DEFAULT_SCANNER =new Scanner(System.in);
	private Scanner scanner = DEFAULT_SCANNER;

	private int parentheseStack=0;
	private String nextToken;
	private boolean inQuote=false;
	
	public ExpressionReader_deprecated() {
		// TODO Auto-generated constructor stub
	}
		
	public ExpressionReader_deprecated(Scanner scanner) {
		this.scanner=scanner;
	}
	
	public void setScanner(Scanner scanner) {
		this.scanner =scanner;
	}
	public void resetScanner() {
		this.scanner=DEFAULT_SCANNER;
	}
	/**
	 * 读取一个token进入{@code nextToken}
	 */
	//不能读取到(display ":::Query input:"))这样的引号
	//"和)之间必须空一格
	//未来看到这里的人希望你来改
	private String readToken() {
		nextToken = scanner.next();
		checkBeginQuote();
		StringBuilder sb = new StringBuilder(nextToken);
		checkEndQuote();
		while(inQuote) {
			nextToken = scanner.next();
			sb.append(" "+nextToken);
			checkEndQuote();
		}
		nextToken=sb.toString();
		return sb.toString();
	}
	
	private void checkBeginQuote() {
		if(nextToken.startsWith("\"")) {
			inQuote=true;
		}

	}
	
	private void checkEndQuote() {
		if(nextToken.endsWith("\"")) {
			inQuote=false;
		}
	}
	
	/**
	 * 是否有右括号
	 * @return
	 */
	private boolean hasRightParenthese() {
		return nextToken.endsWith(")");
	}
	
	/**
	 * 是否有左括号
	 * @return
	 */
	private boolean hasLeftParenthese() {
		return nextToken.startsWith("(");
	}
	
	/**
	 * 去除右括号
	 *假设为 ...10 )
	 * 括号和token之间可以有空隙
	 */
	private void removeRightParenthese() {							
		if(nextToken.endsWith(")")){
			nextToken=nextToken.substring(0, nextToken.length()-1).trim();
			parentheseStack--;
			if(parentheseStack<0) {
				parentheseStack=0;
				throw new RuntimeException("括号不匹配！");
			}
//			//多重右括号出现的情况
//			//))))
			removeRightParenthese();
		}						
	}
	
	/**
	 * 去除左括号
	 * 假设为 ( define ...
	 * 括号和token之间可以有空隙
	 */
	private void removeLeftParenthese() {
		if(nextToken.startsWith("(")) {
			nextToken=nextToken.substring(1, nextToken.length()).trim();
			parentheseStack++;
			
			//防止出现( define 这样的情况
			if(nextToken.equals(""))
				readToken();

		}
	}
	
	private boolean hasParenthese() {
		//去除开头的空白
		nextToken = nextToken.trim();
		return nextToken.startsWith("(") || nextToken.endsWith(")");
	}
	public void startRead() {
		readToken();
	}
	
	/**
	 * nextToken为这样的 (x)
	 * @return
	 */
	private boolean hasLeftAndRightParenthese() {
		return nextToken.startsWith("(") && nextToken.endsWith(")");
	}
	
	private void removeLeftAndRightParenthese() {
		if(nextToken.startsWith("(") && nextToken.endsWith(")")) {
			nextToken = nextToken.substring(1, nextToken.length()-1);
			removeRightParenthese();
		}
	}
	
	/**
	 * 作用：把各个token加入{@code lists}中
	 * 
	 * 注意：不需要提前调用{@code readToken()}
	 * 
	 * 如果是嵌套的括号，则{@code lists}中的元素有{@code List<Object>};一般有String
	 * 
	 * 出现了（     x) 这种情况很可能出错。。
	 */
	public Object readExpression() {		
		List<Object> tokens = new LinkedList<>();
		
		//用于处理(x)这种特殊情况
		if(hasLeftAndRightParenthese()) {
			removeLeftAndRightParenthese();
			tokens.add(nextToken);
			if(parentheseStack != 0)
				readToken();
			return tokens;
		}
		
		//只出现一个值，没有括号的情况
		if(!hasParenthese()) {
			return nextToken;
		}
		removeLeftParenthese();
		//保存当前层次
		int curLevel = parentheseStack;
		//多重左括号出现的情况
		//((((
		while(hasLeftParenthese() && curLevel == parentheseStack) {
			tokens.add(readExpression());
			//可能现在就已经读到右括号了
			if(hasRightParenthese()) {
				removeRightParenthese();
				//防止出现  ...10 ) 这样的情况
				if(!nextToken.equals("")) {
					tokens.add(nextToken);
				}
				if(parentheseStack != 0)
					readToken();
				return tokens;
			}
		}
		

		if(curLevel == parentheseStack) {
			//现在可以说没有左括号和右括号了
			tokens.add(nextToken);		
			readToken();	
		}
		
		//用于处理(lambda (x) (+ x 5))这种特殊情况
		if(hasLeftAndRightParenthese()) {
			removeLeftAndRightParenthese();
			LinkedList<Object> list = new LinkedList<>();
			list.add(nextToken);
			tokens.add(list);
			if(parentheseStack != 0)
				readToken();
		}
		
		
		while(!hasRightParenthese() && curLevel == parentheseStack) {
			if(hasLeftParenthese()) {
				tokens.add(readExpression());
			}else {
				tokens.add(nextToken);
				readToken();
			}
		}
		
		//用于处理(lambda (x) (+ x 5))这种特殊情况
		if(hasLeftAndRightParenthese()) {
			removeLeftAndRightParenthese();
			LinkedList<Object> list = new LinkedList<>();
			list.add(nextToken);
			tokens.add(list);
			if(parentheseStack != 0)
				readToken();
		}
		
		if(curLevel == parentheseStack) {
			removeRightParenthese();
			//防止出现  ...10 ) 这样的情况
			if(!nextToken.equals("")) {
				tokens.add(nextToken);
			}
			if(parentheseStack != 0)
				readToken();
		}
		return tokens;
	}

	public boolean hasNextExpression() {
		return scanner.hasNext();
	}
	
	//优雅算个屁，能动就行了
//	private boolean stopRead = false;
//	public void readExpression(List<Object> lists) {		
//		List<Object> tokens = new LinkedList<>();	
//		//只出现一个值，没有括号的情况
//		if(!hasParenthese()) {
//			tokens.add(nextToken);
//			lists.add(tokens);
//			return;
//		}	
//		int curLevel = parentheseStack;
//		removeLeftParenthese();
//		
//
//		//多重左括号出现的情况
//		//((((
//		while(hasLeftParenthese()) {
//			readExpression(tokens);
//			//可能现在就已经读到右括号了
//			if(hasRightParenthese()) {
//				removeRightParenthese();
//				//多重右括号出现的情况
//				//))))
//				if(hasRightParenthese()) {
//					if(!stopRead) {
//						nextToken = nextToken.substring(0,nextToken.indexOf(")"));
//						stopRead=true;
//					}
//					lists.add(tokens);
//					return;
//				}
//				//防止出现  ...10 ) 这样的情况
//				if(!nextToken.equals("") || stopRead) {
//					if(stopRead)stopRead=false;
//					tokens.add(nextToken);
//				}
//				//读到结尾就不读
//				if(parentheseStack != 0)
//					readToken();
//				lists.add(tokens);
//				return;
//			}
//			
//		}
//		tokens.add(nextToken);
//		readToken();		
//		while(!hasRightParenthese()) {
//			if(hasLeftParenthese()) {
//				readExpression(tokens);
//			}else {
//				tokens.add(nextToken);
//				readToken();
//			}
//		}		
//		removeRightParenthese();
//		//多重右括号出现的情况
//		//))))
//		if(hasRightParenthese()) {
//			lists.add(tokens);
//			return;
//		}
//		//防止出现  ...10 ) 这样的情况
//		//或者跳过多个右括号的不加
//		if(!nextToken.equals("") || parentheseStack == curLevel) {
//			tokens.add(nextToken);
//		}				
//		if(parentheseStack != 0)
//			readToken();
//		lists.add(tokens);
//	}

}
