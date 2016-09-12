package main.java.lexer;
/*User Code*/


%%

%public
%class LexerStandalone
%unicode
%standalone
%line
%column	

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

		/*Keywords*/
	
	"bool" 			{System.out.println("["+(yyline+1)+","+yycolumn+"]"+" BOOL");}
	"break"			{System.out.println("["+(yyline+1)+","+yycolumn+"]"+" BREAK");}
	"class"			{System.out.println("["+(yyline+1)+","+yycolumn+"]"+" CLASS");}
	"continue"		{System.out.println("["+(yyline+1)+","+yycolumn+"]"+" CONTINUE");}
	"else"			{System.out.println("["+(yyline+1)+","+yycolumn+"]"+" ELSE");}
	"false"			{System.out.println("["+(yyline+1)+","+yycolumn+"]"+" FALSE");}
	"float"			{System.out.println("["+(yyline+1)+","+yycolumn+"]"+" FLOAT");}
	"for"			{System.out.println("["+(yyline+1)+","+yycolumn+"]"+" FOR");}
	"if"			{System.out.println("["+(yyline+1)+","+yycolumn+"]"+" IF");}
	"integer"		{System.out.println("["+(yyline+1)+","+yycolumn+"]"+" INTEGER");}
	"return"		{System.out.println("["+(yyline+1)+","+yycolumn+"]"+" RETURN");}
	"true"			{System.out.println("["+(yyline+1)+","+yycolumn+"]"+" TRUE");}
	"void"			{System.out.println("["+(yyline+1)+","+yycolumn+"]"+" VOID");}
	"while"			{System.out.println("["+(yyline+1)+","+yycolumn+"]"+" WHILE");}
	"extern"		{System.out.println("["+(yyline+1)+","+yycolumn+"]"+" EXTERN");}

		/*Delimiters*/

	"("				{System.out.println("["+(yyline+1)+","+yycolumn+"]"+" LPAREN");}
	")"				{System.out.println("["+(yyline+1)+","+yycolumn+"]"+" RPAREN");}
	"{"				{System.out.println("["+(yyline+1)+","+yycolumn+"]"+" LBRACE");}
	"}"				{System.out.println("["+(yyline+1)+","+yycolumn+"]"+" RBRACE");}
	"]"				{System.out.println("["+(yyline+1)+","+yycolumn+"]"+" RBRACK");}
	"["				{System.out.println("["+(yyline+1)+","+yycolumn+"]"+" LBRACK");}

	";"				{System.out.println("["+(yyline+1)+","+yycolumn+"]"+" SEMICOLON");}
	","				{System.out.println("["+(yyline+1)+","+yycolumn+"]"+" COMMA");}
	"."				{System.out.println("["+(yyline+1)+","+yycolumn+"]"+" DOT");}

		/*Operators*/

  	"+"             {System.out.println("["+(yyline+1)+","+yycolumn+"]"+" PLUS");}
  	"-"             {System.out.println("["+(yyline+1)+","+yycolumn+"]"+" MINUS");}
  	"*"             {System.out.println("["+(yyline+1)+","+yycolumn+"]"+" MULT");}
  	"/"             {System.out.println("["+(yyline+1)+","+yycolumn+"]"+" DIV");}
  	"%"             {System.out.println("["+(yyline+1)+","+yycolumn+"]"+" MOD");}

  	">"             {System.out.println("["+(yyline+1)+","+yycolumn+"]"+" GT");}
  	"<"             {System.out.println("["+(yyline+1)+","+yycolumn+"]"+" LT");}
  	"<="            {System.out.println("["+(yyline+1)+","+yycolumn+"]"+" LTEQ");}
  	">="            {System.out.println("["+(yyline+1)+","+yycolumn+"]"+" GTEQ");}
  	"=="            {System.out.println("["+(yyline+1)+","+yycolumn+"]"+" EQEQ");}
  	"!="            {System.out.println("["+(yyline+1)+","+yycolumn+"]"+" NOTEQ");}

 	"&&"            {System.out.println("["+(yyline+1)+","+yycolumn+"]"+" ANDAND");}
  	"||"            {System.out.println("["+(yyline+1)+","+yycolumn+"]"+" OROR");}
  	"!"             {System.out.println("["+(yyline+1)+","+yycolumn+"]"+" NOT");}

	"="             {System.out.println("["+(yyline+1)+","+yycolumn+"]"+" EQ");}
  	"+="            {System.out.println("["+(yyline+1)+","+yycolumn+"]"+" PLUSEQ");}
  	"-="            {System.out.println("["+(yyline+1)+","+yycolumn+"]"+" MINUSEQ");}

		/*Literals*/

	{IntegerLiteral}			{System.out.println("["+(yyline+1)+","+yycolumn+"]"+" INTEGER "+yytext());}
	{FloatLiteral}   			{System.out.println("["+(yyline+1)+","+yycolumn+"]"+" FLOAT "+yytext());}

		/*Identifiers*/

	{Identifier} 				{System.out.println("["+(yyline+1)+","+yycolumn+"]"+" ID "+yytext());}

		/*Comment*/

	{Comment}					{System.out.println("["+(yyline+1)+","+yycolumn+"]"+" COMMENT");}
	{LineTerminator}			{;}
	{WhiteSpace}				{;}

		/*Error*/

	.	{System.out.println("Invalid Symbol "+yytext()+" Line: "+yyline+" Column: "+yycolumn);}

}