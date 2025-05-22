package net.sunday.effective.antlr4.test.calculate;

import net.sunday.effective.antlr4.calculate.CalcExprVisitor;
import net.sunday.effective.antlr4.calculate.grammar.CalcExprLexer;
import net.sunday.effective.antlr4.calculate.grammar.CalcExprParser;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.jupiter.api.Test;

public class CalcExprTest {

    @Test
    void testCalcExpr() {
        CalcExprLexer lexer = new CalcExprLexer(CharStreams.fromString("1 + 2 * (-3 + 4) - 12 / 6"));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        CalcExprParser parser = new CalcExprParser(tokens);
        ParseTree tree = parser.calc();
        System.out.println("tree: " + tree.toStringTree(parser));

        CalcExprVisitor visitor = new CalcExprVisitor();
        System.out.println(visitor.visit(tree));
    }
}
