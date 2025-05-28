grammar PALConditionExpr;

@header {package net.sunday.effective.antlr4.price.grammar;}

root: expression EOF;

expression
    : expressionNode                            # exprNode
    | L_PAREN expression R_PAREN                # parensOp
    | expression mulDiv expression              # mulDivOp
    | expression addSub expression              # addSubOp
    | expression compare expression             # compareOp
    | expression boolOperator expression        # boolOP
    ;

expressionNode:
    ATTR_STR | INTEGER | DECIMAL;

// 比较运算符
compare:
    DEQ | NEQ | LTE | LT | GTE | GT;

// 布尔运算符
boolOperator:
    AND | OR;

addSub:       ADD | SUB ;
mulDiv:       MUL | DIV ;

// Constructors symbols
DOT:         '.';
L_PAREN:     '(';
R_PAREN:     ')';

// Scalar Binary operators
DEQ:         '==';
NEQ:         '!=';
LTE:         '<=';
LT:          '<';
GTE:         '>=';
GT:          '>';
SUB:         '-';
ADD:         '+';
MUL:         '*';
DIV:         '/';

// Bool operators
AND:         '&&';
OR:          '||';


// Literals
ATTR_STR: ('$A' | '$B' | '$S') DOT Digit+;
INTEGER: Digit+;
DECIMAL: Digit+ DOT Digit+;


// Fragments
fragment Digit: [0-9];

WS : [ \t\r\n]+ -> skip;
