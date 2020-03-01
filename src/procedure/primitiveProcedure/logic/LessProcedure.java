package procedure.primitiveProcedure.logic;

import java.util.List;

import procedure.PrimitiveProcedure;

public class LessProcedure extends PrimitiveProcedure {


	public LessProcedure() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Boolean proc(List<Object> arguments) {
		// TODO Auto-generated method stub
		Float arg1 = (Float) arguments.get(0);
		Float arg2 = (Float) arguments.get(1);
		int result=Float.compare(arg1, arg2);
		if(result < 0) {
			return true;
		}
		return false;
	}

}
