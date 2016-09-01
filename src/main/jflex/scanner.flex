import java_cup.runtime.*;

/*User Code*/


%%

%public
%class Scanner
%unicode
%cup
%line
%column	
%eofval{
   {return new Symbol(sym.EOF, yyline, yycolumn, "");}
%eofval}

LineTerminator = \r|\n|\r\n

WhiteSpace = {LineTerminator}|[ \s\t\f]
	
Comment={TraditionalComment}|{EndOfLineComment}
	
TraditionalComment = "/*"[^*]~"*/"|"/*""*"+"/"
EndOfLineComment = "//".*

Identifier = [a-zA-Z][a-zA-Z0-9_]* 

IntegerLiteral = [0-9]+	
FloatLiteral = ([0-9]+"."[0-9]*)|([0-9]*"."[0-9]+)


	
%%
<YYINITIAL> {
	/*Lexical Rules*/
	
	"break"			{return new Symbol(sym.BREAK, yyline, yycolumn, yytext());}
	"class"			{return new Symbol(sym.CLASS, yyline, yycolumn, yytext());}
	"continue"		{return new Symbol(sym.CONTINUE, yyline, yycolumn, yytext());}
	"else"			{return new Symbol(sym.ELSE, yyline, yycolumn, yytext());}
	"for"			{return new Symbol(sym.FOR, yyline, yycolumn, yytext());}
	"if"			{return new Symbol(sym.IF, yyline, yycolumn, yytext());}
	"return"		{return new Symbol(sym.RETURN, yyline, yycolumn, yytext());}
	"while"			{return new Symbol(sym.WHILE, yyline, yycolumn, yytext());}
	"extern"		{return new Symbol(sym.EXTERN, yyline, yycolumn, yytext());}
	"void"			{return new Symbol(sym.TVOID, yyline, yycolumn, yytext());}
	"bool" 			{return new Symbol(sym.TBOOL, yyline, yycolumn, yytext());}
	"float"			{return new Symbol(sym.TFLOAT, yyline, yycolumn, yytext());}
	"integer"		{return new Symbol(sym.TINTEGER, yyline, yycolumn, yytext());}
	"true|false"    {return new Symbol(sym.BOOL_LITERAL, yyline, yycolumn, yytext());}

	"("				{return new Symbol(sym.LPAREN, yyline, yycolumn, yytext());}
	")"				{return new Symbol(sym.RPAREN, yyline, yycolumn, yytext());}
	"{"				{return new Symbol(sym.LBRACE, yyline, yycolumn, yytext());}
	"}"				{return new Symbol(sym.RBRACE, yyline, yycolumn, yytext());}
	"["				{return new Symbol(sym.LBRACK, yyline, yycolumn, yytext());}	
	"]"				{return new Symbol(sym.RBRACK, yyline, yycolumn, yytext());}
	";"				{return new Symbol(sym.SEMICOLON, yyline, yycolumn, yytext());}
	","				{return new Symbol(sym.COMMA, yyline, yycolumn, yytext());}

	"="             {return new Symbol(sym.EQ, yyline, yycolumn, yytext());}
  	">"             {return new Symbol(sym.GT, yyline, yycolumn, yytext());}
  	"<"             {return new Symbol(sym.LT, yyline, yycolumn, yytext());}
  	"!"             {return new Symbol(sym.NOT, yyline, yycolumn, yytext());}
  	"=="            {return new Symbol(sym.EQEQ, yyline, yycolumn, yytext());}
  	"<="            {return new Symbol(sym.LTEQ, yyline, yycolumn, yytext());}
  	">="            {return new Symbol(sym.GTEQ, yyline, yycolumn, yytext());}
  	"!="            {return new Symbol(sym.NOTEQ, yyline, yycolumn, yytext());}
 	"&&"            {return new Symbol(sym.ANDAND, yyline, yycolumn, yytext());}
  	"||"            {return new Symbol(sym.OROR, yyline, yycolumn, yytext());}
  	"+"             {return new Symbol(sym.PLUS, yyline, yycolumn, yytext());}
  	"-"             {return new Symbol(sym.MINUS, yyline, yycolumn, yytext());}
  	"*"				{return new Symbol(sym.MULT, yyline, yycolumn, yytext());}
	"/"				{return new Symbol(sym.DIV, yyline, yycolumn, yytext());}

  	"%"             {return new Symbol(sym.MOD, yyline, yycolumn, yytext());}
  	"+="            {return new Symbol(sym.PLUSEQ, yyline, yycolumn, yytext());}
  	"-="            {return new Symbol(sym.MINUSEQ, yyline, yycolumn, yytext());}

	{IntegerLiteral}			{return new Symbol(sym.INT_LITERAL, yyline, yycolumn, yytext());}
	{Identifier} 				{return new Symbol(sym.ID, yyline, yycolumn, yytext());}
	{FloatLiteral}   			{return new Symbol(sym.FLOAT_LITERAL, yyline, yycolumn, yytext());}
	{Comment}					{}
	{LineTerminator}			{}
	{WhiteSpace}				{} 

}