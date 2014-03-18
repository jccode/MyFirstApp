package com.example.myfirstapp.password;
import java.util.Random;


public class PwUtil {
	
	public static final int PLUS = 0;
	public static final int MINUS = 1;

	public String encode(String txt) throws IllegalArgumentException {
		int len = txt.length();
		Random rand = new Random();
		int idx = rand.nextInt(len - 1) + 1; // [1, len-1]
		int baseNum = rand.nextInt(9) + 1; // [1, 9]
		int op = rand.nextInt(10); // [0, 9]
		int fn = (op % 2) == 1 ? PLUS: MINUS;
		
		char[] txts = txt.toCharArray();
		char[] rets = new char[txts.length+3];
		
		rets[0] = literalIntToChar(idx);
		rets[rets.length - 1] = literalIntToChar(op);
		rets[idx] = literalIntToChar(baseNum);
		
		for(int i=0, j=0; i<rets.length; i++) {
			if(rets[i] == 0) {
				rets[i] = opChar(txts[j], baseNum, fn);
				j++;
			}
		}
		
		return new String(rets);
	}
	
	public String decode(String txt) {
		int len =  txt.length();
		int idx = charToLiteralInt(txt.charAt(0));
		int op = charToLiteralInt(txt.charAt(len-1));
		int fn = (op % 2) == 1 ? MINUS: PLUS;
		int baseNum = charToLiteralInt(txt.charAt(idx));
		
		String evaluate = txt.substring(1, idx) + txt.substring(idx+1, len-1);
		
		char[] ecs = evaluate.toCharArray();
		char[] rets = new char[ecs.length];
		for(int i = 0; i < ecs.length; i++) {
			rets[i] = opChar(ecs[i], baseNum, fn);
		}
		
		return new String(rets);
	}
	
	private char opChar(char c, int operand, int fn) throws IllegalArgumentException {
		char ret;
		if(Character.isDigit(c)) 
			ret = opNumber(c, operand, fn);
		else if(Character.isLowerCase(c)) 
			ret = opLowerLetter(c, operand, fn);
		else if(Character.isUpperCase(c))
			ret = opUpperLetter(c, operand, fn);
		else 
			throw new IllegalArgumentException(c + " must be a number, or a letter");
		return ret;
	}
	
	private char literalIntToChar(int number) {
		return (char)(48 + number);
	}
	
	private int charToLiteralInt(char c) {
		return ((int)c) - 48;
	}
	
	private char opNumber(char c, int operand, int fn) {
		return opCharWithRange(c, operand, fn, 48, 57);
	}
	
	private char opUpperLetter(char c, int operand, int fn) {
		return opCharWithRange(c, operand, fn, 65, 90);
	}
	
	private char opLowerLetter(char c, int operand, int fn) {
		return opCharWithRange(c, operand, fn, 97, 122);
	}
	
	private char opCharWithRange(char c, int operand, int fn, int min, int max) {
		int ret;
		if(fn == PLUS) 
			ret = (c + operand);
		else if(fn == MINUS) 
			ret = (c - operand);
		else 
			ret = c;
		
		int range = max - min + 1;
		if(ret < min) 
			ret = (ret + range);
		else if(ret > max)
			ret = (ret - range);
		
		return (char)ret;
	}
}
