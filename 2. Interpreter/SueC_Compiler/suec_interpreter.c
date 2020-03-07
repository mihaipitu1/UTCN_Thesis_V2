#include <stdio.h>
#include "suec_header.h"
#include "y.tab.h"

extern FILE* yyin;
 
int execute_node(nodeType *p) {
	if(!p) return 0;
	switch(p->type) {
		case constType: return execute_const(p);
		case idType: return execute_id(p);
		case operType: return execute_oper(p);
	}
	return 0;
}

int execute_const(nodeType *p) {
	switch(p->constant.type) {
		case NUM: printf("[Interpreter] Got in NUM\n");return p->constant.iValue;
		case WORD: printf("[Interpreter] Got in WORD\n");return p->constant.sValue;
	}
	return 0;
}

int execute_id(nodeType *p) {
	switch(p->id.type) {
		case HCVAR: printf("[Interpreter] Got in HCVAR\n");return hcSym[p->id.value];
		case LCVAR: printf("[Interpreter] Got in LCVAR\n");return lcSym[p->id.value];
	}
	return 0;
}

int execute_oper(nodeType *p) {

	if(!p)
	{
		printf("[Interpreter] Got an error here");
		return 0;
	}
	switch(p->oper.oper) {
		//main keywords
	case FOR: printf("[Interpreter] Got in FOR\n"); 
			 for(execute_node(p->oper.op[0]);execute_node(p->oper.op[1]);execute_node(p->oper.op[2]))
				execute_node(p->oper.op[3]);
			  return 0;
	case WHILE: printf("[Interpreter] Got in WHILE\n"); 
			  while(execute_node(p->oper.op[0]))
				execute_node(p->oper.op[1]);
			  return 0;
	case IF: printf("[Interpreter] Got in IF\n");
			 if(execute_node(p->oper.op[0]))
				execute_node(p->oper.op[1]);
			 else if(p->oper.nops>2)
				execute_node(p->oper.op[2]);
			 return 0;
	case READ: printf("[Interpreter] Got in READ\n");
			 fscanf(yyin,"%d", execute_node(p->oper.op[0]));
			 return 0;
	case WRITE: printf("[Interpreter] Got in WRITE\n");
				printf("%d\n", execute_node(p->oper.op[0]));
			 return 0;
	case ';': execute_node(p->oper.op[0]);
			 return execute_node(p->oper.op[1]);
		//main operations
	case '=': printf("[Interpreter] Got in =\n");
			  switch(p->oper.op[0]->id.type)
			  {
			  	case HCVAR: return hcSym[p->oper.op[0]->id.value] = execute_node(p->oper.op[1]);
			  	case LCVAR: return lcSym[p->oper.op[0]->id.value] = execute_node(p->oper.op[1]);
			  }
			  return 0;
	case '+': printf("[Interpreter] Got in +\n");return execute_node(p->oper.op[0]) + execute_node(p->oper.op[1]);
	case '-': printf("[Interpreter] Got in -\n");return execute_node(p->oper.op[0]) - execute_node(p->oper.op[1]);
	case '*': printf("[Interpreter] Got in *\n");return execute_node(p->oper.op[0]) * execute_node(p->oper.op[1]);
	case '/': printf("[Interpreter] Got in /\n");return execute_node(p->oper.op[0]) / execute_node(p->oper.op[1]);
	case '<': printf("[Interpreter] Got in <\n");return execute_node(p->oper.op[0]) < execute_node(p->oper.op[1]);
	case '>': printf("[Interpreter] Got in >\n");return execute_node(p->oper.op[0]) > execute_node(p->oper.op[1]);
	case GE: printf("[Interpreter] Got in GE\n");return execute_node(p->oper.op[0]) >= execute_node(p->oper.op[1]);
	case LE: printf("[Interpreter] Got in LE\n");return execute_node(p->oper.op[0]) <= execute_node(p->oper.op[1]);
	case NE: printf("[Interpreter] Got in NE\n");return execute_node(p->oper.op[0]) != execute_node(p->oper.op[1]);
	case EQ: printf("[Interpreter] Got in EQ\n");return execute_node(p->oper.op[0]) == execute_node(p->oper.op[1]);
	}
	return 0;
}
