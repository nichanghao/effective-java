package net.sunday.effective.antlr4.test.price;

import net.sunday.effective.antlr4.price.PALConditionExprVisitor;
import net.sunday.effective.antlr4.price.PALRuleExprVisitor;
import net.sunday.effective.antlr4.price.grammar.PALConditionExprLexer;
import net.sunday.effective.antlr4.price.grammar.PALConditionExprParser;
import net.sunday.effective.antlr4.price.grammar.PALRuleExprLexer;
import net.sunday.effective.antlr4.price.grammar.PALRuleExprParser;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PALExprTest {

    /**
     * 测试 PAL 条件表达式
     */
    @Test
    void testPALConditionExpr() {
        PALConditionExprLexer lexer = new PALConditionExprLexer(CharStreams.fromString("($A.120 + $B.340 > 100) && ($A.120 >= 100 || $B.340 < 200) && $S.560 > 300"));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        PALConditionExprParser parser = new PALConditionExprParser(tokens);
        ParseTree tree = parser.expression();
        System.out.println("tree: " + tree.toStringTree(parser));

        PALConditionExprVisitor visitor = new PALConditionExprVisitor();

        Assertions.assertTrue(visitor.visit(tree).asBool());
    }

    /**
     * 测试 PAL 规则表达式
     */
    @Test
    void testPALRuleExpr() {
        PALRuleExprLexer lexer = new PALRuleExprLexer(CharStreams.fromString("($A.120 + 100 -20) * 1.5"));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        PALRuleExprParser parser = new PALRuleExprParser(tokens);
        ParseTree tree = parser.expression();
        System.out.println("tree: " + tree.toStringTree(parser));

        PALRuleExprVisitor visitor = new PALRuleExprVisitor();
        System.out.println(visitor.visit(tree));
    }

}
