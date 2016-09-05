import java_cup.runtime.*;

/*User Code*/


%%

%public
%class Lexer
%unicode
%cup
%line
%column	
%eofval{
   {return new Symbol(Sym.EOF, yyline, yycolumn, "");}
%eofval}

LineTerminator = \r|\n|\r\n

WhiteSpace = {LineTerminator}|[ \s\t\f]
	
Comment={TraditionalComment}|{EndOfLineComment}
	
TraditionalComment = "/*"[^*]~"*/"|"/*""*"+"/"
EndOfLineComment = "//".*

Identifier = [a-zA-Z][a-zA-Z0-9_]* 

IntegerLiteral = [0-9]+	
FloatLiteral = ([0-9]+"."[0-9]+)


	
%%
<YYINITIAL> {
	/*Lexical Rules*/
	
	"break"			{return new Symbol(Sym.BREAK, yyline, yycolumn, yytext());}
	"class"			{return new Symbol(Sym.CLASS, yyline, yycolumn, yytext());}
	"continue"		{return new Symbol(Sym.CONTINUE, yyline, yycolumn, yytext());}
	"else"			{return new Symbol(Sym.ELSE, yyline, yycolumn, yytext());}
	"for"			{return new Symbol(Sym.FOR, yyline, yycolumn, yytext());}
	"if"			{return new Symbol(Sym.IF, yyline, yycolumn, yytext());}
	"return"		{return new Symbol(Sym.RETURN, yyline, yycolumn, yytext());}
	"while"			{return new Symbol(Sym.WHILE, yyline, yycolumn, yytext());}
	"extern"		{return new Symbol(Sym.EXTERN, yyline, yycolumn, yytext());}
	"void"			{return new Symbol(Sym.TVOID, yyline, yycolumn, yytext());}
	"bool" 			{return new Symbol(Sym.TBOOL, yyline, yycolumn, yytext());}
	"float"			{return new Symbol(Sym.TFLOAT, yyline, yycolumn, yytext());}
	"integer"		{return new Symbol(Sym.TINTEGER, yyline, yycolumn, yytext());}
	"true|false"    {return new Symbol(Sym.BOOL_LITERAL, yyline, yycolumn, yytext());}

	"("				{return new Symbol(Sym.LPAREN, yyline, yycolumn, yytext());}
	")"				{return new Symbol(Sym.RPAREN, yyline, yycolumn, yytext());}
	"{"				{return new Symbol(Sym.LBRACE, yyline, yycolumn, yytext());}
	"}"				{return new Symbol(Sym.RBRACE, yyline, yycolumn, yytext());}
	"["				{return new Symbol(Sym.LBRACK, yyline, yycolumn, yytext());}	
	"]"				{return new Symbol(Sym.RBRACK, yyline, yycolumn, yytext());}
	";"				{return new Symbol(Sym.SEMICOLON, yyline, yycolumn, yytext());}
	","				{return new Symbol(Sym.COMMA, yyline, yycolumn, yytext());}
	"."				{return new Symbol(Sym.DOT, yyline, yycolumn, yytext());}

	"="             {return new Symbol(Sym.EQ, yyline, yycolumn, yytext());}
  	">"             {return new Symbol(Sym.GT, yyline, yycolumn, yytext());}
  	"<"             {return new Symbol(Sym.LT, yyline, yycolumn, yytext());}
  	"!"             {return new Symbol(Sym.NOT, yyline, yycolumn, yytext());}
  	"=="            {return new Symbol(Sym.EQEQ, yyline, yycolumn, yytext());}
  	"<="            {return new Symbol(Sym.LTEQ, yyline, yycolumn, yytext());}
  	">="            {return new Symbol(Sym.GTEQ, yyline, yycolumn, yytext());}
  	"!="            {return new Symbol(Sym.NOTEQ, yyline, yycolumn, yytext());}
 	"&&"            {return new Symbol(Sym.ANDAND, yyline, yycolumn, yytext());}
  	"||"            {return new Symbol(Sym.OROR, yyline, yycolumn, yytext());}
  	"+"             {return new Symbol(Sym.PLUS, yyline, yycolumn, yytext());}
  	"-"             {return new Symbol(Sym.MINUS, yyline, yycolumn, yytext());}
  	"*"				{return new Symbol(Sym.MULT, yyline, yycolumn, yytext());}
	"/"				{return new Symbol(Sym.DIV, yyline, yycolumn, yytext());}

  	"%"             {return new Symbol(Sym.MOD, yyline, yycolumn, yytext());}
  	"+="            {return new Symbol(Sym.PLUSEQ, yyline, yycolumn, yytext());}
  	"-="            {return new Symbol(Sym.MINUSEQ, yyline, yycolumn, yytext());}

	{IntegerLiteral}			{return new Symbol(Sym.INT_LITERAL, yyline, yycolumn, yytext());}
	{Identifier} 				{return new Symbol(Sym.ID, yyline, yycolumn, yytext());}
	{FloatLiteral}   			{return new Symbol(Sym.FLOAT_LITERAL, yyline, yycolumn, yytext());}
	{Comment}					{}
	{LineTerminator}			{}
	{WhiteSpace}				{} 
	. { System.out.println("Invalid symbol " + yytext()+"Line: "+yyline+"Column: "+yycolumn);}
}