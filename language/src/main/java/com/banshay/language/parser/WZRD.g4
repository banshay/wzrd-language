grammar WZRD;



//start : stmt+ EOF ;
//
//stmt : kind=('var' | 'let' | 'const') binding1 (',' binding1)* ';'?     #DeclStmt
//     |                                                 expr1 ';'?     #ExprStmt
//     ;
//binding1 : ID ('=' expr1)? ;
//
//expr1 : ID '=' expr1               #AssignmentExpr1
//      | expr2                      #PrecedenceTwoExpr1
//      ;
//expr2 : left=expr2 '+' right=expr3 #AddExpr2
//      | expr3                      #PrecedenceThreeExpr2
//      ;
//expr3 : literal1                    #LiteralExpr3
//      | ID                         #ReferenceExpr3
//      | '(' expr1 ')'              #PrecedenceOneExpr3
//      ;
//
//literal1 : INT | DOUBLE | 'undefined' ;
//INT : DIGIT+ ;
//DOUBLE : DIGIT+ '.' DIGIT+ ;

//---------------------------------------------------------



wzrd
//   : clazz EOF
   : function function*
//   : statement statement*
   ;

//clazz
//   : 'class' CLASS (':' CLASS)? '{' (dependency)* (function)* '}'
//   ;

//dependency
//   : 'dep' CLASS
//   ;

function
   : 'fn' functionName '(' (ID (',' ID)*)? ')' block
   ;

functionName
   : ID
   ;

block
   : '{' statement* '}'
   ;

statement
   : block #BlockStatement
   | functionLiteral #FunctionStatement
   | expression #ExpressionStatement
   | whileExpression #WhileStatement
   | ifExpression #IfStatement
   ;

functionLiteral
: ID '(' (expression (',' expression)*)? ')'
;

ifExpression
   : 'if' expression block ('else' block)?
   ;

whileExpression
   : 'while' expression block
   ;

//member
//   : type ID ('=' expression)?
//   ;

expression
//   : '!' expression #
//   | ID '[' expression ']'
   : '(' expression ')' #NestedExpression
   | expression '*' expression #MultiplicationExpression
   | expression '/' expression #DivisionExpression
   | expression ('+' | '-') expression #AddExpression
   | expression comparisonToken expression #BooleanExpression
//   | ID ('=' ID | expression)? #BindingExpression
   | ID #VariableExpression
   | binding #BindingExpression
   | literal #LiteralExpression
   ;

comparisonToken
   : ('==' | ('<' | '>')('=')? | '&&' | '||')
   ;

binding
   : ID ('=' expression)?
   ;

literal
   : NUMBER #NumberLiteral
   | STR #StringLiteral
   ;

STR
   : '"' STRING*? '"'
   ;

WS
   : [ \t\r\n]+ -> skip
   ;

COMMENT
   : '/*' .*? '*/' -> skip
   ;

LINE_COMMENT
   : '//' ~ [\r\n]* -> skip
   ;

ID
   : LOWER (LETTER | DIGIT)*
   ;

NUMBER
   : [1-9] DIGIT* | [0]
   ;

fragment UPPER
   : [A-Z]
   ;

fragment LOWER
   : [a-z]
   ;

fragment LETTER
   : [A-Za-z]
   ;

fragment DIGIT
   : [0-9]
   ;

fragment STRING
   : ( '\\' [\\"] | ~[\\"\r\n] )
   ;

