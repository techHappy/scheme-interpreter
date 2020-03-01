package expression;

import java.util.List;

import exception.UnboundException;
import interpreter.Frame;
import interpreter.Evaluator;
import interpreter.ExpressionBuilder;

public class AssignmentExp extends Expression {

	private  String var;
	private  Expression value;
	

	@Override
	public Object eval(Frame env) {
		// TODO Auto-generated method stub
		try {
			env.setVariableValue(var, Evaluator.eval(value, env));
		} catch (UnboundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "ok";
	}

	@Override
	public void build(List<Object> lists) {
		// TODO Auto-generated method stub
		//(set! <name> <new-value>)
		//假设name为符号
		//假设new-value为符号或表达式
		var = (String) lists.get(0);
		
		Object value_ = lists.get(1);
		value = ExpressionBuilder.buildExpression(value_);
	}

}
