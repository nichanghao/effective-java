grammar PALRuleExpr;

@header {package net.sunday.effective.antlr4.price.grammar;}

root: expression EOF;

expression
    : expressionNode                            # exprNode
    | L_PAREN expression R_PAREN                # parensOp
    | expression mulDiv expression              # mulDivOp
    | expression addSub expression              # addSubOp
    ;

expressionNode:
    ATTR_STR | INTEGER | DECIMAL;


addSub:       ADD | SUB ;
mulDiv:       MUL | DIV ;



// Constructors symbols
DOT:         '.';
L_PAREN:     '(';
R_PAREN:     ')';

// Scalar Binary operators
SUB:         '-';
ADD:         '+';
MUL:         '*';
DIV:         '/';


// Literals
ATTR_STR: ('$A' | '$B' | '$S') DOT Digit+;
INTEGER: Digit+;
DECIMAL: Digit+ DOT Digit+;


// Fragments
fragment Digit: [0-9];

WS : [ \t\r\n]+ -> skip;
