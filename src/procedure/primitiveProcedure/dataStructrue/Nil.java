package procedure.primitiveProcedure.dataStructrue;

import java.util.List;

import procedure.PrimitiveProcedure;

/**
 * Nil Ϊ PrimitiveProcedure����Ϊ��(force nil)��ֵΪnil
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