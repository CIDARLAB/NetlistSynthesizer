// Generated from VerilogFluigi.g4 by ANTLR 4.5.3

    package org.cellocad.BU.fluigi;

import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class VerilogFluigiParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.5.3", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, T__15=16, T__16=17, 
		T__17=18, T__18=19, T__19=20, T__20=21, T__21=22, T__22=23, T__23=24, 
		T__24=25, T__25=26, T__26=27, T__27=28, T__28=29, ID=30, WS=31;
	public static final int
		RULE_root = 0, RULE_modDec = 1, RULE_stats = 2, RULE_stat = 3, RULE_decl = 4, 
		RULE_assignStat = 5, RULE_exp = 6, RULE_lhs = 7, RULE_rhs = 8, RULE_op = 9, 
		RULE_bufferVar = 10, RULE_var = 11, RULE_modName = 12, RULE_input = 13, 
		RULE_output = 14, RULE_finput = 15, RULE_cinput = 16, RULE_foutput = 17, 
		RULE_coutput = 18, RULE_wire = 19;
	public static final String[] ruleNames = {
		"root", "modDec", "stats", "stat", "decl", "assignStat", "exp", "lhs", 
		"rhs", "op", "bufferVar", "var", "modName", "input", "output", "finput", 
		"cinput", "foutput", "coutput", "wire"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'endmodule'", "'module'", "'('", "'input'", "','", "'output'", 
		"')'", "';'", "'finput'", "'foutput'", "'cinput'", "'coutput'", "'wire'", 
		"'assign'", "'='", "'~'", "'!'", "'@'", "'#'", "'$'", "'%'", "'^'", "'&'", 
		"'*'", "'?'", "'+'", "'-'", "'|'", "'/'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, "ID", "WS"
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

	@Override
	public String getGrammarFileName() { return "VerilogFluigi.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public VerilogFluigiParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class RootContext extends ParserRuleContext {
		public ModDecContext modDec() {
			return getRuleContext(ModDecContext.class,0);
		}
		public StatsContext stats() {
			return getRuleContext(StatsContext.class,0);
		}
		public RootContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_root; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof VerilogFluigiListener ) ((VerilogFluigiListener)listener).enterRoot(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof VerilogFluigiListener ) ((VerilogFluigiListener)listener).exitRoot(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof VerilogFluigiVisitor ) return ((VerilogFluigiVisitor<? extends T>)visitor).visitRoot(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RootContext root() throws RecognitionException {
		RootContext _localctx = new RootContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_root);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(40);
			modDec();
			setState(41);
			stats();
			setState(42);
			match(T__0);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ModDecContext extends ParserRuleContext {
		public ModNameContext modName() {
			return getRuleContext(ModNameContext.class,0);
		}
		public List<OutputContext> output() {
			return getRuleContexts(OutputContext.class);
		}
		public OutputContext output(int i) {
			return getRuleContext(OutputContext.class,i);
		}
		public List<InputContext> input() {
			return getRuleContexts(InputContext.class);
		}
		public InputContext input(int i) {
			return getRuleContext(InputContext.class,i);
		}
		public List<FinputContext> finput() {
			return getRuleContexts(FinputContext.class);
		}
		public FinputContext finput(int i) {
			return getRuleContext(FinputContext.class,i);
		}
		public List<FoutputContext> foutput() {
			return getRuleContexts(FoutputContext.class);
		}
		public FoutputContext foutput(int i) {
			return getRuleContext(FoutputContext.class,i);
		}
		public List<CinputContext> cinput() {
			return getRuleContexts(CinputContext.class);
		}
		public CinputContext cinput(int i) {
			return getRuleContext(CinputContext.class,i);
		}
		public List<CoutputContext> coutput() {
			return getRuleContexts(CoutputContext.class);
		}
		public CoutputContext coutput(int i) {
			return getRuleContext(CoutputContext.class,i);
		}
		public ModDecContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_modDec; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof VerilogFluigiListener ) ((VerilogFluigiListener)listener).enterModDec(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof VerilogFluigiListener ) ((VerilogFluigiListener)listener).exitModDec(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof VerilogFluigiVisitor ) return ((VerilogFluigiVisitor<? extends T>)visitor).visitModDec(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ModDecContext modDec() throws RecognitionException {
		ModDecContext _localctx = new ModDecContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_modDec);
		int _la;
		try {
			int _alt;
			setState(187);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,17,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(44);
				match(T__1);
				setState(45);
				modName();
				setState(46);
				match(T__2);
				setState(47);
				match(T__3);
				setState(51); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(48);
					input();
					setState(49);
					match(T__4);
					}
					}
					setState(53); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==ID );
				setState(55);
				match(T__5);
				setState(66);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,2,_ctx) ) {
				case 1:
					{
					setState(56);
					output();
					}
					break;
				case 2:
					{
					{
					setState(60); 
					_errHandler.sync(this);
					_alt = 1;
					do {
						switch (_alt) {
						case 1:
							{
							{
							setState(57);
							output();
							setState(58);
							match(T__4);
							}
							}
							break;
						default:
							throw new NoViableAltException(this);
						}
						setState(62); 
						_errHandler.sync(this);
						_alt = getInterpreter().adaptivePredict(_input,1,_ctx);
					} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
					setState(64);
					output();
					}
					}
					break;
				}
				setState(68);
				match(T__6);
				setState(69);
				match(T__7);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(71);
				match(T__1);
				setState(72);
				modName();
				setState(73);
				match(T__2);
				setState(74);
				match(T__5);
				setState(78); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(75);
					output();
					setState(76);
					match(T__4);
					}
					}
					setState(80); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==ID );
				setState(82);
				match(T__3);
				setState(93);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,5,_ctx) ) {
				case 1:
					{
					setState(83);
					input();
					}
					break;
				case 2:
					{
					{
					setState(87); 
					_errHandler.sync(this);
					_alt = 1;
					do {
						switch (_alt) {
						case 1:
							{
							{
							setState(84);
							input();
							setState(85);
							match(T__4);
							}
							}
							break;
						default:
							throw new NoViableAltException(this);
						}
						setState(89); 
						_errHandler.sync(this);
						_alt = getInterpreter().adaptivePredict(_input,4,_ctx);
					} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
					setState(91);
					input();
					}
					}
					break;
				}
				setState(95);
				match(T__6);
				setState(96);
				match(T__7);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(98);
				match(T__1);
				setState(99);
				modName();
				setState(100);
				match(T__2);
				setState(139);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,11,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						setState(137);
						switch (_input.LA(1)) {
						case T__8:
							{
							{
							setState(101);
							match(T__8);
							setState(107);
							_errHandler.sync(this);
							_la = _input.LA(1);
							while (_la==ID) {
								{
								{
								setState(102);
								finput();
								setState(103);
								match(T__4);
								}
								}
								setState(109);
								_errHandler.sync(this);
								_la = _input.LA(1);
							}
							}
							}
							break;
						case T__9:
							{
							{
							setState(110);
							match(T__9);
							setState(116);
							_errHandler.sync(this);
							_la = _input.LA(1);
							while (_la==ID) {
								{
								{
								setState(111);
								foutput();
								setState(112);
								match(T__4);
								}
								}
								setState(118);
								_errHandler.sync(this);
								_la = _input.LA(1);
							}
							}
							}
							break;
						case T__10:
							{
							{
							setState(119);
							match(T__10);
							setState(125);
							_errHandler.sync(this);
							_la = _input.LA(1);
							while (_la==ID) {
								{
								{
								setState(120);
								cinput();
								setState(121);
								match(T__4);
								}
								}
								setState(127);
								_errHandler.sync(this);
								_la = _input.LA(1);
							}
							}
							}
							break;
						case T__11:
							{
							{
							setState(128);
							match(T__11);
							setState(134);
							_errHandler.sync(this);
							_la = _input.LA(1);
							while (_la==ID) {
								{
								{
								setState(129);
								coutput();
								setState(130);
								match(T__4);
								}
								}
								setState(136);
								_errHandler.sync(this);
								_la = _input.LA(1);
							}
							}
							}
							break;
						default:
							throw new NoViableAltException(this);
						}
						} 
					}
					setState(141);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,11,_ctx);
				}
				setState(182);
				switch (_input.LA(1)) {
				case T__8:
					{
					{
					setState(142);
					match(T__8);
					setState(148);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,12,_ctx);
					while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
						if ( _alt==1 ) {
							{
							{
							setState(143);
							finput();
							setState(144);
							match(T__4);
							}
							} 
						}
						setState(150);
						_errHandler.sync(this);
						_alt = getInterpreter().adaptivePredict(_input,12,_ctx);
					}
					setState(151);
					finput();
					}
					}
					break;
				case T__9:
					{
					{
					setState(152);
					match(T__9);
					setState(158);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,13,_ctx);
					while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
						if ( _alt==1 ) {
							{
							{
							setState(153);
							foutput();
							setState(154);
							match(T__4);
							}
							} 
						}
						setState(160);
						_errHandler.sync(this);
						_alt = getInterpreter().adaptivePredict(_input,13,_ctx);
					}
					setState(161);
					foutput();
					}
					}
					break;
				case T__10:
					{
					{
					setState(162);
					match(T__10);
					setState(168);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,14,_ctx);
					while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
						if ( _alt==1 ) {
							{
							{
							setState(163);
							cinput();
							setState(164);
							match(T__4);
							}
							} 
						}
						setState(170);
						_errHandler.sync(this);
						_alt = getInterpreter().adaptivePredict(_input,14,_ctx);
					}
					setState(171);
					cinput();
					}
					}
					break;
				case T__11:
					{
					{
					setState(172);
					match(T__11);
					setState(178);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,15,_ctx);
					while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
						if ( _alt==1 ) {
							{
							{
							setState(173);
							coutput();
							setState(174);
							match(T__4);
							}
							} 
						}
						setState(180);
						_errHandler.sync(this);
						_alt = getInterpreter().adaptivePredict(_input,15,_ctx);
					}
					setState(181);
					coutput();
					}
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(184);
				match(T__6);
				setState(185);
				match(T__7);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StatsContext extends ParserRuleContext {
		public List<StatContext> stat() {
			return getRuleContexts(StatContext.class);
		}
		public StatContext stat(int i) {
			return getRuleContext(StatContext.class,i);
		}
		public StatsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_stats; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof VerilogFluigiListener ) ((VerilogFluigiListener)listener).enterStats(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof VerilogFluigiListener ) ((VerilogFluigiListener)listener).exitStats(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof VerilogFluigiVisitor ) return ((VerilogFluigiVisitor<? extends T>)visitor).visitStats(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StatsContext stats() throws RecognitionException {
		StatsContext _localctx = new StatsContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_stats);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(190); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(189);
				stat();
				}
				}
				setState(192); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__3) | (1L << T__5) | (1L << T__8) | (1L << T__9) | (1L << T__10) | (1L << T__11) | (1L << T__12) | (1L << T__13))) != 0) );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StatContext extends ParserRuleContext {
		public AssignStatContext assignStat() {
			return getRuleContext(AssignStatContext.class,0);
		}
		public DeclContext decl() {
			return getRuleContext(DeclContext.class,0);
		}
		public StatContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_stat; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof VerilogFluigiListener ) ((VerilogFluigiListener)listener).enterStat(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof VerilogFluigiListener ) ((VerilogFluigiListener)listener).exitStat(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof VerilogFluigiVisitor ) return ((VerilogFluigiVisitor<? extends T>)visitor).visitStat(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StatContext stat() throws RecognitionException {
		StatContext _localctx = new StatContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_stat);
		try {
			setState(196);
			switch (_input.LA(1)) {
			case T__13:
				enterOuterAlt(_localctx, 1);
				{
				setState(194);
				assignStat();
				}
				break;
			case T__3:
			case T__5:
			case T__8:
			case T__9:
			case T__10:
			case T__11:
			case T__12:
				enterOuterAlt(_localctx, 2);
				{
				setState(195);
				decl();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DeclContext extends ParserRuleContext {
		public List<InputContext> input() {
			return getRuleContexts(InputContext.class);
		}
		public InputContext input(int i) {
			return getRuleContext(InputContext.class,i);
		}
		public List<FinputContext> finput() {
			return getRuleContexts(FinputContext.class);
		}
		public FinputContext finput(int i) {
			return getRuleContext(FinputContext.class,i);
		}
		public List<CinputContext> cinput() {
			return getRuleContexts(CinputContext.class);
		}
		public CinputContext cinput(int i) {
			return getRuleContext(CinputContext.class,i);
		}
		public List<OutputContext> output() {
			return getRuleContexts(OutputContext.class);
		}
		public OutputContext output(int i) {
			return getRuleContext(OutputContext.class,i);
		}
		public List<CoutputContext> coutput() {
			return getRuleContexts(CoutputContext.class);
		}
		public CoutputContext coutput(int i) {
			return getRuleContext(CoutputContext.class,i);
		}
		public List<FoutputContext> foutput() {
			return getRuleContexts(FoutputContext.class);
		}
		public FoutputContext foutput(int i) {
			return getRuleContext(FoutputContext.class,i);
		}
		public List<WireContext> wire() {
			return getRuleContexts(WireContext.class);
		}
		public WireContext wire(int i) {
			return getRuleContext(WireContext.class,i);
		}
		public DeclContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_decl; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof VerilogFluigiListener ) ((VerilogFluigiListener)listener).enterDecl(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof VerilogFluigiListener ) ((VerilogFluigiListener)listener).exitDecl(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof VerilogFluigiVisitor ) return ((VerilogFluigiVisitor<? extends T>)visitor).visitDecl(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DeclContext decl() throws RecognitionException {
		DeclContext _localctx = new DeclContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_decl);
		try {
			int _alt;
			setState(303);
			switch (_input.LA(1)) {
			case T__3:
				enterOuterAlt(_localctx, 1);
				{
				setState(198);
				match(T__3);
				setState(209);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,21,_ctx) ) {
				case 1:
					{
					setState(199);
					input();
					}
					break;
				case 2:
					{
					{
					setState(203); 
					_errHandler.sync(this);
					_alt = 1;
					do {
						switch (_alt) {
						case 1:
							{
							{
							setState(200);
							input();
							setState(201);
							match(T__4);
							}
							}
							break;
						default:
							throw new NoViableAltException(this);
						}
						setState(205); 
						_errHandler.sync(this);
						_alt = getInterpreter().adaptivePredict(_input,20,_ctx);
					} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
					setState(207);
					input();
					}
					}
					break;
				}
				setState(211);
				match(T__7);
				}
				break;
			case T__8:
				enterOuterAlt(_localctx, 2);
				{
				setState(213);
				match(T__8);
				setState(224);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,23,_ctx) ) {
				case 1:
					{
					setState(214);
					finput();
					}
					break;
				case 2:
					{
					{
					setState(218); 
					_errHandler.sync(this);
					_alt = 1;
					do {
						switch (_alt) {
						case 1:
							{
							{
							setState(215);
							finput();
							setState(216);
							match(T__4);
							}
							}
							break;
						default:
							throw new NoViableAltException(this);
						}
						setState(220); 
						_errHandler.sync(this);
						_alt = getInterpreter().adaptivePredict(_input,22,_ctx);
					} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
					setState(222);
					finput();
					}
					}
					break;
				}
				setState(226);
				match(T__7);
				}
				break;
			case T__10:
				enterOuterAlt(_localctx, 3);
				{
				setState(228);
				match(T__10);
				setState(239);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,25,_ctx) ) {
				case 1:
					{
					setState(229);
					cinput();
					}
					break;
				case 2:
					{
					{
					setState(233); 
					_errHandler.sync(this);
					_alt = 1;
					do {
						switch (_alt) {
						case 1:
							{
							{
							setState(230);
							cinput();
							setState(231);
							match(T__4);
							}
							}
							break;
						default:
							throw new NoViableAltException(this);
						}
						setState(235); 
						_errHandler.sync(this);
						_alt = getInterpreter().adaptivePredict(_input,24,_ctx);
					} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
					setState(237);
					cinput();
					}
					}
					break;
				}
				setState(241);
				match(T__7);
				}
				break;
			case T__5:
				enterOuterAlt(_localctx, 4);
				{
				setState(243);
				match(T__5);
				setState(254);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,27,_ctx) ) {
				case 1:
					{
					setState(244);
					output();
					}
					break;
				case 2:
					{
					{
					setState(248); 
					_errHandler.sync(this);
					_alt = 1;
					do {
						switch (_alt) {
						case 1:
							{
							{
							setState(245);
							output();
							setState(246);
							match(T__4);
							}
							}
							break;
						default:
							throw new NoViableAltException(this);
						}
						setState(250); 
						_errHandler.sync(this);
						_alt = getInterpreter().adaptivePredict(_input,26,_ctx);
					} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
					setState(252);
					output();
					}
					}
					break;
				}
				setState(256);
				match(T__7);
				}
				break;
			case T__11:
				enterOuterAlt(_localctx, 5);
				{
				setState(258);
				match(T__11);
				setState(269);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,29,_ctx) ) {
				case 1:
					{
					setState(259);
					coutput();
					}
					break;
				case 2:
					{
					{
					setState(263); 
					_errHandler.sync(this);
					_alt = 1;
					do {
						switch (_alt) {
						case 1:
							{
							{
							setState(260);
							coutput();
							setState(261);
							match(T__4);
							}
							}
							break;
						default:
							throw new NoViableAltException(this);
						}
						setState(265); 
						_errHandler.sync(this);
						_alt = getInterpreter().adaptivePredict(_input,28,_ctx);
					} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
					setState(267);
					coutput();
					}
					}
					break;
				}
				setState(271);
				match(T__7);
				}
				break;
			case T__9:
				enterOuterAlt(_localctx, 6);
				{
				setState(273);
				match(T__9);
				setState(284);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,31,_ctx) ) {
				case 1:
					{
					setState(274);
					foutput();
					}
					break;
				case 2:
					{
					{
					setState(278); 
					_errHandler.sync(this);
					_alt = 1;
					do {
						switch (_alt) {
						case 1:
							{
							{
							setState(275);
							foutput();
							setState(276);
							match(T__4);
							}
							}
							break;
						default:
							throw new NoViableAltException(this);
						}
						setState(280); 
						_errHandler.sync(this);
						_alt = getInterpreter().adaptivePredict(_input,30,_ctx);
					} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
					setState(282);
					foutput();
					}
					}
					break;
				}
				setState(286);
				match(T__7);
				}
				break;
			case T__12:
				enterOuterAlt(_localctx, 7);
				{
				setState(288);
				match(T__12);
				setState(299);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,33,_ctx) ) {
				case 1:
					{
					setState(289);
					wire();
					}
					break;
				case 2:
					{
					{
					setState(293); 
					_errHandler.sync(this);
					_alt = 1;
					do {
						switch (_alt) {
						case 1:
							{
							{
							setState(290);
							wire();
							setState(291);
							match(T__4);
							}
							}
							break;
						default:
							throw new NoViableAltException(this);
						}
						setState(295); 
						_errHandler.sync(this);
						_alt = getInterpreter().adaptivePredict(_input,32,_ctx);
					} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
					setState(297);
					wire();
					}
					}
					break;
				}
				setState(301);
				match(T__7);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AssignStatContext extends ParserRuleContext {
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public AssignStatContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assignStat; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof VerilogFluigiListener ) ((VerilogFluigiListener)listener).enterAssignStat(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof VerilogFluigiListener ) ((VerilogFluigiListener)listener).exitAssignStat(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof VerilogFluigiVisitor ) return ((VerilogFluigiVisitor<? extends T>)visitor).visitAssignStat(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AssignStatContext assignStat() throws RecognitionException {
		AssignStatContext _localctx = new AssignStatContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_assignStat);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(305);
			match(T__13);
			setState(306);
			exp();
			setState(307);
			match(T__7);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExpContext extends ParserRuleContext {
		public LhsContext lhs() {
			return getRuleContext(LhsContext.class,0);
		}
		public RhsContext rhs() {
			return getRuleContext(RhsContext.class,0);
		}
		public ExpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_exp; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof VerilogFluigiListener ) ((VerilogFluigiListener)listener).enterExp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof VerilogFluigiListener ) ((VerilogFluigiListener)listener).exitExp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof VerilogFluigiVisitor ) return ((VerilogFluigiVisitor<? extends T>)visitor).visitExp(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExpContext exp() throws RecognitionException {
		ExpContext _localctx = new ExpContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_exp);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(309);
			lhs();
			setState(310);
			match(T__14);
			setState(311);
			rhs();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LhsContext extends ParserRuleContext {
		public VarContext var() {
			return getRuleContext(VarContext.class,0);
		}
		public LhsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_lhs; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof VerilogFluigiListener ) ((VerilogFluigiListener)listener).enterLhs(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof VerilogFluigiListener ) ((VerilogFluigiListener)listener).exitLhs(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof VerilogFluigiVisitor ) return ((VerilogFluigiVisitor<? extends T>)visitor).visitLhs(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LhsContext lhs() throws RecognitionException {
		LhsContext _localctx = new LhsContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_lhs);
		try {
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(313);
			var();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class RhsContext extends ParserRuleContext {
		public List<VarContext> var() {
			return getRuleContexts(VarContext.class);
		}
		public VarContext var(int i) {
			return getRuleContext(VarContext.class,i);
		}
		public List<OpContext> op() {
			return getRuleContexts(OpContext.class);
		}
		public OpContext op(int i) {
			return getRuleContext(OpContext.class,i);
		}
		public BufferVarContext bufferVar() {
			return getRuleContext(BufferVarContext.class,0);
		}
		public RhsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_rhs; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof VerilogFluigiListener ) ((VerilogFluigiListener)listener).enterRhs(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof VerilogFluigiListener ) ((VerilogFluigiListener)listener).exitRhs(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof VerilogFluigiVisitor ) return ((VerilogFluigiVisitor<? extends T>)visitor).visitRhs(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RhsContext rhs() throws RecognitionException {
		RhsContext _localctx = new RhsContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_rhs);
		int _la;
		try {
			setState(324);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,36,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				{
				setState(315);
				var();
				}
				setState(319); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(316);
					op();
					{
					setState(317);
					var();
					}
					}
					}
					setState(321); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__15) | (1L << T__16) | (1L << T__17) | (1L << T__18) | (1L << T__19) | (1L << T__20) | (1L << T__21) | (1L << T__22) | (1L << T__23) | (1L << T__24) | (1L << T__25) | (1L << T__26) | (1L << T__27) | (1L << T__28))) != 0) );
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				{
				setState(323);
				bufferVar();
				}
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class OpContext extends ParserRuleContext {
		public OpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_op; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof VerilogFluigiListener ) ((VerilogFluigiListener)listener).enterOp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof VerilogFluigiListener ) ((VerilogFluigiListener)listener).exitOp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof VerilogFluigiVisitor ) return ((VerilogFluigiVisitor<? extends T>)visitor).visitOp(this);
			else return visitor.visitChildren(this);
		}
	}

	public final OpContext op() throws RecognitionException {
		OpContext _localctx = new OpContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_op);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(326);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__15) | (1L << T__16) | (1L << T__17) | (1L << T__18) | (1L << T__19) | (1L << T__20) | (1L << T__21) | (1L << T__22) | (1L << T__23) | (1L << T__24) | (1L << T__25) | (1L << T__26) | (1L << T__27) | (1L << T__28))) != 0)) ) {
			_errHandler.recoverInline(this);
			} else {
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BufferVarContext extends ParserRuleContext {
		public VarContext var() {
			return getRuleContext(VarContext.class,0);
		}
		public BufferVarContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_bufferVar; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof VerilogFluigiListener ) ((VerilogFluigiListener)listener).enterBufferVar(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof VerilogFluigiListener ) ((VerilogFluigiListener)listener).exitBufferVar(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof VerilogFluigiVisitor ) return ((VerilogFluigiVisitor<? extends T>)visitor).visitBufferVar(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BufferVarContext bufferVar() throws RecognitionException {
		BufferVarContext _localctx = new BufferVarContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_bufferVar);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(328);
			var();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class VarContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(VerilogFluigiParser.ID, 0); }
		public VarContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_var; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof VerilogFluigiListener ) ((VerilogFluigiListener)listener).enterVar(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof VerilogFluigiListener ) ((VerilogFluigiListener)listener).exitVar(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof VerilogFluigiVisitor ) return ((VerilogFluigiVisitor<? extends T>)visitor).visitVar(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VarContext var() throws RecognitionException {
		VarContext _localctx = new VarContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_var);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(330);
			match(ID);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ModNameContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(VerilogFluigiParser.ID, 0); }
		public ModNameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_modName; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof VerilogFluigiListener ) ((VerilogFluigiListener)listener).enterModName(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof VerilogFluigiListener ) ((VerilogFluigiListener)listener).exitModName(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof VerilogFluigiVisitor ) return ((VerilogFluigiVisitor<? extends T>)visitor).visitModName(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ModNameContext modName() throws RecognitionException {
		ModNameContext _localctx = new ModNameContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_modName);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(332);
			match(ID);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class InputContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(VerilogFluigiParser.ID, 0); }
		public InputContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_input; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof VerilogFluigiListener ) ((VerilogFluigiListener)listener).enterInput(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof VerilogFluigiListener ) ((VerilogFluigiListener)listener).exitInput(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof VerilogFluigiVisitor ) return ((VerilogFluigiVisitor<? extends T>)visitor).visitInput(this);
			else return visitor.visitChildren(this);
		}
	}

	public final InputContext input() throws RecognitionException {
		InputContext _localctx = new InputContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_input);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(334);
			match(ID);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class OutputContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(VerilogFluigiParser.ID, 0); }
		public OutputContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_output; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof VerilogFluigiListener ) ((VerilogFluigiListener)listener).enterOutput(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof VerilogFluigiListener ) ((VerilogFluigiListener)listener).exitOutput(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof VerilogFluigiVisitor ) return ((VerilogFluigiVisitor<? extends T>)visitor).visitOutput(this);
			else return visitor.visitChildren(this);
		}
	}

	public final OutputContext output() throws RecognitionException {
		OutputContext _localctx = new OutputContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_output);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(336);
			match(ID);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FinputContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(VerilogFluigiParser.ID, 0); }
		public FinputContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_finput; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof VerilogFluigiListener ) ((VerilogFluigiListener)listener).enterFinput(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof VerilogFluigiListener ) ((VerilogFluigiListener)listener).exitFinput(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof VerilogFluigiVisitor ) return ((VerilogFluigiVisitor<? extends T>)visitor).visitFinput(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FinputContext finput() throws RecognitionException {
		FinputContext _localctx = new FinputContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_finput);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(338);
			match(ID);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CinputContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(VerilogFluigiParser.ID, 0); }
		public CinputContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_cinput; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof VerilogFluigiListener ) ((VerilogFluigiListener)listener).enterCinput(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof VerilogFluigiListener ) ((VerilogFluigiListener)listener).exitCinput(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof VerilogFluigiVisitor ) return ((VerilogFluigiVisitor<? extends T>)visitor).visitCinput(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CinputContext cinput() throws RecognitionException {
		CinputContext _localctx = new CinputContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_cinput);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(340);
			match(ID);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FoutputContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(VerilogFluigiParser.ID, 0); }
		public FoutputContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_foutput; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof VerilogFluigiListener ) ((VerilogFluigiListener)listener).enterFoutput(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof VerilogFluigiListener ) ((VerilogFluigiListener)listener).exitFoutput(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof VerilogFluigiVisitor ) return ((VerilogFluigiVisitor<? extends T>)visitor).visitFoutput(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FoutputContext foutput() throws RecognitionException {
		FoutputContext _localctx = new FoutputContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_foutput);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(342);
			match(ID);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CoutputContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(VerilogFluigiParser.ID, 0); }
		public CoutputContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_coutput; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof VerilogFluigiListener ) ((VerilogFluigiListener)listener).enterCoutput(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof VerilogFluigiListener ) ((VerilogFluigiListener)listener).exitCoutput(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof VerilogFluigiVisitor ) return ((VerilogFluigiVisitor<? extends T>)visitor).visitCoutput(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CoutputContext coutput() throws RecognitionException {
		CoutputContext _localctx = new CoutputContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_coutput);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(344);
			match(ID);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class WireContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(VerilogFluigiParser.ID, 0); }
		public WireContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_wire; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof VerilogFluigiListener ) ((VerilogFluigiListener)listener).enterWire(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof VerilogFluigiListener ) ((VerilogFluigiListener)listener).exitWire(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof VerilogFluigiVisitor ) return ((VerilogFluigiVisitor<? extends T>)visitor).visitWire(this);
			else return visitor.visitChildren(this);
		}
	}

	public final WireContext wire() throws RecognitionException {
		WireContext _localctx = new WireContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_wire);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(346);
			match(ID);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3!\u015f\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\3\2\3\2\3\2\3\2\3\3\3\3\3\3\3\3\3\3\3\3"+
		"\3\3\6\3\66\n\3\r\3\16\3\67\3\3\3\3\3\3\3\3\3\3\6\3?\n\3\r\3\16\3@\3\3"+
		"\3\3\5\3E\n\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\6\3Q\n\3\r\3\16"+
		"\3R\3\3\3\3\3\3\3\3\3\3\6\3Z\n\3\r\3\16\3[\3\3\3\3\5\3`\n\3\3\3\3\3\3"+
		"\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\7\3l\n\3\f\3\16\3o\13\3\3\3\3\3\3\3\3\3"+
		"\7\3u\n\3\f\3\16\3x\13\3\3\3\3\3\3\3\3\3\7\3~\n\3\f\3\16\3\u0081\13\3"+
		"\3\3\3\3\3\3\3\3\7\3\u0087\n\3\f\3\16\3\u008a\13\3\7\3\u008c\n\3\f\3\16"+
		"\3\u008f\13\3\3\3\3\3\3\3\3\3\7\3\u0095\n\3\f\3\16\3\u0098\13\3\3\3\3"+
		"\3\3\3\3\3\3\3\7\3\u009f\n\3\f\3\16\3\u00a2\13\3\3\3\3\3\3\3\3\3\3\3\7"+
		"\3\u00a9\n\3\f\3\16\3\u00ac\13\3\3\3\3\3\3\3\3\3\3\3\7\3\u00b3\n\3\f\3"+
		"\16\3\u00b6\13\3\3\3\5\3\u00b9\n\3\3\3\3\3\3\3\5\3\u00be\n\3\3\4\6\4\u00c1"+
		"\n\4\r\4\16\4\u00c2\3\5\3\5\5\5\u00c7\n\5\3\6\3\6\3\6\3\6\3\6\6\6\u00ce"+
		"\n\6\r\6\16\6\u00cf\3\6\3\6\5\6\u00d4\n\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6"+
		"\6\6\u00dd\n\6\r\6\16\6\u00de\3\6\3\6\5\6\u00e3\n\6\3\6\3\6\3\6\3\6\3"+
		"\6\3\6\3\6\6\6\u00ec\n\6\r\6\16\6\u00ed\3\6\3\6\5\6\u00f2\n\6\3\6\3\6"+
		"\3\6\3\6\3\6\3\6\3\6\6\6\u00fb\n\6\r\6\16\6\u00fc\3\6\3\6\5\6\u0101\n"+
		"\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\6\6\u010a\n\6\r\6\16\6\u010b\3\6\3\6\5"+
		"\6\u0110\n\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\6\6\u0119\n\6\r\6\16\6\u011a"+
		"\3\6\3\6\5\6\u011f\n\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\6\6\u0128\n\6\r\6\16"+
		"\6\u0129\3\6\3\6\5\6\u012e\n\6\3\6\3\6\5\6\u0132\n\6\3\7\3\7\3\7\3\7\3"+
		"\b\3\b\3\b\3\b\3\t\3\t\3\n\3\n\3\n\3\n\6\n\u0142\n\n\r\n\16\n\u0143\3"+
		"\n\5\n\u0147\n\n\3\13\3\13\3\f\3\f\3\r\3\r\3\16\3\16\3\17\3\17\3\20\3"+
		"\20\3\21\3\21\3\22\3\22\3\23\3\23\3\24\3\24\3\25\3\25\3\25\2\2\26\2\4"+
		"\6\b\n\f\16\20\22\24\26\30\32\34\36 \"$&(\2\3\3\2\22\37\u0179\2*\3\2\2"+
		"\2\4\u00bd\3\2\2\2\6\u00c0\3\2\2\2\b\u00c6\3\2\2\2\n\u0131\3\2\2\2\f\u0133"+
		"\3\2\2\2\16\u0137\3\2\2\2\20\u013b\3\2\2\2\22\u0146\3\2\2\2\24\u0148\3"+
		"\2\2\2\26\u014a\3\2\2\2\30\u014c\3\2\2\2\32\u014e\3\2\2\2\34\u0150\3\2"+
		"\2\2\36\u0152\3\2\2\2 \u0154\3\2\2\2\"\u0156\3\2\2\2$\u0158\3\2\2\2&\u015a"+
		"\3\2\2\2(\u015c\3\2\2\2*+\5\4\3\2+,\5\6\4\2,-\7\3\2\2-\3\3\2\2\2./\7\4"+
		"\2\2/\60\5\32\16\2\60\61\7\5\2\2\61\65\7\6\2\2\62\63\5\34\17\2\63\64\7"+
		"\7\2\2\64\66\3\2\2\2\65\62\3\2\2\2\66\67\3\2\2\2\67\65\3\2\2\2\678\3\2"+
		"\2\289\3\2\2\29D\7\b\2\2:E\5\36\20\2;<\5\36\20\2<=\7\7\2\2=?\3\2\2\2>"+
		";\3\2\2\2?@\3\2\2\2@>\3\2\2\2@A\3\2\2\2AB\3\2\2\2BC\5\36\20\2CE\3\2\2"+
		"\2D:\3\2\2\2D>\3\2\2\2EF\3\2\2\2FG\7\t\2\2GH\7\n\2\2H\u00be\3\2\2\2IJ"+
		"\7\4\2\2JK\5\32\16\2KL\7\5\2\2LP\7\b\2\2MN\5\36\20\2NO\7\7\2\2OQ\3\2\2"+
		"\2PM\3\2\2\2QR\3\2\2\2RP\3\2\2\2RS\3\2\2\2ST\3\2\2\2T_\7\6\2\2U`\5\34"+
		"\17\2VW\5\34\17\2WX\7\7\2\2XZ\3\2\2\2YV\3\2\2\2Z[\3\2\2\2[Y\3\2\2\2[\\"+
		"\3\2\2\2\\]\3\2\2\2]^\5\34\17\2^`\3\2\2\2_U\3\2\2\2_Y\3\2\2\2`a\3\2\2"+
		"\2ab\7\t\2\2bc\7\n\2\2c\u00be\3\2\2\2de\7\4\2\2ef\5\32\16\2f\u008d\7\5"+
		"\2\2gm\7\13\2\2hi\5 \21\2ij\7\7\2\2jl\3\2\2\2kh\3\2\2\2lo\3\2\2\2mk\3"+
		"\2\2\2mn\3\2\2\2n\u008c\3\2\2\2om\3\2\2\2pv\7\f\2\2qr\5$\23\2rs\7\7\2"+
		"\2su\3\2\2\2tq\3\2\2\2ux\3\2\2\2vt\3\2\2\2vw\3\2\2\2w\u008c\3\2\2\2xv"+
		"\3\2\2\2y\177\7\r\2\2z{\5\"\22\2{|\7\7\2\2|~\3\2\2\2}z\3\2\2\2~\u0081"+
		"\3\2\2\2\177}\3\2\2\2\177\u0080\3\2\2\2\u0080\u008c\3\2\2\2\u0081\177"+
		"\3\2\2\2\u0082\u0088\7\16\2\2\u0083\u0084\5&\24\2\u0084\u0085\7\7\2\2"+
		"\u0085\u0087\3\2\2\2\u0086\u0083\3\2\2\2\u0087\u008a\3\2\2\2\u0088\u0086"+
		"\3\2\2\2\u0088\u0089\3\2\2\2\u0089\u008c\3\2\2\2\u008a\u0088\3\2\2\2\u008b"+
		"g\3\2\2\2\u008bp\3\2\2\2\u008by\3\2\2\2\u008b\u0082\3\2\2\2\u008c\u008f"+
		"\3\2\2\2\u008d\u008b\3\2\2\2\u008d\u008e\3\2\2\2\u008e\u00b8\3\2\2\2\u008f"+
		"\u008d\3\2\2\2\u0090\u0096\7\13\2\2\u0091\u0092\5 \21\2\u0092\u0093\7"+
		"\7\2\2\u0093\u0095\3\2\2\2\u0094\u0091\3\2\2\2\u0095\u0098\3\2\2\2\u0096"+
		"\u0094\3\2\2\2\u0096\u0097\3\2\2\2\u0097\u0099\3\2\2\2\u0098\u0096\3\2"+
		"\2\2\u0099\u00b9\5 \21\2\u009a\u00a0\7\f\2\2\u009b\u009c\5$\23\2\u009c"+
		"\u009d\7\7\2\2\u009d\u009f\3\2\2\2\u009e\u009b\3\2\2\2\u009f\u00a2\3\2"+
		"\2\2\u00a0\u009e\3\2\2\2\u00a0\u00a1\3\2\2\2\u00a1\u00a3\3\2\2\2\u00a2"+
		"\u00a0\3\2\2\2\u00a3\u00b9\5$\23\2\u00a4\u00aa\7\r\2\2\u00a5\u00a6\5\""+
		"\22\2\u00a6\u00a7\7\7\2\2\u00a7\u00a9\3\2\2\2\u00a8\u00a5\3\2\2\2\u00a9"+
		"\u00ac\3\2\2\2\u00aa\u00a8\3\2\2\2\u00aa\u00ab\3\2\2\2\u00ab\u00ad\3\2"+
		"\2\2\u00ac\u00aa\3\2\2\2\u00ad\u00b9\5\"\22\2\u00ae\u00b4\7\16\2\2\u00af"+
		"\u00b0\5&\24\2\u00b0\u00b1\7\7\2\2\u00b1\u00b3\3\2\2\2\u00b2\u00af\3\2"+
		"\2\2\u00b3\u00b6\3\2\2\2\u00b4\u00b2\3\2\2\2\u00b4\u00b5\3\2\2\2\u00b5"+
		"\u00b7\3\2\2\2\u00b6\u00b4\3\2\2\2\u00b7\u00b9\5&\24\2\u00b8\u0090\3\2"+
		"\2\2\u00b8\u009a\3\2\2\2\u00b8\u00a4\3\2\2\2\u00b8\u00ae\3\2\2\2\u00b9"+
		"\u00ba\3\2\2\2\u00ba\u00bb\7\t\2\2\u00bb\u00bc\7\n\2\2\u00bc\u00be\3\2"+
		"\2\2\u00bd.\3\2\2\2\u00bdI\3\2\2\2\u00bdd\3\2\2\2\u00be\5\3\2\2\2\u00bf"+
		"\u00c1\5\b\5\2\u00c0\u00bf\3\2\2\2\u00c1\u00c2\3\2\2\2\u00c2\u00c0\3\2"+
		"\2\2\u00c2\u00c3\3\2\2\2\u00c3\7\3\2\2\2\u00c4\u00c7\5\f\7\2\u00c5\u00c7"+
		"\5\n\6\2\u00c6\u00c4\3\2\2\2\u00c6\u00c5\3\2\2\2\u00c7\t\3\2\2\2\u00c8"+
		"\u00d3\7\6\2\2\u00c9\u00d4\5\34\17\2\u00ca\u00cb\5\34\17\2\u00cb\u00cc"+
		"\7\7\2\2\u00cc\u00ce\3\2\2\2\u00cd\u00ca\3\2\2\2\u00ce\u00cf\3\2\2\2\u00cf"+
		"\u00cd\3\2\2\2\u00cf\u00d0\3\2\2\2\u00d0\u00d1\3\2\2\2\u00d1\u00d2\5\34"+
		"\17\2\u00d2\u00d4\3\2\2\2\u00d3\u00c9\3\2\2\2\u00d3\u00cd\3\2\2\2\u00d4"+
		"\u00d5\3\2\2\2\u00d5\u00d6\7\n\2\2\u00d6\u0132\3\2\2\2\u00d7\u00e2\7\13"+
		"\2\2\u00d8\u00e3\5 \21\2\u00d9\u00da\5 \21\2\u00da\u00db\7\7\2\2\u00db"+
		"\u00dd\3\2\2\2\u00dc\u00d9\3\2\2\2\u00dd\u00de\3\2\2\2\u00de\u00dc\3\2"+
		"\2\2\u00de\u00df\3\2\2\2\u00df\u00e0\3\2\2\2\u00e0\u00e1\5 \21\2\u00e1"+
		"\u00e3\3\2\2\2\u00e2\u00d8\3\2\2\2\u00e2\u00dc\3\2\2\2\u00e3\u00e4\3\2"+
		"\2\2\u00e4\u00e5\7\n\2\2\u00e5\u0132\3\2\2\2\u00e6\u00f1\7\r\2\2\u00e7"+
		"\u00f2\5\"\22\2\u00e8\u00e9\5\"\22\2\u00e9\u00ea\7\7\2\2\u00ea\u00ec\3"+
		"\2\2\2\u00eb\u00e8\3\2\2\2\u00ec\u00ed\3\2\2\2\u00ed\u00eb\3\2\2\2\u00ed"+
		"\u00ee\3\2\2\2\u00ee\u00ef\3\2\2\2\u00ef\u00f0\5\"\22\2\u00f0\u00f2\3"+
		"\2\2\2\u00f1\u00e7\3\2\2\2\u00f1\u00eb\3\2\2\2\u00f2\u00f3\3\2\2\2\u00f3"+
		"\u00f4\7\n\2\2\u00f4\u0132\3\2\2\2\u00f5\u0100\7\b\2\2\u00f6\u0101\5\36"+
		"\20\2\u00f7\u00f8\5\36\20\2\u00f8\u00f9\7\7\2\2\u00f9\u00fb\3\2\2\2\u00fa"+
		"\u00f7\3\2\2\2\u00fb\u00fc\3\2\2\2\u00fc\u00fa\3\2\2\2\u00fc\u00fd\3\2"+
		"\2\2\u00fd\u00fe\3\2\2\2\u00fe\u00ff\5\36\20\2\u00ff\u0101\3\2\2\2\u0100"+
		"\u00f6\3\2\2\2\u0100\u00fa\3\2\2\2\u0101\u0102\3\2\2\2\u0102\u0103\7\n"+
		"\2\2\u0103\u0132\3\2\2\2\u0104\u010f\7\16\2\2\u0105\u0110\5&\24\2\u0106"+
		"\u0107\5&\24\2\u0107\u0108\7\7\2\2\u0108\u010a\3\2\2\2\u0109\u0106\3\2"+
		"\2\2\u010a\u010b\3\2\2\2\u010b\u0109\3\2\2\2\u010b\u010c\3\2\2\2\u010c"+
		"\u010d\3\2\2\2\u010d\u010e\5&\24\2\u010e\u0110\3\2\2\2\u010f\u0105\3\2"+
		"\2\2\u010f\u0109\3\2\2\2\u0110\u0111\3\2\2\2\u0111\u0112\7\n\2\2\u0112"+
		"\u0132\3\2\2\2\u0113\u011e\7\f\2\2\u0114\u011f\5$\23\2\u0115\u0116\5$"+
		"\23\2\u0116\u0117\7\7\2\2\u0117\u0119\3\2\2\2\u0118\u0115\3\2\2\2\u0119"+
		"\u011a\3\2\2\2\u011a\u0118\3\2\2\2\u011a\u011b\3\2\2\2\u011b\u011c\3\2"+
		"\2\2\u011c\u011d\5$\23\2\u011d\u011f\3\2\2\2\u011e\u0114\3\2\2\2\u011e"+
		"\u0118\3\2\2\2\u011f\u0120\3\2\2\2\u0120\u0121\7\n\2\2\u0121\u0132\3\2"+
		"\2\2\u0122\u012d\7\17\2\2\u0123\u012e\5(\25\2\u0124\u0125\5(\25\2\u0125"+
		"\u0126\7\7\2\2\u0126\u0128\3\2\2\2\u0127\u0124\3\2\2\2\u0128\u0129\3\2"+
		"\2\2\u0129\u0127\3\2\2\2\u0129\u012a\3\2\2\2\u012a\u012b\3\2\2\2\u012b"+
		"\u012c\5(\25\2\u012c\u012e\3\2\2\2\u012d\u0123\3\2\2\2\u012d\u0127\3\2"+
		"\2\2\u012e\u012f\3\2\2\2\u012f\u0130\7\n\2\2\u0130\u0132\3\2\2\2\u0131"+
		"\u00c8\3\2\2\2\u0131\u00d7\3\2\2\2\u0131\u00e6\3\2\2\2\u0131\u00f5\3\2"+
		"\2\2\u0131\u0104\3\2\2\2\u0131\u0113\3\2\2\2\u0131\u0122\3\2\2\2\u0132"+
		"\13\3\2\2\2\u0133\u0134\7\20\2\2\u0134\u0135\5\16\b\2\u0135\u0136\7\n"+
		"\2\2\u0136\r\3\2\2\2\u0137\u0138\5\20\t\2\u0138\u0139\7\21\2\2\u0139\u013a"+
		"\5\22\n\2\u013a\17\3\2\2\2\u013b\u013c\5\30\r\2\u013c\21\3\2\2\2\u013d"+
		"\u0141\5\30\r\2\u013e\u013f\5\24\13\2\u013f\u0140\5\30\r\2\u0140\u0142"+
		"\3\2\2\2\u0141\u013e\3\2\2\2\u0142\u0143\3\2\2\2\u0143\u0141\3\2\2\2\u0143"+
		"\u0144\3\2\2\2\u0144\u0147\3\2\2\2\u0145\u0147\5\26\f\2\u0146\u013d\3"+
		"\2\2\2\u0146\u0145\3\2\2\2\u0147\23\3\2\2\2\u0148\u0149\t\2\2\2\u0149"+
		"\25\3\2\2\2\u014a\u014b\5\30\r\2\u014b\27\3\2\2\2\u014c\u014d\7 \2\2\u014d"+
		"\31\3\2\2\2\u014e\u014f\7 \2\2\u014f\33\3\2\2\2\u0150\u0151\7 \2\2\u0151"+
		"\35\3\2\2\2\u0152\u0153\7 \2\2\u0153\37\3\2\2\2\u0154\u0155\7 \2\2\u0155"+
		"!\3\2\2\2\u0156\u0157\7 \2\2\u0157#\3\2\2\2\u0158\u0159\7 \2\2\u0159%"+
		"\3\2\2\2\u015a\u015b\7 \2\2\u015b\'\3\2\2\2\u015c\u015d\7 \2\2\u015d)"+
		"\3\2\2\2\'\67@DR[_mv\177\u0088\u008b\u008d\u0096\u00a0\u00aa\u00b4\u00b8"+
		"\u00bd\u00c2\u00c6\u00cf\u00d3\u00de\u00e2\u00ed\u00f1\u00fc\u0100\u010b"+
		"\u010f\u011a\u011e\u0129\u012d\u0131\u0143\u0146";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}