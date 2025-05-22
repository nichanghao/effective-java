grammar PALConditionExpr;

@header {package net.sunday.effective.antlr4.price.grammar;}

root: expression EOF;

expression
    : expressionNode                            # exprNode
    | L_PAREN expression R_PAREN                # parensOp
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
