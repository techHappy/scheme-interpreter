package expression;

import java.util.LinkedList;
import java.util.List;

import interpreter.Frame;
import interpreter.ExpressionBuilder;
import procedure.*;

public class LambdaExp extends Expression{

	//lambda����Ϊ�ַ�����
	private List<Object> parameters;
	//lambda��Ϊ���ʽ
	private List<Expression> body;
	
	public LambdaExp() {
		// TODO Auto-generated constructor stub
	}
		
	public LambdaExp(List<Object> parameters, List<Expression> body) {
		super();
		this.parameters = parameters;
		this.body = body;
	}

	@Override
	public Procedure eval(Frame env) {
		// TODO Auto-generated method stub
		return new CompoundProcedure(parameters,body,env);
	}

	@Override
	public void build(List<Object> lists) {
		// TODO Auto-generated method stub
		//������������
		//(lambda (<var1><var2>...) (...) (...) )
		parameters = (List<Object>) lists.get(0);

		lists.remove(0);
		
		body=new LinkedList<>();
		for(Object object : lists) {
			Expression list=ExpressionBuilder.buildExpression(object);
			body.add(list);
		}
	}


}
