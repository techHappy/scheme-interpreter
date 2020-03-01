package procedure.primitiveProcedure.stream;

import java.util.LinkedList;
import java.util.List;

import exception.ArgumentNumberException;
import expression.Expression;
import procedure.PrimitiveProcedure;
import procedure.primitiveProcedure.dataStructrue.Nil;
import procedure.primitiveProcedure.pair.ConsProcedure;

public class ConsStreamProcedure extends PrimitiveProcedure {

	public ConsStreamProcedure() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Object proc(List<Object> arguments) {
		// TODO Auto-generated method stub
		try {
			checkArgumentNum(arguments, 2);
			
			ConsProcedure cons=new ConsProcedure();
			DelayProcedure delay=new DelayProcedure();
			delay.setEnviroment(enviroment);

			//第一个参数已经计算好
			//第二个参数延迟计算
			Object arg1=arguments.get(0);
			Expression arg2= (Expression) arguments.get(1);
			
			List<Object> consArguments = new LinkedList<>();
			List<Object> delayExp = new LinkedList<>();
			delayExp.add(arg2);
			consArguments.add(arg1);
			consArguments.add(delay.proc(delayExp));
			
			return cons.proc(consArguments);
		} catch (ArgumentNumberException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Nil.NIL;
	}

}
