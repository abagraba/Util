//### This file created by BYACC 1.8(/Java extension  1.15)
//### Java capabilities added 7 Jan 97, Bob Jamison
//### Updated : 27 Nov 97  -- Bob Jamison, Joe Nieten
//###           01 Jan 98  -- Bob Jamison -- fixed generic semantic constructor
//###           01 Jun 99  -- Bob Jamison -- added Runnable support
//###           06 Aug 00  -- Bob Jamison -- made state variables class-global
//###           03 Jan 01  -- Bob Jamison -- improved flags, tracing
//###           16 May 01  -- Bob Jamison -- added custom stack sizing
//###           04 Mar 02  -- Yuval Oren  -- improved java performance, added options
//###           14 Mar 02  -- Tomas Hurka -- -d support, static initializer workaround
//### Please send bug reports to tom@hukatronic.cz
//### static char yysccsid[] = "@(#)yaccpar	1.8 (Berkeley) 01/20/90";

package util.io.fileformat.obj.parsing;

//#line 2 "obj.y"
import java.io.IOException;
import java.io.Reader;



//#line 23 "OBJParser.java"

public class OBJParser {

	boolean	yydebug;	// do I want debug output?
	int		yynerrs;	// number of errors so far
	int		yyerrflag;	// was there an error?
	int		yychar;	// the current working character

	// ########## MESSAGES ##########
	// ###############################################################
	// method: debug
	// ###############################################################
	void debug(String msg) {
		if (yydebug)
			System.out.println(msg);
	}

	// ########## STATE STACK ##########
	final static int	YYSTACKSIZE	= 500;					// maximum stack size
	int					statestk[]	= new int[YYSTACKSIZE]; // state stack
	int					stateptr;
	int					stateptrmax;						// highest index of stackptr
	int					statemax;							// state when highest index reached
															// ###############################################################
															// methods: state stack push,pop,drop,peek
															// ###############################################################

	final void state_push(int state) {
		try {
			stateptr++;
			statestk[stateptr] = state;
		}
		catch (ArrayIndexOutOfBoundsException e) {
			int oldsize = statestk.length;
			int newsize = oldsize * 2;
			int[] newstack = new int[newsize];
			System.arraycopy(statestk, 0, newstack, 0, oldsize);
			statestk = newstack;
			statestk[stateptr] = state;
		}
	}

	final int state_pop() {
		return statestk[stateptr--];
	}

	final void state_drop(int cnt) {
		stateptr -= cnt;
	}

	final int state_peek(int relative) {
		return statestk[stateptr - relative];
	}

	// ###############################################################
	// method: init_stacks : allocate and prepare stacks
	// ###############################################################
	final boolean init_stacks() {
		stateptr = -1;
		val_init();
		return true;
	}

	// ###############################################################
	// method: dump_stacks : show n levels of the stacks
	// ###############################################################
	void dump_stacks(int count) {
		int i;
		System.out.println("=index==state====value=     s:" + stateptr + "  v:" + valptr);
		for (i = 0; i < count; i++)
			System.out.println(" " + i + "    " + statestk[i] + "      " + valstk[i]);
		System.out.println("======================");
	}

	// ########## SEMANTIC VALUES ##########
	// ## **user defined:OBJValue
	String		yytext;										// user variable to return contextual strings
	OBJValue	yyval;										// used to return semantic vals from action routines
	OBJValue	yylval;										// the 'lval' (result) I got from yylex()
	OBJValue	valstk[]	= new OBJValue[YYSTACKSIZE];
	int			valptr;

	// ###############################################################
	// methods: value stack push,pop,drop,peek.
	// ###############################################################
	final void val_init() {
		yyval = new OBJValue();
		yylval = new OBJValue();
		valptr = -1;
	}

	final void val_push(OBJValue val) {
		try {
			valptr++;
			valstk[valptr] = val;
		}
		catch (ArrayIndexOutOfBoundsException e) {
			int oldsize = valstk.length;
			int newsize = oldsize * 2;
			OBJValue[] newstack = new OBJValue[newsize];
			System.arraycopy(valstk, 0, newstack, 0, oldsize);
			valstk = newstack;
			valstk[valptr] = val;
		}
	}

	final OBJValue val_pop() {
		return valstk[valptr--];
	}

	final void val_drop(int cnt) {
		valptr -= cnt;
	}

	final OBJValue val_peek(int relative) {
		return valstk[valptr - relative];
	}

