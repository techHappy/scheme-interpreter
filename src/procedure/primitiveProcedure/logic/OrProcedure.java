package procedure.primitiveProcedure.logic;

import java.util.List;

import procedure.PrimitiveProcedure;

public class OrProcedure extends PrimitiveProcedure {


	public OrProcedure() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Object proc(List<Object> arguments) {
		// TODO Auto-generated method stub
		for(Object object:arguments) {
			if((boolean) object) {
				return true;
			}
		}
		return false;
	}

}
