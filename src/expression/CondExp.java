package expression;

import java.util.LinkedList;
import java.util.List;

import interpreter.Frame;
import interpreter.Evaluator;
import interpreter.ExpressionBuilder;
import procedure.primitiveProcedure.dataStructrue.Nil;

public class CondExp extends Expression{
	
	
	static class Clause{
		private  Expression predicate;
		private  List<Expression> actions = new LinkedList<>();
		public Clause(Expression predicate,List<Expression> actions) {
			// TODO Auto-generated constructor stub
			this.predicate=predicate;
			this.actions=actions;
		}
		public Clause() {
			// TODO Auto-generated constructor stub
		}
	}
	
	LinkedList<Clause> clauses = new LinkedList<>();

	@Override
	public Object eval(Frame env) {
		// TODO Auto-generated method stub
		for(Clause clause:clauses) {
			Boolean result = null;
			Object object = Evaluator.eval(clause.predicate, env);
			if(Boolean.class.isInstance(object)) {
				result = (boolean)object;
			}
			else {
				if(object == Nil.NIL) {
					result = false;
				}else {
					result = true;
				}
			}
			if(result) {
				return Evaluator.evalSequence(clause.actions, env);
			}
		}
		//��û�У��ͷ��ؿն���
		return Nil.NIL;
	}

	@Override
	public void build(List<Object> lists) {
		// TODO Auto-generated method stub
		//(cond (<predicate1> <action1>) (<predicate2> <action2>) ... (else <action>))
		int n=lists.size();
		for(int i=0;i<n-1;i++) {
			List<Object> list= (List<Object>) lists.get(i);
			Clause clause = new Clause();
			clause.predicate=ExpressionBuilder.buildExpression(list.get(0));
			for(int j=1;j<list.size();j++) {
				clause.actions.add(ExpressionBuilder.buildExpression(list.get(j)));			
			}
			clauses.add(clause);
		}
		// ���⴦�����һ������Ϊ������else
		List<Object> last= (List<Object>) lists.get(n-1);
		Clause clause = new Clause();
		Object pre = last.get(0);
		if(String.class.isInstance(pre)) {
			if(((String)pre).equals("else")) {
				//else�ܲ�������
				clause.predicate=new VariableExp("true");
			}else {
				throw new RuntimeException("cond���Ĺؼ��ֲ���else��");
			}
		}else {
			clause.predicate=ExpressionBuilder.buildExpression(pre);
		}
		for(int j=1;j<last.size();j++) {
			clause.actions.add(ExpressionBuilder.buildExpression(last.get(j)));			
		}
		clauses.add(clause);
	}


}
