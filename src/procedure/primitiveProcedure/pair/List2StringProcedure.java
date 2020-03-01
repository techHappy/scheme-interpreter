package procedure.primitiveProcedure.pair;

import java.util.List;

import exception.ArgumentNumberException;
import procedure.PrimitiveProcedure;
import procedure.primitiveProcedure.dataStructrue.Nil;
import procedure.primitiveProcedure.dataStructrue.Node;

public class List2StringProcedure extends PrimitiveProcedure {


	public List2StringProcedure() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Object proc(List<Object> arguments) {
		// TODO Auto-generated method stub
		try {
			checkArgumentNum(arguments, 1);
			StringBuilder sb = new StringBuilder();
			
			Object p = arguments.get(0);
			while(p != Nil.NIL) {
				Node node = (Node)p;
				Object val = node.getCar();
				if(Float.class.isInstance(val)) {
					Float f = (Float)val;
					sb.append(f.intValue());
				}
				else {
					sb.append(String.valueOf(val));
				}
				p = node.getCdr();
			}
			
			return sb.toString();
		} catch (ArgumentNumberException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Nil.NIL;
	}

}
