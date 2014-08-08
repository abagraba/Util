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

import util.io.fileformat.obj.elements.Element;
import util.io.fileformat.obj.elements.ElementType;
import util.io.fileformat.obj.parsing.CSData;
//#line 28 "OBJParser.java"




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
public final static short RAT=271;
public final static short DEGREE=272;
public final static short BASIS=273;
public final static short STEP=274;
public final static short CSTYPE=275;
public final static short POINT=276;
public final static short LINE=277;
public final static short FACE=278;
public final static short CURVE=279;
public final static short CURVE2D=280;
public final static short SURFACE=281;
public final static short OFF=282;
public final static short END=283;
public final static short PARAM=284;
public final static short TRIM=285;
public final static short HOLE=286;
public final static short SPECCURVE=287;
public final static short SPECPOINT=288;
public final static short CONNECT=289;
public final static short BEVEL=290;
public final static short CINTER=291;
public final static short DINTER=292;
public final static short LOD=293;
public final static short MATERIAL=294;
public final static short LIBRARY=295;
public final static short SHADOW=296;
public final static short RAYTRACE=297;
public final static short CURVEAPPROX=298;
public final static short SURFACEAPPROX=299;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    1,    1,    1,    3,    3,    3,    3,    3,    3,
    3,    3,    3,    3,    3,    3,    2,    2,    2,    2,
    2,    4,    4,    4,    4,    4,    5,    5,    5,    6,
    6,    6,    6,    7,    7,    8,    8,    9,    9,    9,
   10,   11,   11,   11,   13,   14,   15,   15,   15,   15,
   28,   28,   28,   28,   28,   28,   28,   29,   29,   12,
   31,   31,   32,   32,   32,   33,   33,   33,   33,   33,
   33,   36,   36,   37,   37,   38,   38,   39,   40,   16,
   17,   18,   18,   19,   19,   20,   21,   43,   43,   43,
   43,   35,   35,   35,   35,   51,   51,   51,   51,   22,
   44,   44,   23,   24,   52,   45,   45,   48,   25,   53,
   46,   46,   49,   26,   54,   47,   47,   50,   27,   55,
   41,   41,   34,   42,   30,   30,
};
final static short yylen[] = {                            2,
    2,    0,    3,    2,    3,    3,    3,    3,    3,    3,
    3,    3,    3,    3,    3,    2,    0,    1,    1,    1,
    1,    1,    1,    1,    1,    1,    1,    1,    1,    1,
    1,    1,    1,    1,    1,    4,    5,    2,    3,    4,
    4,    2,    3,    4,    2,    2,    2,    2,    2,    2,
    1,    3,    4,    4,    4,    3,    4,    2,    3,    2,
    1,    2,    4,    2,    6,    3,    3,    1,    1,    1,
    1,    2,    2,    2,    2,    2,    2,    2,    3,    2,
    2,    2,    2,    3,    2,    2,    2,    1,    1,    1,
    1,    1,    1,    1,    1,    1,    1,    1,    1,    1,
    1,    2,    2,    2,    3,    1,    2,    2,    2,    4,
    1,    2,    2,    2,    5,    1,    2,    2,    2,    1,
    1,    2,    2,    1,    1,    2,
};
final static short yydefred[] = {                         2,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    4,   18,   19,   20,   21,   22,   23,   24,   25,   26,
   27,   28,   29,   30,   31,   32,   33,   34,   35,    0,
   61,   16,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   81,   82,   83,    0,   85,    0,   80,    0,  100,
   45,    0,    0,   46,    0,    0,    0,   47,   48,   49,
   50,    0,    0,    0,    0,  120,   64,    0,    0,    0,
   87,    0,   86,    3,   60,    0,    0,    0,    0,    0,
   62,    0,    0,    0,   71,    5,    0,    6,    0,    7,
    0,    8,    0,   15,   84,   14,    9,   10,    0,  103,
   11,    0,  104,    0,  109,    0,    0,  114,    0,    0,
  119,    0,    0,  123,    0,    0,   13,   12,    0,    0,
    0,   72,   74,   76,   78,   73,   75,   77,    0,   40,
   41,   44,  102,    0,    0,    0,  108,    0,    0,  113,
    0,    0,  118,    0,   63,  122,    0,   66,   67,    0,
   37,  110,    0,  105,  107,  112,    0,  117,    0,  124,
   79,  115,   92,   65,   93,   94,   95,
};
final static short yydgoto[] = {                          1,
    2,   20,   21,   22,   23,   24,   25,   26,   27,   28,
   29,   30,   31,   32,   33,   34,   35,   36,   37,   38,
   39,   63,   64,   68,   69,   70,   71,    0,    0,    0,
   40,   41,   91,   77,  174,   92,   93,   94,   95,  132,
  124,  171,    0,  110,  147,  150,  153,  115,  118,  121,
    0,  116,  119,  122,   78,
};
final static short yysindex[] = {                         0,
    0, -238, -225, -157, -156, -155, -150, -241, -237, -224,
 -208, -222, -140, -139, -216, -161, -191, -190, -159, -151,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0, -223,
    0,    0, -149, -145, -147, -138, -137, -136, -135, -134,
 -133,    0,    0,    0, -132,    0, -131,    0, -129,    0,
    0, -128, -127,    0, -125, -221, -127,    0,    0,    0,
    0, -124, -123, -122, -126,    0,    0, -161, -121, -120,
    0, -117,    0,    0,    0, -246, -118, -118, -118, -161,
    0, -118, -118, -118,    0,    0, -115,    0, -113,    0,
 -112,    0, -111,    0,    0,    0,    0,    0, -127,    0,
    0, -189,    0, -114,    0, -124, -107,    0, -123, -106,
    0, -122, -127,    0, -161, -108,    0,    0, -161, -161,
 -105,    0,    0,    0,    0,    0,    0,    0, -104,    0,
    0,    0,    0, -102, -103, -101,    0, -124,  -98,    0,
 -123,  -99,    0, -122,    0,    0,  -97,    0,    0,  -95,
    0,    0,  -94,    0,    0,    0, -103,    0,  -93,    0,
    0,    0,    0,    0,    0,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    2,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  131,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    3,    0,    0,    0,    4,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  -92,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0, -210, -204, -198,    0,    0,    0,    0,    5,    0,
    0,    0,    6,    0,    0,    0,    0,    0,    1,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0, -192,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    8,    0,
    0,    0,    0,    0,  -91,    0,    0,    7,    0,    0,
   13,    0,    0,   19,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    9,  -56,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  -75,    0,    0,    0,    0,    0,  -42,
  -76,    0,    0,   44,   14,   10,   15,   11,   12,   16,
    0,   -4,   -5,  -14,  -66,
};
final static int YYTABLESIZE=307;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         74,
  101,   17,   38,   42,   39,   43,  106,   36,   73,   72,
  113,  125,  111,  135,   51,  129,  130,    3,  116,   52,
   61,   53,   67,  125,    4,    5,    6,    7,    8,    9,
   10,   11,   42,   59,   55,  112,   60,   12,   13,   14,
   15,   16,   17,   75,   54,  133,  134,   57,  156,  136,
  137,  138,   58,  158,  159,   18,   19,   56,  125,   85,
   86,   87,   88,   89,   90,   80,  155,  144,   79,  145,
   81,  109,   68,   68,   68,   68,   68,   68,   69,   69,
   69,   69,   69,   69,   70,   70,   70,   70,   70,   70,
  121,  121,  121,  121,  121,  121,   82,   76,   43,   45,
   47,   83,   44,   46,   48,   49,   84,  154,   96,   50,
   98,  148,  173,  151,   97,   62,   65,  109,   60,   66,
  100,   99,  102,  101,  104,  103,  106,  105,  107,  108,
    1,   60,  111,  123,  114,  117,  120,  127,  126,  154,
  128,  131,  146,  148,  139,  151,  140,  141,  142,  149,
  152,  157,  143,  163,  160,  161,  162,  164,  144,  167,
  166,  165,  169,  170,  172,   66,  100,  105,  168,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  175,
  176,    0,    0,    0,  177,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  101,   17,
   38,   42,   39,   43,  106,   36,    0,    0,    0,    0,
  111,    0,    0,    0,    0,    0,  116,    0,    0,    0,
    0,    0,    0,  101,  101,  101,  101,  101,  101,  106,
  106,  106,  106,  106,  106,  111,  111,  111,  111,  111,
  111,  116,  116,  116,  116,  116,  116,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         14,
    0,    0,    0,    0,    0,    0,    0,    0,   14,   14,
   67,   78,    0,   90,  256,  262,  263,  256,    0,  261,
   12,  259,   14,   90,  263,  264,  265,  266,  267,  268,
  269,  270,  258,  256,  259,  257,  259,  276,  277,  278,
  279,  280,  281,  260,  282,   88,   89,  256,  125,   92,
   93,   94,  261,  129,  130,  294,  295,  282,  125,  283,
  284,  285,  286,  287,  288,  256,  123,  257,  260,  259,
  261,   63,  283,  284,  285,  286,  287,  288,  283,  284,
  285,  286,  287,  288,  283,  284,  285,  286,  287,  288,
  283,  284,  285,  286,  287,  288,  256,  259,  256,  256,
  256,  261,  260,  260,  260,  256,  258,  122,  258,  260,
  258,  116,  169,  119,  260,  256,  256,  109,  259,  259,
  258,  260,  258,  260,  258,  260,  258,  260,  258,  258,
    0,  259,  258,  260,  259,  259,  259,  258,  260,  154,
  258,  260,  257,  148,  260,  151,  260,  260,  260,  257,
  257,  260,  109,  257,  260,  260,  259,  259,  257,  259,
  151,  148,  260,  259,  259,  259,  259,  259,  154,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  169,
  169,   -1,   -1,   -1,  169,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  258,  258,
  258,  258,  258,  258,  258,  258,   -1,   -1,   -1,   -1,
  258,   -1,   -1,   -1,   -1,   -1,  258,   -1,   -1,   -1,
   -1,   -1,   -1,  283,  284,  285,  286,  287,  288,  283,
  284,  285,  286,  287,  288,  283,  284,  285,  286,  287,
  288,  283,  284,  285,  286,  287,  288,
};
}
final static short YYFINAL=1;
final static short YYMAXTOKEN=299;
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
"NORMAL","PARAMETER","GROUP","SMOOTH","MERGE","OBJECT","RAT","DEGREE","BASIS",
"STEP","CSTYPE","POINT","LINE","FACE","CURVE","CURVE2D","SURFACE","OFF","END",
"PARAM","TRIM","HOLE","SPECCURVE","SPECPOINT","CONNECT","BEVEL","CINTER",
"DINTER","LOD","MATERIAL","LIBRARY","SHADOW","RAYTRACE","CURVEAPPROX",
"SURFACEAPPROX",
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
"grouping : smoothGroup",
"grouping : mergeGroup",
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
"csdata : csType",
"csdata : csdata DEGREE INT",
"csdata : csdata DEGREE INT INT",
"csdata : csdata BASIS U intchain",
"csdata : csdata BASIS VERTEX intchain",
"csdata : csdata STEP INT",
"csdata : csdata STEP INT INT",
"csType : CSTYPE STRING",
"csType : CSTYPE RAT STRING",
"cs : ffelement END",
"ffelement : ffdecl",
"ffelement : ffelement ffbody",
"ffdecl : CURVE FLOAT FLOAT vchain2",
"ffdecl : CURVE2D pchain2",
"ffdecl : SURFACE FLOAT FLOAT FLOAT FLOAT vertexChain2",
"ffbody : PARAM U pchain2",
"ffbody : PARAM VERTEX pchain2",
"ffbody : trim",
"ffbody : hole",
"ffbody : scrv",
"ffbody : sp",
"trim : TRIM curve2d",
"trim : trim curve2d",
"hole : HOLE curve2d",
"hole : hole curve2d",
"scrv : SPECCURVE curve2d",
"scrv : scrv curve2d",
"sp : SPECPOINT pchain",
"curve2d : FLOAT FLOAT c",
"object : OBJECT STRING",
"group : GROUP STRING",
"smoothGroup : SMOOTH INT",
"smoothGroup : SMOOTH OFF",
"mergeGroup : MERGE INT FLOAT",
"mergeGroup : MERGE OFF",
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
"c : INT",
"intchain : INT",
"intchain : INT intchain",
};

