package net.sunday.effective.antlr4.calculate;

import net.sunday.effective.antlr4.calculate.grammar.CalcExprBaseVisitor;
import net.sunday.effective.antlr4.calculate.grammar.CalcExprParser;

import java.math.BigDecimal;
import java.math.MathContext;

public class CalcExprVisitor extends CalcExprBaseVisitor<BigDecimal> {

    /**
     * 用于设置BigDecimal的计算精度
     */
    private static final MathContext MATH_CONTEXT = MathContext.DECIMAL128;

    @Override
    public BigDecimal visitCalc(CalcExprParser.CalcContext ctx) {
        return visit(ctx.expr());
    }

    @Override
    public BigDecimal visitParensOp(CalcExprParser.ParensOpContext ctx) {
        // 左右括号，取出括号内的表达式
        return visit(ctx.expr());
    }

    @Override
    public BigDecimal visitPosNegNumOp(CalcExprParser.PosNegNumOpContext ctx) {
        String text = ctx.getText();
        if (ctx.NUMBER() != null) {
            return new BigDecimal(text);
        } else if (ctx.PERCENT_NUMBER() != null) {
            return new BigDecimal(text.substring(0, text.length() - 1)).divide(BigDecimal.valueOf(100), MATH_CONTEXT);
        } else {
            throw new RuntimeException("unsupported number type");
        }
    }

    @Override
    public BigDecimal visitMulDivModOp(CalcExprParser.MulDivModOpContext ctx) {
        // 乘除法运算符，取出左右表达式
        BigDecimal left = visit(ctx.expr(0));
        BigDecimal right = visit(ctx.expr(1));

        if (ctx.MUL() != null) {
            return left.multiply(right, MATH_CONTEXT);
        } else if (ctx.DIV() != null) {
            return left.divide(right, MATH_CONTEXT);
        } else {
            throw new RuntimeException("unsupported number type");
        }
    }

    @Override
    public BigDecimal visitAddSubOp(CalcExprParser.AddSubOpContext ctx) {
        BigDecimal left = visit(ctx.expr(0));
        BigDecimal right = visit(ctx.expr(1));

        if (ctx.ADD() != null) {
            return left.add(right, MATH_CONTEXT);
        } else if (ctx.SUB() != null) {
            return left.subtract(right, MATH_CONTEXT);
        } else {
            throw new RuntimeException("unsupported number type");
        }
    }

}
