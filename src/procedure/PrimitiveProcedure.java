package procedure;

import java.util.List;

import exception.ArgumentNumberException;

public abstract class PrimitiveProcedure extends Procedure {

	
	public PrimitiveProcedure() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public abstract Object proc(List<Object> arguments);
	
	protected void checkArgumentNum(List<Object> arguments,int need) throws ArgumentNumberException {
		int num=arguments.size();
		if(need != num) {
			throw new ArgumentNumberException(need, num);
		}
	}
}
