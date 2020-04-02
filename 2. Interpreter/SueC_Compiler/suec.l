%{
#include "y.tab.h"
#include "string.h"
#include "stdio.h"
#include "stdlib.h"

extern FILE* yyin;
%}

%%

"int"  {return INTEGER;}
"string" {return STRING;}	

 
"if"   {return IF;}
"else" {return ELSE;}
"for"  {return FOR;}
"while" {return WHILE;}
"read"  {return READ;}
"write" {return WRITE;}
"length" {return LENGTH;}
"copy" {return COPY;}
"unite" {return UNITE;}
"compare" {return COMPARE;}


[A-Z]	{
		yylval.variable = *yytext - 'A';
		return HCVAR;
	}

[a-z]	{
		yylval.variable = *yytext - 'a';
		return LCVAR;
	}



[0-9]+ 	{
			yylval.iValue = atoi(yytext);
			return NUM;
		}
		
\"[a-zA-Z .,!?-]+\"  	{
							yylval.word = strdup(yytext);
							return WORD;
						}


[-()+*=/<>;{}"]	 {return *yytext;}

">="		return GE;
"<="		return LE;
"=="		return EQ;
"!="		return NE;

[ \t\n]+	;

.			yyerror("Unknown character");

%%
int yywrap(void)
{
	return 1;
}
	