package expression;

import java.util.List;

import exception.UnboundException;
import interpreter.Frame;

public class VariableExp extends Expression {

	private String symbol;
	
	public VariableExp() {
		// TODO Auto-generated constructor stub
	}
	
	public VariableExp(String symbol) {
		super();
		this.symbol = symbol;
	}

	@Override
	public Object eval(Frame env) {
		// TODO Auto-generated method stub
		try {
			return env.lookupVariableValue(symbol);
		} catch (UnboundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void build(List<Object> lists) {
		// TODO Auto-generated method stub
		symbol=(String) lists.get(0);
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return symbol;
	}
}
