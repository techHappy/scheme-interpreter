package exception;

@SuppressWarnings("serial")
public class UnboundException extends Exception{
	
	String unboundVar;
	public UnboundException(String unboundVar) {
		// TODO Auto-generated constructor stub
		super("Î´Ô¼Êø±äÁ¿£º"+unboundVar);
		this.unboundVar=unboundVar;		
	}

}
