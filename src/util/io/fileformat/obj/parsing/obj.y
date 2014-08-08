%{
import java.io.IOException;
import java.io.Reader;


import util.IntArray;
import util.LIntArray;

import util.io.fileformat.obj.elements.Element;
import util.io.fileformat.obj.elements.ElementType;
import util.io.fileformat.obj.parsing.CSData;
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

%token	OFF			
%token	END			

%token	PARAM		
%token	TRIM	
%token	HOLE		
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
				|	smoothGroup
				|	mergeGroup
				;
materials		:	library
				|	material
				;
/*********************/
/**Vertex Definitions*/
/*********************/
vertex			:	VERTEX FLOAT FLOAT FLOAT			{ data.addVertex($2, $3, $4); }
				|	VERTEX FLOAT FLOAT FLOAT FLOAT		{ data.addVertex($2, $3, $4, $5); }
				;
texture			:	TEXTURE FLOAT						{ data.addTexture($2); }
				|	TEXTURE FLOAT FLOAT					{ data.addTexture($2, $3); }
				|	TEXTURE FLOAT FLOAT FLOAT			{ data.addTexture($2, $3, $4); }
				;
normal			:	NORMAL FLOAT FLOAT FLOAT			{ data.addNormal($2, $3, $4); }
				;
parameter		:	PARAMETER FLOAT						{ data.addParameter($2); }
				|	PARAMETER FLOAT FLOAT				{ data.addParameter($2, $3); }
				|	PARAMETER FLOAT FLOAT FLOAT			{ data.addParameter($2, $3, $4); }
				;
/***************/		
/***Elements****/
/***************/		
point			:	POINT v								{ data.addElement(new Element.Point(new int[]{$2.i})); $2.freeValue(); }
				;
line			:	LINE vchain2						{ data.addElement(new Element.Line($2.ints.toArray())); $2.freeValue(); }
				;
face			:	FACE vchain3						{ data.addElement(new Element.Face($2.ints.toArray())); $2.freeValue(); }
				|	FACE vtchain3						{ data.addElement(new Element.Face($2.ints.toArray())); $2.freeValue(); }
				|	FACE vnchain3						{ data.addElement(new Element.Face($2.ints.toArray())); $2.freeValue(); }
				|	FACE vtnchain3						{ data.addElement(new Element.Face($2.ints.toArray())); $2.freeValue(); }
				;
/************************/		
/*Curve/Surface Elements*/
/************************/		
csdata			:	csType						{ $$ = $1; }
				|	csdata DEGREE INT					{ $$ = $1; ((CSData)$$.obj).setDegree($3); }
				|	csdata DEGREE INT INT				{ $$ = $1; ((CSData)$$.obj).setDegree($3, $4); }
				|	csdata BASIS U intchain				{ $$ = $1; ((CSData)$$.obj).setUMatrix($4); }
				|	csdata BASIS VERTEX intchain		{ $$ = $1; ((CSData)$$.obj).setVMatrix($4); }
				|	csdata STEP INT						{ $$ = $1; ((CSData)$$.obj).setStep($3); }
				|	csdata STEP INT INT					{ $$ = $1; ((CSData)$$.obj).setStep($3, $4); }
				;
csType			:	CSTYPE STRING				{ $$ = $2; $$.obj = new CSData($2, false); }
				|	CSTYPE RAT STRING			{ $$ = $3; $$.obj = new CSData($3, true); }
				
/***************/
cs				:	ffelement END
				;
/***************/
ffelement		:	ffdecl
				|	ffelement ffbody
				;
ffdecl			:	CURVE FLOAT FLOAT vchain2			{ $$ = $2; $$.obj = Element.Curve.makeCurve(csdata, $2.f, $3.f, $4.ints.toArray()); $3.freeValue(); $4.freeValue(); }
				|	CURVE2D pchain2						{ $$ = $2; System.out.println("Curve2D needs implementation."); }
				|	SURFACE FLOAT FLOAT FLOAT FLOAT	vertexChain2	{ $$ = $2; $$.obj = Element.Surface.makeSurface(csdata, $2.f, $3.f, $4.f, $5.f, $6.ints.toArray()); $3.freeValue(); $4.freeValue(); $5.freeValue(); $6.freeValue(); }
				;
/***************/
ffbody			:	PARAM U pchain2
				|	PARAM VERTEX pchain2
				|	trim
				|	hole
				|	scrv
				|	sp
				;
trim			:	TRIM curve2d
				|	trim curve2d
				;
hole			:	HOLE curve2d
				|	hole curve2d
				;
scrv			:	SPECCURVE curve2d
				|	scrv curve2d
				;
sp				:	SPECPOINT pchain
				;
curve2d			:	FLOAT FLOAT c
				;
/***************/		
/*****Groups****/
/***************/		
object			:	OBJECT STRING						{ data.setObject($2); }
				;
group			:	GROUP STRING						{ data.setGroup($2); }
				;
smoothGroup		:	SMOOTH INT							{ data.setSmooth($2); }
				|	SMOOTH OFF							{ data.setSmooth(null); }
				;
mergeGroup		:	MERGE INT FLOAT						{ data.setMerge($2, $3); }
				|	MERGE OFF							{ data.setMerge(null, null); }
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
vertexChain		:	vchain								{ $$ = $1; }
				|	vtchain								{ $$ = $1; }								
				|	vnchain								{ $$ = $1; }
				|	vtnchain							{ $$ = $1; }
				;
vertexChain2	:	vchain2								{ $$ = $1; }
				|	vtchain2							{ $$ = $1; }
				|	vnchain2							{ $$ = $1; }
				|	vtnchain2							{ $$ = $1; }
				;
vertexChain3	:	vchain3								{ $$ = $1; }
				|	vtchain3							{ $$ = $1; }
				|	vnchain3							{ $$ = $1; }
				|	vtnchain3							{ $$ = $1; }
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
c				:	INT									{ 
															//$$ = $1; $$.i = data.evaluateParameter($1); 
														}
				;
/***************/
intchain		:	INT									{ $$ = $1; $$.ints = new LIntArray($1.i); }
				|	INT intchain						{ $$ = $2; $$.ints.prepend($1.i); $1.freeValue(); }
				;
/******************/		
%%
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
