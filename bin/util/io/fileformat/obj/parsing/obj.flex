package util.io.fileformat.obj.parsing;
import java.io.File;
import java.io.Reader;
%%

%class OBJLexer
%byaccj



%{
	private OBJParser yyparser;
	private String str;

	public OBJLexer(Reader r, OBJParser yyparser) {
		this(r);
		this.yyparser = yyparser;
	}
  
	public int line(){
		return yyline + 1;
	}
  
	public int pos(){
		return yychar + 1;
	}
  
  
%}

INT				=	[+-]?[1-9][0-9]*
FLOAT			=	[+-]?\d+\.[0-9]+
STRING			=	[\.\w]+

U				=	u
VERTEX			=	v
TEXTURE			=	vt
NORMAL			=	vn
PARAMETER		=	vp

GROUP			=	g
SMOOTH			=	s
MERGE			=	mg
OBJECT			=	o

RAT				=	rat
DEGREE			=	deg
BASIS			=	bmat
STEP			=	step
CSTYPE			=	cstype

POINT			=	p
LINE			=	l
FACE			=	f
CURVE			=	curv
CURVE2D			=	curv2
SURFACE			=	surf

PARAM			=	parm
TRIM			=	trim
HOLE			=	hole
SPECCURVE		=	scrv
SPECPOINT		=	sp

OFF				=	off
END				=	end

CONNECT			=	con

BEVEL			=	bevel
CINTER			=	c_interp
DINTER			=	d_interp
LOD				=	lod
MATERIAL		=	usemtl
LIBRARY			=	mtllib
SHADOW			=	shadow_obj
RAYTRACE		=	trace_obj
CURVEAPPROX		=	ctech
SURFACEAPPROX	=	stech

NL			=   (\r\n|[\n\r]) 

%x COMMENT

%%

<COMMENT>{
	{NL}			{ yyline++; yychar = 0; yybegin(YYINITIAL); return OBJParser.NL; }
	.				{ yychar++; }
}         

	#				{ yychar++; yybegin(COMMENT); }

	\/				{ yychar++; return OBJParser.SLASH; }

	{INT}			{ yychar += yytext().length(); yyparser.yylval = OBJValue.newValue(Integer.parseInt(yytext())); return OBJParser.INT; }
	{FLOAT}			{ yychar += yytext().length(); yyparser.yylval = OBJValue.newValue(Float.parseFloat(yytext())); return OBJParser.FLOAT; }

	{U}				{ yychar += yytext().length(); return OBJParser.U; }
	{VERTEX}		{ yychar += yytext().length(); return OBJParser.VERTEX; }
	{TEXTURE}		{ yychar += yytext().length(); return OBJParser.TEXTURE; }
	{NORMAL}		{ yychar += yytext().length(); return OBJParser.NORMAL; }
	{PARAMETER}	    { yychar += yytext().length(); return OBJParser.PARAMETER; }

	{GROUP}		    { yychar += yytext().length(); return OBJParser.GROUP; }
	{SMOOTH}		{ yychar += yytext().length(); return OBJParser.SMOOTH; }
	{MERGE}		    { yychar += yytext().length(); return OBJParser.MERGE; }
	{OBJECT}		{ yychar += yytext().length(); return OBJParser.OBJECT; }

	{RAT}			{ yychar += yytext().length(); return OBJParser.RAT; }
	{DEGREE}		{ yychar += yytext().length(); return OBJParser.DEGREE; }
	{BASIS}		    { yychar += yytext().length(); return OBJParser.BASIS; }
	{STEP}		    { yychar += yytext().length(); return OBJParser.STEP; }
	{CSTYPE}		{ yychar += yytext().length(); return OBJParser.CSTYPE; }

	{POINT}		    { yychar += yytext().length(); return OBJParser.POINT; }
	{LINE}		    { yychar += yytext().length(); return OBJParser.LINE; }
	{FACE}		    { yychar += yytext().length(); return OBJParser.FACE; }
	{CURVE}		    { yychar += yytext().length(); return OBJParser.CURVE; }
	{CURVE2D}		{ yychar += yytext().length(); return OBJParser.CURVE2D; }
	{SURFACE}		{ yychar += yytext().length(); return OBJParser.SURFACE; }
	{PARAM}		    { yychar += yytext().length(); return OBJParser.PARAM; }
	{TRIM}			{ yychar += yytext().length(); return OBJParser.TRIM; }
	{HOLE}			{ yychar += yytext().length(); return OBJParser.HOLE; }
	{SPECCURVE}	    { yychar += yytext().length(); return OBJParser.SPECCURVE; }
	{SPECPOINT}	    { yychar += yytext().length(); return OBJParser.SPECPOINT; }

	{OFF}			{ yychar += yytext().length(); return OBJParser.OFF; }
	{END}			{ yychar += yytext().length(); return OBJParser.END; }
	
	{CONNECT}		{ yychar += yytext().length(); return OBJParser.CONNECT; }

	{BEVEL}		    { yychar += yytext().length(); return OBJParser.BEVEL; }
	{CINTER}		{ yychar += yytext().length(); return OBJParser.CINTER; }
	{DINTER}		{ yychar += yytext().length(); return OBJParser.DINTER; }
	{LOD}			{ yychar += yytext().length(); return OBJParser.LOD; }
	{MATERIAL}	    { yychar += yytext().length(); return OBJParser.MATERIAL; }
	{LIBRARY}		{ yychar += yytext().length(); return OBJParser.LIBRARY; }
	{SHADOW}		{ yychar += yytext().length(); return OBJParser.SHADOW; }
	{RAYTRACE}	    { yychar += yytext().length(); return OBJParser.RAYTRACE; }
	{CURVEAPPROX}	{ yychar += yytext().length(); return OBJParser.CURVEAPPROX; }
	{SURFACEAPPROX}	{ yychar += yytext().length(); return OBJParser.SURFACEAPPROX; }

	{STRING}		{ yychar += yytext().length(); yyparser.yylval = OBJValue.newValue(yytext()); return OBJParser.STRING; }

	\\{NL}			{ yyline++; yychar = 0; }
	{NL}			{ yyline++; yychar = 0; return OBJParser.NL; }
	[\t ]			{ yychar++;  }
	.				{ System.err.println("Unexpected ["+ yytext() + "] at line " + line() + ":" + pos() + "."); }
