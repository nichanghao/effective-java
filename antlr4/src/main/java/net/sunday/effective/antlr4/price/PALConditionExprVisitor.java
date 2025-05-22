package net.sunday.effective.antlr4.price;

import net.sunday.effective.antlr4.price.grammar.PALConditionExprBaseVisitor;
import net.sunday.effective.antlr4.price.grammar.PALConditionExprParser;

/**
 * 估价的条件表达式访问器
 */
public class PALConditionExprVisitor extends PALConditionExprBaseVisitor<Boolean> {

    @Override
    public Boolean visitRoot(PALConditionExprParser.RootContext ctx) {
        return visit(ctx.expression());
    }

    @Override
    public Boolean visitBoolOP(PALConditionExprParser.BoolOPContext ctx) {

        PALConditionExprParser.BoolOperatorContext boolOperatorContext = ctx.boolOperator();
        if (boolOperatorContext.AND() != null) {
            // 短路运算
            if (!visit(ctx.expression(0))) {
                return false;
            }
            return visit(ctx.expression(1));

        } else if (boolOperatorContext.OR() != null) {
            if (visit(ctx.expression(0))) {
                return true;
            }
            return visit(ctx.expression(1));

        } else {
            throw new IllegalCallerException("unexpected bool operator: " + ctx.boolOperator().getText());
        }
    }

    @Override
    public Boolean visitParensOp(PALConditionExprParser.ParensOpContext ctx) {
        return visit(ctx.expression());
    }

    @Override
    public Boolean visitExprNode(PALConditionExprParser.ExprNodeContext ctx) {
        return visit(ctx.expressionNode());
    }

    @Override
    public Boolean visitCompareOp(PALConditionExprParser.CompareOpContext ctx) {

        double left = parseDouble(ctx.expression(0));
        double right = parseDouble(ctx.expression(1));

        PALConditionExprParser.CompareContext compareCtx = ctx.compare();
        if (compareCtx.DEQ() != null) {
            return left == right;
        } else if (compareCtx.NEQ() != null) {
            return left != right;
        } else if (compareCtx.LTE() != null) {
            return left <= right;
        } else if (compareCtx.LT() != null) {
            return left < right;
        } else if (compareCtx.GTE() != null) {
            return left >= right;
        } else if (compareCtx.GT() != null) {
            return left > right;
        } else {
            throw new IllegalCallerException("unexpected compare operator: " + compareCtx.getText());
        }
    }

    private double parseDouble(PALConditionExprParser.ExpressionContext exprCtx) {
        if (exprCtx instanceof PALConditionExprParser.ExprNodeContext e) {
            if (e.expressionNode().ATTR_STR() != null) {
                return Double.parseDouble(e.getText()
                        .replace("$A.", "")
                        .replace("$B.", "")
                        .replace("$S.", ""));
            } else if (e.expressionNode().INTEGER() != null || e.expressionNode().DECIMAL() != null) {
                return Double.parseDouble(e.getText());
            }
        }
        throw new IllegalCallerException("unexpected expressionNode: " + exprCtx.getText());
    }


}
