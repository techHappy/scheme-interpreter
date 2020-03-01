package exception;

@SuppressWarnings("serial")
public class UnknownLetterException extends Exception{
	
	public UnknownLetterException(int lineNo,char letter) {
		// TODO Auto-generated constructor stub
		super("在第"+lineNo+"行，未知的符号"+letter);
	}

}
