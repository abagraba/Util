%{
import java.io.IOException;
import java.io.Reader;
import java.util.LinkedList;
import java.io.InputStream;

%}

%token	SLASH
      
%token	INT
%token	FLOAT
      
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
file		:	stmt								{  }
			| 	file stmt							{  }
			;	

stmt		:	vertex
			|	texture
			|	normal
			;

vertex		:	VERTEX FLOAT FLOAT FLOAT			{ data.addVertex($2, $3, $4); }
			;
texture		:	TEXTURE FLOAT FLOAT					{ data.addTexture($2, $3); }
			;
normal		:	NORMAL FLOAT FLOAT FLOAT			{ data.addNormal($2, $3, $4); }
			;
		
element		:	line
			|	face
			;
		
point		:	POINT INT
			;
line		:	LINE INT INT						{ ints = new IntArray(new int[]{$2.i, $3.i}); }
			|	line INT							{ ints.add($2.i); }
			;
facev		:	FACE INT INT INT					{ ints = new IntArray(new int[]{$2.i, $3.i}); }
			|	face INT							{ ints.add($2.i); }
			;

			
vt			:	INT SLASH INT
			;
vn			:	INT SLASH SLASH INT
			;
vtn			:	INT SLASH INT SLASH INT
			;
			
vchain		:	INT INT INT							{ ints = new IntArray(new int[]{$1.i, $2.i, $3.i}); }
			|	vchain INT							( ints.add($2.i); )
			;
vtchain		:	vt vt vt							{ ints = new IntArray(new int[]{$1.i, $2.i, $3.i}); }
			|	vtchain vt							( ints.add($2.i); )
			;			
vnchain		:	vn vn vn							{ ints = new IntArray(new int[]{$1.i, $2.i, $3.i}); }
			|	vnchain vn							( ints.add($2.i); )
			;			
vtnchain	:	vtn vtn vtn
			|	vtnchain vtn
			;			

			
%%
	public static boolean debug = false;
	private OBJLexer lexer;

	private OBJRawData data = new OBJRawData();
	private IntArray ints;
	
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
		System.out.print("Error: [" + (lexer.line() + 1 )+ ':' + lexer.yytext() + "] :" + error);
	}


	public OBJParser(Reader r) {
		lexer = new OBJLexer(r, this);
	}
	
	
