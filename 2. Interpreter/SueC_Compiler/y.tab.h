/* A Bison parser, made by GNU Bison 3.0.4.  */

/* Bison interface for Yacc-like parsers in C

   Copyright (C) 1984, 1989-1990, 2000-2015 Free Software Foundation, Inc.

   This program is free software: you can redistribute it and/or modify
   it under the terms of the GNU General Public License as published by
   the Free Software Foundation, either version 3 of the License, or
   (at your option) any later version.

   This program is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
   GNU General Public License for more details.

   You should have received a copy of the GNU General Public License
   along with this program.  If not, see <http://www.gnu.org/licenses/>.  */

/* As a special exception, you may create a larger work that contains
   part or all of the Bison parser skeleton and distribute that work
   under terms of your choice, so long as that work isn't itself a
   parser generator using the skeleton or a modified version thereof
   as a parser skeleton.  Alternatively, if you modify or redistribute
   the parser skeleton itself, you may (at your option) remove this
   special exception, which will cause the skeleton and the resulting
   Bison output files to be licensed under the GNU General Public
   License without this special exception.

   This special exception was added by the Free Software Foundation in
   version 2.2 of Bison.  */

#ifndef YY_YY_Y_TAB_H_INCLUDED
# define YY_YY_Y_TAB_H_INCLUDED
/* Debug traces.  */
#ifndef YYDEBUG
# define YYDEBUG 0
#endif
#if YYDEBUG
extern int yydebug;
#endif

/* Token type.  */
#ifndef YYTOKENTYPE
# define YYTOKENTYPE
  enum yytokentype
  {
    INTEGER = 258,
    STRING = 259,
    IF = 260,
    ELSE = 261,
    FOR = 262,
    WHILE = 263,
    READ = 264,
    WRITE = 265,
    LENGTH = 266,
    COPY = 267,
    UNITE = 268,
    COMPARE = 269,
    NUM = 270,
    HCVAR = 271,
    LCVAR = 272,
    WORD = 273,
    GE = 274,
    LE = 275,
    EQ = 276,
    NE = 277
  };
#endif
/* Tokens.  */
#define INTEGER 258
#define STRING 259
#define IF 260
#define ELSE 261
#define FOR 262
#define WHILE 263
#define READ 264
#define WRITE 265
#define LENGTH 266
#define COPY 267
#define UNITE 268
#define COMPARE 269
#define NUM 270
#define HCVAR 271
#define LCVAR 272
#define WORD 273
#define GE 274
#define LE 275
#define EQ 276
#define NE 277

/* Value type.  */
#if ! defined YYSTYPE && ! defined YYSTYPE_IS_DECLARED

union YYSTYPE
{
#line 25 "suec.y" /* yacc.c:1909  */

	int iValue;
	char variable;
	char* word;
	struct noperand *np;

#line 105 "y.tab.h" /* yacc.c:1909  */
};

typedef union YYSTYPE YYSTYPE;
# define YYSTYPE_IS_TRIVIAL 1
# define YYSTYPE_IS_DECLARED 1
#endif


extern YYSTYPE yylval;

int yyparse (void);

#endif /* !YY_YY_Y_TAB_H_INCLUDED  */
