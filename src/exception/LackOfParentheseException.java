package exception;

@SuppressWarnings("serial")
public class LackOfParentheseException extends Exception{
	
	public LackOfParentheseException(int lineNo,String parentheseType) {
		// TODO Auto-generated constructor stub
		super("�ڵ�"+lineNo+"�У�ȱ��"+parentheseType);
	}

}
