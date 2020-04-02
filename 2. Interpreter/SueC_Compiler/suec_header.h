typedef enum { constType, idType, operType } nodeEnum;

typedef struct {
	int type;
	int iValue;
	char* sValue;
} constNodeType;

typedef struct {
	int valueType;
	int charType;
	int value;
} idNodeType;

typedef struct {
	int oper;
	int nops;
	struct nodeTypeTag **op;
} operNodeType;

typedef struct nodeTypeTag {
	nodeEnum type;
	
	union {
		constNodeType constant;
		idNodeType id;
		operNodeType oper;
	}
} nodeType;

int hcTypeSym[26];
int lcTypeSym[26];
int hcSym[26];
int lcSym[26];
char hcSymStr[26][1024];
char lcSymStr[26][1024];