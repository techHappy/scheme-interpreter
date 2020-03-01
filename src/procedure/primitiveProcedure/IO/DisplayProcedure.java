package procedure.primitiveProcedure.IO;

import java.util.List;

import procedure.PrimitiveProcedure;
import procedure.primitiveProcedure.dataStructrue.Nil;

public class DisplayProcedure extends PrimitiveProcedure {

	public DisplayProcedure() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Object proc(List<Object> arguments) {
		// TODO Auto-generated method stub
		for(Object object : arguments) {
			System.out.print(object);
		}
		return Nil.NIL;
	}

}
