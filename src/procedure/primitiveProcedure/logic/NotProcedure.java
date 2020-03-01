package procedure.primitiveProcedure.logic;

import java.util.List;

import exception.ArgumentNumberException;
import procedure.PrimitiveProcedure;

public class NotProcedure extends PrimitiveProcedure {


	public NotProcedure() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Object proc(List<Object> arguments) {
		// TODO Auto-generated method stub
		try {
			checkArgumentNum(arguments, 1);
			return !((boolean)arguments.get(0));
		} catch (ArgumentNumberException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

}
