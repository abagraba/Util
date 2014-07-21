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

import util.io.fileformat.obj.Element;
import util.io.fileformat.obj.Element.Type;
//#line 23 "OBJParser.java"




public class OBJParser
{

boolean yydebug;        //do I want debug output?
int yynerrs;            //number of errors so far
int yyerrflag;          //was there an error?
int yychar;             //the current working character

//########## MESSAGES ##########
//###############################################################
// method: debug
//###############################################################
void debug(String msg)
{
  if (yydebug)
    System.out.println(msg);
}

//########## STATE STACK ##########
final static int YYSTACKSIZE = 500;  //maximum stack size
int statestk[] = new int[YYSTACKSIZE]; //state stack
int stateptr;
int stateptrmax;                     //highest index of stackptr
int statemax;                        //state when highest index reached
//###############################################################
// methods: state stack push,pop,drop,peek
//###############################################################
final void state_push(int state)
{
  try {
		stateptr++;
		statestk[stateptr]=state;
	 }
	 catch (ArrayIndexOutOfBoundsException e) {
     int oldsize = statestk.length;
     int newsize = oldsize * 2;
     int[] newstack = new int[newsize];
     System.arraycopy(statestk,0,newstack,0,oldsize);
     statestk = newstack;
     statestk[stateptr]=state;
  }
}
final int state_pop()
{
  return statestk[stateptr--];
}
final void state_drop(int cnt)
{
  stateptr -= cnt; 
}
final int state_peek(int relative)
{
  return statestk[stateptr-relative];
}
//###############################################################
// method: init_stacks : allocate and prepare stacks
//###############################################################
final boolean init_stacks()
{
  stateptr = -1;
  val_init();
  return true;
}
//###############################################################
// method: dump_stacks : show n levels of the stacks
//###############################################################
void dump_stacks(int count)
{
int i;
  System.out.println("=index==state====value=     s:"+stateptr+"  v:"+valptr);
  for (i=0;i<count;i++)
    System.out.println(" "+i+"    "+statestk[i]+"      "+valstk[i]);
  System.out.println("======================");
}


//########## SEMANTIC VALUES ##########
//## **user defined:OBJValue
String   yytext;//user variable to return contextual strings
OBJValue yyval; //used to return semantic vals from action routines
OBJValue yylval;//the 'lval' (result) I got from yylex()
OBJValue valstk[] = new OBJValue[YYSTACKSIZE];
int valptr;
//###############################################################
// methods: value stack push,pop,drop,peek.
//###############################################################
final void val_init()
{
  yyval=new OBJValue();
  yylval=new OBJValue();
  valptr=-1;
}
final void val_push(OBJValue val)
{
  try {
    valptr++;
    valstk[valptr]=val;
  }
  catch (ArrayIndexOutOfBoundsException e) {
    int oldsize = valstk.length;
    int newsize = oldsize*2;
    OBJValue[] newstack = new OBJValue[newsize];
    System.arraycopy(valstk,0,newstack,0,oldsize);
    valstk = newstack;
    valstk[valptr]=val;
  }
}
final OBJValue val_pop()
{
  return valstk[valptr--];
}
final void val_drop(int cnt)
{
  valptr -= cnt;
}
final OBJValue val_peek(int relative)
{
  return valstk[valptr-relative];
}
final OBJValue dup_yyval(OBJValue val)
{
  return val;
}
//#### end semantic value section ####
public final static short SLASH=257;
public final static short NL=258;
public final static short INT=259;
public final static short FLOAT=260;
public final static short STRING=261;
public final static short VERTEX=262;
public final static short TEXTURE=263;
public final static short NORMAL=264;
public final static short PARAMETER=265;
public final static short GROUP=266;
public final static short SMOOTH=267;
public final static short MERGE=268;
public final static short OBJECT=269;
public final static short DEGREE=270;
public final static short BASIS=271;
public final static short STEP=272;
public final static short CSTYPE=273;
public final static short POINT=274;
public final static short LINE=275;
public final static short FACE=276;
public final static short CURVE=277;
public final static short CURVE2D=278;
public final static short SURFACE=279;
public final static short PARAM=280;
public final static short TRIMOUT=281;
public final static short TRIMIN=282;
public final static short SPECCURVE=283;
public final static short SPECPOINT=284;
public final static short END=285;
public final static short CONNECT=286;
public final static short BEVEL=287;
public final static short CINTER=288;
public final static short DINTER=289;
public final static short LOD=290;
public final static short MATERIAL=291;
public final static short LIBRARY=292;
public final static short SHADOW=293;
public final static short RAYTRACE=294;
public final static short CURVEAPPROX=295;
public final static short SURFACEAPPROX=296;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    1,    1,    1,    3,    3,    3,    3,    3,    3,
    3,    3,    2,    2,    2,    2,    2,    2,    4,    4,
    4,    9,   10,   11,    5,    5,    5,   12,   13,   14,
   14,   14,   14,    6,    7,    8,   15,   21,   22,   23,
   16,   16,   17,   17,   18,   18,   19,   19,   20,   20,
};
final static short yylen[] = {                            2,
    2,    0,    3,    2,    3,    3,    3,    3,    3,    3,
    3,    2,    0,    1,    1,    1,    1,    1,    1,    1,
    1,    4,    3,    4,    1,    1,    1,    2,    2,    2,
    2,    2,    2,    2,    2,    2,    1,    3,    4,    5,
    2,    2,    3,    2,    3,    2,    3,    2,    3,    2,
};
final static short yydefred[] = {                         2,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    4,   14,   15,   16,   17,   18,   19,
   20,   21,   25,   26,   27,   12,    0,    0,    0,    0,
    0,    0,    0,   34,   37,   28,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   36,
    0,   35,    3,    5,    0,    6,   23,    7,    0,   11,
   41,   42,    8,    0,    0,   44,    0,   46,    0,   48,
    0,   50,    0,    0,    0,   10,    9,   22,   24,    0,
    0,   43,    0,    0,    0,   45,   47,   49,   39,    0,
   38,    0,   40,
};
final static short yydgoto[] = {                          1,
    2,   13,   14,   15,   16,   17,   18,   19,   20,   21,
   22,   23,   24,   25,   36,   38,   42,   43,   44,   45,
   46,   47,   48,
};
final static short yysindex[] = {                         0,
    0, -247, -258, -222, -214, -213, -231, -226, -226, -246,
 -230, -224, -209,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0, -207, -248, -206, -205,
 -204, -203, -202,    0,    0,    0, -226, -226, -200, -198,
 -226, -226, -199, -197, -195, -199, -197, -195, -193,    0,
 -192,    0,    0,    0, -191,    0,    0,    0, -190,    0,
    0,    0,    0, -235, -226,    0, -196,    0, -189,    0,
 -186,    0, -199, -197, -195,    0,    0,    0,    0, -187,
 -184,    0, -185, -182, -183,    0,    0,    0,    0, -181,
    0, -184,    0,
};
final static short yyrindex[] = {                         0,
    0,    1,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   53,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    2,    0, -180,
    0,    3,    4,    5,    6,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
 -179,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   -2,    0,    0,    0,    0,    0,
  -32,  -26,  -25,
};
final static int YYTABLESIZE=264;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         26,
   13,   29,   30,   31,   32,   33,   37,   41,    3,   39,
   68,   55,   40,   73,    4,    5,    6,   70,    7,   72,
   74,   80,   75,   81,   33,   49,    8,    9,   10,   34,
   50,   51,   35,   27,   61,   62,   52,   28,   65,   66,
   86,   29,   31,   11,   12,   30,   32,   87,   53,   88,
   54,   56,    1,   58,   57,   60,   59,   63,   64,   67,
   83,   69,   82,   71,   76,   77,    0,   84,   78,   79,
   85,   89,   90,   91,   80,   92,    0,   93,   37,   38,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   13,   29,
   30,   31,   32,   33,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                        258,
    0,    0,    0,    0,    0,    0,    9,   10,  256,  256,
   43,  260,  259,   46,  262,  263,  264,   44,  266,   45,
   47,  257,   48,  259,  256,  256,  274,  275,  276,  261,
  261,  256,  259,  256,   37,   38,  261,  260,   41,   42,
   73,  256,  256,  291,  292,  260,  260,   74,  258,   75,
  258,  258,    0,  258,  260,  258,  260,  258,  257,  259,
  257,  259,   65,  259,  258,  258,   -1,  257,  260,  260,
  257,  259,  257,  259,  257,  259,   -1,  259,  259,  259,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  258,  258,
  258,  258,  258,  258,
};
}
final static short YYFINAL=1;
final static short YYMAXTOKEN=296;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,"SLASH","NL","INT","FLOAT","STRING","VERTEX","TEXTURE","NORMAL",
"PARAMETER","GROUP","SMOOTH","MERGE","OBJECT","DEGREE","BASIS","STEP","CSTYPE",
"POINT","LINE","FACE","CURVE","CURVE2D","SURFACE","PARAM","TRIMOUT","TRIMIN",
"SPECCURVE","SPECPOINT","END","CONNECT","BEVEL","CINTER","DINTER","LOD",
"MATERIAL","LIBRARY","SHADOW","RAYTRACE","CURVEAPPROX","SURFACEAPPROX",
};
final static String yyrule[] = {
"$accept : end",
"end : file stmt",
"file :",
"file : file stmt NL",
"file : file exception",
"exception : VERTEX error NL",
"exception : TEXTURE error NL",
"exception : NORMAL error NL",
"exception : FACE error NL",
"exception : LIBRARY error NL",
"exception : MATERIAL error NL",
"exception : GROUP error NL",
"exception : error NL",
"stmt :",
"stmt : definition",
"stmt : element",
"stmt : group",
"stmt : library",
"stmt : material",
"definition : vertex",
"definition : texture",
"definition : normal",
"vertex : VERTEX FLOAT FLOAT FLOAT",
"texture : TEXTURE FLOAT FLOAT",
"normal : NORMAL FLOAT FLOAT FLOAT",
"element : point",
"element : line",
"element : face",
"point : POINT v",
"line : LINE vchain2",
"face : FACE vchain3",
"face : FACE vtchain3",
"face : FACE vnchain3",
"face : FACE vtnchain3",
"group : GROUP STRING",
"library : LIBRARY STRING",
"material : MATERIAL STRING",
"v : INT",
"vt : INT SLASH INT",
"vn : INT SLASH SLASH INT",
"vtn : INT SLASH INT SLASH INT",
"vchain2 : v v",
"vchain2 : vchain2 v",
"vchain3 : v v v",
"vchain3 : vchain3 v",
"vtchain3 : vt vt vt",
"vtchain3 : vtchain3 vt",
"vnchain3 : vn vn vn",
"vnchain3 : vnchain3 vn",
"vtnchain3 : vtn vtn vtn",
"vtnchain3 : vtnchain3 vtn",
};

