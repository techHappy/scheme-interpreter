package expression;

import java.util.LinkedList;
import java.util.List;

import interpreter.Frame;
import interpreter.Evaluator;
import interpreter.ExpressionBuilder;
import procedure.PrimitiveProcedure;
import procedure.Procedure;
import procedure.primitiveProcedure.base.ApplyProcedure;
import procedure.primitiveProcedure.stream.ConsStreamProcedure;
import procedure.primitiveProcedure.stream.DelayProcedure;

public class ApplicationExp extends Expression{
	
	private int id = 0;
	//名字表达式
	private Expression name;
	//子表达式
	private List<Expression> argsExps = new LinkedList<>();
	
	public ApplicationExp() {
		// TODO Auto-generated constructor stub
	}
	
	public void setName(Expression name) {
		this.name = name;
	}

	public void setArgsExps(List<Expression> argsExps) {
		this.argsExps = argsExps;
	}


	@Override
	public Object eval(Frame env) {
		// TODO Auto-generated method stub
		
		//子表达式的值
		List<Object> arguments = new LinkedList<>();
		//过程
		Procedure procedure = (Procedure) Evaluator.eval(name, env);
		
		if(procedure.getEnviroment() == null) {
			procedure.setEnviroment(env);
		}
		if(ApplyProcedure.class.isInstance(procedure)) {
			procedure.setEnviroment(env);
		}
		
//		if(PrimitiveProcedure.class.isInstance(procedure)) {
//			//procedure存在且为基本过程
//			procedure.setEnviroment(env);
//		}else {
//			//复合过程在此时计算出procedure
//			procedure = (Procedure) Evaluator.eval(name, env);
//		}
		
		if(DelayProcedure.class.isInstance(procedure)){
			//为 (delay <exp>) 提供延迟,不计算参数
			//因为Delay需要环境，和别的primitiveProduce不同，有状态
			procedure = new DelayProcedure();
			procedure.setEnviroment(env);
			
//			for(Expression expression : argsExps) {
//				Object object = (Object) expression;
//				arguments.add(object);
//			}
			arguments.add(argsExps.get(0));
		}else if(ConsStreamProcedure.class.isInstance(procedure)) {
			//为  (cons-stream <a> <b>)提供延迟,不计算第二个参数
			//因为cons-stream需要环境，和别的primitiveProduce不同，有状态
			procedure = new ConsStreamProcedure();
			procedure.setEnviroment(env);

//			Object arg1 = Evaluator.eval(argsExps.get(0), env);
//			//为什么写得这么奇怪，我也不知道。。这也许就是魔鬼调试吧
//			arguments.clear();
//			arguments.add(arg1);
			arguments.add(Evaluator.eval(argsExps.get(0), env));
			arguments.add(argsExps.get(1));
		}else {
			//应用序，在此时已计算出arguments的值
			arguments=Evaluator.listOfValues(argsExps, env);
		}
		
		
		
		Object result = Evaluator.apply(procedure,arguments);
		return result;
	}

	@Override
	public void build(List<Object> lists) {
		// TODO Auto-generated method stub
		//( <operator> <operand1> <operand2> ... <operandn>)
		
		//名字operator
		//可能为符号或表达式
		Object name_ = lists.get(0);
//		if(String.class.isInstance(name_)) {
//			//符号
//			if(Evaluator.primitiveProcedures.containsKey(name_)) {
//				//基本运算
//				procedure=Evaluator.primitiveProcedures.get(name_);
//			}else {
//				//复合运算
//				this.name = ExpressionBuilder.buildExpression(name_);
//			}
//		}else {
//			//表达式
//			this.name=ExpressionBuilder.buildExpression(name_);
//		}
		
		this.name = ExpressionBuilder.buildExpression(name_);
		
		//子表达式operands
		//子表达式可能不存在
		List<Object> argLists=new LinkedList<>();
		if(lists.size() >= 2) {
			argLists=lists.subList(1, lists.size());
		}
				
		//从object变成expression
		//因为有多个表达式
		argsExps = new LinkedList<>();				
		for(Object o:argLists) {
			argsExps.add(ExpressionBuilder.buildExpression(o));				
		}			
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return name.toString();
	}
}
