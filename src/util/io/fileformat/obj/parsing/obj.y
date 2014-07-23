%{
import java.io.IOException;
import java.io.Reader;


import util.IntArray;
import util.LIntArray;

import util.io.fileformat.obj.Element;
import util.io.fileformat.obj.Element.Type;
%}

%token	SLASH
%token	NL
      
%token	INT
%token	FLOAT
%token	STRING
      
%token	U
      
%token	VERTEX		
%token	TEXTURE		
%token	NORMAL		
%token	PARAMETER	

%token	GROUP		
%token	SMOOTH		
%token	MERGE		
%token	OBJECT		

%token	RAT		
%token	DEGREE		
%token	BASIS		
%token	STEP		
%token	CSTYPE		

%token	POINT		
%token	LINE		
%token	FACE		
%token	CURVE		
%token	CURVE2D		
%token	SURFACE		
%token	END			

%token	PARAM		
%token	TRIMOUT		
%token	TRIMIN		
%token	SPECCURVE	
%token	SPECPOINT	


%token	CONNECT		

%token	BEVEL		
%token	CINTER		
%token	DINTER		
%token	LOD			
%token	MATERIAL	
%token	LIBRARY		
%token	SHADOW		
%token	RAYTRACE	
%token	CURVEAPPROX	
%token	SURFACEAPPROX    
      


%%

/***************/		
end				:	file stmt
				;
file			:	
				| 	file stmt NL						{  }
				| 	file exception						{ System.out.println("\tReadjusting frame."); }
				;	
/***************/		
exception		:	VERTEX error NL						{ data.corruptVertex(); }
				|	TEXTURE error NL					{ data.corruptTexture(); }
				|	NORMAL error NL						{ data.corruptNormal(); }
				|	PARAMETER error NL					{ data.corruptParameter(); }
/***************/		
				|	POINT error NL						{ data.corruptElement(); }
				|	LINE error NL						{ data.corruptElement(); }
				|	FACE error NL						{ data.corruptElement(); }
/***************/		
				|	LIBRARY error NL					{ System.out.println("Invalid material library definition."); }
				|	MATERIAL error NL					{ System.out.println("Invalid material definition."); }
				|	OBJECT error NL						{ System.out.println("Invalid object definition."); }
				|	GROUP error NL						{ System.out.println("Invalid group definition."); }
/***************/		
				|	error NL							{ printError(); }
				;
/*****************/		
/*Core Constructs*/
/*****************/		
stmt			:	
				|	definition
				|	element
				|	grouping								
				|	materials
				;
definition		:	vertex
				|   texture
				|   normal
				|	parameter
				|	cs
				;	
element			:	point
				|	line
				|	face
				;
grouping		:	object
				|	group
				;
materials		:	library
				|	material
				;
/*********************/
/**Vertex Definitions*/
/*********************/
vertex			:	VERTEX FLOAT FLOAT FLOAT			{ data.addVertex($2, $3, $4); $2.freeValue(); $3.freeValue(); $4.freeValue(); }
				|	VERTEX FLOAT FLOAT FLOAT FLOAT		{ data.addVertex($2, $3, $4, $5); $2.freeValue(); $3.freeValue(); $4.freeValue(); $5.freeValue(); }
				;
texture			:	TEXTURE FLOAT						{ data.addTexture($2); $2.freeValue(); }
				|	TEXTURE FLOAT FLOAT					{ data.addTexture($2, $3); $2.freeValue(); $3.freeValue(); }
				|	TEXTURE FLOAT FLOAT FLOAT			{ data.addTexture($2, $3, $4); $2.freeValue(); $3.freeValue(); $4.freeValue(); }
				;
normal			:	NORMAL FLOAT FLOAT FLOAT			{ data.addNormal($2, $3, $4); $2.freeValue(); $3.freeValue(); $4.freeValue(); }
				;
parameter		:	PARAMETER FLOAT						{ data.addParameter($2); $2.freeValue(); }
				|	PARAMETER FLOAT FLOAT				{ data.addParameter($2, $3); $2.freeValue(); $3.freeValue(); }
				|	PARAMETER FLOAT FLOAT FLOAT			{ data.addParameter($2, $3, $4); $2.freeValue(); $3.freeValue(); $4.freeValue(); }
				;
/****************/		
/*Basic Elements*/
/****************/		
point			:	POINT v								{ data.addElement(new Element(Type.POINT, new int[]{$2.i})); $2.freeValue(); }
				;
line			:	LINE vchain2						{ data.addElement(new Element(Type.LINE, $2.ints.toArray())); $2.freeValue(); }
				;
face			:	FACE vchain3						{ data.addElement(new Element(Type.FACEV, $2.ints.toArray())); $2.freeValue(); }
				|	FACE vtchain3						{ data.addElement(new Element(Type.FACEVT, $2.ints.toArray())); $2.freeValue(); }
				|	FACE vnchain3						{ data.addElement(new Element(Type.FACEVN, $2.ints.toArray())); $2.freeValue(); }
				|	FACE vtnchain3						{ data.addElement(new Element(Type.FACEVTN, $2.ints.toArray())); $2.freeValue(); }
				;
