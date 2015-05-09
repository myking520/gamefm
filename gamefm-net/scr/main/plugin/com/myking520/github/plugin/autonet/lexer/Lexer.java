package com.myking520.github.plugin.autonet.lexer;

import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class Lexer {
	public final static Map<String,Token> tokens=new HashMap<String,Token>();
	static{
		tokens.put(Word.wPublic.txt,Word.wPublic );
		tokens.put(Word.wInclude.txt,Word.wInclude );
		tokens.put(Word.wRequest.txt,Word.wRequest );
		tokens.put(Word.wResponse.txt,Word.wResponse );
		tokens.put(Word.wVoid.txt,Word.wVoid );
		tokens.put(Word.wStatic.txt,Word.wStatic );
	}
	private StringReader sr;
	public void read(String str){
		sr=new StringReader(str);
	}
	public Token scan() {
		return null;
	}
}
