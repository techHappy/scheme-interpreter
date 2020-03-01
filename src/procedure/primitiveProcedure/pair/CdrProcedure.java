package procedure.primitiveProcedure.pair;

import java.util.List;

import exception.ArgumentNumberException;
import procedure.PrimitiveProcedure;
import procedure.primitiveProcedure.dataStructrue.Nil;
import procedure.primitiveProcedure.dataStructrue.Node;

public class CdrProcedure extends PrimitiveProcedure {


	public CdrProcedure() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Object proc(List<Object> arguments) {
		// TODO Auto-generated method stub
		try {
			checkArgumentNum(arguments, 1);
			return ((Node)arguments.get(0)).getCdr();
		} catch (ArgumentNumberException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Nil.NIL;
	}

}