/************************/		
/*Curve/Surface Elements*/
/************************/		
csdata			:	cstype								{ $$ = $1; }
				|	cheader DEGREE INT					{ $$ = $1; (CurveSurfaceData)($$.obj).setDegree($3); }
				|	sheader DEGREE INT INT				{ $$ = $1; (CurveSurfaceData)($$.obj).setDegree($3, $4); }
				|	cheader BASIS U intchain			{ $$ = $1; (CurveSurfaceData)($$.obj).setUMatrix($4); }
				|	cheader BASIS VERTEX intchain		{ $$ = $1; (CurveSurfaceData)($$.obj).setVMatrix($4); }
				|	cheader STEP INT					{ $$ = $1; (CurveSurfaceData)($$.obj).setStep($3); }
				|	sheader STEP INT INT				{ $$ = $1; (CurveSurfaceData)($$.obj).setStep($3, $4); }
				;
cstype			:	CSTYPE STRING						{ $$ = $2; $$.obj = new CurveSurfaceData($2, false); }
				|	CSTYPE RAT STRING					{ $$ = $3; $$.obj = new CurveSurfaceData($3, true); }
				;
/***************/
cs				:	ffelement END
				;
/***************/
ffelement		:	ffdecl
				;
ffdecl			:	CURVE FLOAT FLOAT vchain2			{  }
				|	CURVE2D pchain2						{  }
				|	SURFACE FLOAT FLOAT FLOAT FLOAT	vertexChain2	{  }
				;
/***************/		
/*****Groups****/
/***************/		
object			:	OBJECT STRING						{ data.setObject($2); $2.freeValue(); }
				;
group			:	GROUP STRING						{ data.setGroup($2); $2.freeValue(); }
				;
/***************/		
/***Materials***/
/***************/	
library			:	LIBRARY STRING						{ data.addLibrary($2); $2.freeValue(); }
				;	
material		:	MATERIAL STRING						{ data.setMaterial($2); $2.freeValue(); }
				;
/******************/		
/*Number Arguments*/
/******************/
vertexChain		:	vchain
				|	vtchain
				|	vnchain
				|	vtnchain
				;
vertexChain2	:	vchain2
				|	vtchain2
				|	vnchain2
				|	vtnchain2
				;
vertexChain3	:	vchain3
				|	vtchain3
				|	vnchain3
				|	vtnchain3
				;
/***************/
v				:	INT									{ $$ = $1; $$.i = data.evaluateVertex($1); }
				;			
vchain			:	v									{ $$ = $1; $$.ints = new LIntArray($1.i); }
				|	v vchain							{ $$ = $2; $$.ints.prepend($2.i); $1.freeValue(); }
				;
vchain2			:	v vchain							{ $$ = $2; $$.ints.prepend($1.i); $1.freeValue(); }
				;	
vchain3			:	v vchain2							{ $$ = $2; $$.ints.prepend($1.i); $1.freeValue(); }
				;
/***************/
vt				:	INT SLASH INT						{ $$ = $1; $$.ints = new LIntArray(data.evaluateVertex($1), data.evaluateTexture($3)); $3.freeValue(); }
				;
vtchain			:	vt									{ $$ = $1; }
				|	vt vtchain							{ $$ = $2; $$.ints.prepend($1.ints); $1.freeValue(); }
				;
vtchain2		:	vt vtchain							{ $$ = $2; $$.ints.prepend($1.ints); $1.freeValue(); }
				;			
vtchain3		:	vt vtchain2							{ $$ = $2; $$.ints.prepend($1.ints); $1.freeValue(); }
				;
/***************/
vn				:	INT SLASH SLASH INT					{ $$ = $1; $$.ints = new LIntArray(data.evaluateVertex($1), data.evaluateNormal($4)); $4.freeValue(); }
				;
vnchain			:	vn									{ $$ = $1; }
				|	vn vnchain							{ $$ = $2; $$.ints.prepend($1.ints); $1.freeValue(); }
				;
vnchain2		:	vn vnchain							{ $$ = $2; $$.ints.prepend($1.ints); $1.freeValue(); }
				;			
vnchain3		:	vn vnchain2							{ $$ = $2; $$.ints.prepend($1.ints); $1.freeValue(); }
				;
/***************/
vtn				:	INT SLASH INT SLASH INT				{ $$ = $1; $$.ints = new LIntArray(data.evaluateVertex($1), data.evaluateTexture($3), data.evaluateNormal($5)); $3.freeValue(); $5.freeValue(); }
				;
vtnchain		:	vtn									{ $$ = $1; }
				|	vtn vtnchain						{ $$ = $2; $$.ints.prepend($1.ints); $1.freeValue(); }
				;
vtnchain2		:	vtn vtnchain						{ $$ = $2; $$.ints.prepend($1.ints); $1.freeValue(); }
				;			
vtnchain3		:	vtn vtnchain2						{ $$ = $2; $$.ints.prepend($1.ints); $1.freeValue(); }
				;
/***************/
p				:	INT									{ $$ = $1; $$.i = data.evaluateParameter($1); }
				;
pchain			:	p									{ $$ = $1; $$.ints = new LIntArray($1.i); }
				|	p pchain							{ $$ = $2; $$.ints.prepend($2.i); $1.freeValue(); }
				;
pchain2			:	p pchain							{ $$ = $2; $$.ints.prepend($1.i); $1.freeValue(); }
				;
/***************/
intchain		:	INT									{ $$ = $1; $$.ints = new LIntArray($1.i); }
				|	INT intchain						{ $$ = $2; $$.ints.prepend($1.i); $1.freeValue(); }
				;
/******************/		
%%
/******************/		
			
	public static boolean debug = false;
	private OBJLexer lexer;
	private OBJRawData data = new OBJRawData();
	private CurveSurfaceData csdata;
	
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
