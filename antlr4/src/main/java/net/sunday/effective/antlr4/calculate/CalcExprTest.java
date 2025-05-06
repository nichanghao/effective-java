package net.sunday.effective.antlr4.calculate;

import net.sunday.effective.antlr4.calculate.grammar.CalcExprLexer;
import net.sunday.effective.antlr4.calculate.grammar.CalcExprParser;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

public class CalcExprTest {

    public static void main(String[] args) throws Exception {
        CalcExprLexer lexer = new CalcExprLexer(CharStreams.fromString("1 + 2 * (-3 + 4) - 12 / 6"));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        CalcExprParser parser = new CalcExprParser(tokens);
        ParseTree tree = parser.calc();
        System.out.println("tree: " + tree.toStringTree(parser));

        CalcExprVisitor visitor = new CalcExprVisitor();
        System.out.println(visitor.visit(tree));

    }
}
