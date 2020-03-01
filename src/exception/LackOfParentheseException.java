package exception;

@SuppressWarnings("serial")
public class LackOfParentheseException extends Exception{
	
	public LackOfParentheseException(int lineNo,String parentheseType) {
		// TODO Auto-generated constructor stub
		super("在第"+lineNo+"行，缺少"+parentheseType);
	}

}
