/*User Code*/

%%
%class Lexer
%unicode
%standalone
%line
%column	

LineTerminator = \r|\n|\r\n

WhiteSpace = {LineTerminator}|[ \s\t\f]
	
Comment={TraditionalComment}|{EndOfLineComment}
	
TraditionalComment = "/*"[^*]~"*/"|"/*""*"+"/"
EndOfLineComment = "//".*
IntegerLiteral = [0-9]+	
Identifier = [a-zA-Z][a-zA-Z0-9_]* 
FloatLiteral = ([0-9]+"."[0-9]*)|([0-9]*"."[0-9]+)


	
%%

	/*Lexical Rules*/
	
	"bool" 			{System.out.println("["+yyline+","+yycolumn+"]"+" BOOL");}
	"break"			{System.out.println("["+yyline+","+yycolumn+"]"+" BREAK");}
	"class"			{System.out.println("["+yyline+","+yycolumn+"]"+" CLASS");}
	"continue"		{System.out.println("["+yyline+","+yycolumn+"]"+" CONTINUE");}
	"else"			{System.out.println("["+yyline+","+yycolumn+"]"+" ELSE");}
	"false"			{System.out.println("["+yyline+","+yycolumn+"]"+" FALSE");}
	"float"			{System.out.println("["+yyline+","+yycolumn+"]"+" FLOAT");}
	"for"			{System.out.println("["+yyline+","+yycolumn+"]"+" FOR");}
	"if"			{System.out.println("["+yyline+","+yycolumn+"]"+" IF");}
	"integer"		{System.out.println("["+yyline+","+yycolumn+"]"+" INTEGER");}
	"return"		{System.out.println("["+yyline+","+yycolumn+"]"+" RETURN");}
	"true"			{System.out.println("["+yyline+","+yycolumn+"]"+" TRUE");}
	"void"			{System.out.println("["+yyline+","+yycolumn+"]"+" VOID");}
	"while"			{System.out.println("["+yyline+","+yycolumn+"]"+" WHILE");}
	"extern"		{System.out.println("["+yyline+","+yycolumn+"]"+" EXTERN");}

	"("				{System.out.println("["+yyline+","+yycolumn+"]"+" LPAREN");}
	")"				{System.out.println("["+yyline+","+yycolumn+"]"+" RPAREN");}
	"{"				{System.out.println("["+yyline+","+yycolumn+"]"+" LBRACE");}
	"}"				{System.out.println("["+yyline+","+yycolumn+"]"+" RBRACE");}
	"["				{System.out.println("["+yyline+","+yycolumn+"]"+" LBRACK");}	
	"]"				{System.out.println("["+yyline+","+yycolumn+"]"+" RBRACK");}
	";"				{System.out.println("["+yyline+","+yycolumn+"]"+" SEMICOLON");}		
	","				{System.out.println("["+yyline+","+yycolumn+"]"+" COMMA");}

	"="             {System.out.println("["+yyline+","+yycolumn+"]"+" EQ");}
  	">"             {System.out.println("["+yyline+","+yycolumn+"]"+" GT");}
  	"<"             {System.out.println("["+yyline+","+yycolumn+"]"+" LT");}
  	"!"             {System.out.println("["+yyline+","+yycolumn+"]"+" NOT");}
  	"=="            {System.out.println("["+yyline+","+yycolumn+"]"+" EQEQ");}
  	"<="            {System.out.println("["+yyline+","+yycolumn+"]"+" LTEQ");}
  	">="            {System.out.println("["+yyline+","+yycolumn+"]"+" GTEQ");}
  	"!="            {System.out.println("["+yyline+","+yycolumn+"]"+" NOTEQ");}
 	"&&"            {System.out.println("["+yyline+","+yycolumn+"]"+" ANDAND");}
  	"||"            {System.out.println("["+yyline+","+yycolumn+"]"+" OROR");}
  	"+"             {System.out.println("["+yyline+","+yycolumn+"]"+" PLUS");}
  	"-"             {System.out.println("["+yyline+","+yycolumn+"]"+" MINUS");}
  	
  	"%"             {System.out.println("["+yyline+","+yycolumn+"]"+" MOD");}
  	"+="            {System.out.println("["+yyline+","+yycolumn+"]"+" PLUSEQ");}
  	"-="            {System.out.println("["+yyline+","+yycolumn+"]"+" MINUSEQ");}

	{IntegerLiteral}			{System.out.println("["+yyline+","+yycolumn+"]"+"INTEGER "+yytext());}
	{Identifier} 				{System.out.println("["+yyline+","+yycolumn+"]"+"ID "+yytext());}
	{FloatLiteral}   			{System.out.println("["+yyline+","+yycolumn+"]"+"FLOAT "+yytext());}
	{Comment}					{System.out.println("Reconoci un comment!");}
	
