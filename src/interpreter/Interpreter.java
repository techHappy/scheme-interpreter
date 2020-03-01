package interpreter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.LineNumberReader;
import java.io.PrintStream;
import java.util.Scanner;

import expression.Expression;

public class Interpreter {
	
	private PrintStream  out =  System.out;
	public static ExpressionReader reader = new ExpressionReader();
	private void promptForInput() {
		out.println(";;;input:");
	}
	
	private void announceOutput(Object result) {
		if(Number.class.isInstance(result)) {
			//保留两位小数，以避免浮点运算造成较大误差
			Number number=(Number)result;
			out.format(";;;value:%.2f", (float)number.floatValue());
		}else {
			out.println(";;;value:" + result);
		}
		out.println();
	}

	public void driverLoop() {
		while(true) {
			promptForInput();
			Object result = Evaluator.eval(ExpressionBuilder.buildExpression(reader.read())
					, Evaluator.globalEnviroment);
			announceOutput(result);
		}
	}
	
	private void readLib(String path) {
		try {
			reader.setReader(new LineNumberReader(new FileReader(path)));
			while(reader.hasNextExpression()) {
				Object expression = reader.readExpression();
				Evaluator.eval(ExpressionBuilder.buildExpression(expression)
						, Evaluator.globalEnviroment);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			reader.resetReader();
		}
	}

	private void readLibs() {
		File file = new File(System.getProperty("user.dir"),"\\lib");
		for(String path : file.list()) {
			path = file+"\\"+path;
			File lib = new File(path);
			try {
				reader.setReader(new LineNumberReader(new FileReader(lib)));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			while(reader.hasNextExpression()) {
				Object expression = reader.readExpression();
				Evaluator.eval(ExpressionBuilder.buildExpression(expression)
						, Evaluator.globalEnviroment);
			}
		}
		reader.resetReader();
	}
	public static void main(String[] args) {
		Interpreter interpreter = new Interpreter();

		interpreter.readLibs();
		interpreter.readLib(System.getProperty("user.dir")+"\\logic_program\\logicProgram.txt");
		interpreter.readLib(System.getProperty("user.dir")+"\\logic_program\\queryDatabase.txt");
		interpreter.readLib(System.getProperty("user.dir")+"\\machine\\machine.txt");
		interpreter.readLib(System.getProperty("user.dir")+"\\lambda\\lambda.txt");
		interpreter.driverLoop();
	}

}

/*
for test:

( + 2 5)
(+ 2 5 )
value:7

(+ 2 (+ 3 5 ) )
value:10

(+ (+ (* 3 5) (- 10 6)) (- 10 2))
( + (  + ( * 3 5) (        - 10 6)) ( - 10 2))
( +  ( + (* 3  5 ) (-  10 6) ) (- 10    2 ) )  
value:27

(define x 5)
value:ok
(+ x 5)

(define pi 3.14159)
(define radius 10)
(* pi (* radius radius))
;value:314.159
(define circumference (* 2 pi radius))
;value:62.831802

(define (square x) (* x x))
(define (sum-of-squares x y)
   (+ (square x) (square y)))
(define (f a) (sum-of-squares (+ a 1) (* a 2)))
(square 21)
(square (+ 2 5))
(square (square 3))

(define (abs x) (cond ((> x 0) x) ((= x 0) 0) ((< x 0) (- x))))
(define (abs x) (cond ((< x 0) (- x)) (else x)))
(define (abs x) (if (< x 0) (- x) x))

(cond ((< x 0) (- x)) (else x))
(cond (x 0) (else 2))

(define plus4 (lambda (x) (+ x 4)))

(let ((x 3)) (+ x 5))
*/