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
	 * ��ȡһ��token����{@code nextToken}
	 */
	//���ܶ�ȡ��(display ":::Query input:"))����������
	//"��)֮������һ��
	//δ�������������ϣ��������
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
	 * �Ƿ���������
	 * @return
	 */
	private boolean hasRightParenthese() {
		return nextToken.endsWith(")");
	}
	
	/**
	 * �Ƿ���������
	 * @return
	 */
	private boolean hasLeftParenthese() {
		return nextToken.startsWith("(");
	}
	
	/**
	 * ȥ��������
	 *����Ϊ ...10 )
	 * ���ź�token֮������п�϶
	 */
	private void removeRightParenthese() {							
		if(nextToken.endsWith(")")){
			nextToken=nextToken.substring(0, nextToken.length()-1).trim();
			parentheseStack--;
			if(parentheseStack<0) {
				parentheseStack=0;
				throw new RuntimeException("���Ų�ƥ�䣡");
			}
//			//���������ų��ֵ����
//			//))))
			removeRightParenthese();
		}						
	}
	
	/**
	 * ȥ��������
	 * ����Ϊ ( define ...
	 * ���ź�token֮������п�϶
	 */
	private void removeLeftParenthese() {
		if(nextToken.startsWith("(")) {
			nextToken=nextToken.substring(1, nextToken.length()).trim();
			parentheseStack++;
			
			//��ֹ����( define ���������
			if(nextToken.equals(""))
				readToken();

		}
	}
	
	private boolean hasParenthese() {
		//ȥ����ͷ�Ŀհ�
		nextToken = nextToken.trim();
		return nextToken.startsWith("(") || nextToken.endsWith(")");
	}
	public void startRead() {
		readToken();
	}
	
	/**
	 * nextTokenΪ������ (x)
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
	 * ���ã��Ѹ���token����{@code lists}��
	 * 
	 * ע�⣺����Ҫ��ǰ����{@code readToken()}
	 * 
	 * �����Ƕ�׵����ţ���{@code lists}�е�Ԫ����{@code List<Object>};һ����String
	 * 
	 * �����ˣ�     x) ��������ܿ��ܳ�����
	 */
	public Object readExpression() {		
		List<Object> tokens = new LinkedList<>();
		
		//���ڴ���(x)�����������
		if(hasLeftAndRightParenthese()) {
			removeLeftAndRightParenthese();
			tokens.add(nextToken);
			if(parentheseStack != 0)
				readToken();
			return tokens;
		}
		
		//ֻ����һ��ֵ��û�����ŵ����
		if(!hasParenthese()) {
			return nextToken;
		}
		removeLeftParenthese();
		//���浱ǰ���
		int curLevel = parentheseStack;
		//���������ų��ֵ����
		//((((
		while(hasLeftParenthese() && curLevel == parentheseStack) {
			tokens.add(readExpression());
			//�������ھ��Ѿ�������������
			if(hasRightParenthese()) {
				removeRightParenthese();
				//��ֹ����  ...10 ) ���������
				if(!nextToken.equals("")) {
					tokens.add(nextToken);
				}
				if(parentheseStack != 0)
					readToken();
				return tokens;
			}
		}
		

		if(curLevel == parentheseStack) {
			//���ڿ���˵û�������ź���������
			tokens.add(nextToken);		
			readToken();	
		}
		
		//���ڴ���(lambda (x) (+ x 5))�����������
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
		
		//���ڴ���(lambda (x) (+ x 5))�����������
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
			//��ֹ����  ...10 ) ���������
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
	
	//�������ƨ���ܶ�������
//	private boolean stopRead = false;
//	public void readExpression(List<Object> lists) {		
//		List<Object> tokens = new LinkedList<>();	
//		//ֻ����һ��ֵ��û�����ŵ����
//		if(!hasParenthese()) {
//			tokens.add(nextToken);
//			lists.add(tokens);
//			return;
//		}	
//		int curLevel = parentheseStack;
//		removeLeftParenthese();
//		
//
//		//���������ų��ֵ����
//		//((((
//		while(hasLeftParenthese()) {
//			readExpression(tokens);
//			//�������ھ��Ѿ�������������
//			if(hasRightParenthese()) {
//				removeRightParenthese();
//				//���������ų��ֵ����
//				//))))
//				if(hasRightParenthese()) {
//					if(!stopRead) {
//						nextToken = nextToken.substring(0,nextToken.indexOf(")"));
//						stopRead=true;
//					}
//					lists.add(tokens);
//					return;
//				}
//				//��ֹ����  ...10 ) ���������
//				if(!nextToken.equals("") || stopRead) {
//					if(stopRead)stopRead=false;
//					tokens.add(nextToken);
//				}
//				//������β�Ͳ���
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
//		//���������ų��ֵ����
//		//))))
//		if(hasRightParenthese()) {
//			lists.add(tokens);
//			return;
//		}
//		//��ֹ����  ...10 ) ���������
//		//����������������ŵĲ���
//		if(!nextToken.equals("") || parentheseStack == curLevel) {
//			tokens.add(nextToken);
//		}				
//		if(parentheseStack != 0)
//			readToken();
//		lists.add(tokens);
//	}

}
