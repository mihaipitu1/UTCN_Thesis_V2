%{
#include "suec_header.h"
#include "string.h"
#include "stdarg.h"
#include "stdio.h"
#include "stdlib.h"
#include "y.tab.h"

nodeType *leafString(int type, char* value);
nodeType *leafInt(int type, int value);
nodeType *iden(int valueType, int charType, int value);
nodeType *operand(int oper, int nops, ...);
nodeType *varType(int charType, int value);
void freeNode(nodeType* node);
void yyerror(char* error);
char* trimString(char* inputString);

extern FILE* yyin;
extern int yydebug;
FILE* logFile;
FILE* outputFile;

%}

%union {
	int iValue;
	char variable;
	char* word;
	struct noperand *np;
};

%token INTEGER STRING
%token IF ELSE FOR WHILE
%token READ WRITE
%token LENGTH COPY UNITE COMPARE

%token <iValue> NUM
%token <variable> HCVAR
%token <variable> LCVAR
%token <word> WORD


%left GE LE EQ NE '>' '<'
%left '+' '-'
%left '*' '/'

%type <np> statement expression statementlist condStatement forStatement 
%type <np> whileStatement simplestatement variableStatement variable
%start program

%%

program : program statement	{ execute_node($2); freeNode($2); }
	| /* NULL */
	;


statement : simplestatement ';'
        | loopStatement
		| condStatement
		| '{' statementlist '}' { $$ = $2; }
        ;

variableStatement :  INTEGER variable { fprintf(logFile,"[Yacc] Got in variable definition >> INTEGER HCVAR\n"); $$ = iden(INTEGER,HCVAR,$2); } 
				| INTEGER LCVAR { fprintf(logFile,"[Yacc] Got in variable definition >> INTEGER LCVAR\n"); $$ = iden(INTEGER,LCVAR,$2); }
				| STRING HCVAR { fprintf(logFile,"[Yacc] Got in variable definition >> STRING HCVAR\n"); $$ = iden(STRING,HCVAR,$2); }
				| STRING LCVAR { fprintf(logFile,"[Yacc] Got in variable definition >> STRING LCVAR\n"); $$ = iden(STRING,LCVAR,$2); }
				;

variable : HCVAR { $$ = varType(HCVAR,$1);}
		 | LCVAR { $$ = varType(LCVAR,$1);}
		 ;		
condStatement : IF '(' expression ')' statement ELSE statement { fprintf(logFile,"[Yacc]Got in IF-ELSE\n");$$ = operand(IF,3,$3,$5,$7); }
		| IF '(' expression ')' statement { fprintf(logFile,"[Yacc] Got in IF\n");$$ = operand(IF,2,$3,$5); }
		;
		
loopStatement : whileStatement
			  | forStatement 
		;
		
forStatement :	FOR '(' simplestatement ';' expression ';' expression ')' statement {fprintf(logFile,"[Yacc] Got in FOR\n"); $$ = operand(FOR,4,$3,$5,$7,$9); }
		;
		
whileStatement :  WHILE '(' expression ')' statement { fprintf(logFile,"[Yacc] Got in WHILE\n");$$ = operand(WHILE, 2,$3,$5); }
		;
		
		
statementlist : statement
              | statementlist statement	{ $$ = operand(';', $1, $2); }
              ;

simplestatement : expression 
                | WRITE expression 		{ fprintf(logFile,"[Yacc] Got in WRITE\n");$$ = operand(WRITE, 1, $2); }
				| variable '=' expression	{ fprintf(logFile,"[Yacc] Got in = >> HCVAR\n");$$ = operand('=',2 , $1, $3); }
				| READ variable 		{ fprintf(logFile,"[Yacc] Got in READ LCVAR\n");$$ = operand(READ, 1, $2); }
				| variableStatement
				;

