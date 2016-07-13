// Generated from VerilogFluigi.g4 by ANTLR 4.5.1

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
	static { RuntimeMetaData.checkVersion("4.5.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, T__15=16, T__16=17, 
		T__17=18, T__18=19, T__19=20, T__20=21, T__21=22, T__22=23, T__23=24, 
		T__24=25, T__25=26, T__26=27, T__27=28, T__28=29, T__29=30, T__30=31, 
		ID=32, WS=33;
	public static final int
		RULE_root = 0, RULE_modDec = 1, RULE_stats = 2, RULE_stat = 3, RULE_decl = 4, 
		RULE_assignStat = 5, RULE_exp = 6, RULE_lhs = 7, RULE_rhs = 8, RULE_op = 9, 
		RULE_bufferVar = 10, RULE_var = 11, RULE_modName = 12, RULE_input = 13, 
		RULE_output = 14, RULE_finput = 15, RULE_cinput = 16, RULE_foutput = 17, 
		RULE_coutput = 18, RULE_wire = 19, RULE_cchannel = 20, RULE_fchannel = 21;
	public static final String[] ruleNames = {
		"root", "modDec", "stats", "stat", "decl", "assignStat", "exp", "lhs", 
		"rhs", "op", "bufferVar", "var", "modName", "input", "output", "finput", 
		"cinput", "foutput", "coutput", "wire", "cchannel", "fchannel"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'endmodule'", "'module'", "'('", "'input'", "','", "'output'", 
		"')'", "';'", "'finput'", "'foutput'", "'cinput'", "'coutput'", "'wire'", 
		"'cchannel'", "'fchannel'", "'assign'", "'='", "'~'", "'!'", "'@'", "'#'", 
		"'$'", "'%'", "'^'", "'&'", "'*'", "'?'", "'+'", "'-'", "'|'", "'/'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, "ID", "WS"
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
			setState(44);
			modDec();
			setState(45);
			stats();
			setState(46);
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
			setState(191);
			switch ( getInterpreter().adaptivePredict(_input,17,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(48);
				match(T__1);
				setState(49);
				modName();
				setState(50);
				match(T__2);
				setState(51);
				match(T__3);
				setState(55); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(52);
					input();
					setState(53);
					match(T__4);
					}
					}
					setState(57); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==ID );
				setState(59);
				match(T__5);
				setState(70);
				switch ( getInterpreter().adaptivePredict(_input,2,_ctx) ) {
				case 1:
					{
					setState(60);
					output();
					}
					break;
				case 2:
					{
					{
					setState(64); 
					_errHandler.sync(this);
					_alt = 1;
					do {
						switch (_alt) {
						case 1:
							{
							{
							setState(61);
							output();
							setState(62);
							match(T__4);
							}
							}
							break;
						default:
							throw new NoViableAltException(this);
						}
						setState(66); 
						_errHandler.sync(this);
						_alt = getInterpreter().adaptivePredict(_input,1,_ctx);
					} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
					setState(68);
					output();
					}
					}
					break;
				}
				setState(72);
				match(T__6);
				setState(73);
				match(T__7);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(75);
				match(T__1);
				setState(76);
				modName();
				setState(77);
				match(T__2);
				setState(78);
				match(T__5);
				setState(82); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(79);
					output();
					setState(80);
					match(T__4);
					}
					}
					setState(84); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==ID );
				setState(86);
				match(T__3);
				setState(97);
				switch ( getInterpreter().adaptivePredict(_input,5,_ctx) ) {
				case 1:
					{
					setState(87);
					input();
					}
					break;
				case 2:
					{
					{
					setState(91); 
					_errHandler.sync(this);
					_alt = 1;
					do {
						switch (_alt) {
						case 1:
							{
							{
							setState(88);
							input();
							setState(89);
							match(T__4);
							}
							}
							break;
						default:
							throw new NoViableAltException(this);
						}
						setState(93); 
						_errHandler.sync(this);
						_alt = getInterpreter().adaptivePredict(_input,4,_ctx);
					} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
					setState(95);
					input();
					}
					}
					break;
				}
				setState(99);
				match(T__6);
				setState(100);
				match(T__7);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(102);
				match(T__1);
				setState(103);
				modName();
				setState(104);
				match(T__2);
				setState(143);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,11,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						setState(141);
						switch (_input.LA(1)) {
						case T__8:
							{
							{
							setState(105);
							match(T__8);
							setState(111);
							_errHandler.sync(this);
							_la = _input.LA(1);
							while (_la==ID) {
								{
								{
								setState(106);
								finput();
								setState(107);
								match(T__4);
								}
								}
								setState(113);
								_errHandler.sync(this);
								_la = _input.LA(1);
							}
							}
							}
							break;
						case T__9:
							{
							{
							setState(114);
							match(T__9);
							setState(120);
							_errHandler.sync(this);
							_la = _input.LA(1);
							while (_la==ID) {
								{
								{
								setState(115);
								foutput();
								setState(116);
								match(T__4);
								}
								}
								setState(122);
								_errHandler.sync(this);
								_la = _input.LA(1);
							}
							}
							}
							break;
						case T__10:
							{
							{
							setState(123);
							match(T__10);
							setState(129);
							_errHandler.sync(this);
							_la = _input.LA(1);
							while (_la==ID) {
								{
								{
								setState(124);
								cinput();
								setState(125);
								match(T__4);
								}
								}
								setState(131);
								_errHandler.sync(this);
								_la = _input.LA(1);
							}
							}
							}
							break;
						case T__11:
							{
							{
							setState(132);
							match(T__11);
							setState(138);
							_errHandler.sync(this);
							_la = _input.LA(1);
							while (_la==ID) {
								{
								{
								setState(133);
								coutput();
								setState(134);
								match(T__4);
								}
								}
								setState(140);
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
					setState(145);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,11,_ctx);
				}
				setState(186);
				switch (_input.LA(1)) {
				case T__8:
					{
					{
					setState(146);
					match(T__8);
					setState(152);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,12,_ctx);
					while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
						if ( _alt==1 ) {
							{
							{
							setState(147);
							finput();
							setState(148);
							match(T__4);
							}
							} 
						}
						setState(154);
						_errHandler.sync(this);
						_alt = getInterpreter().adaptivePredict(_input,12,_ctx);
					}
					setState(155);
					finput();
					}
					}
					break;
				case T__9:
					{
					{
					setState(156);
					match(T__9);
					setState(162);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,13,_ctx);
					while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
						if ( _alt==1 ) {
							{
							{
							setState(157);
							foutput();
							setState(158);
							match(T__4);
							}
							} 
						}
						setState(164);
						_errHandler.sync(this);
						_alt = getInterpreter().adaptivePredict(_input,13,_ctx);
					}
					setState(165);
					foutput();
					}
					}
					break;
				case T__10:
					{
					{
					setState(166);
					match(T__10);
					setState(172);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,14,_ctx);
					while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
						if ( _alt==1 ) {
							{
							{
							setState(167);
							cinput();
							setState(168);
							match(T__4);
							}
							} 
						}
						setState(174);
						_errHandler.sync(this);
						_alt = getInterpreter().adaptivePredict(_input,14,_ctx);
					}
					setState(175);
					cinput();
					}
					}
					break;
				case T__11:
					{
					{
					setState(176);
					match(T__11);
					setState(182);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,15,_ctx);
					while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
						if ( _alt==1 ) {
							{
							{
							setState(177);
							coutput();
							setState(178);
							match(T__4);
							}
							} 
						}
						setState(184);
						_errHandler.sync(this);
						_alt = getInterpreter().adaptivePredict(_input,15,_ctx);
					}
					setState(185);
					coutput();
					}
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(188);
				match(T__6);
				setState(189);
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
			setState(194); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(193);
				stat();
				}
				}
				setState(196); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__3) | (1L << T__5) | (1L << T__8) | (1L << T__9) | (1L << T__10) | (1L << T__11) | (1L << T__12) | (1L << T__13) | (1L << T__14) | (1L << T__15))) != 0) );
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
			setState(200);
			switch (_input.LA(1)) {
			case T__15:
				enterOuterAlt(_localctx, 1);
				{
				setState(198);
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
			case T__13:
			case T__14:
				enterOuterAlt(_localctx, 2);
				{
				setState(199);
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
		public List<CchannelContext> cchannel() {
			return getRuleContexts(CchannelContext.class);
		}
		public CchannelContext cchannel(int i) {
			return getRuleContext(CchannelContext.class,i);
		}
		public List<FchannelContext> fchannel() {
			return getRuleContexts(FchannelContext.class);
		}
		public FchannelContext fchannel(int i) {
			return getRuleContext(FchannelContext.class,i);
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
			setState(337);
			switch (_input.LA(1)) {
			case T__3:
				enterOuterAlt(_localctx, 1);
				{
				setState(202);
				match(T__3);
				setState(213);
				switch ( getInterpreter().adaptivePredict(_input,21,_ctx) ) {
				case 1:
					{
					setState(203);
					input();
					}
					break;
				case 2:
					{
					{
					setState(207); 
					_errHandler.sync(this);
					_alt = 1;
					do {
						switch (_alt) {
						case 1:
							{
							{
							setState(204);
							input();
							setState(205);
							match(T__4);
							}
							}
							break;
						default:
							throw new NoViableAltException(this);
						}
						setState(209); 
						_errHandler.sync(this);
						_alt = getInterpreter().adaptivePredict(_input,20,_ctx);
					} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
					setState(211);
					input();
					}
					}
					break;
				}
				setState(215);
				match(T__7);
				}
				break;
			case T__8:
				enterOuterAlt(_localctx, 2);
				{
				setState(217);
				match(T__8);
				setState(228);
				switch ( getInterpreter().adaptivePredict(_input,23,_ctx) ) {
				case 1:
					{
					setState(218);
					finput();
					}
					break;
				case 2:
					{
					{
					setState(222); 
					_errHandler.sync(this);
					_alt = 1;
					do {
						switch (_alt) {
						case 1:
							{
							{
							setState(219);
							finput();
							setState(220);
							match(T__4);
							}
							}
							break;
						default:
							throw new NoViableAltException(this);
						}
						setState(224); 
						_errHandler.sync(this);
						_alt = getInterpreter().adaptivePredict(_input,22,_ctx);
					} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
					setState(226);
					finput();
					}
					}
					break;
				}
				setState(230);
				match(T__7);
				}
				break;
			case T__10:
				enterOuterAlt(_localctx, 3);
				{
				setState(232);
				match(T__10);
				setState(243);
				switch ( getInterpreter().adaptivePredict(_input,25,_ctx) ) {
				case 1:
					{
					setState(233);
					cinput();
					}
					break;
				case 2:
					{
					{
					setState(237); 
					_errHandler.sync(this);
					_alt = 1;
					do {
						switch (_alt) {
						case 1:
							{
							{
							setState(234);
							cinput();
							setState(235);
							match(T__4);
							}
							}
							break;
						default:
							throw new NoViableAltException(this);
						}
						setState(239); 
						_errHandler.sync(this);
						_alt = getInterpreter().adaptivePredict(_input,24,_ctx);
					} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
					setState(241);
					cinput();
					}
					}
					break;
				}
				setState(245);
				match(T__7);
				}
				break;
			case T__5:
				enterOuterAlt(_localctx, 4);
				{
				setState(247);
				match(T__5);
				setState(258);
				switch ( getInterpreter().adaptivePredict(_input,27,_ctx) ) {
				case 1:
					{
					setState(248);
					output();
					}
					break;
				case 2:
					{
					{
					setState(252); 
					_errHandler.sync(this);
					_alt = 1;
					do {
						switch (_alt) {
						case 1:
							{
							{
							setState(249);
							output();
							setState(250);
							match(T__4);
							}
							}
							break;
						default:
							throw new NoViableAltException(this);
						}
						setState(254); 
						_errHandler.sync(this);
						_alt = getInterpreter().adaptivePredict(_input,26,_ctx);
					} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
					setState(256);
					output();
					}
					}
					break;
				}
				setState(260);
				match(T__7);
				}
				break;
			case T__11:
				enterOuterAlt(_localctx, 5);
				{
				setState(262);
				match(T__11);
				setState(273);
				switch ( getInterpreter().adaptivePredict(_input,29,_ctx) ) {
				case 1:
					{
					setState(263);
					coutput();
					}
					break;
				case 2:
					{
					{
					setState(267); 
					_errHandler.sync(this);
					_alt = 1;
					do {
						switch (_alt) {
						case 1:
							{
							{
							setState(264);
							coutput();
							setState(265);
							match(T__4);
							}
							}
							break;
						default:
							throw new NoViableAltException(this);
						}
						setState(269); 
						_errHandler.sync(this);
						_alt = getInterpreter().adaptivePredict(_input,28,_ctx);
					} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
					setState(271);
					coutput();
					}
					}
					break;
				}
				setState(275);
				match(T__7);
				}
				break;
			case T__9:
				enterOuterAlt(_localctx, 6);
				{
				setState(277);
				match(T__9);
				setState(288);
				switch ( getInterpreter().adaptivePredict(_input,31,_ctx) ) {
				case 1:
					{
					setState(278);
					foutput();
					}
					break;
				case 2:
					{
					{
					setState(282); 
					_errHandler.sync(this);
					_alt = 1;
					do {
						switch (_alt) {
						case 1:
							{
							{
							setState(279);
							foutput();
							setState(280);
							match(T__4);
							}
							}
							break;
						default:
							throw new NoViableAltException(this);
						}
						setState(284); 
						_errHandler.sync(this);
						_alt = getInterpreter().adaptivePredict(_input,30,_ctx);
					} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
					setState(286);
					foutput();
					}
					}
					break;
				}
				setState(290);
				match(T__7);
				}
				break;
			case T__12:
				enterOuterAlt(_localctx, 7);
				{
				setState(292);
				match(T__12);
				setState(303);
				switch ( getInterpreter().adaptivePredict(_input,33,_ctx) ) {
				case 1:
					{
					setState(293);
					wire();
					}
					break;
				case 2:
					{
					{
					setState(297); 
					_errHandler.sync(this);
					_alt = 1;
					do {
						switch (_alt) {
						case 1:
							{
							{
							setState(294);
							wire();
							setState(295);
							match(T__4);
							}
							}
							break;
						default:
							throw new NoViableAltException(this);
						}
						setState(299); 
						_errHandler.sync(this);
						_alt = getInterpreter().adaptivePredict(_input,32,_ctx);
					} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
					setState(301);
					wire();
					}
					}
					break;
				}
				setState(305);
				match(T__7);
				}
				break;
			case T__13:
				enterOuterAlt(_localctx, 8);
				{
				setState(307);
				match(T__13);
				setState(318);
				switch ( getInterpreter().adaptivePredict(_input,35,_ctx) ) {
				case 1:
					{
					setState(308);
					cchannel();
					}
					break;
				case 2:
					{
					{
					setState(312); 
					_errHandler.sync(this);
					_alt = 1;
					do {
						switch (_alt) {
						case 1:
							{
							{
							setState(309);
							cchannel();
							setState(310);
							match(T__4);
							}
							}
							break;
						default:
							throw new NoViableAltException(this);
						}
						setState(314); 
						_errHandler.sync(this);
						_alt = getInterpreter().adaptivePredict(_input,34,_ctx);
					} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
					setState(316);
					cchannel();
					}
					}
					break;
				}
				setState(320);
				match(T__7);
				}
				break;
			case T__14:
				enterOuterAlt(_localctx, 9);
				{
				setState(322);
				match(T__14);
				setState(333);
				switch ( getInterpreter().adaptivePredict(_input,37,_ctx) ) {
				case 1:
					{
					setState(323);
					fchannel();
					}
					break;
				case 2:
					{
					{
					setState(327); 
					_errHandler.sync(this);
					_alt = 1;
					do {
						switch (_alt) {
						case 1:
							{
							{
							setState(324);
							fchannel();
							setState(325);
							match(T__4);
							}
							}
							break;
						default:
							throw new NoViableAltException(this);
						}
						setState(329); 
						_errHandler.sync(this);
						_alt = getInterpreter().adaptivePredict(_input,36,_ctx);
					} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
					setState(331);
					fchannel();
					}
					}
					break;
				}
				setState(335);
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
			setState(339);
			match(T__15);
			setState(340);
			exp();
			setState(341);
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
			setState(343);
			lhs();
			setState(344);
			match(T__16);
			setState(345);
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
			setState(347);
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
			setState(358);
			switch ( getInterpreter().adaptivePredict(_input,40,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				{
				setState(349);
				var();
				}
				setState(353); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(350);
					op();
					{
					setState(351);
					var();
					}
					}
					}
					setState(355); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__17) | (1L << T__18) | (1L << T__19) | (1L << T__20) | (1L << T__21) | (1L << T__22) | (1L << T__23) | (1L << T__24) | (1L << T__25) | (1L << T__26) | (1L << T__27) | (1L << T__28) | (1L << T__29) | (1L << T__30))) != 0) );
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				{
				setState(357);
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
			setState(360);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__17) | (1L << T__18) | (1L << T__19) | (1L << T__20) | (1L << T__21) | (1L << T__22) | (1L << T__23) | (1L << T__24) | (1L << T__25) | (1L << T__26) | (1L << T__27) | (1L << T__28) | (1L << T__29) | (1L << T__30))) != 0)) ) {
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
			setState(362);
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
			setState(364);
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
			setState(366);
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
			setState(368);
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
			setState(370);
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
			setState(372);
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
			setState(374);
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
			setState(376);
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
			setState(378);
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
			setState(380);
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

	public static class CchannelContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(VerilogFluigiParser.ID, 0); }
		public CchannelContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_cchannel; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof VerilogFluigiListener ) ((VerilogFluigiListener)listener).enterCchannel(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof VerilogFluigiListener ) ((VerilogFluigiListener)listener).exitCchannel(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof VerilogFluigiVisitor ) return ((VerilogFluigiVisitor<? extends T>)visitor).visitCchannel(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CchannelContext cchannel() throws RecognitionException {
		CchannelContext _localctx = new CchannelContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_cchannel);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(382);
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

	public static class FchannelContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(VerilogFluigiParser.ID, 0); }
		public FchannelContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_fchannel; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof VerilogFluigiListener ) ((VerilogFluigiListener)listener).enterFchannel(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof VerilogFluigiListener ) ((VerilogFluigiListener)listener).exitFchannel(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof VerilogFluigiVisitor ) return ((VerilogFluigiVisitor<? extends T>)visitor).visitFchannel(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FchannelContext fchannel() throws RecognitionException {
		FchannelContext _localctx = new FchannelContext(_ctx, getState());
		enterRule(_localctx, 42, RULE_fchannel);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(384);
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
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3#\u0185\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\3\2\3\2\3\2\3\2\3\3"+
		"\3\3\3\3\3\3\3\3\3\3\3\3\6\3:\n\3\r\3\16\3;\3\3\3\3\3\3\3\3\3\3\6\3C\n"+
		"\3\r\3\16\3D\3\3\3\3\5\3I\n\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3"+
		"\6\3U\n\3\r\3\16\3V\3\3\3\3\3\3\3\3\3\3\6\3^\n\3\r\3\16\3_\3\3\3\3\5\3"+
		"d\n\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\7\3p\n\3\f\3\16\3s\13\3"+
		"\3\3\3\3\3\3\3\3\7\3y\n\3\f\3\16\3|\13\3\3\3\3\3\3\3\3\3\7\3\u0082\n\3"+
		"\f\3\16\3\u0085\13\3\3\3\3\3\3\3\3\3\7\3\u008b\n\3\f\3\16\3\u008e\13\3"+
		"\7\3\u0090\n\3\f\3\16\3\u0093\13\3\3\3\3\3\3\3\3\3\7\3\u0099\n\3\f\3\16"+
		"\3\u009c\13\3\3\3\3\3\3\3\3\3\3\3\7\3\u00a3\n\3\f\3\16\3\u00a6\13\3\3"+
		"\3\3\3\3\3\3\3\3\3\7\3\u00ad\n\3\f\3\16\3\u00b0\13\3\3\3\3\3\3\3\3\3\3"+
		"\3\7\3\u00b7\n\3\f\3\16\3\u00ba\13\3\3\3\5\3\u00bd\n\3\3\3\3\3\3\3\5\3"+
		"\u00c2\n\3\3\4\6\4\u00c5\n\4\r\4\16\4\u00c6\3\5\3\5\5\5\u00cb\n\5\3\6"+
		"\3\6\3\6\3\6\3\6\6\6\u00d2\n\6\r\6\16\6\u00d3\3\6\3\6\5\6\u00d8\n\6\3"+
		"\6\3\6\3\6\3\6\3\6\3\6\3\6\6\6\u00e1\n\6\r\6\16\6\u00e2\3\6\3\6\5\6\u00e7"+
		"\n\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\6\6\u00f0\n\6\r\6\16\6\u00f1\3\6\3\6"+
		"\5\6\u00f6\n\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\6\6\u00ff\n\6\r\6\16\6\u0100"+
		"\3\6\3\6\5\6\u0105\n\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\6\6\u010e\n\6\r\6\16"+
		"\6\u010f\3\6\3\6\5\6\u0114\n\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\6\6\u011d\n"+
		"\6\r\6\16\6\u011e\3\6\3\6\5\6\u0123\n\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\6"+
		"\6\u012c\n\6\r\6\16\6\u012d\3\6\3\6\5\6\u0132\n\6\3\6\3\6\3\6\3\6\3\6"+
		"\3\6\3\6\6\6\u013b\n\6\r\6\16\6\u013c\3\6\3\6\5\6\u0141\n\6\3\6\3\6\3"+
		"\6\3\6\3\6\3\6\3\6\6\6\u014a\n\6\r\6\16\6\u014b\3\6\3\6\5\6\u0150\n\6"+
		"\3\6\3\6\5\6\u0154\n\6\3\7\3\7\3\7\3\7\3\b\3\b\3\b\3\b\3\t\3\t\3\n\3\n"+
		"\3\n\3\n\6\n\u0164\n\n\r\n\16\n\u0165\3\n\5\n\u0169\n\n\3\13\3\13\3\f"+
		"\3\f\3\r\3\r\3\16\3\16\3\17\3\17\3\20\3\20\3\21\3\21\3\22\3\22\3\23\3"+
		"\23\3\24\3\24\3\25\3\25\3\26\3\26\3\27\3\27\3\27\2\2\30\2\4\6\b\n\f\16"+
		"\20\22\24\26\30\32\34\36 \"$&(*,\2\3\3\2\24!\u01a3\2.\3\2\2\2\4\u00c1"+
		"\3\2\2\2\6\u00c4\3\2\2\2\b\u00ca\3\2\2\2\n\u0153\3\2\2\2\f\u0155\3\2\2"+
		"\2\16\u0159\3\2\2\2\20\u015d\3\2\2\2\22\u0168\3\2\2\2\24\u016a\3\2\2\2"+
		"\26\u016c\3\2\2\2\30\u016e\3\2\2\2\32\u0170\3\2\2\2\34\u0172\3\2\2\2\36"+
		"\u0174\3\2\2\2 \u0176\3\2\2\2\"\u0178\3\2\2\2$\u017a\3\2\2\2&\u017c\3"+
		"\2\2\2(\u017e\3\2\2\2*\u0180\3\2\2\2,\u0182\3\2\2\2./\5\4\3\2/\60\5\6"+
		"\4\2\60\61\7\3\2\2\61\3\3\2\2\2\62\63\7\4\2\2\63\64\5\32\16\2\64\65\7"+
		"\5\2\2\659\7\6\2\2\66\67\5\34\17\2\678\7\7\2\28:\3\2\2\29\66\3\2\2\2:"+
		";\3\2\2\2;9\3\2\2\2;<\3\2\2\2<=\3\2\2\2=H\7\b\2\2>I\5\36\20\2?@\5\36\20"+
		"\2@A\7\7\2\2AC\3\2\2\2B?\3\2\2\2CD\3\2\2\2DB\3\2\2\2DE\3\2\2\2EF\3\2\2"+
		"\2FG\5\36\20\2GI\3\2\2\2H>\3\2\2\2HB\3\2\2\2IJ\3\2\2\2JK\7\t\2\2KL\7\n"+
		"\2\2L\u00c2\3\2\2\2MN\7\4\2\2NO\5\32\16\2OP\7\5\2\2PT\7\b\2\2QR\5\36\20"+
		"\2RS\7\7\2\2SU\3\2\2\2TQ\3\2\2\2UV\3\2\2\2VT\3\2\2\2VW\3\2\2\2WX\3\2\2"+
		"\2Xc\7\6\2\2Yd\5\34\17\2Z[\5\34\17\2[\\\7\7\2\2\\^\3\2\2\2]Z\3\2\2\2^"+
		"_\3\2\2\2_]\3\2\2\2_`\3\2\2\2`a\3\2\2\2ab\5\34\17\2bd\3\2\2\2cY\3\2\2"+
		"\2c]\3\2\2\2de\3\2\2\2ef\7\t\2\2fg\7\n\2\2g\u00c2\3\2\2\2hi\7\4\2\2ij"+
		"\5\32\16\2j\u0091\7\5\2\2kq\7\13\2\2lm\5 \21\2mn\7\7\2\2np\3\2\2\2ol\3"+
		"\2\2\2ps\3\2\2\2qo\3\2\2\2qr\3\2\2\2r\u0090\3\2\2\2sq\3\2\2\2tz\7\f\2"+
		"\2uv\5$\23\2vw\7\7\2\2wy\3\2\2\2xu\3\2\2\2y|\3\2\2\2zx\3\2\2\2z{\3\2\2"+
		"\2{\u0090\3\2\2\2|z\3\2\2\2}\u0083\7\r\2\2~\177\5\"\22\2\177\u0080\7\7"+
		"\2\2\u0080\u0082\3\2\2\2\u0081~\3\2\2\2\u0082\u0085\3\2\2\2\u0083\u0081"+
		"\3\2\2\2\u0083\u0084\3\2\2\2\u0084\u0090\3\2\2\2\u0085\u0083\3\2\2\2\u0086"+
		"\u008c\7\16\2\2\u0087\u0088\5&\24\2\u0088\u0089\7\7\2\2\u0089\u008b\3"+
		"\2\2\2\u008a\u0087\3\2\2\2\u008b\u008e\3\2\2\2\u008c\u008a\3\2\2\2\u008c"+
		"\u008d\3\2\2\2\u008d\u0090\3\2\2\2\u008e\u008c\3\2\2\2\u008fk\3\2\2\2"+
		"\u008ft\3\2\2\2\u008f}\3\2\2\2\u008f\u0086\3\2\2\2\u0090\u0093\3\2\2\2"+
		"\u0091\u008f\3\2\2\2\u0091\u0092\3\2\2\2\u0092\u00bc\3\2\2\2\u0093\u0091"+
		"\3\2\2\2\u0094\u009a\7\13\2\2\u0095\u0096\5 \21\2\u0096\u0097\7\7\2\2"+
		"\u0097\u0099\3\2\2\2\u0098\u0095\3\2\2\2\u0099\u009c\3\2\2\2\u009a\u0098"+
		"\3\2\2\2\u009a\u009b\3\2\2\2\u009b\u009d\3\2\2\2\u009c\u009a\3\2\2\2\u009d"+
		"\u00bd\5 \21\2\u009e\u00a4\7\f\2\2\u009f\u00a0\5$\23\2\u00a0\u00a1\7\7"+
		"\2\2\u00a1\u00a3\3\2\2\2\u00a2\u009f\3\2\2\2\u00a3\u00a6\3\2\2\2\u00a4"+
		"\u00a2\3\2\2\2\u00a4\u00a5\3\2\2\2\u00a5\u00a7\3\2\2\2\u00a6\u00a4\3\2"+
		"\2\2\u00a7\u00bd\5$\23\2\u00a8\u00ae\7\r\2\2\u00a9\u00aa\5\"\22\2\u00aa"+
		"\u00ab\7\7\2\2\u00ab\u00ad\3\2\2\2\u00ac\u00a9\3\2\2\2\u00ad\u00b0\3\2"+
		"\2\2\u00ae\u00ac\3\2\2\2\u00ae\u00af\3\2\2\2\u00af\u00b1\3\2\2\2\u00b0"+
		"\u00ae\3\2\2\2\u00b1\u00bd\5\"\22\2\u00b2\u00b8\7\16\2\2\u00b3\u00b4\5"+
		"&\24\2\u00b4\u00b5\7\7\2\2\u00b5\u00b7\3\2\2\2\u00b6\u00b3\3\2\2\2\u00b7"+
		"\u00ba\3\2\2\2\u00b8\u00b6\3\2\2\2\u00b8\u00b9\3\2\2\2\u00b9\u00bb\3\2"+
		"\2\2\u00ba\u00b8\3\2\2\2\u00bb\u00bd\5&\24\2\u00bc\u0094\3\2\2\2\u00bc"+
		"\u009e\3\2\2\2\u00bc\u00a8\3\2\2\2\u00bc\u00b2\3\2\2\2\u00bd\u00be\3\2"+
		"\2\2\u00be\u00bf\7\t\2\2\u00bf\u00c0\7\n\2\2\u00c0\u00c2\3\2\2\2\u00c1"+
		"\62\3\2\2\2\u00c1M\3\2\2\2\u00c1h\3\2\2\2\u00c2\5\3\2\2\2\u00c3\u00c5"+
		"\5\b\5\2\u00c4\u00c3\3\2\2\2\u00c5\u00c6\3\2\2\2\u00c6\u00c4\3\2\2\2\u00c6"+
		"\u00c7\3\2\2\2\u00c7\7\3\2\2\2\u00c8\u00cb\5\f\7\2\u00c9\u00cb\5\n\6\2"+
		"\u00ca\u00c8\3\2\2\2\u00ca\u00c9\3\2\2\2\u00cb\t\3\2\2\2\u00cc\u00d7\7"+
		"\6\2\2\u00cd\u00d8\5\34\17\2\u00ce\u00cf\5\34\17\2\u00cf\u00d0\7\7\2\2"+
		"\u00d0\u00d2\3\2\2\2\u00d1\u00ce\3\2\2\2\u00d2\u00d3\3\2\2\2\u00d3\u00d1"+
		"\3\2\2\2\u00d3\u00d4\3\2\2\2\u00d4\u00d5\3\2\2\2\u00d5\u00d6\5\34\17\2"+
		"\u00d6\u00d8\3\2\2\2\u00d7\u00cd\3\2\2\2\u00d7\u00d1\3\2\2\2\u00d8\u00d9"+
		"\3\2\2\2\u00d9\u00da\7\n\2\2\u00da\u0154\3\2\2\2\u00db\u00e6\7\13\2\2"+
		"\u00dc\u00e7\5 \21\2\u00dd\u00de\5 \21\2\u00de\u00df\7\7\2\2\u00df\u00e1"+
		"\3\2\2\2\u00e0\u00dd\3\2\2\2\u00e1\u00e2\3\2\2\2\u00e2\u00e0\3\2\2\2\u00e2"+
		"\u00e3\3\2\2\2\u00e3\u00e4\3\2\2\2\u00e4\u00e5\5 \21\2\u00e5\u00e7\3\2"+
		"\2\2\u00e6\u00dc\3\2\2\2\u00e6\u00e0\3\2\2\2\u00e7\u00e8\3\2\2\2\u00e8"+
		"\u00e9\7\n\2\2\u00e9\u0154\3\2\2\2\u00ea\u00f5\7\r\2\2\u00eb\u00f6\5\""+
		"\22\2\u00ec\u00ed\5\"\22\2\u00ed\u00ee\7\7\2\2\u00ee\u00f0\3\2\2\2\u00ef"+
		"\u00ec\3\2\2\2\u00f0\u00f1\3\2\2\2\u00f1\u00ef\3\2\2\2\u00f1\u00f2\3\2"+
		"\2\2\u00f2\u00f3\3\2\2\2\u00f3\u00f4\5\"\22\2\u00f4\u00f6\3\2\2\2\u00f5"+
		"\u00eb\3\2\2\2\u00f5\u00ef\3\2\2\2\u00f6\u00f7\3\2\2\2\u00f7\u00f8\7\n"+
		"\2\2\u00f8\u0154\3\2\2\2\u00f9\u0104\7\b\2\2\u00fa\u0105\5\36\20\2\u00fb"+
		"\u00fc\5\36\20\2\u00fc\u00fd\7\7\2\2\u00fd\u00ff\3\2\2\2\u00fe\u00fb\3"+
		"\2\2\2\u00ff\u0100\3\2\2\2\u0100\u00fe\3\2\2\2\u0100\u0101\3\2\2\2\u0101"+
		"\u0102\3\2\2\2\u0102\u0103\5\36\20\2\u0103\u0105\3\2\2\2\u0104\u00fa\3"+
		"\2\2\2\u0104\u00fe\3\2\2\2\u0105\u0106\3\2\2\2\u0106\u0107\7\n\2\2\u0107"+
		"\u0154\3\2\2\2\u0108\u0113\7\16\2\2\u0109\u0114\5&\24\2\u010a\u010b\5"+
		"&\24\2\u010b\u010c\7\7\2\2\u010c\u010e\3\2\2\2\u010d\u010a\3\2\2\2\u010e"+
		"\u010f\3\2\2\2\u010f\u010d\3\2\2\2\u010f\u0110\3\2\2\2\u0110\u0111\3\2"+
		"\2\2\u0111\u0112\5&\24\2\u0112\u0114\3\2\2\2\u0113\u0109\3\2\2\2\u0113"+
		"\u010d\3\2\2\2\u0114\u0115\3\2\2\2\u0115\u0116\7\n\2\2\u0116\u0154\3\2"+
		"\2\2\u0117\u0122\7\f\2\2\u0118\u0123\5$\23\2\u0119\u011a\5$\23\2\u011a"+
		"\u011b\7\7\2\2\u011b\u011d\3\2\2\2\u011c\u0119\3\2\2\2\u011d\u011e\3\2"+
		"\2\2\u011e\u011c\3\2\2\2\u011e\u011f\3\2\2\2\u011f\u0120\3\2\2\2\u0120"+
		"\u0121\5$\23\2\u0121\u0123\3\2\2\2\u0122\u0118\3\2\2\2\u0122\u011c\3\2"+
		"\2\2\u0123\u0124\3\2\2\2\u0124\u0125\7\n\2\2\u0125\u0154\3\2\2\2\u0126"+
		"\u0131\7\17\2\2\u0127\u0132\5(\25\2\u0128\u0129\5(\25\2\u0129\u012a\7"+
		"\7\2\2\u012a\u012c\3\2\2\2\u012b\u0128\3\2\2\2\u012c\u012d\3\2\2\2\u012d"+
		"\u012b\3\2\2\2\u012d\u012e\3\2\2\2\u012e\u012f\3\2\2\2\u012f\u0130\5("+
		"\25\2\u0130\u0132\3\2\2\2\u0131\u0127\3\2\2\2\u0131\u012b\3\2\2\2\u0132"+
		"\u0133\3\2\2\2\u0133\u0134\7\n\2\2\u0134\u0154\3\2\2\2\u0135\u0140\7\20"+
		"\2\2\u0136\u0141\5*\26\2\u0137\u0138\5*\26\2\u0138\u0139\7\7\2\2\u0139"+
		"\u013b\3\2\2\2\u013a\u0137\3\2\2\2\u013b\u013c\3\2\2\2\u013c\u013a\3\2"+
		"\2\2\u013c\u013d\3\2\2\2\u013d\u013e\3\2\2\2\u013e\u013f\5*\26\2\u013f"+
		"\u0141\3\2\2\2\u0140\u0136\3\2\2\2\u0140\u013a\3\2\2\2\u0141\u0142\3\2"+
		"\2\2\u0142\u0143\7\n\2\2\u0143\u0154\3\2\2\2\u0144\u014f\7\21\2\2\u0145"+
		"\u0150\5,\27\2\u0146\u0147\5,\27\2\u0147\u0148\7\7\2\2\u0148\u014a\3\2"+
		"\2\2\u0149\u0146\3\2\2\2\u014a\u014b\3\2\2\2\u014b\u0149\3\2\2\2\u014b"+
		"\u014c\3\2\2\2\u014c\u014d\3\2\2\2\u014d\u014e\5,\27\2\u014e\u0150\3\2"+
		"\2\2\u014f\u0145\3\2\2\2\u014f\u0149\3\2\2\2\u0150\u0151\3\2\2\2\u0151"+
		"\u0152\7\n\2\2\u0152\u0154\3\2\2\2\u0153\u00cc\3\2\2\2\u0153\u00db\3\2"+
		"\2\2\u0153\u00ea\3\2\2\2\u0153\u00f9\3\2\2\2\u0153\u0108\3\2\2\2\u0153"+
		"\u0117\3\2\2\2\u0153\u0126\3\2\2\2\u0153\u0135\3\2\2\2\u0153\u0144\3\2"+
		"\2\2\u0154\13\3\2\2\2\u0155\u0156\7\22\2\2\u0156\u0157\5\16\b\2\u0157"+
		"\u0158\7\n\2\2\u0158\r\3\2\2\2\u0159\u015a\5\20\t\2\u015a\u015b\7\23\2"+
		"\2\u015b\u015c\5\22\n\2\u015c\17\3\2\2\2\u015d\u015e\5\30\r\2\u015e\21"+
		"\3\2\2\2\u015f\u0163\5\30\r\2\u0160\u0161\5\24\13\2\u0161\u0162\5\30\r"+
		"\2\u0162\u0164\3\2\2\2\u0163\u0160\3\2\2\2\u0164\u0165\3\2\2\2\u0165\u0163"+
		"\3\2\2\2\u0165\u0166\3\2\2\2\u0166\u0169\3\2\2\2\u0167\u0169\5\26\f\2"+
		"\u0168\u015f\3\2\2\2\u0168\u0167\3\2\2\2\u0169\23\3\2\2\2\u016a\u016b"+
		"\t\2\2\2\u016b\25\3\2\2\2\u016c\u016d\5\30\r\2\u016d\27\3\2\2\2\u016e"+
		"\u016f\7\"\2\2\u016f\31\3\2\2\2\u0170\u0171\7\"\2\2\u0171\33\3\2\2\2\u0172"+
		"\u0173\7\"\2\2\u0173\35\3\2\2\2\u0174\u0175\7\"\2\2\u0175\37\3\2\2\2\u0176"+
		"\u0177\7\"\2\2\u0177!\3\2\2\2\u0178\u0179\7\"\2\2\u0179#\3\2\2\2\u017a"+
		"\u017b\7\"\2\2\u017b%\3\2\2\2\u017c\u017d\7\"\2\2\u017d\'\3\2\2\2\u017e"+
		"\u017f\7\"\2\2\u017f)\3\2\2\2\u0180\u0181\7\"\2\2\u0181+\3\2\2\2\u0182"+
		"\u0183\7\"\2\2\u0183-\3\2\2\2+;DHV_cqz\u0083\u008c\u008f\u0091\u009a\u00a4"+
		"\u00ae\u00b8\u00bc\u00c1\u00c6\u00ca\u00d3\u00d7\u00e2\u00e6\u00f1\u00f5"+
		"\u0100\u0104\u010f\u0113\u011e\u0122\u012d\u0131\u013c\u0140\u014b\u014f"+
		"\u0153\u0165\u0168";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}