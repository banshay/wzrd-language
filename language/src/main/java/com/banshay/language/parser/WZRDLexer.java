// Generated from language/src/main/java/com/banshay/language/parser/WZRD.g4 by ANTLR 4.9.2
package com.banshay.language.parser;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class WZRDLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.9.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, T__15=16, T__16=17, 
		T__17=18, T__18=19, STR=20, WS=21, COMMENT=22, LINE_COMMENT=23, ID=24, 
		NUMBER=25;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"T__0", "T__1", "T__2", "T__3", "T__4", "T__5", "T__6", "T__7", "T__8", 
			"T__9", "T__10", "T__11", "T__12", "T__13", "T__14", "T__15", "T__16", 
			"T__17", "T__18", "STR", "WS", "COMMENT", "LINE_COMMENT", "ID", "NUMBER", 
			"UPPER", "LOWER", "LETTER", "DIGIT", "STRING"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'fn'", "'('", "','", "')'", "'{'", "'}'", "'if'", "'else'", "'while'", 
			"'*'", "'/'", "'+'", "'-'", "'=='", "'<'", "'>'", "'='", "'&&'", "'||'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, "STR", "WS", "COMMENT", 
			"LINE_COMMENT", "ID", "NUMBER"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}


	public WZRDLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "WZRD.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\33\u00b9\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31"+
		"\t\31\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\3\2"+
		"\3\2\3\2\3\3\3\3\3\4\3\4\3\5\3\5\3\6\3\6\3\7\3\7\3\b\3\b\3\b\3\t\3\t\3"+
		"\t\3\t\3\t\3\n\3\n\3\n\3\n\3\n\3\n\3\13\3\13\3\f\3\f\3\r\3\r\3\16\3\16"+
		"\3\17\3\17\3\17\3\20\3\20\3\21\3\21\3\22\3\22\3\23\3\23\3\23\3\24\3\24"+
		"\3\24\3\25\3\25\7\25t\n\25\f\25\16\25w\13\25\3\25\3\25\3\26\6\26|\n\26"+
		"\r\26\16\26}\3\26\3\26\3\27\3\27\3\27\3\27\7\27\u0086\n\27\f\27\16\27"+
		"\u0089\13\27\3\27\3\27\3\27\3\27\3\27\3\30\3\30\3\30\3\30\7\30\u0094\n"+
		"\30\f\30\16\30\u0097\13\30\3\30\3\30\3\31\3\31\3\31\7\31\u009e\n\31\f"+
		"\31\16\31\u00a1\13\31\3\32\3\32\7\32\u00a5\n\32\f\32\16\32\u00a8\13\32"+
		"\3\32\5\32\u00ab\n\32\3\33\3\33\3\34\3\34\3\35\3\35\3\36\3\36\3\37\3\37"+
		"\3\37\5\37\u00b8\n\37\4u\u0087\2 \3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23"+
		"\13\25\f\27\r\31\16\33\17\35\20\37\21!\22#\23%\24\'\25)\26+\27-\30/\31"+
		"\61\32\63\33\65\2\67\29\2;\2=\2\3\2\f\5\2\13\f\17\17\"\"\4\2\f\f\17\17"+
		"\3\2\63;\3\2\62\62\3\2C\\\3\2c|\4\2C\\c|\3\2\62;\4\2$$^^\6\2\f\f\17\17"+
		"$$^^\2\u00bc\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2"+
		"\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2"+
		"\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2"+
		"\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2"+
		"\2\2/\3\2\2\2\2\61\3\2\2\2\2\63\3\2\2\2\3?\3\2\2\2\5B\3\2\2\2\7D\3\2\2"+
		"\2\tF\3\2\2\2\13H\3\2\2\2\rJ\3\2\2\2\17L\3\2\2\2\21O\3\2\2\2\23T\3\2\2"+
		"\2\25Z\3\2\2\2\27\\\3\2\2\2\31^\3\2\2\2\33`\3\2\2\2\35b\3\2\2\2\37e\3"+
		"\2\2\2!g\3\2\2\2#i\3\2\2\2%k\3\2\2\2\'n\3\2\2\2)q\3\2\2\2+{\3\2\2\2-\u0081"+
		"\3\2\2\2/\u008f\3\2\2\2\61\u009a\3\2\2\2\63\u00aa\3\2\2\2\65\u00ac\3\2"+
		"\2\2\67\u00ae\3\2\2\29\u00b0\3\2\2\2;\u00b2\3\2\2\2=\u00b7\3\2\2\2?@\7"+
		"h\2\2@A\7p\2\2A\4\3\2\2\2BC\7*\2\2C\6\3\2\2\2DE\7.\2\2E\b\3\2\2\2FG\7"+
		"+\2\2G\n\3\2\2\2HI\7}\2\2I\f\3\2\2\2JK\7\177\2\2K\16\3\2\2\2LM\7k\2\2"+
		"MN\7h\2\2N\20\3\2\2\2OP\7g\2\2PQ\7n\2\2QR\7u\2\2RS\7g\2\2S\22\3\2\2\2"+
		"TU\7y\2\2UV\7j\2\2VW\7k\2\2WX\7n\2\2XY\7g\2\2Y\24\3\2\2\2Z[\7,\2\2[\26"+
		"\3\2\2\2\\]\7\61\2\2]\30\3\2\2\2^_\7-\2\2_\32\3\2\2\2`a\7/\2\2a\34\3\2"+
		"\2\2bc\7?\2\2cd\7?\2\2d\36\3\2\2\2ef\7>\2\2f \3\2\2\2gh\7@\2\2h\"\3\2"+
		"\2\2ij\7?\2\2j$\3\2\2\2kl\7(\2\2lm\7(\2\2m&\3\2\2\2no\7~\2\2op\7~\2\2"+
		"p(\3\2\2\2qu\7$\2\2rt\5=\37\2sr\3\2\2\2tw\3\2\2\2uv\3\2\2\2us\3\2\2\2"+
		"vx\3\2\2\2wu\3\2\2\2xy\7$\2\2y*\3\2\2\2z|\t\2\2\2{z\3\2\2\2|}\3\2\2\2"+
		"}{\3\2\2\2}~\3\2\2\2~\177\3\2\2\2\177\u0080\b\26\2\2\u0080,\3\2\2\2\u0081"+
		"\u0082\7\61\2\2\u0082\u0083\7,\2\2\u0083\u0087\3\2\2\2\u0084\u0086\13"+
		"\2\2\2\u0085\u0084\3\2\2\2\u0086\u0089\3\2\2\2\u0087\u0088\3\2\2\2\u0087"+
		"\u0085\3\2\2\2\u0088\u008a\3\2\2\2\u0089\u0087\3\2\2\2\u008a\u008b\7,"+
		"\2\2\u008b\u008c\7\61\2\2\u008c\u008d\3\2\2\2\u008d\u008e\b\27\2\2\u008e"+
		".\3\2\2\2\u008f\u0090\7\61\2\2\u0090\u0091\7\61\2\2\u0091\u0095\3\2\2"+
		"\2\u0092\u0094\n\3\2\2\u0093\u0092\3\2\2\2\u0094\u0097\3\2\2\2\u0095\u0093"+
		"\3\2\2\2\u0095\u0096\3\2\2\2\u0096\u0098\3\2\2\2\u0097\u0095\3\2\2\2\u0098"+
		"\u0099\b\30\2\2\u0099\60\3\2\2\2\u009a\u009f\5\67\34\2\u009b\u009e\59"+
		"\35\2\u009c\u009e\5;\36\2\u009d\u009b\3\2\2\2\u009d\u009c\3\2\2\2\u009e"+
		"\u00a1\3\2\2\2\u009f\u009d\3\2\2\2\u009f\u00a0\3\2\2\2\u00a0\62\3\2\2"+
		"\2\u00a1\u009f\3\2\2\2\u00a2\u00a6\t\4\2\2\u00a3\u00a5\5;\36\2\u00a4\u00a3"+
		"\3\2\2\2\u00a5\u00a8\3\2\2\2\u00a6\u00a4\3\2\2\2\u00a6\u00a7\3\2\2\2\u00a7"+
		"\u00ab\3\2\2\2\u00a8\u00a6\3\2\2\2\u00a9\u00ab\t\5\2\2\u00aa\u00a2\3\2"+
		"\2\2\u00aa\u00a9\3\2\2\2\u00ab\64\3\2\2\2\u00ac\u00ad\t\6\2\2\u00ad\66"+
		"\3\2\2\2\u00ae\u00af\t\7\2\2\u00af8\3\2\2\2\u00b0\u00b1\t\b\2\2\u00b1"+
		":\3\2\2\2\u00b2\u00b3\t\t\2\2\u00b3<\3\2\2\2\u00b4\u00b5\7^\2\2\u00b5"+
		"\u00b8\t\n\2\2\u00b6\u00b8\n\13\2\2\u00b7\u00b4\3\2\2\2\u00b7\u00b6\3"+
		"\2\2\2\u00b8>\3\2\2\2\f\2u}\u0087\u0095\u009d\u009f\u00a6\u00aa\u00b7"+
		"\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}