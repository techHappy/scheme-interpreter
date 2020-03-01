package interpreter;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import expression.ApplicationExp;
import expression.AssignmentExp;
import expression.BeginExp;
import expression.CondExp;
import expression.DefinitionExp;
import expression.Expression;
import expression.IfExp;
import expression.LambdaExp;
import expression.LetExp;
import expression.QuoteExp;
import expression.SelfEvaluatingExp;
import expression.VariableExp;

public class ExpressionBuilder {

	private static Map<String, Class<? extends Expression>> keyWords=
			new HashMap<String, Class<? extends Expression>>();
	
	static {
		keyWords.put("quote", QuoteExp.class);
		keyWords.put("set!", AssignmentExp.class);
		keyWords.put("define", DefinitionExp.class);
		keyWords.put("lambda", LambdaExp.class);
		keyWords.put("if", IfExp.class);
		keyWords.put("begin", BeginExp.class);	
		keyWords.put("cond", CondExp.class);
		keyWords.put("let", LetExp.class);
	}
	
	/**
	 * 假设获得的lists为这种形式：
	 *  keyword->...>...>    
	 *  ...可能为String或List<Object>
	 * @param lists
	 * @return
	 */
	public static Expression buildExpression(Object object) {
		
		//object为符号
		if(String.class.isInstance(object)) {
			List<Object> list=new LinkedList<>();
			list.add(object);
			if(SelfEvaluatingExp.isSelfEvaluating(object)) {
				//Number
				Expression expression = new SelfEvaluatingExp();
				expression.build(list);
				return expression;
			}else if(QuoteExp.isQuote(object)) {
				//String
				Expression expression = new QuoteExp();
				expression.build(list);
				return expression;
			}
			else {
				//Variable
				Expression expression = new VariableExp();
				expression.build(list);
				return expression;
			}
		}
		
		//object为表达式
		@SuppressWarnings("unchecked")
		List<Object> lists = (List<Object>)object;
		
		Class<? extends Expression> expClass = keyWords.get(lists.get(0));
		
		if(expClass != null) {
			//如果是keywords中的，去除标识符
			lists.remove(0);
			try {
				Expression expression = expClass.newInstance();
				expression.build(lists);
				return expression;
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else {
			//无法识别的标识符即为过程
			ApplicationExp applicationExp = new ApplicationExp();
			applicationExp.build(lists);
			return applicationExp;
		}
		
		return null;
	}
	
	
}
