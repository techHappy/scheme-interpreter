package expression;


import java.util.LinkedList;
import java.util.List;

import interpreter.Frame;
import interpreter.Evaluator;
import interpreter.ExpressionBuilder;

public class DefinitionExp extends Expression {
	
	private  String var;
	//
	private Expression value;

	@Override
	public Object eval(Frame env) {
		env.defineVariable(var, Evaluator.eval(value, env));
		return "ok";
	}

	@Override
	public void build(List<Object> lists) {
		// TODO Auto-generated method stub
		if(String.class.isInstance(lists.get(0))) {
			//(define <var> <value>)这种形式
			var = (String) lists.get(0);
			Object o = lists.get(1);
			value = ExpressionBuilder.buildExpression(o);
						
		}else {
			//(define (<var> <parameter1><parameter2>...) <body>)
			//<body>为一系列表达式,最后可能包含String
			//(define (square x)  (...) (...))这种形式
			List<Object> nameList = (List<Object>) lists.get(0);//(square x)
			lists.remove(0);//((...) (...))
			var = (String) nameList.get(0);//square
			nameList.remove(0);//x
			


			//这种形式是lambda的包装
			List<Expression> exps=new LinkedList<>();
			for(Object object:lists) {
				exps.add(ExpressionBuilder.buildExpression(object));
			}
			
			LambdaExp lambdaExp = new LambdaExp((List<Object>)nameList,exps);
			value=lambdaExp;
		}
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return var;
	}
}
