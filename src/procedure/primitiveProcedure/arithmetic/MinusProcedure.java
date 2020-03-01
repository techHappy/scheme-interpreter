package procedure.primitiveProcedure.arithmetic;

import java.util.List;

import procedure.PrimitiveProcedure;

public class MinusProcedure extends PrimitiveProcedure {


	public MinusProcedure() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Float proc(List<Object> arguments) {
		// TODO Auto-generated method stub
		
		if(arguments.size() == 1) {
			//单目
			return 0-(Float)arguments.get(0);
		}else {
			//多目
			Float result = (float) arguments.remove(0);		
			for(Object s : arguments) {
				Float arg = (Float)s;
				result -= arg;
			}
			return  result;
		}
	}

}
