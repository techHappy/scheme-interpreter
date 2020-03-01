package procedure.primitiveProcedure.stream;

import java.util.LinkedList;
import java.util.List;

import exception.ArgumentNumberException;
import expression.ApplicationExp;
import expression.Expression;
import expression.LambdaExp;
import expression.VariableExp;
import procedure.PrimitiveProcedure;
import procedure.primitiveProcedure.dataStructrue.Nil;

public class DelayProcedure extends PrimitiveProcedure {

	public DelayProcedure() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Object proc(List<Object> arguments) {
		// TODO Auto-generated method stub
		//(delay <exp>) �ȼ���(memo-proc (lambda () <exp>))
		try {
			checkArgumentNum(arguments, 1);
			
			Expression expression = (Expression) arguments.get(0);
			List<Object> parameters = new LinkedList<>();
			List<Expression> body = new LinkedList<>();
			body.add(expression);
			
			LambdaExp lambdaExp = new LambdaExp(parameters,body);
			//����
			//�ṩ���䣬��ĳ�̶ֳ��Ͽ������Ч��
			//��֤memo-proc�⺯���Ĵ���
			ApplicationExp memoAppExp = new ApplicationExp();
			VariableExp name = new VariableExp("memo-proc");
			List<Expression> argsExps = new LinkedList<>();			
			argsExps.add(lambdaExp);
			
			memoAppExp.setName(name);
			memoAppExp.setArgsExps(argsExps);
			
			return memoAppExp.eval(enviroment);
		} catch (ArgumentNumberException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Nil.NIL;
		
	}

}
