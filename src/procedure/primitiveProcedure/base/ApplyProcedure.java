package procedure.primitiveProcedure.base;

import java.util.LinkedList;
import java.util.List;

import exception.ArgumentNumberException;
import expression.Expression;
import interpreter.Evaluator;
import interpreter.ExpressionBuilder;
import interpreter.Frame;
import procedure.PrimitiveProcedure;
import procedure.Procedure;
import procedure.primitiveProcedure.dataStructrue.Nil;
import procedure.primitiveProcedure.dataStructrue.Node;

public class ApplyProcedure extends PrimitiveProcedure {

	public ApplyProcedure() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Object proc(List<Object> arguments) {
		// TODO Auto-generated method stub
		try {
			checkArgumentNum(arguments, 2);
			
			Procedure procedure = (Procedure)arguments.get(0);
			//假设是已经转化为Node的参数
			Node argNodes = (Node)arguments.get(1);
			
			List<Object> args = Node.nodeToList(argNodes);
//			List<Expression> argExps =new LinkedList<Expression>();
//			for(Object object : argList) {
//				argExps.add(ExpressionBuilder.buildExpression(object));
//			}
//			List<Object> args = Evaluator.evalAll(argExps, enviroment);
			Object result = Evaluator.apply(procedure, args);
			
			return result;
		} catch (ArgumentNumberException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Nil.NIL;
	}

}
