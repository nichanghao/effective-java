package net.sunday.effective.observation.agent;

import java.lang.instrument.Instrumentation;
import net.sunday.effective.observation.agent.transfomer.PersonTransformer;

public class CustomJavaAgent {

    /**
     * jvm 参数形式启动，运行此方法
     *
     * @param args agentArgs 是我们启动 Java Agent 时带进来的参数，比如-javaagent:xxx.jar agentArgs
     * @param inst 此参数可以拿到加载的class信息，可以用来修改class
     */
    public static void premain(String args, Instrumentation inst) {
        System.out.println("hello I`m premain agent!!!");

        operateInstrumentationWithJavassist(inst);
    }

    private static void operateInstrumentationWithJavassist(Instrumentation inst) {
        inst.addTransformer(new PersonTransformer(), true);
    }
}
