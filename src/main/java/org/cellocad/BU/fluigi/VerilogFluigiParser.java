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
		RULE_var = 10, RULE_modName = 11, RULE_input = 12, RULE_output = 13, RULE_wire = 14;
	public static final String[] ruleNames = {
		"root", "modDec", "stats", "stat", "decl", "assignStat", "exp", "lhs", 
		"rhs", "op", "var", "modName", "input", "output", "wire"
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
			setState(30);
			modDec();
			setState(31);
			stats();
			setState(32);
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
			setState(88);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,6,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(34);
				match(T__1);
				setState(35);
				modName();
				setState(36);
				match(T__2);
				setState(37);
				match(T__3);
				setState(41); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(38);
					input();
					setState(39);
					match(T__4);
					}
					}
					setState(43); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==ID );
				setState(45);
				match(T__5);
				setState(56);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,2,_ctx) ) {
				case 1:
					{
					setState(46);
					output();
					}
					break;
				case 2:
					{
					{
					setState(50); 
					_errHandler.sync(this);
					_alt = 1;
					do {
						switch (_alt) {
						case 1:
							{
							{
							setState(47);
							output();
							setState(48);
							match(T__4);
							}
							}
							break;
						default:
							throw new NoViableAltException(this);
						}
						setState(52); 
						_errHandler.sync(this);
						_alt = getInterpreter().adaptivePredict(_input,1,_ctx);
					} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
					setState(54);
					output();
					}
					}
					break;
				}
				setState(58);
				match(T__6);
				setState(59);
				match(T__7);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(61);
				match(T__1);
				setState(62);
				modName();
				setState(63);
				match(T__2);
				setState(64);
				match(T__5);
				setState(68); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(65);
					output();
					setState(66);
					match(T__4);
					}
					}
					setState(70); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==ID );
				setState(72);
				match(T__3);
				setState(83);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,5,_ctx) ) {
				case 1:
					{
					setState(73);
					input();
					}
					break;
				case 2:
					{
					{
					setState(77); 
					_errHandler.sync(this);
					_alt = 1;
					do {
						switch (_alt) {
						case 1:
							{
							{
							setState(74);
							input();
							setState(75);
							match(T__4);
							}
							}
							break;
						default:
							throw new NoViableAltException(this);
						}
						setState(79); 
						_errHandler.sync(this);
						_alt = getInterpreter().adaptivePredict(_input,4,_ctx);
					} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
					setState(81);
					input();
					}
					}
					break;
				}
				setState(85);
				match(T__6);
				setState(86);
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
	}

	public final StatsContext stats() throws RecognitionException {
		StatsContext _localctx = new StatsContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_stats);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(91); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(90);
				stat();
				}
				}
				setState(93); 
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
				setState(95);
				assignStat();
				}
				break;
			case T__3:
			case T__5:
			case T__8:
				enterOuterAlt(_localctx, 2);
				{
				setState(96);
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
	}

	public final LhsContext lhs() throws RecognitionException {
		LhsContext _localctx = new LhsContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_lhs);
		try {
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(154);
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
			{
			setState(156);
			var();
			}
			setState(160); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(157);
				op();
				{
				setState(158);
				var();
				}
				}
				}
				setState(162); 
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
			setState(164);
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
	}

	public final VarContext var() throws RecognitionException {
		VarContext _localctx = new VarContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_var);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(166);
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
	}

	public final ModNameContext modName() throws RecognitionException {
		ModNameContext _localctx = new ModNameContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_modName);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(168);
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
		enterRule(_localctx, 24, RULE_input);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(170);
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
		enterRule(_localctx, 26, RULE_output);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(172);
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
		enterRule(_localctx, 28, RULE_wire);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(174);
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
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3\34\u00b3\4\2\t\2"+
		"\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\3\2\3\2\3\2\3\2\3"+
		"\3\3\3\3\3\3\3\3\3\3\3\3\3\6\3,\n\3\r\3\16\3-\3\3\3\3\3\3\3\3\3\3\6\3"+
		"\65\n\3\r\3\16\3\66\3\3\3\3\5\3;\n\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3"+
		"\3\3\3\6\3G\n\3\r\3\16\3H\3\3\3\3\3\3\3\3\3\3\6\3P\n\3\r\3\16\3Q\3\3\3"+
		"\3\5\3V\n\3\3\3\3\3\3\3\5\3[\n\3\3\4\6\4^\n\4\r\4\16\4_\3\5\3\5\5\5d\n"+
		"\5\3\6\3\6\3\6\3\6\3\6\6\6k\n\6\r\6\16\6l\3\6\3\6\5\6q\n\6\3\6\3\6\3\6"+
		"\3\6\3\6\3\6\3\6\6\6z\n\6\r\6\16\6{\3\6\3\6\5\6\u0080\n\6\3\6\3\6\3\6"+
		"\3\6\3\6\3\6\3\6\6\6\u0089\n\6\r\6\16\6\u008a\3\6\3\6\5\6\u008f\n\6\3"+
		"\6\3\6\5\6\u0093\n\6\3\7\3\7\3\7\3\7\3\b\3\b\3\b\3\b\3\t\3\t\3\n\3\n\3"+
		"\n\3\n\6\n\u00a3\n\n\r\n\16\n\u00a4\3\13\3\13\3\f\3\f\3\r\3\r\3\16\3\16"+
		"\3\17\3\17\3\20\3\20\3\20\2\2\21\2\4\6\b\n\f\16\20\22\24\26\30\32\34\36"+
		"\2\3\3\2\16\32\u00b5\2 \3\2\2\2\4Z\3\2\2\2\6]\3\2\2\2\bc\3\2\2\2\n\u0092"+
		"\3\2\2\2\f\u0094\3\2\2\2\16\u0098\3\2\2\2\20\u009c\3\2\2\2\22\u009e\3"+
		"\2\2\2\24\u00a6\3\2\2\2\26\u00a8\3\2\2\2\30\u00aa\3\2\2\2\32\u00ac\3\2"+
		"\2\2\34\u00ae\3\2\2\2\36\u00b0\3\2\2\2 !\5\4\3\2!\"\5\6\4\2\"#\7\3\2\2"+
		"#\3\3\2\2\2$%\7\4\2\2%&\5\30\r\2&\'\7\5\2\2\'+\7\6\2\2()\5\32\16\2)*\7"+
		"\7\2\2*,\3\2\2\2+(\3\2\2\2,-\3\2\2\2-+\3\2\2\2-.\3\2\2\2./\3\2\2\2/:\7"+
		"\b\2\2\60;\5\34\17\2\61\62\5\34\17\2\62\63\7\7\2\2\63\65\3\2\2\2\64\61"+
		"\3\2\2\2\65\66\3\2\2\2\66\64\3\2\2\2\66\67\3\2\2\2\678\3\2\2\289\5\34"+
		"\17\29;\3\2\2\2:\60\3\2\2\2:\64\3\2\2\2;<\3\2\2\2<=\7\t\2\2=>\7\n\2\2"+
		">[\3\2\2\2?@\7\4\2\2@A\5\30\r\2AB\7\5\2\2BF\7\b\2\2CD\5\34\17\2DE\7\7"+
		"\2\2EG\3\2\2\2FC\3\2\2\2GH\3\2\2\2HF\3\2\2\2HI\3\2\2\2IJ\3\2\2\2JU\7\6"+
		"\2\2KV\5\32\16\2LM\5\32\16\2MN\7\7\2\2NP\3\2\2\2OL\3\2\2\2PQ\3\2\2\2Q"+
		"O\3\2\2\2QR\3\2\2\2RS\3\2\2\2ST\5\32\16\2TV\3\2\2\2UK\3\2\2\2UO\3\2\2"+
		"\2VW\3\2\2\2WX\7\t\2\2XY\7\n\2\2Y[\3\2\2\2Z$\3\2\2\2Z?\3\2\2\2[\5\3\2"+
		"\2\2\\^\5\b\5\2]\\\3\2\2\2^_\3\2\2\2_]\3\2\2\2_`\3\2\2\2`\7\3\2\2\2ad"+
		"\5\f\7\2bd\5\n\6\2ca\3\2\2\2cb\3\2\2\2d\t\3\2\2\2ep\7\6\2\2fq\5\32\16"+
		"\2gh\5\32\16\2hi\7\7\2\2ik\3\2\2\2jg\3\2\2\2kl\3\2\2\2lj\3\2\2\2lm\3\2"+
		"\2\2mn\3\2\2\2no\5\32\16\2oq\3\2\2\2pf\3\2\2\2pj\3\2\2\2qr\3\2\2\2rs\7"+
		"\n\2\2s\u0093\3\2\2\2t\177\7\b\2\2u\u0080\5\34\17\2vw\5\34\17\2wx\7\7"+
		"\2\2xz\3\2\2\2yv\3\2\2\2z{\3\2\2\2{y\3\2\2\2{|\3\2\2\2|}\3\2\2\2}~\5\34"+
		"\17\2~\u0080\3\2\2\2\177u\3\2\2\2\177y\3\2\2\2\u0080\u0081\3\2\2\2\u0081"+
		"\u0082\7\n\2\2\u0082\u0093\3\2\2\2\u0083\u008e\7\13\2\2\u0084\u008f\5"+
		"\36\20\2\u0085\u0086\5\36\20\2\u0086\u0087\7\7\2\2\u0087\u0089\3\2\2\2"+
		"\u0088\u0085\3\2\2\2\u0089\u008a\3\2\2\2\u008a\u0088\3\2\2\2\u008a\u008b"+
		"\3\2\2\2\u008b\u008c\3\2\2\2\u008c\u008d\5\36\20\2\u008d\u008f\3\2\2\2"+
		"\u008e\u0084\3\2\2\2\u008e\u0088\3\2\2\2\u008f\u0090\3\2\2\2\u0090\u0091"+
		"\7\n\2\2\u0091\u0093\3\2\2\2\u0092e\3\2\2\2\u0092t\3\2\2\2\u0092\u0083"+
		"\3\2\2\2\u0093\13\3\2\2\2\u0094\u0095\7\f\2\2\u0095\u0096\5\16\b\2\u0096"+
		"\u0097\7\n\2\2\u0097\r\3\2\2\2\u0098\u0099\5\20\t\2\u0099\u009a\7\r\2"+
		"\2\u009a\u009b\5\22\n\2\u009b\17\3\2\2\2\u009c\u009d\5\26\f\2\u009d\21"+
		"\3\2\2\2\u009e\u00a2\5\26\f\2\u009f\u00a0\5\24\13\2\u00a0\u00a1\5\26\f"+
		"\2\u00a1\u00a3\3\2\2\2\u00a2\u009f\3\2\2\2\u00a3\u00a4\3\2\2\2\u00a4\u00a2"+
		"\3\2\2\2\u00a4\u00a5\3\2\2\2\u00a5\23\3\2\2\2\u00a6\u00a7\t\2\2\2\u00a7"+
		"\25\3\2\2\2\u00a8\u00a9\7\33\2\2\u00a9\27\3\2\2\2\u00aa\u00ab\7\33\2\2"+
		"\u00ab\31\3\2\2\2\u00ac\u00ad\7\33\2\2\u00ad\33\3\2\2\2\u00ae\u00af\7"+
		"\33\2\2\u00af\35\3\2\2\2\u00b0\u00b1\7\33\2\2\u00b1\37\3\2\2\2\23-\66"+
		":HQUZ_clp{\177\u008a\u008e\u0092\u00a4";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}