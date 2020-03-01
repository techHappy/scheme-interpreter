package expression;

import java.util.List;

import interpreter.Frame;

/**
 * 
 * @author 90946
 * selfEvaluatingExp会把数字String转化为Float
 * 因此计算时的参数已经转化好了
 * 现在假设数字都由Float表示
 * 数字不能做标识符
 */
public class SelfEvaluatingExp extends Expression {
	
	Object object;
	
	public SelfEvaluatingExp() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Object eval(Frame env) {
		return object;	
	}

	@Override
	public void build(List<Object> lists) {
		// TODO Auto-generated method stub
		
		String token = (String) lists.get(0);
		try {
			object = Float.parseFloat(token);
		} catch (NumberFormatException e) {
			// TODO: handle exception
			
		}
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return object.toString();
	}
	
	/**
	 * 是否是自求值的
	 * 现在自求值的只有float
	 * @param o
	 * @return
	 */
	public static boolean isSelfEvaluating(Object o) {
		try {
			Float.parseFloat((String) o);
			return true;
		} catch (NumberFormatException e) {
			// TODO: handle exception
			return false;
		}
	}
	
	public static Number ValueOfSelfEvaluating(Object o) {
		if(isSelfEvaluating(o)) {
			return Float.parseFloat((String) o);
		}
		throw new RuntimeException(o + " is not selfEvaluating Expression!");
	}
}
