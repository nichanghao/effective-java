package net.sunday.effective.groovy;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.lang.Script;
import groovy.util.DelegatingScript;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

/**
 * 日志分析 dls
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class LogAnalysisDSL {

    private final DelegatingScript script;

    public static LogAnalysisDSL of(String dslScript) {

        // groovy 执行器
        GroovyShell shell = new GroovyShell();
        DelegatingScript delegatingScript = (DelegatingScript) shell.parse(dslScript);

        return new LogAnalysisDSL(delegatingScript);
    }


    public static void main(String[] args) {
        GroovyShell shell = new GroovyShell();

        Binding binding = new Binding();
        binding.setProperty("x", 10);

        Script parse = shell.parse("3*x");
        parse.setBinding(binding);
        System.out.println(parse.run());


    }
}
