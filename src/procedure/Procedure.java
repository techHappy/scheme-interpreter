package procedure;

import java.util.List;

import expression.Expression;
import interpreter.Frame;

public abstract class Procedure {
	
	protected List<Object> parameters;
	protected List<Expression> body;
	protected Frame enviroment;
	
	
	public Procedure() {
		// TODO Auto-generated constructor stub
	}

	public abstract Object proc(List<Object> arguments);

	public void setParameters(List<Object> parameters) {
		this.parameters = parameters;
	}

	public void setEnviroment(Frame enviroment) {
		this.enviroment = enviroment;
	}

	public Frame getEnviroment() {
		return enviroment;
	}

}
