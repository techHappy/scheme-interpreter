package exception;

@SuppressWarnings("serial")
public class ArgumentNumberException extends Exception{
	
	public ArgumentNumberException(int need,int num) {
		// TODO Auto-generated constructor stub
		super("������������need:"+need+",num:"+num);	
	}

}