//#line 173 "obj.y"
	public static boolean debug = false;
	private OBJLexer lexer;

	private OBJRawData data = new OBJRawData();
	
	private int yylex () {
		int token = -1;
		try {
			token = lexer.yylex();
			//System.out.println(yyname[token]);
		}
		catch (IOException e) {
			System.err.println("IO error :"+e);
		}
		return token;
	}


	public void yyerror (String error) {
	}
	
	public void printError(){
		System.out.println("Parsing Error: " + lexer.line() + ':' + lexer.pos() + " [" + lexer.yytext() + "]");
	}
	


	public OBJParser(Reader r) {
		lexer = new OBJLexer(r, this);
	}
	
	
//#line 372 "OBJParser.java"
//###############################################################
// method: yylexdebug : check lexer state
//###############################################################
void yylexdebug(int state,int ch)
{
String s=null;
  if (ch < 0) ch=0;
  if (ch <= YYMAXTOKEN) //check index bounds
     s = yyname[ch];    //now get it
  if (s==null)
    s = "illegal-symbol";
  debug("state "+state+", reading "+ch+" ("+s+")");
}





//The following are now global, to aid in error reporting
int yyn;       //next next thing to do
int yym;       //
int yystate;   //current parsing state from state table
String yys;    //current token string


//###############################################################
// method: yyparse : parse input and execute indicated items
//###############################################################
int yyparse()
{
boolean doaction;
  init_stacks();
  yynerrs = 0;
  yyerrflag = 0;
  yychar = -1;          //impossible char forces a read
  yystate=0;            //initial state
  state_push(yystate);  //save it
  val_push(yylval);     //save empty value
  while (true) //until parsing is done, either correctly, or w/error
    {
    doaction=true;
    if (yydebug) debug("loop"); 
    //#### NEXT ACTION (from reduction table)
    for (yyn=yydefred[yystate];yyn==0;yyn=yydefred[yystate])
      {
      if (yydebug) debug("yyn:"+yyn+"  state:"+yystate+"  yychar:"+yychar);
      if (yychar < 0)      //we want a char?
        {
        yychar = yylex();  //get next token
        if (yydebug) debug(" next yychar:"+yychar);
        //#### ERROR CHECK ####
        if (yychar < 0)    //it it didn't work/error
          {
          yychar = 0;      //change it to default string (no -1!)
          if (yydebug)
            yylexdebug(yystate,yychar);
          }
        }//yychar<0
      yyn = yysindex[yystate];  //get amount to shift by (shift index)
      if ((yyn != 0) && (yyn += yychar) >= 0 &&
          yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
        {
        if (yydebug)
          debug("state "+yystate+", shifting to state "+yytable[yyn]);
        //#### NEXT STATE ####
        yystate = yytable[yyn];//we are in a new state
        state_push(yystate);   //save it
        val_push(yylval);      //push our lval as the input for next rule
        yychar = -1;           //since we have 'eaten' a token, say we need another
        if (yyerrflag > 0)     //have we recovered an error?
           --yyerrflag;        //give ourselves credit
        doaction=false;        //but don't process yet
        break;   //quit the yyn=0 loop
        }

    yyn = yyrindex[yystate];  //reduce
    if ((yyn !=0 ) && (yyn += yychar) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
      {   //we reduced!
      if (yydebug) debug("reduce");
      yyn = yytable[yyn];
      doaction=true; //get ready to execute
      break;         //drop down to actions
      }
    else //ERROR RECOVERY
      {
      if (yyerrflag==0)
        {
        yyerror("syntax error");
        yynerrs++;
        }
      if (yyerrflag < 3) //low error count?
        {
        yyerrflag = 3;
        while (true)   //do until break
          {
          if (stateptr<0)   //check for under & overflow here
            {
            yyerror("stack underflow. aborting...");  //note lower case 's'
            return 1;
            }
          yyn = yysindex[state_peek(0)];
          if ((yyn != 0) && (yyn += YYERRCODE) >= 0 &&
                    yyn <= YYTABLESIZE && yycheck[yyn] == YYERRCODE)
            {
            if (yydebug)
              debug("state "+state_peek(0)+", error recovery shifting to state "+yytable[yyn]+" ");
            yystate = yytable[yyn];
            state_push(yystate);
            val_push(yylval);
            doaction=false;
            break;
            }
          else
            {
            if (yydebug)
              debug("error recovery discarding state "+state_peek(0)+" ");
            if (stateptr<0)   //check for under & overflow here
              {
              yyerror("Stack underflow. aborting...");  //capital 'S'
              return 1;
              }
            state_pop();
            val_pop();
            }
          }
        }
      else            //discard this token
        {
        if (yychar == 0)
          return 1; //yyabort
        if (yydebug)
          {
          yys = null;
          if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
          if (yys == null) yys = "illegal-symbol";
          debug("state "+yystate+", error recovery discards token "+yychar+" ("+yys+")");
          }
        yychar = -1;  //read another
        }
      }//end error recovery
    }//yyn=0 loop
    if (!doaction)   //any reason not to proceed?
      continue;      //skip action
    yym = yylen[yyn];          //get count of terminals on rhs
    if (yydebug)
      debug("state "+yystate+", reducing "+yym+" by rule "+yyn+" ("+yyrule[yyn]+")");
    if (yym>0)                 //if count of rhs not 'nil'
      yyval = val_peek(yym-1); //get current semantic value
    yyval = dup_yyval(yyval); //duplicate yyval if ParserVal is used as semantic value
    switch(yyn)
      {
//########## USER-SUPPLIED ACTIONS ##########
case 3:
//#line 67 "obj.y"
{  }
break;
case 4:
//#line 68 "obj.y"
{ System.out.println("\tReadjusting frame."); }
break;
case 5:
//#line 71 "obj.y"
{ data.corruptVertex(); }
break;
case 6:
//#line 72 "obj.y"
{ data.corruptTexture(); }
break;
case 7:
//#line 73 "obj.y"
{ data.corruptNormal(); }
break;
case 8:
//#line 74 "obj.y"
{ data.corruptElement(); }
break;
case 9:
//#line 75 "obj.y"
{ System.out.println("Invalid material library definition."); }
break;
case 10:
//#line 76 "obj.y"
{ System.out.println("Invalid material definition."); }
break;
case 11:
//#line 77 "obj.y"
{ System.out.println("Invalid group definition."); }
break;
case 12:
//#line 78 "obj.y"
{ printError(); }
break;
case 22:
//#line 100 "obj.y"
{ data.addVertex(val_peek(2), val_peek(1), val_peek(0)); val_peek(2).freeValue(); val_peek(1).freeValue(); val_peek(0).freeValue(); }
break;
case 23:
//#line 102 "obj.y"
{ data.addTexture(val_peek(1), val_peek(0)); val_peek(1).freeValue(); val_peek(0).freeValue(); }
break;
case 24:
//#line 104 "obj.y"
{ data.addNormal(val_peek(2), val_peek(1), val_peek(0)); val_peek(2).freeValue(); val_peek(1).freeValue(); val_peek(0).freeValue(); }
break;
case 28:
//#line 115 "obj.y"
{ data.addElement(new Element(Type.POINT, new int[]{val_peek(0).i})); val_peek(0).freeValue(); }
break;
case 29:
//#line 117 "obj.y"
{ data.addElement(new Element(Type.LINE, val_peek(0).ints.toArray())); val_peek(0).freeValue(); }
break;
case 30:
//#line 119 "obj.y"
{ data.addElement(new Element(Type.FACEV, val_peek(0).ints.toArray())); val_peek(0).freeValue(); }
break;
case 31:
//#line 120 "obj.y"
{ data.addElement(new Element(Type.FACEVT, val_peek(0).ints.toArray())); val_peek(0).freeValue(); }
break;
case 32:
//#line 121 "obj.y"
{ data.addElement(new Element(Type.FACEVN, val_peek(0).ints.toArray())); val_peek(0).freeValue(); }
break;
case 33:
//#line 122 "obj.y"
{ data.addElement(new Element(Type.FACEVTN, val_peek(0).ints.toArray())); val_peek(0).freeValue(); }
break;
case 34:
//#line 128 "obj.y"
{ data.setGroup(val_peek(0)); }
break;
case 35:
//#line 134 "obj.y"
{ yyval = val_peek(1); }
break;
case 36:
//#line 136 "obj.y"
{ data.setMaterial(val_peek(0)); }
break;
case 37:
//#line 142 "obj.y"
{ yyval = val_peek(0); yyval.i = data.evaluateVertex(val_peek(0)); }
break;
case 38:
//#line 144 "obj.y"
{ yyval = val_peek(2); yyval.ints = new IntArray(data.evaluateVertex(val_peek(2)), data.evaluateTexture(val_peek(0))); val_peek(0).freeValue(); }
break;
case 39:
//#line 146 "obj.y"
{ yyval = val_peek(3); yyval.ints = new IntArray(data.evaluateVertex(val_peek(3)), data.evaluateNormal(val_peek(0))); val_peek(0).freeValue(); }
break;
case 40:
//#line 148 "obj.y"
{ yyval = val_peek(4); yyval.ints = new IntArray(data.evaluateVertex(val_peek(4)), data.evaluateTexture(val_peek(2)), data.evaluateNormal(val_peek(0))); val_peek(2).freeValue(); val_peek(0).freeValue(); }
break;
case 41:
//#line 151 "obj.y"
{ yyval = val_peek(1); yyval.ints = new IntArray(val_peek(1).i, val_peek(0).i); val_peek(0).freeValue(); }
break;
case 42:
//#line 152 "obj.y"
{ yyval = val_peek(1); yyval.ints.add(val_peek(0).i); val_peek(0).freeValue(); }
break;
case 43:
//#line 155 "obj.y"
{ yyval = val_peek(2); yyval.ints = new IntArray(val_peek(2).i, val_peek(1).i, val_peek(0).i); val_peek(1).freeValue(); val_peek(0).freeValue(); }
break;
case 44:
//#line 156 "obj.y"
{ yyval = val_peek(1); yyval.ints.add(val_peek(0).i); val_peek(0).freeValue(); }
break;
case 45:
//#line 158 "obj.y"
{ yyval = val_peek(2); yyval.ints.append(val_peek(1).ints, val_peek(0).ints); val_peek(1).freeValue(); val_peek(0).freeValue(); }
break;
case 46:
//#line 159 "obj.y"
{ yyval = val_peek(1); yyval.ints.append(val_peek(0).ints); val_peek(0).freeValue(); }
break;
case 47:
//#line 161 "obj.y"
{ yyval = val_peek(2); yyval.ints.append(val_peek(1).ints, val_peek(0).ints); val_peek(1).freeValue(); val_peek(0).freeValue(); }
break;
case 48:
//#line 162 "obj.y"
{ yyval = val_peek(1); yyval.ints.append(val_peek(0).ints); val_peek(0).freeValue(); }
break;
case 49:
//#line 164 "obj.y"
{ yyval = val_peek(2); yyval.ints.append(val_peek(1).ints, val_peek(0).ints); val_peek(1).freeValue(); val_peek(0).freeValue(); }
break;
case 50:
//#line 165 "obj.y"
{ yyval = val_peek(1); yyval.ints.append(val_peek(0).ints); val_peek(0).freeValue(); }
break;
//#line 665 "OBJParser.java"
//########## END OF USER-SUPPLIED ACTIONS ##########
    }//switch
    //#### Now let's reduce... ####
    if (yydebug) debug("reduce");
    state_drop(yym);             //we just reduced yylen states
    yystate = state_peek(0);     //get new state
    val_drop(yym);               //corresponding value drop
    yym = yylhs[yyn];            //select next TERMINAL(on lhs)
    if (yystate == 0 && yym == 0)//done? 'rest' state and at first TERMINAL
      {
      if (yydebug) debug("After reduction, shifting from state 0 to state "+YYFINAL+"");
      yystate = YYFINAL;         //explicitly say we're done
      state_push(YYFINAL);       //and save it
      val_push(yyval);           //also save the semantic value of parsing
      if (yychar < 0)            //we want another character?
        {
        yychar = yylex();        //get next character
        if (yychar<0) yychar=0;  //clean, if necessary
        if (yydebug)
          yylexdebug(yystate,yychar);
        }
      if (yychar == 0)          //Good exit (if lex returns 0 ;-)
         break;                 //quit the loop--all DONE
      }//if yystate
    else                        //else not done yet
      {                         //get next state and push, for next yydefred[]
      yyn = yygindex[yym];      //find out where to go
      if ((yyn != 0) && (yyn += yystate) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yystate)
        yystate = yytable[yyn]; //get new state
      else
        yystate = yydgoto[yym]; //else go to new defred
      if (yydebug) debug("after reduction, shifting from state "+state_peek(0)+" to state "+yystate+"");
      state_push(yystate);     //going again, so push state & val...
      val_push(yyval);         //for next action
      }
    }//main loop
  return 0;//yyaccept!!
}
//## end of method parse() ######################################



//## run() --- for Thread #######################################
/**
 * A default run method, used for operating this parser
 * object in the background.  It is intended for extending Thread
 * or implementing Runnable.  Turn off with -Jnorun .
 */
public void run()
{
  yyparse();
}
//## end of method run() ########################################



//## Constructors ###############################################
/**
 * Default constructor.  Turn off with -Jnoconstruct .

 */
public OBJParser()
{
  //nothing to do
}


/**
 * Create a parser, setting the debug to true or false.
 * @param debugMe true for debugging, false for no debug.
 */
public OBJParser(boolean debugMe)
{
  yydebug=debugMe;
}
//###############################################################



}
//################### END OF CLASS ##############################
