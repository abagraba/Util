%{
import java.io.IOException;
import java.io.Reader;

import util.io.fileformat.obj.Element;
import util.io.fileformat.obj.Element.Type;
%}

%token	SLASH
%token	NL
      
%token	INT
%token	FLOAT
%token	STRING
      
%token	VERTEX		
%token	TEXTURE		
%token	NORMAL		
%token	PARAMETER	

%token	GROUP		
%token	SMOOTH		
%token	MERGE		
%token	OBJECT		

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

%token	PARAM		
%token	TRIMOUT		
%token	TRIMIN		
%token	SPECCURVE	
%token	SPECPOINT	

%token	END			

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

end			:	file stmt
			;

file		:	
			| 	file stmt NL						{  }
			| 	file exception						{ System.out.println("\tReadjusting frame."); }
			;	

exception	:	VERTEX error NL						{ data.corruptVertex(); }
			|	TEXTURE error NL					{ data.corruptTexture(); }
			|	NORMAL error NL						{ data.corruptNormal(); }
			|	FACE error NL						{ data.corruptElement(); }
			|	LIBRARY error NL					{ System.out.println("Invalid material library definition."); }
			|	MATERIAL error NL					{ System.out.println("Invalid material definition."); }
			|	GROUP error NL						{ System.out.println("Invalid group definition."); }
			|	error NL							{ printError(); }
			;

/*****************/		
/*Core Constructs*/
/*****************/		
stmt		:	
			|	definition
			|	element
			|	group								
			|	library
			|	material
			;

definition	:	vertex
			|   texture
			|   normal
			;	

/**************/		
/*Vertex Stuff*/
/**************/		
vertex		:	VERTEX FLOAT FLOAT FLOAT			{ data.addVertex($2, $3, $4); $2.freeValue(); $3.freeValue(); $4.freeValue(); }
			;
texture		:	TEXTURE FLOAT FLOAT					{ data.addTexture($2, $3); $2.freeValue(); $3.freeValue(); }
			;
normal		:	NORMAL FLOAT FLOAT FLOAT			{ data.addNormal($2, $3, $4); $2.freeValue(); $3.freeValue(); $4.freeValue(); }
			;

/***************/		
/*Element Stuff*/
/***************/		
element		:	point
			|	line
			|	face
			;

point		:	POINT v								{ data.addElement(new Element(Type.POINT, new int[]{$2.i})); $2.freeValue(); }
			;
line		:	LINE vchain2						{ data.addElement(new Element(Type.LINE, $2.ints.toArray())); $2.freeValue(); }
			;
face		:	FACE vchain3						{ data.addElement(new Element(Type.FACEV, $2.ints.toArray())); $2.freeValue(); }
			|	FACE vtchain3						{ data.addElement(new Element(Type.FACEVT, $2.ints.toArray())); $2.freeValue(); }
			|	FACE vnchain3						{ data.addElement(new Element(Type.FACEVN, $2.ints.toArray())); $2.freeValue(); }
			|	FACE vtnchain3						{ data.addElement(new Element(Type.FACEVTN, $2.ints.toArray())); $2.freeValue(); }
			;
			
/********/		
/*Groups*/
/********/		
group		:	GROUP STRING						{ data.setGroup($2); }
			;

/***********/		
/*Materials*/
/***********/	
library		:	LIBRARY STRING						{ $$ = $1; }
			;	
material	:	MATERIAL STRING						{ data.setMaterial($2); }
			;

/******************/		
/*Number Arguments*/
/******************/		
v			:	INT									{ $$ = $1; $$.i = data.evaluateVertex($1); }
			;			
vt			:	INT SLASH INT						{ $$ = $1; $$.ints = new IntArray(data.evaluateVertex($1), data.evaluateTexture($3)); $3.freeValue(); }
			;
vn			:	INT SLASH SLASH INT					{ $$ = $1; $$.ints = new IntArray(data.evaluateVertex($1), data.evaluateNormal($4)); $4.freeValue(); }
			;
vtn			:	INT SLASH INT SLASH INT				{ $$ = $1; $$.ints = new IntArray(data.evaluateVertex($1), data.evaluateTexture($3), data.evaluateNormal($5)); $3.freeValue(); $5.freeValue(); }
			;
	
vchain2		:	v v									{ $$ = $1; $$.ints = new IntArray($1.i, $2.i); $2.freeValue(); }
			|	vchain2 v							{ $$ = $1; $$.ints.add($2.i); $2.freeValue(); }
			;	
			
vchain3		:	v v v								{ $$ = $1; $$.ints = new IntArray($1.i, $2.i, $3.i); $2.freeValue(); $3.freeValue(); }
			|	vchain3 v							{ $$ = $1; $$.ints.add($2.i); $2.freeValue(); }
			;
vtchain3	:	vt vt vt							{ $$ = $1; $$.ints.append($2.ints, $3.ints); $2.freeValue(); $3.freeValue(); }
			|	vtchain3 vt							{ $$ = $1; $$.ints.append($2.ints); $2.freeValue(); }
			;			
vnchain3	:	vn vn vn							{ $$ = $1; $$.ints.append($2.ints, $3.ints); $2.freeValue(); $3.freeValue(); }
			|	vnchain3 vn							{ $$ = $1; $$.ints.append($2.ints); $2.freeValue(); }
			;			
vtnchain3	:	vtn vtn vtn 						{ $$ = $1; $$.ints.append($2.ints, $3.ints); $2.freeValue(); $3.freeValue(); }
			|	vtnchain3 vtn						{ $$ = $1; $$.ints.append($2.ints); $2.freeValue(); }
			;			

/******************/		
/******************/		

			
%%
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
	
	
