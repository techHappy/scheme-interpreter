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
	//���ֱ��ʽ
	private Expression name;
	//�ӱ��ʽ
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
		
		//�ӱ��ʽ��ֵ
		List<Object> arguments = new LinkedList<>();
		//����
		Procedure procedure = (Procedure) Evaluator.eval(name, env);
		
		if(procedure.getEnviroment() == null) {
			procedure.setEnviroment(env);
		}
		if(ApplyProcedure.class.isInstance(procedure)) {
			procedure.setEnviroment(env);
		}
		
//		if(PrimitiveProcedure.class.isInstance(procedure)) {
//			//procedure������Ϊ��������
//			procedure.setEnviroment(env);
//		}else {
//			//���Ϲ����ڴ�ʱ�����procedure
//			procedure = (Procedure) Evaluator.eval(name, env);
//		}
		
		if(DelayProcedure.class.isInstance(procedure)){
			//Ϊ (delay <exp>) �ṩ�ӳ�,���������
			//��ΪDelay��Ҫ�������ͱ��primitiveProduce��ͬ����״̬
			procedure = new DelayProcedure();
			procedure.setEnviroment(env);
			
//			for(Expression expression : argsExps) {
//				Object object = (Object) expression;
//				arguments.add(object);
//			}
			arguments.add(argsExps.get(0));
		}else if(ConsStreamProcedure.class.isInstance(procedure)) {
			//Ϊ  (cons-stream <a> <b>)�ṩ�ӳ�,������ڶ�������
			//��Ϊcons-stream��Ҫ�������ͱ��primitiveProduce��ͬ����״̬
			procedure = new ConsStreamProcedure();
			procedure.setEnviroment(env);

//			Object arg1 = Evaluator.eval(argsExps.get(0), env);
//			//Ϊʲôд����ô��֣���Ҳ��֪��������Ҳ�����ħ����԰�
//			arguments.clear();
//			arguments.add(arg1);
			arguments.add(Evaluator.eval(argsExps.get(0), env));
			arguments.add(argsExps.get(1));
		}else {
			//Ӧ�����ڴ�ʱ�Ѽ����arguments��ֵ
			arguments=Evaluator.listOfValues(argsExps, env);
		}
		
		
		
		Object result = Evaluator.apply(procedure,arguments);
		return result;
	}

	@Override
	public void build(List<Object> lists) {
		// TODO Auto-generated method stub
		//( <operator> <operand1> <operand2> ... <operandn>)
		
		//����operator
		//����Ϊ���Ż���ʽ
		Object name_ = lists.get(0);
//		if(String.class.isInstance(name_)) {
//			//����
//			if(Evaluator.primitiveProcedures.containsKey(name_)) {
//				//��������
//				procedure=Evaluator.primitiveProcedures.get(name_);
//			}else {
//				//��������
//				this.name = ExpressionBuilder.buildExpression(name_);
//			}
//		}else {
//			//���ʽ
//			this.name=ExpressionBuilder.buildExpression(name_);
//		}
		
		this.name = ExpressionBuilder.buildExpression(name_);
		
		//�ӱ��ʽoperands
		//�ӱ��ʽ���ܲ�����
		List<Object> argLists=new LinkedList<>();
		if(lists.size() >= 2) {
			argLists=lists.subList(1, lists.size());
		}
				
		//��object���expression
		//��Ϊ�ж�����ʽ
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
