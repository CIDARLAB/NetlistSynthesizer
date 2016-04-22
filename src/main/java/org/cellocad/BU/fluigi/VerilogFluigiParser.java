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
		ID=25, WS=26;
	public static final int
		RULE_root = 0, RULE_modDec = 1, RULE_stats = 2, RULE_stat = 3, RULE_decl = 4, 
		RULE_assignStat = 5, RULE_exp = 6, RULE_lhs = 7, RULE_rhs = 8, RULE_op = 9, 
		RULE_modName = 10, RULE_input = 11, RULE_output = 12, RULE_wire = 13;
	public static final String[] ruleNames = {
		"root", "modDec", "stats", "stat", "decl", "assignStat", "exp", "lhs", 
		"rhs", "op", "modName", "input", "output", "wire"
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
	}

	public final RootContext root() throws RecognitionException {
		RootContext _localctx = new RootContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_root);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(28);
			modDec();
			setState(29);
			stats();
			setState(30);
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
	}

	public final ModDecContext modDec() throws RecognitionException {
		ModDecContext _localctx = new ModDecContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_modDec);
		int _la;
		try {
			int _alt;
			setState(84);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,6,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(32);
				match(T__1);
				setState(33);
				modName();
				setState(34);
				match(T__2);
				setState(35);
				match(T__3);
				setState(39); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(36);
					input();
					setState(37);
					match(T__4);
					}
					}
					setState(41); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==ID );
				setState(43);
				match(T__5);
				setState(54);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,2,_ctx) ) {
				case 1:
					{
					setState(44);
					output();
					}
					break;
				case 2:
					{
					{
					setState(48); 
					_errHandler.sync(this);
					_alt = 1;
					do {
						switch (_alt) {
						case 1:
							{
							{
							setState(45);
							output();
							setState(46);
							match(T__4);
							}
							}
							break;
						default:
							throw new NoViableAltException(this);
						}
						setState(50); 
						_errHandler.sync(this);
						_alt = getInterpreter().adaptivePredict(_input,1,_ctx);
					} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
					setState(52);
					output();
					}
					}
					break;
				}
				setState(56);
				match(T__6);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(58);
				match(T__1);
				setState(59);
				modName();
				setState(60);
				match(T__2);
				setState(61);
				match(T__5);
				setState(65); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(62);
					output();
					setState(63);
					match(T__4);
					}
					}
					setState(67); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==ID );
				setState(69);
				match(T__3);
				setState(80);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,5,_ctx) ) {
				case 1:
					{
					setState(70);
					input();
					}
					break;
				case 2:
					{
					{
					setState(74); 
					_errHandler.sync(this);
					_alt = 1;
					do {
						switch (_alt) {
						case 1:
							{
							{
							setState(71);
							input();
							setState(72);
							match(T__4);
							}
							}
							break;
						default:
							throw new NoViableAltException(this);
						}
						setState(76); 
						_errHandler.sync(this);
						_alt = getInterpreter().adaptivePredict(_input,4,_ctx);
					} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
					setState(78);
					input();
					}
					}
					break;
				}
				setState(82);
				match(T__6);
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
	}

	public final StatsContext stats() throws RecognitionException {
		StatsContext _localctx = new StatsContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_stats);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(87); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(86);
				stat();
				}
				}
				setState(89); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__3) | (1L << T__5) | (1L << T__8) | (1L << T__9))) != 0) );
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
	}

	public final StatContext stat() throws RecognitionException {
		StatContext _localctx = new StatContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_stat);
		try {
			setState(97);
			switch (_input.LA(1)) {
			case T__9:
				enterOuterAlt(_localctx, 1);
				{
				setState(91);
				assignStat();
				setState(92);
				match(T__7);
				}
				break;
			case T__3:
			case T__5:
			case T__8:
				enterOuterAlt(_localctx, 2);
				{
				setState(94);
				decl();
				setState(95);
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

	public static class DeclContext extends ParserRuleContext {
		public List<InputContext> input() {
			return getRuleContexts(InputContext.class);
		}
		public InputContext input(int i) {
			return getRuleContext(InputContext.class,i);
		}
		public List<OutputContext> output() {
			return getRuleContexts(OutputContext.class);
		}
		public OutputContext output(int i) {
			return getRuleContext(OutputContext.class,i);
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
	}

	public final DeclContext decl() throws RecognitionException {
		DeclContext _localctx = new DeclContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_decl);
		try {
			int _alt;
			setState(144);
			switch (_input.LA(1)) {
			case T__3:
				enterOuterAlt(_localctx, 1);
				{
				setState(99);
				match(T__3);
				setState(110);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,10,_ctx) ) {
				case 1:
					{
					setState(100);
					input();
					}
					break;
				case 2:
					{
					{
					setState(104); 
					_errHandler.sync(this);
					_alt = 1;
					do {
						switch (_alt) {
						case 1:
							{
							{
							setState(101);
							input();
							setState(102);
							match(T__4);
							}
							}
							break;
						default:
							throw new NoViableAltException(this);
						}
						setState(106); 
						_errHandler.sync(this);
						_alt = getInterpreter().adaptivePredict(_input,9,_ctx);
					} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
					setState(108);
					input();
					}
					}
					break;
				}
				setState(112);
				match(T__7);
				}
				break;
			case T__5:
				enterOuterAlt(_localctx, 2);
				{
				setState(114);
				match(T__5);
				setState(125);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,12,_ctx) ) {
				case 1:
					{
					setState(115);
					output();
					}
					break;
				case 2:
					{
					{
					setState(119); 
					_errHandler.sync(this);
					_alt = 1;
					do {
						switch (_alt) {
						case 1:
							{
							{
							setState(116);
							output();
							setState(117);
							match(T__4);
							}
							}
							break;
						default:
							throw new NoViableAltException(this);
						}
						setState(121); 
						_errHandler.sync(this);
						_alt = getInterpreter().adaptivePredict(_input,11,_ctx);
					} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
					setState(123);
					output();
					}
					}
					break;
				}
				setState(127);
				match(T__7);
				}
				break;
			case T__8:
				enterOuterAlt(_localctx, 3);
				{
				setState(129);
				match(T__8);
				setState(140);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,14,_ctx) ) {
				case 1:
					{
					setState(130);
					wire();
					}
					break;
				case 2:
					{
					{
					setState(134); 
					_errHandler.sync(this);
					_alt = 1;
					do {
						switch (_alt) {
						case 1:
							{
							{
							setState(131);
							wire();
							setState(132);
							match(T__4);
							}
							}
							break;
						default:
							throw new NoViableAltException(this);
						}
						setState(136); 
						_errHandler.sync(this);
						_alt = getInterpreter().adaptivePredict(_input,13,_ctx);
					} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
					setState(138);
					wire();
					}
					}
					break;
				}
				setState(142);
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
	}

	public final AssignStatContext assignStat() throws RecognitionException {
		AssignStatContext _localctx = new AssignStatContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_assignStat);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(146);
			match(T__9);
			setState(147);
			exp();
			setState(148);
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
	}

	public final ExpContext exp() throws RecognitionException {
		ExpContext _localctx = new ExpContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_exp);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(150);
			lhs();
			setState(151);
			match(T__10);
			setState(152);
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
		public OutputContext output() {
			return getRuleContext(OutputContext.class,0);
		}
		public WireContext wire() {
			return getRuleContext(WireContext.class,0);
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
	}

	public final LhsContext lhs() throws RecognitionException {
		LhsContext _localctx = new LhsContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_lhs);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(156);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,16,_ctx) ) {
			case 1:
				{
				setState(154);
				output();
				}
				break;
			case 2:
				{
				setState(155);
				wire();
				}
				break;
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
		public List<OutputContext> output() {
			return getRuleContexts(OutputContext.class);
		}
		public OutputContext output(int i) {
			return getRuleContext(OutputContext.class,i);
		}
		public List<WireContext> wire() {
			return getRuleContexts(WireContext.class);
		}
		public WireContext wire(int i) {
			return getRuleContext(WireContext.class,i);
		}
		public List<InputContext> input() {
			return getRuleContexts(InputContext.class);
		}
		public InputContext input(int i) {
			return getRuleContext(InputContext.class,i);
		}
		public List<OpContext> op() {
			return getRuleContexts(OpContext.class);
		}
		public OpContext op(int i) {
			return getRuleContext(OpContext.class,i);
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
	}

	public final RhsContext rhs() throws RecognitionException {
		RhsContext _localctx = new RhsContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_rhs);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(161);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,17,_ctx) ) {
			case 1:
				{
				setState(158);
				output();
				}
				break;
			case 2:
				{
				setState(159);
				wire();
				}
				break;
			case 3:
				{
				setState(160);
				input();
				}
				break;
			}
			setState(169); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(163);
				op();
				setState(167);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,18,_ctx) ) {
				case 1:
					{
					setState(164);
					output();
					}
					break;
				case 2:
					{
					setState(165);
					wire();
					}
					break;
				case 3:
					{
					setState(166);
					input();
					}
					break;
				}
				}
				}
				setState(171); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__11) | (1L << T__12) | (1L << T__13) | (1L << T__14) | (1L << T__15) | (1L << T__16) | (1L << T__17) | (1L << T__18) | (1L << T__19) | (1L << T__20) | (1L << T__21) | (1L << T__22) | (1L << T__23))) != 0) );
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
	}

	public final OpContext op() throws RecognitionException {
		OpContext _localctx = new OpContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_op);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(173);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__11) | (1L << T__12) | (1L << T__13) | (1L << T__14) | (1L << T__15) | (1L << T__16) | (1L << T__17) | (1L << T__18) | (1L << T__19) | (1L << T__20) | (1L << T__21) | (1L << T__22) | (1L << T__23))) != 0)) ) {
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
	}

	public final ModNameContext modName() throws RecognitionException {
		ModNameContext _localctx = new ModNameContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_modName);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(175);
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
	}

	public final InputContext input() throws RecognitionException {
		InputContext _localctx = new InputContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_input);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(177);
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
	}

	public final OutputContext output() throws RecognitionException {
		OutputContext _localctx = new OutputContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_output);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(179);
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
	}

	public final WireContext wire() throws RecognitionException {
		WireContext _localctx = new WireContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_wire);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(181);
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
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3\34\u00ba\4\2\t\2"+
		"\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\3\2\3\2\3\2\3\2\3\3\3\3\3\3"+
		"\3\3\3\3\3\3\3\3\6\3*\n\3\r\3\16\3+\3\3\3\3\3\3\3\3\3\3\6\3\63\n\3\r\3"+
		"\16\3\64\3\3\3\3\5\39\n\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\6\3D\n\3"+
		"\r\3\16\3E\3\3\3\3\3\3\3\3\3\3\6\3M\n\3\r\3\16\3N\3\3\3\3\5\3S\n\3\3\3"+
		"\3\3\5\3W\n\3\3\4\6\4Z\n\4\r\4\16\4[\3\5\3\5\3\5\3\5\3\5\3\5\5\5d\n\5"+
		"\3\6\3\6\3\6\3\6\3\6\6\6k\n\6\r\6\16\6l\3\6\3\6\5\6q\n\6\3\6\3\6\3\6\3"+
		"\6\3\6\3\6\3\6\6\6z\n\6\r\6\16\6{\3\6\3\6\5\6\u0080\n\6\3\6\3\6\3\6\3"+
		"\6\3\6\3\6\3\6\6\6\u0089\n\6\r\6\16\6\u008a\3\6\3\6\5\6\u008f\n\6\3\6"+
		"\3\6\5\6\u0093\n\6\3\7\3\7\3\7\3\7\3\b\3\b\3\b\3\b\3\t\3\t\5\t\u009f\n"+
		"\t\3\n\3\n\3\n\5\n\u00a4\n\n\3\n\3\n\3\n\3\n\5\n\u00aa\n\n\6\n\u00ac\n"+
		"\n\r\n\16\n\u00ad\3\13\3\13\3\f\3\f\3\r\3\r\3\16\3\16\3\17\3\17\3\17\2"+
		"\2\20\2\4\6\b\n\f\16\20\22\24\26\30\32\34\2\3\3\2\16\32\u00c2\2\36\3\2"+
		"\2\2\4V\3\2\2\2\6Y\3\2\2\2\bc\3\2\2\2\n\u0092\3\2\2\2\f\u0094\3\2\2\2"+
		"\16\u0098\3\2\2\2\20\u009e\3\2\2\2\22\u00a3\3\2\2\2\24\u00af\3\2\2\2\26"+
		"\u00b1\3\2\2\2\30\u00b3\3\2\2\2\32\u00b5\3\2\2\2\34\u00b7\3\2\2\2\36\37"+
		"\5\4\3\2\37 \5\6\4\2 !\7\3\2\2!\3\3\2\2\2\"#\7\4\2\2#$\5\26\f\2$%\7\5"+
		"\2\2%)\7\6\2\2&\'\5\30\r\2\'(\7\7\2\2(*\3\2\2\2)&\3\2\2\2*+\3\2\2\2+)"+
		"\3\2\2\2+,\3\2\2\2,-\3\2\2\2-8\7\b\2\2.9\5\32\16\2/\60\5\32\16\2\60\61"+
		"\7\7\2\2\61\63\3\2\2\2\62/\3\2\2\2\63\64\3\2\2\2\64\62\3\2\2\2\64\65\3"+
		"\2\2\2\65\66\3\2\2\2\66\67\5\32\16\2\679\3\2\2\28.\3\2\2\28\62\3\2\2\2"+
		"9:\3\2\2\2:;\7\t\2\2;W\3\2\2\2<=\7\4\2\2=>\5\26\f\2>?\7\5\2\2?C\7\b\2"+
		"\2@A\5\32\16\2AB\7\7\2\2BD\3\2\2\2C@\3\2\2\2DE\3\2\2\2EC\3\2\2\2EF\3\2"+
		"\2\2FG\3\2\2\2GR\7\6\2\2HS\5\30\r\2IJ\5\30\r\2JK\7\7\2\2KM\3\2\2\2LI\3"+
		"\2\2\2MN\3\2\2\2NL\3\2\2\2NO\3\2\2\2OP\3\2\2\2PQ\5\30\r\2QS\3\2\2\2RH"+
		"\3\2\2\2RL\3\2\2\2ST\3\2\2\2TU\7\t\2\2UW\3\2\2\2V\"\3\2\2\2V<\3\2\2\2"+
		"W\5\3\2\2\2XZ\5\b\5\2YX\3\2\2\2Z[\3\2\2\2[Y\3\2\2\2[\\\3\2\2\2\\\7\3\2"+
		"\2\2]^\5\f\7\2^_\7\n\2\2_d\3\2\2\2`a\5\n\6\2ab\7\n\2\2bd\3\2\2\2c]\3\2"+
		"\2\2c`\3\2\2\2d\t\3\2\2\2ep\7\6\2\2fq\5\30\r\2gh\5\30\r\2hi\7\7\2\2ik"+
		"\3\2\2\2jg\3\2\2\2kl\3\2\2\2lj\3\2\2\2lm\3\2\2\2mn\3\2\2\2no\5\30\r\2"+
		"oq\3\2\2\2pf\3\2\2\2pj\3\2\2\2qr\3\2\2\2rs\7\n\2\2s\u0093\3\2\2\2t\177"+
		"\7\b\2\2u\u0080\5\32\16\2vw\5\32\16\2wx\7\7\2\2xz\3\2\2\2yv\3\2\2\2z{"+
		"\3\2\2\2{y\3\2\2\2{|\3\2\2\2|}\3\2\2\2}~\5\32\16\2~\u0080\3\2\2\2\177"+
		"u\3\2\2\2\177y\3\2\2\2\u0080\u0081\3\2\2\2\u0081\u0082\7\n\2\2\u0082\u0093"+
		"\3\2\2\2\u0083\u008e\7\13\2\2\u0084\u008f\5\34\17\2\u0085\u0086\5\34\17"+
		"\2\u0086\u0087\7\7\2\2\u0087\u0089\3\2\2\2\u0088\u0085\3\2\2\2\u0089\u008a"+
		"\3\2\2\2\u008a\u0088\3\2\2\2\u008a\u008b\3\2\2\2\u008b\u008c\3\2\2\2\u008c"+
		"\u008d\5\34\17\2\u008d\u008f\3\2\2\2\u008e\u0084\3\2\2\2\u008e\u0088\3"+
		"\2\2\2\u008f\u0090\3\2\2\2\u0090\u0091\7\n\2\2\u0091\u0093\3\2\2\2\u0092"+
		"e\3\2\2\2\u0092t\3\2\2\2\u0092\u0083\3\2\2\2\u0093\13\3\2\2\2\u0094\u0095"+
		"\7\f\2\2\u0095\u0096\5\16\b\2\u0096\u0097\7\n\2\2\u0097\r\3\2\2\2\u0098"+
		"\u0099\5\20\t\2\u0099\u009a\7\r\2\2\u009a\u009b\5\22\n\2\u009b\17\3\2"+
		"\2\2\u009c\u009f\5\32\16\2\u009d\u009f\5\34\17\2\u009e\u009c\3\2\2\2\u009e"+
		"\u009d\3\2\2\2\u009f\21\3\2\2\2\u00a0\u00a4\5\32\16\2\u00a1\u00a4\5\34"+
		"\17\2\u00a2\u00a4\5\30\r\2\u00a3\u00a0\3\2\2\2\u00a3\u00a1\3\2\2\2\u00a3"+
		"\u00a2\3\2\2\2\u00a4\u00ab\3\2\2\2\u00a5\u00a9\5\24\13\2\u00a6\u00aa\5"+
		"\32\16\2\u00a7\u00aa\5\34\17\2\u00a8\u00aa\5\30\r\2\u00a9\u00a6\3\2\2"+
		"\2\u00a9\u00a7\3\2\2\2\u00a9\u00a8\3\2\2\2\u00aa\u00ac\3\2\2\2\u00ab\u00a5"+
		"\3\2\2\2\u00ac\u00ad\3\2\2\2\u00ad\u00ab\3\2\2\2\u00ad\u00ae\3\2\2\2\u00ae"+
		"\23\3\2\2\2\u00af\u00b0\t\2\2\2\u00b0\25\3\2\2\2\u00b1\u00b2\7\33\2\2"+
		"\u00b2\27\3\2\2\2\u00b3\u00b4\7\33\2\2\u00b4\31\3\2\2\2\u00b5\u00b6\7"+
		"\33\2\2\u00b6\33\3\2\2\2\u00b7\u00b8\7\33\2\2\u00b8\35\3\2\2\2\26+\64"+
		"8ENRV[clp{\177\u008a\u008e\u0092\u009e\u00a3\u00a9\u00ad";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}