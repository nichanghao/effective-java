package net.sunday.effective.antlr4.price;

import net.sunday.effective.antlr4.price.entity.Value;
import net.sunday.effective.antlr4.price.grammar.PALConditionExprBaseVisitor;
import net.sunday.effective.antlr4.price.grammar.PALConditionExprParser;

/**
 * 估价的条件表达式访问器
 */
public class PALConditionExprVisitor extends PALConditionExprBaseVisitor<Value> {

    @Override
    public Value visitRoot(PALConditionExprParser.RootContext ctx) {
        return visit(ctx.expression());
    }

    @Override
    public Value visitBoolOP(PALConditionExprParser.BoolOPContext ctx) {

        PALConditionExprParser.BoolOperatorContext boolOperatorContext = ctx.boolOperator();
        if (boolOperatorContext.AND() != null) {
            // 短路运算
            if (!visit(ctx.expression(0)).asBool()) {
                return Value.ofBool(false);
            }
            return visit(ctx.expression(1));

        } else if (boolOperatorContext.OR() != null) {
            if (visit(ctx.expression(0)).asBool()) {
                return Value.ofBool(true);
            }
            return visit(ctx.expression(1));

        } else {
            throw new IllegalCallerException("unexpected bool operator: " + ctx.boolOperator().getText());
        }
    }

    @Override
    public Value visitParensOp(PALConditionExprParser.ParensOpContext ctx) {
        return visit(ctx.expression());
    }

    @Override
    public Value visitExprNode(PALConditionExprParser.ExprNodeContext ctx) {

        PALConditionExprParser.ExpressionNodeContext ex = ctx.expressionNode();

        if (ex.ATTR_STR() != null) {
            return Value.ofDouble(Double.parseDouble(ex.getText()
                    .replace("$A.", "")
                    .replace("$B.", "")
                    .replace("$S.", "")));
        } else if (ex.INTEGER() != null || ex.DECIMAL() != null) {
            return Value.ofDouble(Double.parseDouble(ex.getText()));
        } else {
            throw new IllegalCallerException("unexpected expr: " + ex.getText());
        }

    }

    @Override
    public Value visitAddSubOp(PALConditionExprParser.AddSubOpContext ctx) {

        double left = visit(ctx.expression(0)).asDouble();
        double right = visit(ctx.expression(1)).asDouble();

        PALConditionExprParser.AddSubContext addSubCtx = ctx.addSub();
        if (addSubCtx.ADD() != null) {
            return Value.ofDouble(left + right);
        } else if (addSubCtx.SUB() != null) {
            return Value.ofDouble(left - right);
        } else {
            throw new IllegalCallerException("unexpected addSub operator: " + addSubCtx.getText());
        }
    }

    @Override
    public Value visitMulDivOp(PALConditionExprParser.MulDivOpContext ctx) {

        double left = visit(ctx.expression(0)).asDouble();
        double right = visit(ctx.expression(1)).asDouble();

        PALConditionExprParser.MulDivContext mulDivCtx = ctx.mulDiv();
        if (mulDivCtx.DIV() != null) {
            return Value.ofDouble(left / right);
        } else if (mulDivCtx.MUL() != null) {
            return Value.ofDouble(left * right);
        } else {
            throw new IllegalCallerException("unexpected mulDiv operator: " + mulDivCtx.getText());
        }
    }

    @Override
    public Value visitCompareOp(PALConditionExprParser.CompareOpContext ctx) {

        double left = visit(ctx.expression(0)).asDouble();
        double right = visit(ctx.expression(1)).asDouble();

        PALConditionExprParser.CompareContext compareCtx = ctx.compare();
        if (compareCtx.DEQ() != null) {
            return Value.ofBool(left == right);
        } else if (compareCtx.NEQ() != null) {
            return Value.ofBool(left != right);
        } else if (compareCtx.LTE() != null) {
            return Value.ofBool(left <= right);
        } else if (compareCtx.LT() != null) {
            return Value.ofBool(left < right);
        } else if (compareCtx.GTE() != null) {
            return Value.ofBool(left >= right);
        } else if (compareCtx.GT() != null) {
            return Value.ofBool(left > right);
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
        } else if (exprCtx instanceof PALConditionExprParser.AddSubOpContext
                || exprCtx instanceof PALConditionExprParser.MulDivOpContext) {
            return visit(exprCtx).asDouble();
        }
        throw new IllegalCallerException("unexpected expressionNode: " + exprCtx.getText());
    }


}
