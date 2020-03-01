package expression;

import java.util.LinkedList;
import java.util.List;

import interpreter.Frame;
import interpreter.Evaluator;
import interpreter.ExpressionBuilder;

public class BeginExp extends Expression{
	
	LinkedList<Expression> exps;

	@Override
	public Object eval(Frame env) {
		// TODO Auto-generated method stub
		return Evaluator.evalSequence(exps, env);
	}

	@Override
	public void build(List<Object> lists) {
		// TODO Auto-generated method stub
		//(begin <exp1> <exp2> ... <expn>)
		exps=new LinkedList<>();
		for(Object o : lists) {
			exps.add(ExpressionBuilder.buildExpression(o));
		}
	}


}
