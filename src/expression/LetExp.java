package expression;

import java.util.LinkedList;
import java.util.List;

import interpreter.Frame;
import procedure.Procedure;
import interpreter.Evaluator;
import interpreter.ExpressionBuilder;

public class LetExp extends Expression{

	private LambdaExp lambdaExp = new LambdaExp();
	private List<Expression> exps =new LinkedList<>();
	
	@Override
	public Object eval(Frame env) {
		// TODO Auto-generated method stub
		List<Object> args = Evaluator.evalAll(exps, env);
		Procedure procedure = (Procedure) Evaluator.eval(lambdaExp, env);
		return Evaluator.apply(procedure,args);
	}

	@Override
	public void build(List<Object> lists) {
		// TODO Auto-generated method stub
		//let������Ϊ������
		//(let ((<var1><exp1>)(..)) <body> )
		//����<body>Ϊ������ʽ
		//<var>Ϊ����
		//<exp>����Ϊ���Ż���ʽ
		
		List<Object> paras=new LinkedList<>();
		List<Object> pairs = (List<Object>) lists.get(0);
		for(Object pair:pairs) {
			List<Object> list=(List<Object>)pair;
			paras.add(list.get(0));
			exps.add(ExpressionBuilder.buildExpression(list.get(1)));
		}
		
		lists.remove(0);
		lists.add(0, paras);

		lambdaExp.build(lists);
	}

	
}
