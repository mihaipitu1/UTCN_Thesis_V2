#include <stdio.h>
#include "suec_header.h"
#include "y.tab.h"
#include "string.h"

extern FILE* logFile;
extern FILE* outputFile;
 
int execute_node(nodeType *p) {
	fprintf(logFile,"[Interpreter] execute_node \n");
	if(!p) return 0;
	switch(p->type) {
		case constType: fprintf(logFile,"[Interpreter] execute_node >> execute_const() \n"); return execute_const(p);
		case idType: fprintf(logFile,"[Interpreter] execute_node >> execute_id() \n"); return execute_id(p);
		case operType: fprintf(logFile,"[Interpreter] execute_node >> execute_oper() \n"); return execute_oper(p);
	}
	return 0;
}

int execute_const(nodeType *p) {
	fprintf(logFile,"[Interpreter] execute_const \n");
	switch(p->constant.type) {
		case NUM: fprintf(logFile,"[Interpreter] execute_const >> Got in NUM - %d\n", p->constant.iValue);return p->constant.iValue;
		case WORD: fprintf(logFile,"[Interpreter] execute_const >> Got in WORD - %s\n", p->constant.sValue);return p->constant.sValue;
	}
	return 0;
}


int execute_id_int(nodeType *p) {
	switch(p->id.charType) {
		case HCVAR: fprintf(logFile,"[Interpreter] execute_id_int >> Got in INTEGER HCVAR - %c\n",'A'+p->id.value);return hcSym[p->id.value];
		case LCVAR: fprintf(logFile,"[Interpreter] execute_id_int >> Got in INTEGER LCVAR - %c\n",'a'+p->id.value);return lcSym[p->id.value];
	}
	return 0;
}

char* execute_id_str(nodeType *p){
	switch(p->id.charType) {
		case HCVAR: fprintf(logFile,"[Interpreter] execute_id_str >> Got in STRING HCVAR\n");return hcSymStr[p->id.value];
		case LCVAR: fprintf(logFile,"[Interpreter] execute_id_str >> Got in STRING LCVAR\n");return lcSymStr[p->id.value];
	}
	return NULL;
}


int execute_id(nodeType *p) {
	switch(p->id.valueType) {
		case INTEGER: fprintf(logFile,"[Interpreter] execute_id >> Got in INTEGER - execute_id_int() \n");return execute_id_int(p);
		case STRING: fprintf(logFile,"[Interpreter] execute_id >> Got in STRING - execute_id_str() \n");return (int)execute_id_str(p);
	}
	return 0;
}

int execute_oper(nodeType *p) {

	if(!p)
	{
		fprintf(logFile,"[Interpreter] execute_oper >> Got an error here");
		return 0;
	}
	switch(p->oper.oper) {
		//main keywords
	case INTEGER: fprintf(logFile, "[Interpreter] execute_oper >> Got in INTEGER\n");
			  return execute_id(p->oper.op[0]);
	case STRING: fprintf(logFile, "[Interpreter] execute_oper >> Got in STRING\n");
			  return execute_id(p->oper.op[0]);
	case FOR: fprintf(logFile,"[Interpreter] execute_oper >> Got in FOR\n"); 
			 for(execute_node(p->oper.op[0]);execute_node(p->oper.op[1]);execute_node(p->oper.op[2]))
				execute_node(p->oper.op[3]);
			  return 0;
	case WHILE: fprintf(logFile,"[Interpreter] execute_oper >> Got in WHILE\n"); 
			  while(execute_node(p->oper.op[0]))
				execute_node(p->oper.op[1]);
			  return 0;
	case IF: fprintf(logFile,"[Interpreter] execute_oper >> Got in IF\n");
			 if(execute_node(p->oper.op[0]))
				execute_node(p->oper.op[1]);
			 else if(p->oper.nops>2)
				execute_node(p->oper.op[2]);
			 return 0;
	case READ: fprintf(logFile,"[Interpreter] execute_oper >> Got in READ\n");
			 fscanf(stdin,"%d", execute_node(p->oper.op[0]));
			 return 0;
	case WRITE: 
				if(p->oper.op[0]->type == constType && p->oper.op[0]->constant.type == WORD) {
					fprintf(logFile,"[Interpreter] execute_oper >> Got in WRITE STRING\n");
					fprintf(outputFile,"%s\n", p->oper.op[0]->constant.sValue);		
					return 0;
				}
				else
				{ 
					fprintf(logFile,"[Interpreter] execute_oper >> Got in WRITE INT\n");
					fprintf(outputFile,"%d\n", execute_node(p->oper.op[0]));
				}
			 return 0;
	case ';': fprintf(logFile,"[Interpreter] execute_oper >> Got in ; \n");
			  execute_node(p->oper.op[0]);
			  return execute_node(p->oper.op[1]);
		//main operations
	case '=': fprintf(logFile,"[Interpreter] execute_oper >> Got in =\n");
			  switch(p->oper.op[0]->id.valueType){
			  	case INTEGER: switch(p->oper.op[0]->id.charType)
			  			{
			  				case HCVAR: fprintf(logFile,"[Interpreter] execute_oper >> Got in HCVAR =\n"); return hcSym[p->oper.op[0]->id.value] = execute_node(p->oper.op[1]);
			  				case LCVAR: fprintf(logFile,"[Interpreter] execute_oper >> Got in LCVAR =\n"); return lcSym[p->oper.op[0]->id.value] = execute_node(p->oper.op[1]);
			  			}
			  	}
			  return 0;
	case '+': fprintf(logFile,"[Interpreter] execute_oper >> Got in +\n");return execute_node(p->oper.op[0]) + execute_node(p->oper.op[1]);
	case '-': fprintf(logFile,"[Interpreter] execute_oper >> Got in -\n");return execute_node(p->oper.op[0]) - execute_node(p->oper.op[1]);
	case '*': fprintf(logFile,"[Interpreter] execute_oper >> Got in *\n");return execute_node(p->oper.op[0]) * execute_node(p->oper.op[1]);
	case '/': fprintf(logFile,"[Interpreter] execute_oper >> Got in /\n");return execute_node(p->oper.op[0]) / execute_node(p->oper.op[1]);
	case '<': fprintf(logFile,"[Interpreter] execute_oper >> Got in <\n");return execute_node(p->oper.op[0]) < execute_node(p->oper.op[1]);
	case '>': fprintf(logFile,"[Interpreter] execute_oper >> Got in >\n");return execute_node(p->oper.op[0]) > execute_node(p->oper.op[1]);
	case GE: fprintf(logFile,"[Interpreter] execute_oper >> Got in GE\n");return execute_node(p->oper.op[0]) >= execute_node(p->oper.op[1]);
	case LE: fprintf(logFile,"[Interpreter] execute_oper >> Got in LE\n");return execute_node(p->oper.op[0]) <= execute_node(p->oper.op[1]);
	case NE: fprintf(logFile,"[Interpreter] execute_oper >> Got in NE\n");return execute_node(p->oper.op[0]) != execute_node(p->oper.op[1]);
	case EQ: fprintf(logFile,"[Interpreter] execute_oper >> Got in EQ\n");return execute_node(p->oper.op[0]) == execute_node(p->oper.op[1]);
	case LENGTH: fprintf(logFile,"[Interpreter] execute_oper >> Got in LENGTH\n");return strlen(execute_node(p->oper.op[0]));
	}
	return 0;
}


