package exception;

@SuppressWarnings("serial")
public class UnknownLetterException extends Exception{
	
	public UnknownLetterException(int lineNo,char letter) {
		// TODO Auto-generated constructor stub
		super("�ڵ�"+lineNo+"�У�δ֪�ķ���"+letter);
	}

}
