package exception;

@SuppressWarnings("serial")
public class ArgumentNumberException extends Exception{
	
	public ArgumentNumberException(int need,int num) {
		// TODO Auto-generated constructor stub
		super("参数数量错误：need:"+need+",num:"+num);	
	}

}
