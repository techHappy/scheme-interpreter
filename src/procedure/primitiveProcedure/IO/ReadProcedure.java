package procedure.primitiveProcedure.IO;

import java.util.List;

import interpreter.ExpressionReader;
import procedure.PrimitiveProcedure;

public class ReadProcedure extends PrimitiveProcedure {

	private static ExpressionReader reader = interpreter.Interpreter.reader;
	
	public ReadProcedure() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Object proc(List<Object> arguments) {
		// TODO Auto-generated method stub
		return reader.readExpression();		
	}

}