//#line 295 "obj.y"
/******************/		
			
	private OBJLexer lexer;
	private OBJRawData data = new OBJRawData();
	private CSData csdata;
	
	private static boolean debug = false;
	
	private int yylex () {
		int token = -1;
		try {
			token = lexer.yylex();
			if (debug && token != -1)
				System.out.println(yyname[token]);
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
//#line 510 "OBJParser.java"
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
//#line 77 "obj.y"
{  }
break;
case 4:
//#line 78 "obj.y"
{ System.out.println("\tReadjusting frame."); }
break;
case 5:
//#line 81 "obj.y"
{ data.corruptVertex(); }
break;
case 6:
//#line 82 "obj.y"
{ data.corruptTexture(); }
break;
case 7:
//#line 83 "obj.y"
{ data.corruptNormal(); }
break;
case 8:
//#line 84 "obj.y"
{ data.corruptParameter(); }
break;
case 9:
//#line 86 "obj.y"
{ data.corruptElement(); }
break;
case 10:
//#line 87 "obj.y"
{ data.corruptElement(); }
break;
case 11:
//#line 88 "obj.y"
{ data.corruptElement(); }
break;
case 12:
//#line 90 "obj.y"
{ System.out.println("Invalid material library definition."); }
break;
case 13:
//#line 91 "obj.y"
{ System.out.println("Invalid material definition."); }
break;
case 14:
//#line 92 "obj.y"
{ System.out.println("Invalid object definition."); }
break;
case 15:
//#line 93 "obj.y"
{ System.out.println("Invalid group definition."); }
break;
case 16:
//#line 95 "obj.y"
{ printError(); }
break;
case 36:
//#line 127 "obj.y"
{ data.addVertex(val_peek(2), val_peek(1), val_peek(0)); }
break;
case 37:
//#line 128 "obj.y"
{ data.addVertex(val_peek(3), val_peek(2), val_peek(1), val_peek(0)); }
break;
case 38:
//#line 130 "obj.y"
{ data.addTexture(val_peek(0)); }
break;
case 39:
//#line 131 "obj.y"
{ data.addTexture(val_peek(1), val_peek(0)); }
break;
case 40:
//#line 132 "obj.y"
{ data.addTexture(val_peek(2), val_peek(1), val_peek(0)); }
break;
case 41:
//#line 134 "obj.y"
{ data.addNormal(val_peek(2), val_peek(1), val_peek(0)); }
break;
case 42:
//#line 136 "obj.y"
{ data.addParameter(val_peek(0)); }
break;
case 43:
//#line 137 "obj.y"
{ data.addParameter(val_peek(1), val_peek(0)); }
break;
case 44:
//#line 138 "obj.y"
{ data.addParameter(val_peek(2), val_peek(1), val_peek(0)); }
break;
case 45:
//#line 143 "obj.y"
{ data.addElement(new Element.Point(new int[]{val_peek(0).i})); val_peek(0).freeValue(); }
break;
case 46:
//#line 145 "obj.y"
{ data.addElement(new Element.Line(val_peek(0).ints.toArray())); val_peek(0).freeValue(); }
break;
case 47:
//#line 147 "obj.y"
{ data.addElement(new Element.Face(val_peek(0).ints.toArray())); val_peek(0).freeValue(); }
break;
case 48:
//#line 148 "obj.y"
{ data.addElement(new Element.Face(val_peek(0).ints.toArray())); val_peek(0).freeValue(); }
break;
case 49:
//#line 149 "obj.y"
{ data.addElement(new Element.Face(val_peek(0).ints.toArray())); val_peek(0).freeValue(); }
break;
case 50:
//#line 150 "obj.y"
{ data.addElement(new Element.Face(val_peek(0).ints.toArray())); val_peek(0).freeValue(); }
break;
case 51:
//#line 155 "obj.y"
{ yyval = val_peek(0); }
break;
case 52:
//#line 156 "obj.y"
{ yyval = val_peek(2); ((CSData)yyval.obj).setDegree(val_peek(0)); }
break;
case 53:
//#line 157 "obj.y"
{ yyval = val_peek(3); ((CSData)yyval.obj).setDegree(val_peek(1), val_peek(0)); }
break;
case 54:
//#line 158 "obj.y"
{ yyval = val_peek(3); ((CSData)yyval.obj).setUMatrix(val_peek(0)); }
break;
case 55:
//#line 159 "obj.y"
{ yyval = val_peek(3); ((CSData)yyval.obj).setVMatrix(val_peek(0)); }
break;
case 56:
//#line 160 "obj.y"
{ yyval = val_peek(2); ((CSData)yyval.obj).setStep(val_peek(0)); }
break;
case 57:
//#line 161 "obj.y"
{ yyval = val_peek(3); ((CSData)yyval.obj).setStep(val_peek(1), val_peek(0)); }
break;
case 58:
//#line 163 "obj.y"
{ yyval = val_peek(0); yyval.obj = new CSData(val_peek(0), false); }
break;
case 59:
//#line 164 "obj.y"
{ yyval = val_peek(0); yyval.obj = new CSData(val_peek(0), true); }
break;
case 63:
//#line 173 "obj.y"
{ yyval = val_peek(2); yyval.obj = Element.Curve.makeCurve(csdata, val_peek(2).f, val_peek(1).f, val_peek(0).ints.toArray()); val_peek(1).freeValue(); val_peek(0).freeValue(); }
break;
case 64:
//#line 174 "obj.y"
{ yyval = val_peek(0); System.out.println("Curve2D needs implementation."); }
break;
case 65:
//#line 175 "obj.y"
{ yyval = val_peek(4); yyval.obj = Element.Surface.makeSurface(csdata, val_peek(4).f, val_peek(3).f, val_peek(2).f, val_peek(1).f, val_peek(0).ints.toArray()); val_peek(3).freeValue(); val_peek(2).freeValue(); val_peek(1).freeValue(); val_peek(0).freeValue(); }
break;
case 80:
//#line 201 "obj.y"
{ data.setObject(val_peek(0)); }
break;
case 81:
//#line 203 "obj.y"
{ data.setGroup(val_peek(0)); }
break;
case 82:
//#line 205 "obj.y"
{ data.setSmooth(val_peek(0)); }
break;
case 83:
//#line 206 "obj.y"
{ data.setSmooth(null); }
break;
case 84:
//#line 208 "obj.y"
{ data.setMerge(val_peek(1), val_peek(0)); }
break;
case 85:
//#line 209 "obj.y"
{ data.setMerge(null, null); }
break;
case 86:
//#line 214 "obj.y"
{ data.addLibrary(val_peek(0)); val_peek(0).freeValue(); }
break;
case 87:
//#line 216 "obj.y"
{ data.setMaterial(val_peek(0)); val_peek(0).freeValue(); }
break;
case 88:
//#line 221 "obj.y"
{ yyval = val_peek(0); }
break;
case 89:
//#line 222 "obj.y"
{ yyval = val_peek(0); }
break;
case 90:
//#line 223 "obj.y"
{ yyval = val_peek(0); }
break;
case 91:
//#line 224 "obj.y"
{ yyval = val_peek(0); }
break;
case 92:
//#line 226 "obj.y"
{ yyval = val_peek(0); }
break;
case 93:
//#line 227 "obj.y"
{ yyval = val_peek(0); }
break;
case 94:
//#line 228 "obj.y"
{ yyval = val_peek(0); }
break;
case 95:
//#line 229 "obj.y"
{ yyval = val_peek(0); }
break;
case 96:
//#line 231 "obj.y"
{ yyval = val_peek(0); }
break;
case 97:
//#line 232 "obj.y"
{ yyval = val_peek(0); }
break;
case 98:
//#line 233 "obj.y"
{ yyval = val_peek(0); }
break;
case 99:
//#line 234 "obj.y"
{ yyval = val_peek(0); }
break;
case 100:
//#line 237 "obj.y"
{ yyval = val_peek(0); yyval.i = data.evaluateVertex(val_peek(0)); }
break;
case 101:
//#line 239 "obj.y"
{ yyval = val_peek(0); yyval.ints = new LIntArray(val_peek(0).i); }
break;
case 102:
//#line 240 "obj.y"
{ yyval = val_peek(0); yyval.ints.prepend(val_peek(0).i); val_peek(1).freeValue(); }
break;
case 103:
//#line 242 "obj.y"
{ yyval = val_peek(0); yyval.ints.prepend(val_peek(1).i); val_peek(1).freeValue(); }
break;
case 104:
//#line 244 "obj.y"
{ yyval = val_peek(0); yyval.ints.prepend(val_peek(1).i); val_peek(1).freeValue(); }
break;
case 105:
//#line 247 "obj.y"
{ yyval = val_peek(2); yyval.ints = new LIntArray(data.evaluateVertex(val_peek(2)), data.evaluateTexture(val_peek(0))); val_peek(0).freeValue(); }
break;
case 106:
//#line 249 "obj.y"
{ yyval = val_peek(0); }
break;
case 107:
//#line 250 "obj.y"
{ yyval = val_peek(0); yyval.ints.prepend(val_peek(1).ints); val_peek(1).freeValue(); }
break;
case 108:
//#line 252 "obj.y"
{ yyval = val_peek(0); yyval.ints.prepend(val_peek(1).ints); val_peek(1).freeValue(); }
break;
case 109:
//#line 254 "obj.y"
{ yyval = val_peek(0); yyval.ints.prepend(val_peek(1).ints); val_peek(1).freeValue(); }
break;
case 110:
//#line 257 "obj.y"
{ yyval = val_peek(3); yyval.ints = new LIntArray(data.evaluateVertex(val_peek(3)), data.evaluateNormal(val_peek(0))); val_peek(0).freeValue(); }
break;
case 111:
//#line 259 "obj.y"
{ yyval = val_peek(0); }
break;
case 112:
//#line 260 "obj.y"
{ yyval = val_peek(0); yyval.ints.prepend(val_peek(1).ints); val_peek(1).freeValue(); }
break;
case 113:
//#line 262 "obj.y"
{ yyval = val_peek(0); yyval.ints.prepend(val_peek(1).ints); val_peek(1).freeValue(); }
break;
case 114:
//#line 264 "obj.y"
{ yyval = val_peek(0); yyval.ints.prepend(val_peek(1).ints); val_peek(1).freeValue(); }
break;
case 115:
//#line 267 "obj.y"
{ yyval = val_peek(4); yyval.ints = new LIntArray(data.evaluateVertex(val_peek(4)), data.evaluateTexture(val_peek(2)), data.evaluateNormal(val_peek(0))); val_peek(2).freeValue(); val_peek(0).freeValue(); }
break;
case 116:
//#line 269 "obj.y"
{ yyval = val_peek(0); }
break;
case 117:
//#line 270 "obj.y"
{ yyval = val_peek(0); yyval.ints.prepend(val_peek(1).ints); val_peek(1).freeValue(); }
break;
case 118:
//#line 272 "obj.y"
{ yyval = val_peek(0); yyval.ints.prepend(val_peek(1).ints); val_peek(1).freeValue(); }
break;
case 119:
//#line 274 "obj.y"
{ yyval = val_peek(0); yyval.ints.prepend(val_peek(1).ints); val_peek(1).freeValue(); }
break;
case 120:
//#line 277 "obj.y"
{ yyval = val_peek(0); yyval.i = data.evaluateParameter(val_peek(0)); }
break;
case 121:
//#line 279 "obj.y"
{ yyval = val_peek(0); yyval.ints = new LIntArray(val_peek(0).i); }
break;
case 122:
//#line 280 "obj.y"
{ yyval = val_peek(0); yyval.ints.prepend(val_peek(0).i); val_peek(1).freeValue(); }
break;
case 123:
//#line 282 "obj.y"
{ yyval = val_peek(0); yyval.ints.prepend(val_peek(1).i); val_peek(1).freeValue(); }
break;
case 124:
//#line 285 "obj.y"
{ 
															/*$$ = $1; $$.i = data.evaluateParameter($1); */
														}
break;
case 125:
//#line 290 "obj.y"
{ yyval = val_peek(0); yyval.ints = new LIntArray(val_peek(0).i); }
break;
case 126:
//#line 291 "obj.y"
{ yyval = val_peek(0); yyval.ints.prepend(val_peek(1).i); val_peek(1).freeValue(); }
break;
//#line 1013 "OBJParser.java"
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
