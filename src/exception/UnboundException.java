package exception;

@SuppressWarnings("serial")
public class UnboundException extends Exception{
	
	String unboundVar;
	public UnboundException(String unboundVar) {
		// TODO Auto-generated constructor stub
		super("δԼ��������"+unboundVar);
		this.unboundVar=unboundVar;		
	}

}
