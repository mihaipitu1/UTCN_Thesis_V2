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
		case HCVAR: fprintf(logFile,"[Interpreter] execute_id_int >> Got in INTEGER HCVAR - %c\n",'A'+p->id.value);return hcSymbols[p->id.value].iValue;
		case LCVAR: fprintf(logFile,"[Interpreter] execute_id_int >> Got in INTEGER LCVAR - %c\n",'a'+p->id.value);return lcSymbols[p->id.value].iValue;
	}
	return 0;
}

char* execute_id_str(nodeType *p){
	switch(p->id.charType) {
		case HCVAR: fprintf(logFile,"[Interpreter] execute_id_str >> Got in STRING HCVAR\n"); 
			return hcSymbols[p->id.value].sValue;
		case LCVAR: fprintf(logFile,"[Interpreter] execute_id_str >> Got in STRING LCVAR\n"); 
			return lcSymbols[p->id.value].sValue;
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

void do_print(nodeType *p) {
	switch(p->type) {
		case constType:
			fprintf(logFile,"[Interpreter] do_print >> Got in constType\n");
			switch(p->constant.type) {
				case WORD:
					fprintf(logFile,"[Interpreter] do_print >> Got in WORD\n");
					fprintf(outputFile,"%s\n", p->constant.sValue);
					break;		
				case NUM:
					fprintf(logFile,"[Interpreter] do_print >> Got in NUM\n");
					fprintf(outputFile,"%d\n", p->constant.iValue);
					break;
			}
			break;
		case idType:
			fprintf(logFile,"[Interpreter] do_print >> Got in idType\n"); 
			switch(p->id.valueType) {
				case INTEGER:
					fprintf(logFile,"[Interpreter] do_print >> Got in INTEGER\n");
					switch(p->id.charType) {
						case HCVAR: fprintf(logFile,"[Interpreter] do_print >> Got in HCVAR - %c - value: %d\n",'A'+p->id.value,hcSymbols[p->id.value].iValue);
									fprintf(outputFile,"%d\n",hcSymbols[p->id.value].iValue);
									break;
						case LCVAR: fprintf(logFile,"[Interpreter] do_print >> Got in LCVAR - %c - value: %d\n",'a'+p->id.value,lcSymbols[p->id.value].iValue);
									fprintf(outputFile,"%d\n",lcSymbols[p->id.value].iValue);
									break;	
					}
					break;
				case STRING:
					fprintf(logFile,"[Interpreter] do_print >> Got in STRING\n");
					switch(p->id.charType) {
						case HCVAR: fprintf(logFile,"[Interpreter] do_print >> Got in HCVAR - %c - value: %s\n",'A'+p->id.value,hcSymbols[p->id.value].sValue);
									fprintf(outputFile,"%s\n",hcSymbols[p->id.value].sValue);
									break;
						case LCVAR: fprintf(logFile,"[Interpreter] do_print >> Got in LCVAR - %c - value: %s\n",'a'+p->id.value,lcSymbols[p->id.value].sValue);
									fprintf(outputFile,"%s\n",lcSymbols[p->id.value].sValue);
									break;	
					}
					break;
			}
			break;
		case operType:
			fprintf(logFile,"[Interpreter] do_print >> Got in operType\n");
			fprintf(outputFile,"%d\n", execute_node(p));
			break;
	}
}

int execute_oper(nodeType *p) {

	if(!p)
	{
		fprintf(logFile,"[Interpreter] execute_oper >> Got an error here\n");
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
	case WRITE: fprintf(logFile,"[Interpreter] execute_oper >> do_print\n"); 
			 do_print(p->oper.op[0]);
			 return 0;
	case ';': fprintf(logFile,"[Interpreter] execute_oper >> Got in ; \n");
			  execute_node(p->oper.op[0]);
			  return execute_node(p->oper.op[1]);
		//main operations
	case '=': fprintf(logFile,"[Interpreter] execute_oper >> Got in =\n");
			  switch(p->oper.op[0]->id.valueType){
			  	case INTEGER: switch(p->oper.op[0]->id.charType)
			  			{
			  				case HCVAR: fprintf(logFile,"[Interpreter] execute_oper >> Got in HCVAR =\n"); return hcSymbols[p->oper.op[0]->id.value].iValue = execute_node(p->oper.op[1]);
			  				case LCVAR: fprintf(logFile,"[Interpreter] execute_oper >> Got in LCVAR =\n"); return lcSymbols[p->oper.op[0]->id.value].iValue = execute_node(p->oper.op[1]);
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
	case LENGTH: 
		fprintf(logFile,"[Interpreter] execute_oper >> Got in LENGTH\n");
		switch(p->oper.op[0]->type) {
				case constType: 
					fprintf(logFile,"[Interpreter] execute_oper >> LENGTH >> Constant is: %s\n",p->oper.op[0]->constant.sValue);
					return strlen(p->oper.op[0]->constant.sValue);
					break;
				case idType: 
					switch(p->oper.op[0]->id.charType) {
						case HCVAR: 
							fprintf(logFile,"[Interpreter] execute_oper >> LENGTH >> HCVAR is: %s\n",hcSymbols[p->oper.op[0]->id.value].sValue);
							return strlen(hcSymbols[p->oper.op[0]->id.value].sValue);
						case LCVAR:
							fprintf(logFile,"[Interpreter] execute_oper >> LENGTH >> LCVAR is: %s\n",lcSymbols[p->oper.op[0]->id.value].sValue);
							return strlen(lcSymbols[p->oper.op[0]->id.value].sValue);
					}
				}
	case COPY: 
		fprintf(logFile,"[Interpreter] execute_oper >> Got in COPY\n"); 
		switch(p->oper.op[0]->id.charType) {
			case HCVAR:
				fprintf(logFile,"[Interpreter] execute_oper >> COPY >> Oper 0 is HCVAR\n");
				switch(p->oper.op[1]->type) {
					case constType: 
						fprintf(logFile,"[Interpreter] execute_oper >> COPY >> Got in oper 1 as constant\n");
						fprintf(logFile,"[Interpreter] execute_oper >> COPY >> Constant is: %s\n",p->oper.op[1]->constant.sValue);
						fprintf(logFile,"[Interpreter] execute_oper >> COPY >> Before copy is: %s\n",hcSymbols[p->oper.op[0]->id.value].sValue);
						strcpy(hcSymbols[p->oper.op[0]->id.value].sValue,p->oper.op[1]->constant.sValue);
						fprintf(logFile,"[Interpreter] execute_oper >> COPY >> Resulted copy is: %s\n",hcSymbols[p->oper.op[0]->id.value].sValue);
						break;
					case idType: 
						strcpy(hcSymbols[p->oper.op[0]->id.value].sValue, strdup(execute_id_str(p->oper.op[1])));
				}
				break;
			case LCVAR:
				fprintf(logFile,"[Interpreter] execute_oper >> COPY >> Oper 0 is LCVAR\n");
				switch(p->oper.op[1]->type) {
					case constType: 
						fprintf(logFile,"[Interpreter] execute_oper >> COPY >> Got in oper 1 as constant\n");
						fprintf(logFile,"[Interpreter] execute_oper >> COPY >> Constant is: %s\n",p->oper.op[1]->constant.sValue);
						fprintf(logFile,"[Interpreter] execute_oper >> COPY >> Before copy is: %s\n",lcSymbols[p->oper.op[0]->id.value].sValue);
						strcpy(lcSymbols[p->oper.op[0]->id.value].sValue,p->oper.op[1]->constant.sValue);
						fprintf(logFile,"[Interpreter] COPY >> Resulted copy is: %s\n",lcSymbols[p->oper.op[0]->id.value].sValue);
						break;
					case idType: 
						strcpy(lcSymbols[p->oper.op[0]->id.value].sValue, strdup(execute_id_str(p->oper.op[1])));
				}
				break;
			}
		return 0;
	case UNITE: 
		fprintf(logFile,"[Interpreter] execute_oper >> Got in UNITE\n");
		switch(p->oper.op[0]->id.charType) {
			case HCVAR:
				fprintf(logFile,"[Interpreter] execute_oper >> UNITE >> Oper 0 is HCVAR\n");
				switch(p->oper.op[1]->type) {
					case constType: 
						fprintf(logFile,"[Interpreter] execute_oper >> UNITE >> Got in oper 1 as constant\n");
						fprintf(logFile,"[Interpreter] execute_oper >> UNITE >> Constant is: %s\n",p->oper.op[1]->constant.sValue);
						fprintf(logFile,"[Interpreter] execute_oper >> UNITE >> Before unite is: %s\n",hcSymbols[p->oper.op[0]->id.value].sValue);
						strcat(hcSymbols[p->oper.op[0]->id.value].sValue,p->oper.op[1]->constant.sValue);
						fprintf(logFile,"[Interpreter] execute_oper >> UNITE >> Resulted unite is: %s\n",hcSymbols[p->oper.op[0]->id.value].sValue);
						break;
					case idType: 
						strcat(hcSymbols[p->oper.op[0]->id.value].sValue, strdup(execute_id_str(p->oper.op[1])));
				}
				break;
			case LCVAR:
				fprintf(logFile,"[Interpreter] execute_oper >> UNITE >> Oper 0 is LCVAR\n");
				switch(p->oper.op[1]->type) {
					case constType: 
						fprintf(logFile,"[Interpreter] execute_oper >> UNITE >> Got in oper 1 as constant\n");
						fprintf(logFile,"[Interpreter] execute_oper >> UNITE >> Constant is: %s\n",p->oper.op[1]->constant.sValue);
						fprintf(logFile,"[Interpreter] execute_oper >> UNITE >> Before unite is: %s\n",lcSymbols[p->oper.op[0]->id.value].sValue);
						strcat(lcSymbols[p->oper.op[0]->id.value].sValue,p->oper.op[1]->constant.sValue);
						fprintf(logFile,"[Interpreter] execute_oper >> UNITE >> Resulted unite is: %s\n",lcSymbols[p->oper.op[0]->id.value].sValue);
						break;
					case idType: 
						strcat(lcSymbols[p->oper.op[0]->id.value].sValue, strdup(execute_id_str(p->oper.op[1])));
				}
				break;
			}
		return 0;
	case COMPARE: 
		fprintf(logFile,"[Interpreter] execute_oper >> Got in COMPARE\n"); 
		switch(p->oper.op[0]->id.charType) {
			case HCVAR:
				fprintf(logFile,"[Interpreter] execute_oper >> COMPARE >> Oper 0 is HCVAR\n");
				switch(p->oper.op[1]->type) {
					case constType: 
						fprintf(logFile,"[Interpreter] execute_oper >> COMPARE >> Got in oper 1 as constant\n");
						fprintf(logFile,"[Interpreter] execute_oper >> COMPARE >> Constant is: %s\n",p->oper.op[1]->constant.sValue);
						fprintf(logFile,"[Interpreter] execute_oper >> COMPARE >> Resulted unite is: %d\n",strcmp(hcSymbols[p->oper.op[0]->id.value].sValue,p->oper.op[1]->constant.sValue));
						return strcmp(hcSymbols[p->oper.op[0]->id.value].sValue,p->oper.op[1]->constant.sValue);
					case idType: 
						fprintf(logFile,"[Interpreter] execute_oper >> COMPARE >> Resulted unite is: %d\n",strcmp(lcSymbols[p->oper.op[0]->id.value].sValue,execute_id_str(p->oper.op[1])));
						return strcmp(hcSymbols[p->oper.op[0]->id.value].sValue, execute_id_str(p->oper.op[1]));
				}
				break;
			case LCVAR:
				fprintf(logFile,"[Interpreter] execute_oper >> COMPARE >> Oper 0 is LCVAR\n");
				switch(p->oper.op[1]->type) {
					case constType: 
						fprintf(logFile,"[Interpreter] execute_oper >> COMPARE >> Got in oper 1 as constant\n");
						fprintf(logFile,"[Interpreter] execute_oper >> COMPARE >> Constant is: %s\n",p->oper.op[1]->constant.sValue);
						fprintf(logFile,"[Interpreter] execute_oper >> COMPARE >> Before unite is: %s\n",lcSymbols[p->oper.op[0]->id.value].sValue);
						fprintf(logFile,"[Interpreter] execute_oper >> COMPARE >> Resulted unite is: %d\n",strcmp(lcSymbols[p->oper.op[0]->id.value].sValue,p->oper.op[1]->constant.sValue));
						return strcmp(lcSymbols[p->oper.op[0]->id.value].sValue,p->oper.op[1]->constant.sValue);
					case idType: 
						fprintf(logFile,"[Interpreter] execute_oper >> COMPARE >> Resulted unite is: %d\n",strcmp(lcSymbols[p->oper.op[0]->id.value].sValue,execute_id_str(p->oper.op[1])));
						return strcmp(lcSymbols[p->oper.op[0]->id.value].sValue, execute_id_str(p->oper.op[1]));
				}
				break;
			}
	}
	return 0;
}


