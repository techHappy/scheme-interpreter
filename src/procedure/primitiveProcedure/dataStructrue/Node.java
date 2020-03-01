package procedure.primitiveProcedure.dataStructrue;

import java.util.LinkedList;
import java.util.List;

import expression.SelfEvaluatingExp;

public class Node {
	
	private  Object car;
	private  Object cdr;
				
	public Node(Object car, Object cdr) {
		super();
		this.car = car;
		this.cdr = cdr;
	}

	public Object getCar() {
		return car;
	}

	public Object getCdr() {
		return cdr;
	}	

	public void setCar(Object car) {
		this.car = car;
	}

	public void setCdr(Object cdr) {
		this.cdr = cdr;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return nodeToString(this).toString();
	}
	
	//Object 为String || List<Object>
	public static Object ListToNode(List<Object> list) {
		//处理 ()
		if(list.size() == 0) {
			return Nil.NIL;
		}
		Node start = new Node(Nil.NIL, Nil.NIL);
		Node p = start;
		Object first = list.get(0);
		if(List.class.isInstance(first)) {
			start.setCar(ListToNode((List<Object>) first));
		}else {
			start.setCar(first);
		}
		
		for(int i=1;i<list.size();i++) {
			Object object = list.get(i);
			if(List.class.isInstance(object)) {
				p.setCdr(new Node(ListToNode((List<Object>) object), Nil.NIL));
			}else {
				//将String转化为Float
				if(SelfEvaluatingExp.isSelfEvaluating(object)) {
					object = SelfEvaluatingExp.ValueOfSelfEvaluating(object);
				}
				p.setCdr(new Node(object, Nil.NIL));
			}
			p = (Node) p.getCdr();
		}
		return start;
	}
	
	//将Node转化为字符串
	public static StringBuilder nodeToString(Node node) {
		StringBuilder sb=new StringBuilder("(");
		Object t=node;
		//t is Node class
		while(Node.class.isInstance(t)) {
			Object car=((Node) t).getCar();
			Object cdr=((Node) t).getCdr();
			if(Node.class.isInstance(car)) {
				sb.append(nodeToString((Node)car));
			}else {
				sb.append(car.toString());
			}
			sb.append(" ");
			t = cdr;
		}
		//最后如果不是Nil.Nil就显示
		if(!Nil.class.isInstance(t)) {
			sb.append(t);
		}else {
			//去除最后的空格
			sb.deleteCharAt(sb.length()-1);
		}
		sb.append(")");
		return sb;
	}
	
	public static List<Object> nodeToList(Node node){
		List<Object> list = new LinkedList<Object>();
		Object t=node;
		//t is Node class
		while(Node.class.isInstance(t)) {
			Object car=((Node) t).getCar();
			Object cdr=((Node) t).getCdr();
			if(Node.class.isInstance(car)) {
				list.add(nodeToList((Node)car));
			}else {
				list.add(car);
			}
			t = cdr;
		}
		if(!Nil.class.isInstance(t)) {
			if(Node.class.isInstance(t)) {
				list.add(nodeToList((Node)t));
			}else {
				list.add(t);
			}
		}
		return list;
	}
	
//	public static Object listToNode(List<Object> list) {
//		//处理 ()
//		if(list.size() == 0) {
//			return Nil.NIL;
//		}
//		Node start = new Node(Nil.NIL, Nil.NIL);
//		Node p = start;
//		Object first = list.get(0);
//		if(List.class.isInstance(first)) {
//			start.setCar(expressionToNode((List<Object>) first));
//		}else {
//			start.setCar(first);
//		}
//		
//		for(int i=1;i<list.size();i++) {
//			Object object = list.get(i);
//			if(List.class.isInstance(object)) {
//				p.setCdr(new Node(expressionToNode((List<Object>) object), Nil.NIL));
//			}else {
//				p.setCdr(new Node(object, Nil.NIL));
//			}
//			p = (Node) p.getCdr();
//		}
//		return start;
//	}
}
