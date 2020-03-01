package procedure.primitiveProcedure.arithmetic;

import java.util.List;

import exception.ArgumentNumberException;
import procedure.PrimitiveProcedure;

public class ReminderProcedure extends PrimitiveProcedure {


	public ReminderProcedure() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Float proc(List<Object> arguments) {
		// TODO Auto-generated method stub
		if(arguments.size() != 2) {
			try {
				throw new ArgumentNumberException(2,arguments.size());
			} catch (ArgumentNumberException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		int arg1 = ((Float)arguments.get(0)).intValue();
		int arg2 = ((Float)arguments.get(1)).intValue();
		return  (float) (arg1%arg2);
	}

}
