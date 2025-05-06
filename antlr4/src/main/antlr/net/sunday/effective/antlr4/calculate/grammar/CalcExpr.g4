grammar CalcExpr;

@header {package net.sunday.effective.antlr4.calculate.grammar;}

calc: expr EOF;

expr
    : BRACKET_L expr BRACKET_R                  # parensOp      // 匹配括号表达式： (2+3)
    | (ADD | SUB)? (PERCENT_NUMBER | NUMBER)    # posNegNumOp   // 匹配正数或负数： -7
    | expr (MUL | DIV) expr                     # mulDivModOp   // 匹配乘除法： 2*3
    | expr (ADD | SUB) expr                     # addSubOp      // 匹配加减法： 2+3
    ;

// 注意声明的顺序问题
PERCENT_NUMBER: NUMBER PERCENT; // 百分数: 5%
NUMBER: DIGIT (POINT DIGIT)?;   // 小数: 4.3

DIGIT: [0-9]+;  // 数字
BRACKET_L: '('; // 左括号
BRACKET_R: ')'; // 右括号
ADD: '+';
SUB: '-';
MUL: '*';
DIV: '/';
PERCENT: '%';
POINT: '.';

WS : [ \t\r\n]+ -> skip; // 跳过空格换行等字符