expression : NUM			{ fprintf(logFile,"[Yacc] Got in NUM\n");$$ = leafInt(NUM, $1); }
	   |  WORD   				{ fprintf(logFile,"[Yacc] Got in WORD\n");$$ = leafString(WORD,$1); }
	   | variable
	   | expression '+' expression	{ fprintf(logFile,"[Yacc] Got in +\n"); $$ = operand('+', 2, $1, $3); }
	   | expression '-' expression	{ fprintf(logFile,"[Yacc] Got in -\n");$$ = operand('-', 2, $1, $3); }
	   | expression '*' expression	{ fprintf(logFile,"[Yacc] Got in *\n");$$ = operand('*', 2, $1, $3); }
	   | expression '/' expression	{ fprintf(logFile,"[Yacc] Got in /\n");$$ = operand('/', 2, $1, $3); }
	   | expression '<' expression	{ fprintf(logFile,"[Yacc] Got in <\n");$$ = operand('<', 2, $1, $3); }
	   | expression '>' expression	{ fprintf(logFile,"[Yacc] Got in >\n");$$ = operand('>', 2, $1, $3); }
	   | expression GE expression	{ fprintf(logFile,"[Yacc] Got in >=\n");$$ = operand(GE, 2, $1, $3); }
	   | expression LE expression	{ fprintf(logFile,"[Yacc] Got in <=\n");$$ = operand(LE, 2, $1, $3); }
	   | expression NE expression	{ fprintf(logFile,"[Yacc] Got in !=\n");$$ = operand(NE, 2, $1, $3); }
	   | expression EQ expression	{ fprintf(logFile,"[Yacc] Got in ==\n");$$ = operand(EQ, 2, $1, $3); }
	   | LENGTH expression	{ fprintf(logFile,"[Yacc] Got in LENGTH\n");$$ = operand(LENGTH, 1, $2); }
	   | expression COPY expression	{ fprintf(logFile,"[Yacc] Got in COPY\n");$$ = operand(EQ, 2, $1, $3); }
	   | expression UNITE expression	{ fprintf(logFile,"[Yacc] Got in UNITE\n");$$ = operand(EQ, 2, $1, $3); }
	   | expression COMPARE expression	{ fprintf(logFile,"[Yacc] Got in COMPARE\n");$$ = operand(EQ, 2, $1, $3); }
	   | '(' expression ')'		{ $$ = $2; }
	   ;

%%

#define SIZE_NODE ((char*)&p->com - (char*)p)



nodeType* leafString(int type, char* value) {
	nodeType* node;
	if((node = (nodeType*)malloc(sizeof(nodeType))) == NULL)
	{
		yyerror("No memory left");
	}
	node->type = constType;
	node->constant.sValue = trimString(strdup(value));
	node->constant.type = type;
	return node;
}

nodeType* leafInt(int type, int value){
	nodeType* node;
	if((node = (nodeType*)malloc(sizeof(nodeType))) == NULL)
	{
		yyerror("No memory left");
	}
	node->type = constType;
	node->constant.iValue = value;
	node->constant.type = type;
	return node;
}

nodeType *iden(int valueType, int charType, int value) {
	nodeType* node;
	
	if((node = (nodeType*)malloc(sizeof(nodeType))) == NULL)
		yyerror("No memory left");
		
	node->type = idType;
	
	node->id.valueType = valueType;
	node->id.charType = charType;
	node->id.value = value;

	switch(charType) {
		case LCVAR: lcTypeSym[value] = valueType;
		case HCVAR: hcTypeSym[value] = valueType;
	}
	return node;
}

nodeType *varType(int charType, int value) {
	
	int valueType;

	switch(charType) {
		case LCVAR: valueType = lcTypeSym[value];
		case HCVAR: valueType = hcTypeSym[value];
	}

	return iden(valueType,charType,value);
}


nodeType *operand(int oper, int nops, ...) {
	va_list argList;
	nodeType* node;
	int i;
	
	if((node = malloc(sizeof(nodeType))) == NULL)
		yyerror("No memory left");
		
	if((node->oper.op = malloc(nops*sizeof(nodeType))) == NULL)
		yyerror("No memory left");
		
	node->type = operType;

	node->oper.oper = oper;
	node->oper.nops = nops;
	
	va_start(argList, nops);
	
	for(i=0;i<nops;i++)
		node->oper.op[i] = va_arg(argList, nodeType*);
		
	va_end(argList);
	
	return node;
}

void freeNode(nodeType* node) {
	int i;
	
	if(!node) return;
	
	if(node->type == operType) {
		for(i=0; i<node->oper.nops;i++)
			freeNode(node->oper.op[i]);
		free(node->oper.op);
	}
	free(node);
}

void yyerror(char* error) {
	extern char* yytext;
	extern int yylineno;
	fprintf(outputFile,"Error: %s At Line: %d - Char: %c\n", error,yylineno,*yytext);
}


char* trimString(char* inputString) {
	char* trimmed = (char*) malloc(strlen(inputString));

	strcpy(trimmed, inputString);
	strcpy(trimmed, trimmed + 1);
	trimmed[strlen(trimmed)-1] = 0;
	return trimmed;
} 

int main(int argc, char **argv)
{
	char* logFilePath = (char*)malloc(100);
	char* outputFilePath = (char*)malloc(100);

	strcpy(logFilePath, "./output/logs/suec.log");
	strcpy(outputFilePath,"./output/result/suec.output");

	if(!(logFile = fopen(logFilePath,"wb+")))
	{
		printf("[Yacc] Error at log file.\n");
		exit(1);
	}

	if(!(outputFile = fopen(outputFilePath,"wb+"))) 
	{
		printf("[Yacc] Error at output file.\n");
		exit(1);
	}

	if(argc != 2)
	{
		fprintf(logFile,"[Yacc] Error! No input file given.\n");
		exit(1);
	}
	if(!(yyin = fopen(argv[1],"r")))
	{
		fprintf(logFile,"[Yacc] Error! Cannot open file given.\n");
		exit(1);
	}

	yyparse();
	fclose(yyin);
	return 0;
}
	