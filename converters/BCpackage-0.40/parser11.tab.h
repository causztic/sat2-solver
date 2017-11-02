/* A Bison parser, made by GNU Bison 2.3.  */

/* Skeleton interface for Bison's Yacc-like parsers in C

   Copyright (C) 1984, 1989, 1990, 2000, 2001, 2002, 2003, 2004, 2005, 2006
   Free Software Foundation, Inc.

   This program is free software; you can redistribute it and/or modify
   it under the terms of the GNU General Public License as published by
   the Free Software Foundation; either version 2, or (at your option)
   any later version.

   This program is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
   GNU General Public License for more details.

   You should have received a copy of the GNU General Public License
   along with this program; if not, write to the Free Software
   Foundation, Inc., 51 Franklin Street, Fifth Floor,
   Boston, MA 02110-1301, USA.  */

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

/* Tokens.  */
#ifndef YYTOKENTYPE
# define YYTOKENTYPE
   /* Put the tokens into the symbol table, so that GDB and other debuggers
      know about them.  */
   enum yytokentype {
     LPAREN = 258,
     RPAREN = 259,
     LBRACKET = 260,
     RBRACKET = 261,
     SEMICOLON = 262,
     COMMA = 263,
     DEF = 264,
     ASSIGN = 265,
     ITEf = 266,
     NOTf = 267,
     EVENf = 268,
     ODDf = 269,
     ANDf = 270,
     ORf = 271,
     IMPLYf = 272,
     EQUIVf = 273,
     IMPLY = 274,
     EQUIV = 275,
     ODD = 276,
     OR = 277,
     AND = 278,
     NOT = 279,
     ID = 280,
     TRUE = 281,
     FALSE = 282,
     NUM = 283
   };
#endif
/* Tokens.  */
#define LPAREN 258
#define RPAREN 259
#define LBRACKET 260
#define RBRACKET 261
#define SEMICOLON 262
#define COMMA 263
#define DEF 264
#define ASSIGN 265
#define ITEf 266
#define NOTf 267
#define EVENf 268
#define ODDf 269
#define ANDf 270
#define ORf 271
#define IMPLYf 272
#define EQUIVf 273
#define IMPLY 274
#define EQUIV 275
#define ODD 276
#define OR 277
#define AND 278
#define NOT 279
#define ID 280
#define TRUE 281
#define FALSE 282
#define NUM 283




#if ! defined YYSTYPE && ! defined YYSTYPE_IS_DECLARED
typedef union YYSTYPE
#line 37 "parser11.y"
{
  char *charptr;
  int intval;
  Gate *gate;
  std::list<Gate*>* list;
}
/* Line 1529 of yacc.c.  */
#line 112 "parser11.tab.h"
	YYSTYPE;
# define yystype YYSTYPE /* obsolescent; will be withdrawn */
# define YYSTYPE_IS_DECLARED 1
# define YYSTYPE_IS_TRIVIAL 1
#endif

extern YYSTYPE bcp11_lval;

