package expression;

import java.util.List;

import interpreter.Frame;
import procedure.primitiveProcedure.dataStructrue.Nil;
import interpreter.Evaluator;
import interpreter.ExpressionBuilder;

public class IfExp extends Expression {

	private  Expression predicate;
	private  Expression consequent;
	private Expression alternative;
	

	@Override
	public Object eval(Frame env) {
		// TODO Auto-generated method stub
		Object object = Evaluator.eval(predicate, env);
		if(Boolean.class.isInstance(object)) {
			if((boolean)object) {
				return Evaluator.eval(consequent, env);
			}else {
				return Evaluator.eval(alternative, env);
			}
		}else {
			//如果不是boolean,则只要非null就是true
			if(object != Nil.NIL) {
				return Evaluator.eval(consequent, env);
			}else {
				return Evaluator.eval(alternative, env);
			}
		}
	}


	@Override
	public void build(List<Object> lists) {
		// TODO Auto-generated method stub
		//(if <predicate> <consequent> <alternative>)
		predicate = ExpressionBuilder.buildExpression(lists.get(0));
		consequent = ExpressionBuilder.buildExpression(lists.get(1));
		if(lists.size() == 3) {
			alternative = ExpressionBuilder.buildExpression(lists.get(2));
		}
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		StringBuilder info =new StringBuilder();
		info.append("predicate:"+predicate.toString()+"\n");
		info.append("consequent:"+consequent.toString()+"\n");
		if(alternative != null) {
			info.append("alternative:"+alternative.toString()+"\n");			
		}
		return info.toString();
	}
}
