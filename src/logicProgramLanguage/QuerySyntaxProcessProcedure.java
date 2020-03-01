package logicProgramLanguage;

import java.util.LinkedList;
import java.util.List;

import expression.SelfEvaluatingExp;
import procedure.PrimitiveProcedure;
import procedure.primitiveProcedure.dataStructrue.Nil;
import procedure.primitiveProcedure.dataStructrue.Node;
import procedure.primitiveProcedure.pair.ListProcedure;

public class QuerySyntaxProcessProcedure extends PrimitiveProcedure {

	private ListProcedure listProcedure = new ListProcedure();
	
	public QuerySyntaxProcessProcedure() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Object proc(List<Object> arguments) {
		// TODO Auto-generated method stub
		return mapOverSymbols(arguments.get(0));
	}

	//��(job ?x ?y) ���� (job (? x) (? y))
	//˳��ѱ�ʾ��ʽ��List<Object>�����Զ����Node
	private Object mapOverSymbols(Object exp) {
		if(String.class.isInstance(exp)) {
			String symbol= (String)exp;
			if(symbol.startsWith("?")) {
				List<Object> list=new LinkedList<>();
				list.add("?");
				list.add(symbol.substring(1));
				return listProcedure.proc(list);
			}else {
				if(SelfEvaluatingExp.isSelfEvaluating(symbol)) {
					return SelfEvaluatingExp.ValueOfSelfEvaluating(symbol);
				}
				return symbol;
			}
		}else if(List.class.isInstance(exp)){
			List<Object> exps = (List<Object>)exp;
			if(exps.size() == 0) {
//				throw new RuntimeException("���ʽ�ﲻ�����κζ�����");
				return Nil.NIL;
			}
			Node start=new Node(mapOverSymbols(exps.get(0)), Nil.NIL);
			Node p = start;
			for(int i=1;i<exps.size();i++) {
				Object object = exps.get(i);
				//�������β����ģʽ
				if(String.class.isInstance(object)) {
					if(object.equals(".")) {
						Object last = exps.get(i+1);
						if(Node.class.isInstance(last)) {
							throw new RuntimeException("����β����ģʽ�������Node!");
						}
						p.setCdr(mapOverSymbols(last));
						return start;
					}
				}
				p.setCdr(new Node(mapOverSymbols(object), Nil.NIL));
				p = (Node) p.getCdr();
			}
			return start;
		}else {
			//���������е�����
			return exp;
		}
	}
}
