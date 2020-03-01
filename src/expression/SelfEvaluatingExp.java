package expression;

import java.util.List;

import interpreter.Frame;

/**
 * 
 * @author 90946
 * selfEvaluatingExp�������Stringת��ΪFloat
 * ��˼���ʱ�Ĳ����Ѿ�ת������
 * ���ڼ������ֶ���Float��ʾ
 * ���ֲ�������ʶ��
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
	 * �Ƿ�������ֵ��
	 * ��������ֵ��ֻ��float
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
