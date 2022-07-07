grammar WZRD;

test
   : testExpression EOF
   ;

testExpression
   : left = testExpression '+' right = testExpression #AddExpression
   | literal #LiteralExpression
   ;

wzrd
   : clazz EOF
   ;

clazz
   : 'class' CLASS (':' CLASS)? '{' (dependency)* (function)* '}'
   ;

dependency
   : 'dep' CLASS
   ;

function
   : type ID '(' (member (',' member)*)? ')' block
   ;

block
   : '{' statement* '}'
   ;

statement
   : block
   | member
   | expression
   | 'if' expression block ('else' block)?
   | 'return' expression?
   ;

object
   : 'data' ID '{' (member)+ '}'
   ;

member
   : type ID ('=' expression)?
   ;

expression
   : ID '(' (expression (',' expression)*)? ')'
   | ID '[' expression ']'
   | '!' expression
   | expression '*' expression
   | expression ('+' | '-') expression
   | ID
   | literal
   | '(' expression ')'
   ;

literal
   : NUMBER
   | '"' (.)*? '"'
   ;

type
   : primitive
   | ID
   ;

primitive
   : 'void'
   | 'number'
   | 'boolean'
   | 'string'
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

CLASS
   : UPPER (LETTER | DIGIT)*
   ;

NUMBER
   : [1-9] DIGIT*
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

