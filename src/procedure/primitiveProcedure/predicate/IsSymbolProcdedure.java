package procedure.primitiveProcedure.predicate;

import java.util.List;

import exception.ArgumentDifferentLenException;
import procedure.PrimitiveProcedure;

public class IsSymbolProcdedure extends PrimitiveProcedure {

	public IsSymbolProcdedure() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Object proc(List<Object> arguments) {
		// TODO Auto-generated method stub
		if(arguments.size() != 1) {
			try {
				throw new ArgumentDifferentLenException(1, arguments.size());
			} catch (ArgumentDifferentLenException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
		}
		Object symbol = arguments.get(0);
		return String.class.isInstance(symbol);
	}

}
