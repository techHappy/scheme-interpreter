package procedure;

import java.util.List;

import exception.ArgumentDifferentLenException;
import expression.Expression;
import interpreter.Evaluator;
import interpreter.Frame;

public class CompoundProcedure extends Procedure {


	public CompoundProcedure(List<Object> parameters, List<Expression> body, Frame enviroment) {
		// TODO Auto-generated constructor stub
		this.parameters=parameters;
		this.body=body;
		this.enviroment=enviroment;
	}

	@Override
	public Object proc(List<Object> arguments) {
		// TODO Auto-generated method stub
		try {
			//环境的改变
			Frame newEnviroment = enviroment.addFrame(parameters, arguments);
			Object result = Evaluator.evalSequence(body, newEnviroment);
			
			return result;
		} catch (ArgumentDifferentLenException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		StringBuilder info = new StringBuilder("\nCompoundProcedure:\n"+"\tparameter:");
		for(Object o:parameters) {
			info.append(o.toString());
			info.append(" ,");
		}
		info.deleteCharAt(info.length()-1);
		info.append("\n");
		
//		info.append("\tenviroment:\n");
//		info.append(enviroment.toString());
		return info.toString();
	}
}
