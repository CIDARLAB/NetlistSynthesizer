// Generated from VerilogFluigi.g4 by ANTLR 4.5.3

    package org.cellocad.BU.fluigi;

import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class VerilogFluigiLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.5.3", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, T__15=16, T__16=17, 
		T__17=18, T__18=19, T__19=20, T__20=21, T__21=22, T__22=23, T__23=24, 
		ID=25, WS=26;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"T__0", "T__1", "T__2", "T__3", "T__4", "T__5", "T__6", "T__7", "T__8", 
		"T__9", "T__10", "T__11", "T__12", "T__13", "T__14", "T__15", "T__16", 
		"T__17", "T__18", "T__19", "T__20", "T__21", "T__22", "T__23", "ID", "WS"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'endmodule'", "'module'", "'('", "'input'", "','", "'output'", 
		"')'", "';'", "'wire'", "'assign'", "'='", "'~'", "'!'", "'@'", "'#'", 
		"'$'", "'%'", "'^'", "'&'", "'*'", "'?'", "'+'", "'-'", "'|'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, "ID", "WS"
	};
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


	public VerilogFluigiLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "VerilogFluigi.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2\34\u0093\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31"+
		"\t\31\4\32\t\32\4\33\t\33\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\3"+
		"\3\3\3\3\3\3\3\3\3\3\3\3\3\4\3\4\3\5\3\5\3\5\3\5\3\5\3\5\3\6\3\6\3\7\3"+
		"\7\3\7\3\7\3\7\3\7\3\7\3\b\3\b\3\t\3\t\3\n\3\n\3\n\3\n\3\n\3\13\3\13\3"+
		"\13\3\13\3\13\3\13\3\13\3\f\3\f\3\r\3\r\3\16\3\16\3\17\3\17\3\20\3\20"+
		"\3\21\3\21\3\22\3\22\3\23\3\23\3\24\3\24\3\25\3\25\3\26\3\26\3\27\3\27"+
		"\3\30\3\30\3\31\3\31\3\32\3\32\7\32\u0088\n\32\f\32\16\32\u008b\13\32"+
		"\3\33\6\33\u008e\n\33\r\33\16\33\u008f\3\33\3\33\2\2\34\3\3\5\4\7\5\t"+
		"\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31\16\33\17\35\20\37\21!\22#\23"+
		"%\24\'\25)\26+\27-\30/\31\61\32\63\33\65\34\3\2\5\5\2C\\aac|\6\2\62;C"+
		"\\aac|\5\2\13\f\17\17\"\"\u0094\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2"+
		"\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2"+
		"\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2"+
		"\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2"+
		"\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2\2\2\61\3\2\2\2\2\63\3\2\2\2\2\65\3\2\2"+
		"\2\3\67\3\2\2\2\5A\3\2\2\2\7H\3\2\2\2\tJ\3\2\2\2\13P\3\2\2\2\rR\3\2\2"+
		"\2\17Y\3\2\2\2\21[\3\2\2\2\23]\3\2\2\2\25b\3\2\2\2\27i\3\2\2\2\31k\3\2"+
		"\2\2\33m\3\2\2\2\35o\3\2\2\2\37q\3\2\2\2!s\3\2\2\2#u\3\2\2\2%w\3\2\2\2"+
		"\'y\3\2\2\2){\3\2\2\2+}\3\2\2\2-\177\3\2\2\2/\u0081\3\2\2\2\61\u0083\3"+
		"\2\2\2\63\u0085\3\2\2\2\65\u008d\3\2\2\2\678\7g\2\289\7p\2\29:\7f\2\2"+
		":;\7o\2\2;<\7q\2\2<=\7f\2\2=>\7w\2\2>?\7n\2\2?@\7g\2\2@\4\3\2\2\2AB\7"+
		"o\2\2BC\7q\2\2CD\7f\2\2DE\7w\2\2EF\7n\2\2FG\7g\2\2G\6\3\2\2\2HI\7*\2\2"+
		"I\b\3\2\2\2JK\7k\2\2KL\7p\2\2LM\7r\2\2MN\7w\2\2NO\7v\2\2O\n\3\2\2\2PQ"+
		"\7.\2\2Q\f\3\2\2\2RS\7q\2\2ST\7w\2\2TU\7v\2\2UV\7r\2\2VW\7w\2\2WX\7v\2"+
		"\2X\16\3\2\2\2YZ\7+\2\2Z\20\3\2\2\2[\\\7=\2\2\\\22\3\2\2\2]^\7y\2\2^_"+
		"\7k\2\2_`\7t\2\2`a\7g\2\2a\24\3\2\2\2bc\7c\2\2cd\7u\2\2de\7u\2\2ef\7k"+
		"\2\2fg\7i\2\2gh\7p\2\2h\26\3\2\2\2ij\7?\2\2j\30\3\2\2\2kl\7\u0080\2\2"+
		"l\32\3\2\2\2mn\7#\2\2n\34\3\2\2\2op\7B\2\2p\36\3\2\2\2qr\7%\2\2r \3\2"+
		"\2\2st\7&\2\2t\"\3\2\2\2uv\7\'\2\2v$\3\2\2\2wx\7`\2\2x&\3\2\2\2yz\7(\2"+
		"\2z(\3\2\2\2{|\7,\2\2|*\3\2\2\2}~\7A\2\2~,\3\2\2\2\177\u0080\7-\2\2\u0080"+
		".\3\2\2\2\u0081\u0082\7/\2\2\u0082\60\3\2\2\2\u0083\u0084\7~\2\2\u0084"+
		"\62\3\2\2\2\u0085\u0089\t\2\2\2\u0086\u0088\t\3\2\2\u0087\u0086\3\2\2"+
		"\2\u0088\u008b\3\2\2\2\u0089\u0087\3\2\2\2\u0089\u008a\3\2\2\2\u008a\64"+
		"\3\2\2\2\u008b\u0089\3\2\2\2\u008c\u008e\t\4\2\2\u008d\u008c\3\2\2\2\u008e"+
		"\u008f\3\2\2\2\u008f\u008d\3\2\2\2\u008f\u0090\3\2\2\2\u0090\u0091\3\2"+
		"\2\2\u0091\u0092\b\33\2\2\u0092\66\3\2\2\2\5\2\u0089\u008f\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}