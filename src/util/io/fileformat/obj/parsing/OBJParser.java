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


import util.IntArray;
import util.LIntArray;

import util.io.fileformat.obj.Element;
import util.io.fileformat.obj.Element.Type;
//#line 27 "OBJParser.java"




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
public final static short U=262;
public final static short VERTEX=263;
public final static short TEXTURE=264;
public final static short NORMAL=265;
public final static short PARAMETER=266;
public final static short GROUP=267;
public final static short SMOOTH=268;
public final static short MERGE=269;
public final static short OBJECT=270;
public final static short DEGREE=271;
public final static short BASIS=272;
public final static short STEP=273;
public final static short CSTYPE=274;
public final static short POINT=275;
public final static short LINE=276;
public final static short FACE=277;
public final static short CURVE=278;
public final static short CURVE2D=279;
public final static short SURFACE=280;
public final static short END=281;
public final static short PARAM=282;
public final static short TRIMOUT=283;
public final static short TRIMIN=284;
public final static short SPECCURVE=285;
public final static short SPECPOINT=286;
public final static short CONNECT=287;
public final static short BEVEL=288;
public final static short CINTER=289;
public final static short DINTER=290;
public final static short LOD=291;
public final static short MATERIAL=292;
public final static short LIBRARY=293;
public final static short SHADOW=294;
public final static short RAYTRACE=295;
public final static short CURVEAPPROX=296;
public final static short SURFACEAPPROX=297;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    1,    1,    1,    3,    3,    3,    3,    3,    3,
    3,    3,    3,    3,    3,    3,    2,    2,    2,    2,
    2,    4,    4,    4,    4,    4,    5,    5,    5,    6,
    6,    7,    7,    8,    8,    9,    9,    9,   10,   11,
   11,   11,   13,   14,   15,   15,   15,   15,   12,   26,
   26,   26,   26,   27,   27,   28,   28,   29,   29,   30,
   30,   32,   32,   35,   16,   17,   18,   19,   36,   36,
   36,   36,   40,   40,   40,   40,   44,   44,   44,   44,
   20,   33,   33,   21,   22,   45,   37,   37,   41,   23,
   46,   38,   38,   42,   24,   47,   39,   39,   43,   25,
   48,   49,   49,   34,   31,   31,
};
final static short yylen[] = {                            2,
    2,    0,    3,    2,    3,    3,    3,    3,    3,    3,
    3,    3,    3,    3,    3,    2,    0,    1,    1,    1,
    1,    1,    1,    1,    1,    1,    1,    1,    1,    1,
    1,    1,    1,    4,    5,    2,    3,    4,    4,    2,
    3,    4,    2,    2,    2,    2,    2,    2,    2,    1,
    2,    2,    2,    2,    3,    2,    3,    3,    3,    2,
    3,    4,    2,    5,    2,    2,    2,    2,    1,    1,
    1,    1,    1,    1,    1,    1,    1,    1,    1,    1,
    1,    1,    2,    2,    2,    3,    1,    2,    2,    2,
    4,    1,    2,    2,    2,    5,    1,    2,    2,    2,
    1,    1,    2,    2,    1,    2,
};
final static short yydefred[] = {                         2,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    4,   18,   19,   20,
   21,   22,   23,   24,   25,   26,   27,   28,   29,   30,
   31,   32,   33,    0,   50,   16,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   66,    0,   65,    0,    0,
   81,   43,    0,    0,   44,    0,    0,    0,   45,   46,
   47,   48,    0,    0,    0,    0,   68,    0,   67,    3,
    0,    0,    0,   49,   51,   52,   53,    5,    0,    6,
    0,    7,    0,    8,    0,   15,   14,   55,    9,   10,
    0,   84,   11,    0,   85,    0,   90,    0,    0,   95,
    0,    0,  100,    0,   13,   12,    0,    0,    0,    0,
    0,   38,   39,   42,   83,    0,    0,    0,   89,    0,
    0,   94,    0,    0,   99,    0,   57,    0,   58,   59,
   61,   35,   91,    0,   86,   88,   93,    0,   98,  106,
   96,
};
final static short yydgoto[] = {                          1,
    2,   16,   17,   18,   19,   20,   21,   22,   23,   24,
   25,   26,   27,   28,   29,   30,   31,   32,   33,   54,
   55,   59,   60,   61,   62,   34,   35,   75,   76,   77,
  129,    0,   92,    0,    0,    0,  119,  122,  125,    0,
   97,  100,  103,    0,  120,  123,  126,    0,    0,
};
final static short yysindex[] = {                         0,
    0, -237, -223, -186, -183, -180, -178, -240, -195, -243,
 -239, -206, -175, -192, -189, -222,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0, -258,    0,    0, -187, -197, -179, -173,
 -170, -171, -168, -169, -166,    0, -165,    0, -167, -163,
    0,    0, -162, -176,    0, -161, -159, -176,    0,    0,
    0,    0, -160, -158, -156, -154,    0, -153,    0,    0,
 -152, -177, -151,    0,    0,    0,    0,    0, -150,    0,
 -149,    0, -148,    0, -147,    0,    0,    0,    0,    0,
 -176,    0,    0, -225,    0, -157,    0, -160, -143,    0,
 -158, -142,    0, -156,    0,    0, -141, -140, -140, -139,
 -144,    0,    0,    0,    0, -138, -135, -136,    0, -160,
 -133,    0, -158, -134,    0, -156,    0, -140,    0,    0,
    0,    0,    0, -132,    0,    0,    0, -135,    0,    0,
    0,
};
final static short yyrindex[] = {                         0,
    0,    1,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  106,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    2,
    0,    0,    0,    3,    0,    0,    0,    0, -230,    0,
    0,    0,    0,    0,    0,    0, -131,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    4,    0,    0,    0,    5,    0,    0,    0,    0,    0,
    6,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0, -227,    0,    0, -224,
    7,    0,    0,    0,    0,    0, -130,    0,    0,    8,
    0,    0,    9,    0,    0,   10,    0, -213,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,
};
final static short yygindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   11,
   51,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  -97,    0,   26,    0,    0,    0,   13,   14,   15,    0,
    0,    0,    0,    0,   12,   -2,  -13,    0,    0,
};
final static int YYTABLESIZE=268;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         65,
   17,   36,   40,   37,   41,   82,   34,   87,   92,   97,
   64,  130,   71,   72,   73,   45,   50,   49,    3,   51,
   46,   52,   74,   58,   63,    4,    5,    6,    7,    8,
  140,  116,    9,  117,   36,   70,   10,   11,   12,   13,
   54,   54,   54,   56,   56,   56,   60,   60,   60,   53,
   54,  104,   51,   56,   14,   15,   60,  105,  105,  105,
   47,  101,   79,   66,   91,   48,   68,  105,   67,   37,
   78,   69,   39,   38,   98,   41,   40,   43,   80,   42,
   56,   44,   51,   57,  108,  109,   81,   82,   83,   84,
   85,   86,   87,   88,   89,   90,   93,   94,   96,  118,
   99,   91,  102,  105,  106,    1,  107,  110,   95,  111,
  112,  113,  114,  121,  124,  132,  115,  127,  128,  131,
  133,  134,  135,  116,  138,    0,  141,   81,   86,    0,
    0,    0,  136,    0,    0,    0,  137,    0,    0,    0,
  139,    0,    0,    0,    0,    0,    0,    0,    0,    0,
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
    0,    0,    0,    0,    0,    0,    0,    0,   17,   36,
   40,   37,   41,   82,   34,   87,   92,   97,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         13,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   13,  109,  271,  272,  273,  256,  256,  261,  256,  259,
  261,   11,  281,   13,   13,  263,  264,  265,  266,  267,
  128,  257,  270,  259,  258,  258,  274,  275,  276,  277,
  271,  272,  273,  271,  272,  273,  271,  272,  273,  256,
  281,   65,  259,  281,  292,  293,  281,  271,  272,  273,
  256,   64,  260,  256,   54,  261,  256,  281,  261,  256,
  258,  261,  256,  260,   63,  256,  260,  256,  258,  260,
  256,  260,  259,  259,  262,  263,  260,  258,  260,  258,
  260,  258,  258,  261,  258,  258,  258,  257,  259,  257,
  259,   91,  259,  258,  258,    0,  259,  259,   58,  260,
  260,  260,  260,  257,  257,  260,   91,  259,  259,  259,
  259,  257,  259,  257,  259,   -1,  259,  259,  259,   -1,
   -1,   -1,  120,   -1,   -1,   -1,  123,   -1,   -1,   -1,
  126,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
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
  258,  258,  258,  258,  258,  258,  258,  258,
};
}
final static short YYFINAL=1;
final static short YYMAXTOKEN=297;
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
null,null,null,"SLASH","NL","INT","FLOAT","STRING","U","VERTEX","TEXTURE",
"NORMAL","PARAMETER","GROUP","SMOOTH","MERGE","OBJECT","DEGREE","BASIS","STEP",
"CSTYPE","POINT","LINE","FACE","CURVE","CURVE2D","SURFACE","END","PARAM",
"TRIMOUT","TRIMIN","SPECCURVE","SPECPOINT","CONNECT","BEVEL","CINTER","DINTER",
"LOD","MATERIAL","LIBRARY","SHADOW","RAYTRACE","CURVEAPPROX","SURFACEAPPROX",
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
"exception : PARAMETER error NL",
"exception : POINT error NL",
"exception : LINE error NL",
"exception : FACE error NL",
"exception : LIBRARY error NL",
"exception : MATERIAL error NL",
"exception : OBJECT error NL",
"exception : GROUP error NL",
"exception : error NL",
"stmt :",
"stmt : definition",
"stmt : element",
"stmt : grouping",
"stmt : materials",
"definition : vertex",
"definition : texture",
"definition : normal",
"definition : parameter",
"definition : cs",
"element : point",
"element : line",
"element : face",
"grouping : object",
"grouping : group",
"materials : library",
"materials : material",
"vertex : VERTEX FLOAT FLOAT FLOAT",
"vertex : VERTEX FLOAT FLOAT FLOAT FLOAT",
"texture : TEXTURE FLOAT",
"texture : TEXTURE FLOAT FLOAT",
"texture : TEXTURE FLOAT FLOAT FLOAT",
"normal : NORMAL FLOAT FLOAT FLOAT",
"parameter : PARAMETER FLOAT",
"parameter : PARAMETER FLOAT FLOAT",
"parameter : PARAMETER FLOAT FLOAT FLOAT",
"point : POINT v",
"line : LINE vchain2",
"face : FACE vchain3",
"face : FACE vtchain3",
"face : FACE vnchain3",
"face : FACE vtnchain3",
"cs : xcs END",
"xcs : type",
"xcs : xcs degree",
"xcs : xcs basis",
"xcs : xcs step",
"type : CSTYPE STRING",
"type : CSTYPE STRING STRING",
"degree : DEGREE INT",
"degree : DEGREE INT INT",
"basis : BASIS U intchain",
"basis : BASIS VERTEX intchain",
"step : STEP INT",
"step : STEP INT INT",
"curve : CURVE FLOAT FLOAT vchain",
"curve : CURVE2D pchain2",
"surface : SURFACE FLOAT FLOAT FLOAT FLOAT",
"object : OBJECT STRING",
"group : GROUP STRING",
"library : LIBRARY STRING",
"material : MATERIAL STRING",
"vertexChain : vchain",
"vertexChain : vtchain",
"vertexChain : vnchain",
"vertexChain : vtnchain",
"vertexChain2 : vchain2",
"vertexChain2 : vtchain2",
"vertexChain2 : vnchain2",
"vertexChain2 : vtnchain2",
"vertexChain3 : vchain3",
"vertexChain3 : vtchain3",
"vertexChain3 : vnchain3",
"vertexChain3 : vtnchain3",
"v : INT",
"vchain : v",
"vchain : v vchain",
"vchain2 : v vchain",
"vchain3 : v vchain2",
"vt : INT SLASH INT",
"vtchain : vt",
"vtchain : vt vtchain",
"vtchain2 : vt vtchain",
"vtchain3 : vt vtchain2",
"vn : INT SLASH SLASH INT",
"vnchain : vn",
"vnchain : vn vnchain",
"vnchain2 : vn vnchain",
"vnchain3 : vn vnchain2",
"vtn : INT SLASH INT SLASH INT",
"vtnchain : vtn",
"vtnchain : vtn vtnchain",
"vtnchain2 : vtn vtnchain",
"vtnchain3 : vtn vtnchain2",
"p : INT",
"pchain : p",
"pchain : p pchain",
"pchain2 : p pchain",
"intchain : INT",
"intchain : INT intchain",
};

