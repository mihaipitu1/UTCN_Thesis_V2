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

typedef struct {
	int valueType;

	union {
		int iValue;
		char sValue[1024];
	}
} symVar;

symVar hcSymbols[26];
symVar lcSymbols[26];
