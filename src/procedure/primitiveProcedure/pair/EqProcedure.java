package procedure.primitiveProcedure.pair;

import java.util.List;

import exception.ArgumentNumberException;
import procedure.PrimitiveProcedure;

//字符串值相等的
//数字值相等
//Pair同一个对象
//其他没覆盖equals()的引用相等
public class EqProcedure extends PrimitiveProcedure {

	public EqProcedure() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Boolean proc(List<Object> arguments) {
		// TODO Auto-generated method stub
		try {
			checkArgumentNum(arguments, 2);
			Object arg1 =  arguments.get(0);
			Object arg2 =  arguments.get(1);
			return arg1.equals(arg2);
		} catch (ArgumentNumberException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;
	}

}
