package procedure.primitiveProcedure.arithmetic;

import java.util.List;

import procedure.PrimitiveProcedure;

public class PlusProcedure extends PrimitiveProcedure {

	public PlusProcedure() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Float proc(List<Object> arguments) {
		// TODO Auto-generated method stub
		Float result = (float) 0.0;		
		for(Object s : arguments) {
			Float arg = (Float)s;
			result += arg;
		}
		return  result;
	}

}
