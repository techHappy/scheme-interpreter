package exception;

@SuppressWarnings("serial")
public class ArgumentDifferentLenException extends Exception{
	
	public ArgumentDifferentLenException(int paraLen1,int argLen2) {
		// TODO Auto-generated constructor stub
		super("ʵ�ʲ�������ʽ������������ͬ:��ʽ��" + paraLen1+"ʵ��,��"+argLen2);
	}

}
