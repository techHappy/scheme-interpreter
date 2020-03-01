package procedure.primitiveProcedure.pair;

import java.util.List;

import exception.ArgumentNumberException;
import procedure.PrimitiveProcedure;
import procedure.primitiveProcedure.dataStructrue.Nil;
import procedure.primitiveProcedure.dataStructrue.Node;

public class PairProcedure extends PrimitiveProcedure {


	public PairProcedure() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Object proc(List<Object> arguments) {
		// TODO Auto-generated method stub
		try {
			checkArgumentNum(arguments, 1);
			return Node.class.isInstance(arguments.get(0));
		} catch (ArgumentNumberException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Nil.NIL;
	}

	public static boolean isPair(Object object) {
		return Node.class.isInstance(object); 
	}
}
