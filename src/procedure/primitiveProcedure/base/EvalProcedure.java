package procedure.primitiveProcedure.base;

import java.util.LinkedList;
import java.util.List;

import exception.ArgumentNumberException;
import expression.Expression;
import expression.VariableExp;
import interpreter.Evaluator;
import interpreter.ExpressionBuilder;
import interpreter.Frame;
import procedure.PrimitiveProcedure;
import procedure.primitiveProcedure.dataStructrue.Nil;
import procedure.primitiveProcedure.dataStructrue.Node;

public class EvalProcedure extends PrimitiveProcedure {

	public EvalProcedure() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Object proc(List<Object> arguments) {
		// TODO Auto-generated method stub
		try {
			checkArgumentNum(arguments, 2);
			
			Object arg1 = arguments.get(0);
			Expression expression;
			if(Node.class.isInstance(arg1)) {				
				//假设是已经转化为Node的表达式
				Node exp = (Node)arg1;
				List<Object> expList = Node.nodeToList(exp);
				expression = ExpressionBuilder.buildExpression(expList);
			}else if(String.class.isInstance(arg1)) {
				expression = new VariableExp();
				List<Object> list = new LinkedList<Object>();
				list.add(arg1);
				expression.build(list);
			}else {
				System.err.println("Unknow arg in eval");
				throw new RuntimeException();
			}

			Frame env = (Frame)arguments.get(1);
		
			Object result = Evaluator.eval(expression, env);
			
			return result;
		} catch (ArgumentNumberException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Nil.NIL;
	}

}