	final OBJValue dup_yyval(OBJValue val) {
		return val;
	}

	// #### end semantic value section ####
	public final static short	INT				= 257;
	public final static short	FLOAT			= 258;
	public final static short	VERTEX			= 259;
	public final static short	TEXTURE			= 260;
	public final static short	NORMAL			= 261;
	public final static short	PARAMETER		= 262;
	public final static short	GROUP			= 263;
	public final static short	SMOOTH			= 264;
	public final static short	MERGE			= 265;
	public final static short	OBJECT			= 266;
	public final static short	DEGREE			= 267;
	public final static short	BASIS			= 268;
	public final static short	STEP			= 269;
	public final static short	CSTYPE			= 270;
	public final static short	POINT			= 271;
	public final static short	LINE			= 272;
	public final static short	FACE			= 273;
	public final static short	CURVE			= 274;
	public final static short	CURVE2D			= 275;
	public final static short	SURFACE			= 276;
	public final static short	PARAM			= 277;
	public final static short	TRIMOUT			= 278;
	public final static short	TRIMIN			= 279;
	public final static short	SPECCURVE		= 280;
	public final static short	SPECPOINT		= 281;
	public final static short	END				= 282;
	public final static short	CONNECT			= 283;
	public final static short	BEVEL			= 284;
	public final static short	CINTER			= 285;
	public final static short	DINTER			= 286;
	public final static short	LOD				= 287;
	public final static short	MATERIAL		= 288;
	public final static short	LIBRARY			= 289;
	public final static short	SHADOW			= 290;
	public final static short	RAYTRACE		= 291;
	public final static short	CURVEAPPROX		= 292;
	public final static short	SURFACEAPPROX	= 293;
	public final static short	YYERRCODE		= 256;
	final static short			yylhs[]			= { -1, 0, 0, 1, 1, 1, 2, 3, 4, };
	final static short			yylen[]			= { 2, 1, 2, 1, 1, 1, 4, 3, 4, };
	final static short			yydefred[]		= { 0, 0, 0, 0, 0, 1, 3, 4, 5, 0, 0, 0, 2, 0, 7, 0, 6, 8, };
	final static short			yydgoto[]		= { 4, 5, 6, 7, 8, };
	final static short			yysindex[]		= { -259, -255, -254, -253, -259, 0, 0, 0, 0, -252, -251, -250, 0, -249, 0, -248, 0, 0, };
	final static short			yyrindex[]		= { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, };
	final static short			yygindex[]		= { 0, 7, 0, 0, 0, };
	final static int			YYTABLESIZE		= 11;
	static short				yytable[];
	static {
		yytable();
	}

	static void yytable() {
		yytable = new short[] { 1, 2, 3, 9, 10, 11, 13, 14, 15, 16, 17, 12, };
	}

	static short	yycheck[];
	static {
		yycheck();
	}

	static void yycheck() {
		yycheck = new short[] { 259, 260, 261, 258, 258, 258, 258, 258, 258, 258, 258, 4, };
	}

	final static short		YYFINAL		= 4;
	final static short		YYMAXTOKEN	= 293;
	final static String		yyname[]	= { "end-of-file", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
			null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
			null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
			null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
			null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
			null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
			null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
			null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
			null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
			null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
			null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
			null, null, null, null, null, null, null, null, null, null, null, "INT", "FLOAT", "VERTEX", "TEXTURE", "NORMAL", "PARAMETER", "GROUP",
			"SMOOTH", "MERGE", "OBJECT", "DEGREE", "BASIS", "STEP", "CSTYPE", "POINT", "LINE", "FACE", "CURVE", "CURVE2D", "SURFACE", "PARAM",
			"TRIMOUT", "TRIMIN", "SPECCURVE", "SPECPOINT", "END", "CONNECT", "BEVEL", "CINTER", "DINTER", "LOD", "MATERIAL", "LIBRARY", "SHADOW",
			"RAYTRACE", "CURVEAPPROX", "SURFACEAPPROX", };
	final static String		yyrule[]	= { "$accept : file", "file : stmt", "file : file stmt", "stmt : vertex", "stmt : texture", "stmt : normal",
			"vertex : VERTEX FLOAT FLOAT FLOAT", "texture : TEXTURE FLOAT FLOAT", "normal : NORMAL FLOAT FLOAT FLOAT", };

	// #line 78 "obj.y"
	public static boolean	debug		= false;
	private OBJLexer		lexer;

	private OBJRawData		data		= new OBJRawData();

