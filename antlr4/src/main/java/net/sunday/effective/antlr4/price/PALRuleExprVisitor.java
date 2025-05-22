package net.sunday.effective.antlr4.price;

import net.sunday.effective.antlr4.price.grammar.PALRuleExprBaseVisitor;
import net.sunday.effective.antlr4.price.grammar.PALRuleExprParser;

/**
 * 估价的规则表达式访问器
 */
public class PALRuleExprVisitor extends PALRuleExprBaseVisitor<Double> {


    @Override
    public Double visitRoot(PALRuleExprParser.RootContext ctx) {
        return visit(ctx.expression());
    }

    @Override
    public Double visitParensOp(PALRuleExprParser.ParensOpContext ctx) {
        return visit(ctx.expression());
    }

    @Override
    public Double visitMulDivOp(PALRuleExprParser.MulDivOpContext ctx) {

        double left = visit(ctx.expression(0));
        double right = visit(ctx.expression(1));

        PALRuleExprParser.MulDivContext mulDivCtx = ctx.mulDiv();
        if (mulDivCtx.DIV() != null) {
            return left / right;
        } else if (mulDivCtx.MUL() != null) {
            return left * right;
        } else {
            throw new IllegalCallerException("unexpected mulDiv operator: " + mulDivCtx.getText());
        }
    }

    @Override
    public Double visitExprNode(PALRuleExprParser.ExprNodeContext ctx) {
        PALRuleExprParser.ExpressionNodeContext expNodeCtx = ctx.expressionNode();
        if (expNodeCtx.ATTR_STR() != null) {
            return Double.parseDouble(expNodeCtx.getText()
                    .replace("$A.", "")
                    .replace("$B.", "")
                    .replace("$S.", ""));
        } else if (expNodeCtx.INTEGER() != null || expNodeCtx.DECIMAL() != null) {
            return Double.parseDouble(expNodeCtx.getText());
        } else {
            throw new IllegalCallerException("unexpected expNodeCtx: " + expNodeCtx.getText());
        }
    }

    @Override
    public Double visitAddSubOp(PALRuleExprParser.AddSubOpContext ctx) {
        double left = visit(ctx.expression(0));
        double right = visit(ctx.expression(1));

        PALRuleExprParser.AddSubContext addSubCtx = ctx.addSub();
        if (addSubCtx.ADD() != null) {
            return left + right;
        } else if (addSubCtx.SUB() != null) {
            return left - right;
        } else {
            throw new IllegalCallerException("unexpected addSub operator: " + addSubCtx.getText());
        }
    }

}
