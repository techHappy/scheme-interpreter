package interpreter;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import expression.Expression;
import logicProgramLanguage.QuerySyntaxProcessProcedure;
import procedure.PrimitiveProcedure;
import procedure.Procedure;
import procedure.primitiveProcedure.IO.DisplayLineProcedure;
import procedure.primitiveProcedure.IO.DisplayProcedure;
import procedure.primitiveProcedure.IO.ReadProcedure;
import procedure.primitiveProcedure.arithmetic.DivideProcedure;
import procedure.primitiveProcedure.arithmetic.MinusProcedure;
import procedure.primitiveProcedure.arithmetic.MultiplyProcedure;
import procedure.primitiveProcedure.arithmetic.PlusProcedure;
import procedure.primitiveProcedure.arithmetic.ReminderProcedure;
import procedure.primitiveProcedure.base.ApplyProcedure;
import procedure.primitiveProcedure.base.EvalProcedure;
import procedure.primitiveProcedure.dataStructrue.Nil;
import procedure.primitiveProcedure.logic.AndProcedure;
import procedure.primitiveProcedure.logic.GreaterEqualProcedure;
import procedure.primitiveProcedure.logic.GreaterProcedure;
import procedure.primitiveProcedure.logic.LessEqualProcedure;
import procedure.primitiveProcedure.logic.LessProcedure;
import procedure.primitiveProcedure.logic.NotProcedure;
import procedure.primitiveProcedure.logic.OrProcedure;
import procedure.primitiveProcedure.logic.EqualProcedure;
import procedure.primitiveProcedure.pair.CarProcedure;
import procedure.primitiveProcedure.pair.CdrProcedure;
import procedure.primitiveProcedure.pair.ConsProcedure;
import procedure.primitiveProcedure.pair.EqProcedure;
import procedure.primitiveProcedure.pair.List2StringProcedure;
import procedure.primitiveProcedure.pair.ListProcedure;
import procedure.primitiveProcedure.pair.NullProcedure;
import procedure.primitiveProcedure.pair.PairProcedure;
import procedure.primitiveProcedure.pair.SetCarProcedure;
import procedure.primitiveProcedure.pair.SetCdrProcedure;
import procedure.primitiveProcedure.predicate.IsSymbolProcdedure;
import procedure.primitiveProcedure.predicate.IsZeroProcedure;
import procedure.primitiveProcedure.stream.ConsStreamProcedure;
import procedure.primitiveProcedure.stream.DelayProcedure;

public final class Evaluator {

	//在此定义基本过程
	public static Map<String, PrimitiveProcedure> primitiveProcedures;
	static {
		primitiveProcedures = new HashMap<String, PrimitiveProcedure>();
		//arithmetic
		primitiveProcedures.put("+", new PlusProcedure());
		primitiveProcedures.put("-", new MinusProcedure());
		primitiveProcedures.put("*", new MultiplyProcedure());
		primitiveProcedures.put("/", new DivideProcedure());
		primitiveProcedures.put("reminder", new ReminderProcedure());
		//compare
		primitiveProcedures.put(">", new GreaterProcedure());
		primitiveProcedures.put("<", new LessProcedure());
		primitiveProcedures.put("=", new EqualProcedure());
		primitiveProcedures.put(">=", new GreaterEqualProcedure());
		primitiveProcedures.put("<=", new LessEqualProcedure());
		//logic
		primitiveProcedures.put("or", new OrProcedure());
		primitiveProcedures.put("and", new AndProcedure());
		primitiveProcedures.put("not", new NotProcedure());
		//pair
		primitiveProcedures.put("cons", new ConsProcedure());
		primitiveProcedures.put("car", new CarProcedure());
		primitiveProcedures.put("cdr", new CdrProcedure());
		primitiveProcedures.put("list", new ListProcedure());
		primitiveProcedures.put("null?", new NullProcedure());
		primitiveProcedures.put("pair?", new PairProcedure());
		primitiveProcedures.put("eq?", new EqProcedure());
		primitiveProcedures.put("set-car!",new SetCarProcedure());
		primitiveProcedures.put("set-cdr!",new SetCdrProcedure());
		primitiveProcedures.put("list2string",new List2StringProcedure());
		//stream
		primitiveProcedures.put("delay", new DelayProcedure());
		primitiveProcedures.put("cons-stream", new ConsStreamProcedure());
		//IO
		primitiveProcedures.put("read", new ReadProcedure());
		primitiveProcedures.put("display-line", new DisplayLineProcedure());
		primitiveProcedures.put("display", new DisplayProcedure());
		//predicate
		primitiveProcedures.put("symbol?", new IsSymbolProcdedure());
		primitiveProcedures.put("zero?", new IsZeroProcedure());
		//base
		primitiveProcedures.put("eval", new EvalProcedure());
		primitiveProcedures.put("apply", new ApplyProcedure());


		//logic programming
		//用该简易的scheme解释器不好处理
		//用java处理 
		primitiveProcedures.put("query-syntax-process", new QuerySyntaxProcessProcedure());
		
			
	}
	public static Frame globalEnviroment = new Frame();
	static {
		for(String var : primitiveProcedures.keySet()) {
			globalEnviroment.defineVariable(var, primitiveProcedures.get(var));
		}
		//在此定义常量
		globalEnviroment.defineVariable("true", new Boolean(true));
		globalEnviroment.defineVariable("false", new Boolean(false));
		globalEnviroment.defineVariable("nil", Nil.NIL);
		globalEnviroment.defineVariable("newline", "\n");
		globalEnviroment.defineVariable("user-initial-enviroment", globalEnviroment);
	}
	
	
	public static Object eval(Expression exp,Frame env) {
		return exp.eval(env);
	}

	public static Object apply(Procedure procedure,List<Object> arguments) {
		return procedure.proc(arguments);
	}
	
	public static Object evalSequence(List<Expression> exps,Frame env) {
		int n=exps.size();
		for(int i=0;i<n-1;i++) {
			exps.get(i).eval(env);
		}
		return exps.get(n-1).eval(env);
	}
	
	public static List<Object> evalAll(List<Expression> exps,Frame env) {
		List<Object> results=new LinkedList<>();
		for(Expression expression : exps) {
			results.add(expression.eval(env));
		}
		return results;
	}
	
	public static List<Object> listOfValues(List<Expression> exps,Frame env){
		List<Object> args = new LinkedList<>();
		for(Expression expression : exps) {
			args.add(expression.eval(env));
		}
		return args;
	}
}