	private int yylex() {
		int token = -1;
		try {
			token = lexer.yylex();
			// System.out.println(yyname[token]);
		}
		catch (IOException e) {
			System.err.println("IO error :" + e);
		}
		return token;
	}

	public void yyerror(String error) {
		System.out.print("Error: [" + (lexer.line() + 1) + ':' + lexer.yytext() + "] :" + error);
	}

	public OBJParser(Reader r) {
		lexer = new OBJLexer(r, this);
	}

	// #line 239 "OBJParser.java"
	// ###############################################################
	// method: yylexdebug : check lexer state
	// ###############################################################
	void yylexdebug(int state, int ch) {
		String s = null;
		if (ch < 0)
			ch = 0;
		if (ch <= YYMAXTOKEN) // check index bounds
			s = yyname[ch]; // now get it
		if (s == null)
			s = "illegal-symbol";
		debug("state " + state + ", reading " + ch + " (" + s + ")");
	}

	// The following are now global, to aid in error reporting
	int		yyn;		// next next thing to do
	int		yym;		//
	int		yystate;	// current parsing state from state table
	String	yys;		// current token string

	// ###############################################################
	// method: yyparse : parse input and execute indicated items
	// ###############################################################
	int yyparse() {
		boolean doaction;
		init_stacks();
		yynerrs = 0;
		yyerrflag = 0;
		yychar = -1; // impossible char forces a read
		yystate = 0; // initial state
		state_push(yystate); // save it
		val_push(yylval); // save empty value
		while (true) // until parsing is done, either correctly, or w/error
		{
			doaction = true;
			if (yydebug)
				debug("loop");
			// #### NEXT ACTION (from reduction table)
			for (yyn = yydefred[yystate]; yyn == 0; yyn = yydefred[yystate]) {
				if (yydebug)
					debug("yyn:" + yyn + "  state:" + yystate + "  yychar:" + yychar);
				if (yychar < 0) // we want a char?
				{
					yychar = yylex(); // get next token
					if (yydebug)
						debug(" next yychar:" + yychar);
					// #### ERROR CHECK ####
					if (yychar < 0) // it it didn't work/error
					{
						yychar = 0; // change it to default string (no -1!)
						if (yydebug)
							yylexdebug(yystate, yychar);
					}
				}// yychar<0
				yyn = yysindex[yystate]; // get amount to shift by (shift index)
				if (yyn != 0 && (yyn += yychar) >= 0 && yyn <= YYTABLESIZE && yycheck[yyn] == yychar) {
					if (yydebug)
						debug("state " + yystate + ", shifting to state " + yytable[yyn]);
					// #### NEXT STATE ####
					yystate = yytable[yyn];// we are in a new state
					state_push(yystate); // save it
					val_push(yylval); // push our lval as the input for next rule
					yychar = -1; // since we have 'eaten' a token, say we need another
					if (yyerrflag > 0) // have we recovered an error?
						--yyerrflag; // give ourselves credit
					doaction = false; // but don't process yet
					break; // quit the yyn=0 loop
				}

				yyn = yyrindex[yystate]; // reduce
				if (yyn != 0 && (yyn += yychar) >= 0 && yyn <= YYTABLESIZE && yycheck[yyn] == yychar) { // we reduced!
					if (yydebug)
						debug("reduce");
					yyn = yytable[yyn];
					doaction = true; // get ready to execute
					break; // drop down to actions
				}
				else // ERROR RECOVERY
				{
					if (yyerrflag == 0) {
						yyerror("syntax error");
						yynerrs++;
					}
					if (yyerrflag < 3) // low error count?
					{
						yyerrflag = 3;
						while (true) // do until break
						{
							if (stateptr < 0) // check for under & overflow here
							{
								yyerror("stack underflow. aborting..."); // note lower case 's'
								return 1;
							}
							yyn = yysindex[state_peek(0)];
							if (yyn != 0 && (yyn += YYERRCODE) >= 0 && yyn <= YYTABLESIZE && yycheck[yyn] == YYERRCODE) {
								if (yydebug)
									debug("state " + state_peek(0) + ", error recovery shifting to state " + yytable[yyn] + " ");
								yystate = yytable[yyn];
								state_push(yystate);
								val_push(yylval);
								doaction = false;
								break;
							}
							else {
								if (yydebug)
									debug("error recovery discarding state " + state_peek(0) + " ");
								if (stateptr < 0) // check for under & overflow here
								{
									yyerror("Stack underflow. aborting..."); // capital 'S'
									return 1;
								}
								state_pop();
								val_pop();
							}
						}
					}
					else // discard this token
					{
						if (yychar == 0)
							return 1; // yyabort
						if (yydebug) {
							yys = null;
							if (yychar <= YYMAXTOKEN)
								yys = yyname[yychar];
							if (yys == null)
								yys = "illegal-symbol";
							debug("state " + yystate + ", error recovery discards token " + yychar + " (" + yys + ")");
						}
						yychar = -1; // read another
					}
				}// end error recovery
			}// yyn=0 loop
			if (!doaction) // any reason not to proceed?
				continue; // skip action
			yym = yylen[yyn]; // get count of terminals on rhs
			if (yydebug)
				debug("state " + yystate + ", reducing " + yym + " by rule " + yyn + " (" + yyrule[yyn] + ")");
			if (yym > 0) // if count of rhs not 'nil'
				yyval = val_peek(yym - 1); // get current semantic value
			yyval = dup_yyval(yyval); // duplicate yyval if ParserVal is used as semantic value
			switch (yyn) {
			// ########## USER-SUPPLIED ACTIONS ##########
				case 1:
				// #line 58 "obj.y"
				{}
					break;
				case 2:
				// #line 59 "obj.y"
				{}
					break;
				case 6:
				// #line 67 "obj.y"
				{
					data.addVertex(val_peek(2).f, val_peek(1).f, val_peek(0).f);
					System.out.println("[" + lexer.line() + " " + lexer.pos() + "]");
				}
					break;
				case 7:
				// #line 70 "obj.y"
				{
					data.addTexture(val_peek(1).f, val_peek(0).f);
				}
					break;
				case 8:
				// #line 73 "obj.y"
				{
					data.addNormal(val_peek(2).f, val_peek(1).f, val_peek(0).f);
				}
					break;
			// #line 408 "OBJParser.java"
			// ########## END OF USER-SUPPLIED ACTIONS ##########
			}// switch
				// #### Now let's reduce... ####
			if (yydebug)
				debug("reduce");
			state_drop(yym); // we just reduced yylen states
			yystate = state_peek(0); // get new state
			val_drop(yym); // corresponding value drop
			yym = yylhs[yyn]; // select next TERMINAL(on lhs)
			if (yystate == 0 && yym == 0)// done? 'rest' state and at first TERMINAL
			{
				if (yydebug)
					debug("After reduction, shifting from state 0 to state " + YYFINAL + "");
				yystate = YYFINAL; // explicitly say we're done
				state_push(YYFINAL); // and save it
				val_push(yyval); // also save the semantic value of parsing
				if (yychar < 0) // we want another character?
				{
					yychar = yylex(); // get next character
					if (yychar < 0)
						yychar = 0; // clean, if necessary
					if (yydebug)
						yylexdebug(yystate, yychar);
				}
				if (yychar == 0) // Good exit (if lex returns 0 ;-)
					break; // quit the loop--all DONE
			}// if yystate
			else // else not done yet
			{ // get next state and push, for next yydefred[]
				yyn = yygindex[yym]; // find out where to go
				if (yyn != 0 && (yyn += yystate) >= 0 && yyn <= YYTABLESIZE && yycheck[yyn] == yystate)
					yystate = yytable[yyn]; // get new state
				else
					yystate = yydgoto[yym]; // else go to new defred
				if (yydebug)
					debug("after reduction, shifting from state " + state_peek(0) + " to state " + yystate + "");
				state_push(yystate); // going again, so push state & val...
				val_push(yyval); // for next action
			}
		}// main loop
		return 0;// yyaccept!!
	}

	// ## end of method parse() ######################################

	// ## run() --- for Thread #######################################
	/**
	 * A default run method, used for operating this parser object in the background. It is intended for extending
	 * Thread or implementing Runnable. Turn off with -Jnorun .
	 */
	public void run() {
		yyparse();
	}

	// ## end of method run() ########################################

	// ## Constructors ###############################################
	/**
	 * Default constructor. Turn off with -Jnoconstruct .
	 */
	public OBJParser() {
		// nothing to do
	}

	/**
	 * Create a parser, setting the debug to true or false.
	 * 
	 * @param debugMe
	 *            true for debugging, false for no debug.
	 */
	public OBJParser(boolean debugMe) {
		yydebug = debugMe;
	}
	// ###############################################################

}
// ################### END OF CLASS ##############################
