package expression;

import java.util.LinkedList;
import java.util.List;

import interpreter.Frame;
import procedure.primitiveProcedure.dataStructrue.Node;
import procedure.primitiveProcedure.pair.ListProcedure;
/**
 * 表示字符串
 * @author 90946
 *
 */
public class QuoteExp extends Expression{

	//String || Node
	private  Object quotation;
	private List<Object> pacakgedArgs;
	
	@Override
	public Object eval(Frame env) {
		// TODO Auto-generated method stub
		if(quotation == null) {
			quotation = Node.ListToNode(pacakgedArgs);
		}
		return quotation;
	}

	@Override
	public void build(List<Object> lists) {
		// TODO Auto-generated method stub
		//(quote <string> <string>)
		//提供一种简写的方式
		//'string
		//'(str1 str2 ... strn) = (list str1 str2 ... strn)
		//"string string"
		Object arg0 = lists.get(0);
		if(String.class.isInstance(arg0)) {
			String text = (String)arg0;
			if(text.startsWith("\"") && text.endsWith("\"")) {
				//"hello world!"
				quotation = text.substring(1, text.length()-1);
			}else {
				quotation = text;
			}
		}else{
			//(quote hello world)
			pacakgedArgs = (List<Object>) arg0;
		}


	}

	public static boolean isQuote(Object o) {
		String string = (String)o;
		return string.startsWith("'") || (string.startsWith("\"") && string.endsWith("\""));
	}
	
}
