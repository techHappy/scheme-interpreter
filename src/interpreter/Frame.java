package interpreter;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import exception.ArgumentDifferentLenException;
import exception.UnboundException;
import procedure.Procedure;
import procedure.primitiveProcedure.pair.ListProcedure;

public class Frame  {
		
		Frame pre=null;
		HashMap<Object,Object> map = new HashMap<>();
		
		public Frame() {
			// TODO Auto-generated constructor stub
		}
		

		@Override
		public String toString() {
			// TODO Auto-generated method stub
			Frame frame =this;
			int i=0;
			StringBuilder info =new StringBuilder();
			while(frame != null && frame != Evaluator.globalEnviroment) {
				//小心无意识的递归
				info.append(i+":\n");
				for(Object o : frame.map.keySet()) {
					info.append("\t"+o.toString()+" : ");
					Object value = frame.map.get(o);
					if(!Procedure.class.isInstance(value)) {
						info.append(value.toString());
					}else {
						info.append(value.getClass().getSimpleName());
					}
					info.append("\n");
				}
				i++;
				frame=frame.pre;
			}
			info.append(i+":global enviroment\n");
			return info.toString();
		}
	
	
	
	public  Object lookupVariableValue(String var) throws UnboundException {
		Frame frame =this;
		while(frame != null) {
			Object value = frame.map.get(var);
			if(value != null)
				return value;
			frame = frame.pre;
		}	
		
		throw new UnboundException(var);
	}
	
	/**
	 * 保证不改变vars和values
	 * @param vars
	 * @param values
	 * @return
	 * @throws ArgumentDifferentLenException
	 */
	public  Frame addFrame(List<Object> vars_,List<Object> values_) throws ArgumentDifferentLenException {
		Frame baseFrame =this;
		List<Object> vars=new LinkedList<>(vars_);
		List<Object> values=new LinkedList<>(values_);
		
		boolean hasPoint = false;
		if(vars.size()>=2) {
			//支持带点尾部记法
			String countdownSecond = (String) vars.get(vars.size()-2);
			if(countdownSecond.equals(".")) {
				hasPoint=true;
				vars.remove(vars.size()-2);
			}
		}		
		if(hasPoint) {
			if(vars.size()-1 > values.size()) {
				throw new ArgumentDifferentLenException(vars.size(),values.size());
			}
			
		}else {
			if(vars.size() != values.size())
				throw new ArgumentDifferentLenException(vars.size(),values.size());
		}
		
		
		Frame frame = new Frame();
		
		if(vars.size() > 0) {
			int n=vars.size()-1;
			for(int i=0;i<n;i++) {
				frame.map.put(vars.get(0), values.get(0));
				vars.remove(0);
				values.remove(0);
			}
			//支持带点尾部记法
			if(hasPoint) {
				ListProcedure listProcedure =new ListProcedure();
				frame.map.put(vars.get(0), listProcedure.proc(values));						
			}else {
				frame.map.put(vars.get(0), values.get(0));
			}
		}
		
		//加入环境
		frame.pre=baseFrame;
		

		return frame;
	}
	

	/**
	 * define variable in newest frame
	 * @param var
	 * @param value
	 */
	public  void defineVariable(String var,Object value) {
		this.map.put(var, value);
	}
	
	public  void setVariableValue(String var,Object value) throws UnboundException {
		Frame frame=this;
		while(frame != null) {
			if(frame.map.containsKey(var)) {	
				frame.map.put(var, value);
				return;
			}
			frame = frame.pre;
		}
		throw new UnboundException(var);
	}
}