//#line 274 "obj.y"
/******************/		
			
	public static boolean debug = false;
	private OBJLexer lexer;

	private OBJRawData data = new OBJRawData();
	
	private int yylex () {
		int token = -1;
		try {
			token = lexer.yylex();
		}
		catch (IOException e) {
			System.err.println("IO error :"+e);
		}
		return token;
	}


	public void yyerror (String error) {}
	
	public void printError(){
		System.out.println("Parsing Error: " + lexer.line() + ':' + lexer.pos() + " [" + lexer.yytext() + "]");
	}

	public OBJParser(Reader r) {
		lexer = new OBJLexer(r, this);
	}
//#line 460 "OBJParser.java"
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
//#line 73 "obj.y"
{  }
break;
case 4:
//#line 74 "obj.y"
{ System.out.println("\tReadjusting frame."); }
break;
case 5:
//#line 77 "obj.y"
{ data.corruptVertex(); }
break;
case 6:
//#line 78 "obj.y"
{ data.corruptTexture(); }
break;
case 7:
//#line 79 "obj.y"
{ data.corruptNormal(); }
break;
case 8:
//#line 80 "obj.y"
{ data.corruptParameter(); }
break;
case 9:
//#line 82 "obj.y"
{ data.corruptElement(); }
break;
case 10:
//#line 83 "obj.y"
{ data.corruptElement(); }
break;
case 11:
//#line 84 "obj.y"
{ data.corruptElement(); }
break;
case 12:
//#line 86 "obj.y"
{ System.out.println("Invalid material library definition."); }
break;
case 13:
//#line 87 "obj.y"
{ System.out.println("Invalid material definition."); }
break;
case 14:
//#line 88 "obj.y"
{ System.out.println("Invalid object definition."); }
break;
case 15:
//#line 89 "obj.y"
{ System.out.println("Invalid group definition."); }
break;
case 16:
//#line 91 "obj.y"
{ printError(); }
break;
case 34:
//#line 127 "obj.y"
{ data.addVertex(val_peek(2), val_peek(1), val_peek(0)); val_peek(2).freeValue(); val_peek(1).freeValue(); val_peek(0).freeValue(); }
break;
case 35:
//#line 128 "obj.y"
{ data.addVertex(val_peek(3), val_peek(2), val_peek(1), val_peek(0)); val_peek(3).freeValue(); val_peek(2).freeValue(); val_peek(1).freeValue(); val_peek(0).freeValue(); }
break;
case 36:
//#line 130 "obj.y"
{ data.addTexture(val_peek(0)); val_peek(0).freeValue(); }
break;
case 37:
//#line 131 "obj.y"
{ data.addTexture(val_peek(1), val_peek(0)); val_peek(1).freeValue(); val_peek(0).freeValue(); }
break;
case 38:
//#line 132 "obj.y"
{ data.addTexture(val_peek(2), val_peek(1), val_peek(0)); val_peek(2).freeValue(); val_peek(1).freeValue(); val_peek(0).freeValue(); }
break;
case 39:
//#line 134 "obj.y"
{ data.addNormal(val_peek(2), val_peek(1), val_peek(0)); val_peek(2).freeValue(); val_peek(1).freeValue(); val_peek(0).freeValue(); }
break;
case 40:
//#line 136 "obj.y"
{ data.addParameter(val_peek(0)); val_peek(0).freeValue(); }
break;
case 41:
//#line 137 "obj.y"
{ data.addParameter(val_peek(1), val_peek(0)); val_peek(1).freeValue(); val_peek(0).freeValue(); }
break;
case 42:
//#line 138 "obj.y"
{ data.addParameter(val_peek(2), val_peek(1), val_peek(0)); val_peek(2).freeValue(); val_peek(1).freeValue(); val_peek(0).freeValue(); }
break;
case 43:
//#line 144 "obj.y"
{ data.addElement(new Element(Type.POINT, new int[]{val_peek(0).i})); val_peek(0).freeValue(); }
break;
case 44:
//#line 146 "obj.y"
{ data.addElement(new Element(Type.LINE, val_peek(0).ints.toArray())); val_peek(0).freeValue(); }
break;
case 45:
//#line 148 "obj.y"
{ data.addElement(new Element(Type.FACEV, val_peek(0).ints.toArray())); val_peek(0).freeValue(); }
break;
case 46:
//#line 149 "obj.y"
{ data.addElement(new Element(Type.FACEVT, val_peek(0).ints.toArray())); val_peek(0).freeValue(); }
break;
case 47:
//#line 150 "obj.y"
{ data.addElement(new Element(Type.FACEVN, val_peek(0).ints.toArray())); val_peek(0).freeValue(); }
break;
case 48:
//#line 151 "obj.y"
{ data.addElement(new Element(Type.FACEVTN, val_peek(0).ints.toArray())); val_peek(0).freeValue(); }
break;
case 54:
//#line 163 "obj.y"
{  }
break;
case 55:
//#line 164 "obj.y"
{  }
break;
case 56:
//#line 166 "obj.y"
{  }
break;
case 57:
//#line 167 "obj.y"
{  }
break;
case 58:
//#line 169 "obj.y"
{  }
break;
case 59:
//#line 170 "obj.y"
{  }
break;
case 60:
//#line 172 "obj.y"
{  }
break;
case 61:
//#line 173 "obj.y"
{  }
break;
case 62:
//#line 176 "obj.y"
{  }
break;
case 63:
//#line 177 "obj.y"
{  }
break;
case 64:
//#line 180 "obj.y"
{  }
break;
case 65:
//#line 186 "obj.y"
{ data.setObject(val_peek(0)); val_peek(0).freeValue(); }
break;
case 66:
//#line 188 "obj.y"
{ data.setGroup(val_peek(0)); val_peek(0).freeValue(); }
break;
case 67:
//#line 194 "obj.y"
{ data.addLibrary(val_peek(0)); val_peek(0).freeValue(); }
break;
case 68:
//#line 196 "obj.y"
{ data.setMaterial(val_peek(0)); val_peek(0).freeValue(); }
break;
case 81:
//#line 219 "obj.y"
{ yyval = val_peek(0); yyval.i = data.evaluateVertex(val_peek(0)); }
break;
case 82:
//#line 221 "obj.y"
{ yyval = val_peek(0); yyval.ints = new LIntArray(val_peek(0).i); }
break;
case 83:
//#line 222 "obj.y"
{ yyval = val_peek(0); yyval.ints.prepend(val_peek(0).i); val_peek(1).freeValue(); }
break;
case 84:
//#line 224 "obj.y"
{ yyval = val_peek(0); yyval.ints.prepend(val_peek(1).i); val_peek(1).freeValue(); }
break;
case 85:
//#line 226 "obj.y"
{ yyval = val_peek(0); yyval.ints.prepend(val_peek(1).i); val_peek(1).freeValue(); }
break;
case 86:
//#line 229 "obj.y"
{ yyval = val_peek(2); yyval.ints = new LIntArray(data.evaluateVertex(val_peek(2)), data.evaluateTexture(val_peek(0))); val_peek(0).freeValue(); }
break;
case 87:
//#line 231 "obj.y"
{ yyval = val_peek(0); }
break;
case 88:
//#line 232 "obj.y"
{ yyval = val_peek(0); yyval.ints.prepend(val_peek(1).ints); val_peek(1).freeValue(); }
break;
case 89:
//#line 234 "obj.y"
{ yyval = val_peek(0); yyval.ints.prepend(val_peek(1).ints); val_peek(1).freeValue(); }
break;
case 90:
//#line 236 "obj.y"
{ yyval = val_peek(0); yyval.ints.prepend(val_peek(1).ints); val_peek(1).freeValue(); }
break;
case 91:
//#line 239 "obj.y"
{ yyval = val_peek(3); yyval.ints = new LIntArray(data.evaluateVertex(val_peek(3)), data.evaluateNormal(val_peek(0))); val_peek(0).freeValue(); }
break;
case 92:
//#line 241 "obj.y"
{ yyval = val_peek(0); }
break;
case 93:
//#line 242 "obj.y"
{ yyval = val_peek(0); yyval.ints.prepend(val_peek(1).ints); val_peek(1).freeValue(); }
break;
case 94:
//#line 244 "obj.y"
{ yyval = val_peek(0); yyval.ints.prepend(val_peek(1).ints); val_peek(1).freeValue(); }
break;
case 95:
//#line 246 "obj.y"
{ yyval = val_peek(0); yyval.ints.prepend(val_peek(1).ints); val_peek(1).freeValue(); }
break;
case 96:
//#line 249 "obj.y"
{ yyval = val_peek(4); yyval.ints = new LIntArray(data.evaluateVertex(val_peek(4)), data.evaluateTexture(val_peek(2)), data.evaluateNormal(val_peek(0))); val_peek(2).freeValue(); val_peek(0).freeValue(); }
break;
case 97:
//#line 251 "obj.y"
{ yyval = val_peek(0); }
break;
case 98:
//#line 252 "obj.y"
{ yyval = val_peek(0); yyval.ints.prepend(val_peek(1).ints); val_peek(1).freeValue(); }
break;
case 99:
//#line 254 "obj.y"
{ yyval = val_peek(0); yyval.ints.prepend(val_peek(1).ints); val_peek(1).freeValue(); }
break;
case 100:
//#line 256 "obj.y"
{ yyval = val_peek(0); yyval.ints.prepend(val_peek(1).ints); val_peek(1).freeValue(); }
break;
case 101:
//#line 259 "obj.y"
{ yyval = val_peek(0); yyval.i = data.evaluateParameter(val_peek(0)); }
break;
case 102:
//#line 261 "obj.y"
{ yyval = val_peek(0); yyval.ints = new LIntArray(val_peek(0).i); }
break;
case 103:
//#line 262 "obj.y"
{ yyval = val_peek(0); yyval.ints.prepend(val_peek(0).i); val_peek(1).freeValue(); }
break;
case 104:
//#line 264 "obj.y"
{ yyval = val_peek(0); yyval.ints.prepend(val_peek(1).i); val_peek(1).freeValue(); }
break;
case 105:
//#line 267 "obj.y"
{ yyval = val_peek(0); yyval.ints = new LIntArray(val_peek(0).i); }
break;
case 106:
//#line 268 "obj.y"
{ yyval = val_peek(0); yyval.ints.prepend(val_peek(1).i); val_peek(1).freeValue(); }
break;
//#line 889 "OBJParser.java"
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
