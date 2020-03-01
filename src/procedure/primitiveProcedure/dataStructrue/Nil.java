package procedure.primitiveProcedure.dataStructrue;

import java.util.List;

import procedure.PrimitiveProcedure;

/**
 * Nil 为 PrimitiveProcedure，是为了(force nil)的值为nil
 * @author 90946
 *
 */
public final class Nil extends PrimitiveProcedure{
	private Nil() {		
		
	}
	public final static Nil NIL = new Nil();
	
	
	@Override
	public Object proc(List<Object> arguments) {
		return Nil.NIL;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "nil";
	}
}