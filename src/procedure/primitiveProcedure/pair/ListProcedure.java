package procedure.primitiveProcedure.pair;

import java.util.List;

import procedure.PrimitiveProcedure;
import procedure.primitiveProcedure.dataStructrue.Nil;
import procedure.primitiveProcedure.dataStructrue.Node;

public class ListProcedure extends PrimitiveProcedure {


	public ListProcedure() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Node proc(List<Object> arguments) {
		// TODO Auto-generated method stub
		//假设可以没有argument
		if(arguments.size() == 0)
			return new Node(Nil.NIL, Nil.NIL);	
		Node start=new Node(arguments.get(0), Nil.NIL);
		Node p=start;
		for(int i=1;i<arguments.size();i++) {
			p.setCdr(new Node(arguments.get(i),Nil.NIL));
			p=(Node) p.getCdr();
		}
		return start;
	}

}
