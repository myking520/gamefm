package com.myking520.github.plugin.autonet.lexer;

import java.util.HashMap;
import java.util.Map;

public class Lexer {
	public final static Map<String, Token> words = new HashMap<String, Token>();
	public final static char EOF = (char) -1;
	static {
		words.put(Word.wPublic.txt, Word.wPublic);
		words.put(Word.wInclude.txt, Word.wInclude);
		words.put(Word.wRequest.txt, Word.wRequest);
		words.put(Word.wResponse.txt, Word.wResponse);
		words.put(Word.wVoid.txt, Word.wVoid);
		words.put(Word.wStatic.txt, Word.wStatic);
		words.put(Word.wLeftbrace.txt, Word.wLeftbrace);
		words.put(Word.wRightbrace.txt, Word.wRightbrace);
		words.put(Word.wINT.txt, Word.wINT);
	}
	private String str;

	public void read(String str) {
		this.str = str;
		this.readch();
	}

	int readIndex = 0;
	private char peek = ' ';
	int line = 1;

	public Token scan() {
		for (;; readch()) {
			if (peek == '\n') {
				this.line++;
				continue;
			}
			if (this.peek == ' ' || this.peek == '\t') {
				continue;
			}
			break;
		}
		switch (peek) {
		case EOF:
			return null;
		case '{':
			this.readch();
			return Word.wLeftbrace;
		case '}':
			this.readch();
			return Word.wRightbrace;
		case ':':
			this.readch();
			return new Word(":", ':');
		default:
			break;
		}
		if (Character.isLetter(peek)) {
			StringBuffer b = new StringBuffer();
			do {
				b.append(peek);
				this.readch();
			} while (Character.isLetterOrDigit(peek));
			String s = b.toString();
			Word w = (Word) words.get(s);
			if (w != null)
				return w;
			w = new Word(s, Tag.ID);
			words.put(s, w);
			return w;
		}
		Token tok = new Token(peek);
		peek = ' ';
		return tok;

	}

	public int getLine() {
		return line;
	}

	private void readch() {
		if (readIndex < str.length()) {
			peek = str.charAt(readIndex++);
		} else {
			peek = EOF;
		}

	}
}
