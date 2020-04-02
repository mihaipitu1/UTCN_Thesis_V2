Command running:

lex suec.l
yacc -y suec.y
gcc -o suec.out lex.yy.c y.tab.c suec_interpreter.c
./suec.out test.suc
