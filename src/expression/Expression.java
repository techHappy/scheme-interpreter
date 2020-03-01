package expression;

import java.util.List;

import interpreter.Frame;

/**
 * 
 * @author 90946
 *
 *
 */
public abstract class Expression {

	public Expression() {
		// TODO Auto-generated constructor stub
	}
	
	public abstract Object eval(Frame env);

	public abstract void build(List<Object> lists);		
}
