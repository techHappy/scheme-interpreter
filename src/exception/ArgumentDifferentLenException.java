package exception;

@SuppressWarnings("serial")
public class ArgumentDifferentLenException extends Exception{
	
	public ArgumentDifferentLenException(int paraLen1,int argLen2) {
		// TODO Auto-generated constructor stub
		super("实际参数和形式参数数量不相同:形式：" + paraLen1+"实际,："+argLen2);
	}

}
