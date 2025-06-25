package net.sunday.effective.java.agent;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;

import java.io.ByteArrayInputStream;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

/**
 * {@link net.sunday.effective.java.agent.entity.Person} transformer
 * javassist 官方文档: {@link <a href="http://www.javassist.org/tutorial/tutorial.html" />}
 */

public class PersonTransformer implements ClassFileTransformer {

    @Override
    public byte[] transform(ClassLoader loader,
                            String className,
                            Class<?> classBeingRedefined,
                            ProtectionDomain protectionDomain,
                            byte[] classfileBuffer) throws IllegalClassFormatException {

        System.out.println("loading class: " + className);
        if (!(className.startsWith("net.sunday.effective.java.agent.entity.Person")
                || className.startsWith("net/sunday/effective/java/agent/entity/Person"))) {
            return classfileBuffer;
        }

        CtClass cl;
        try {
            ClassPool classPool = ClassPool.getDefault();
            cl = classPool.makeClass(new ByteArrayInputStream(classfileBuffer));

            // 获取 person 的 test 方法
            CtMethod ctMethod = cl.getDeclaredMethod("sayOK");

            // 声明本地变量
            ctMethod.addLocalVariable("start", CtClass.longType);
            ctMethod.addLocalVariable("end", CtClass.longType);

            ctMethod.insertBefore("""
                    System.err.println("[execute person's sayOK() before]");
                    start = System.currentTimeMillis();
                    """);

            // $_ 该方法的返回值，这是 javassist 里特定的标示符
            ctMethod.insertAfter("""
                    System.err.println("[person's sayOK() return value]: " + $_);
                    System.err.println("[person's sayOK() after] cost: " + (System.currentTimeMillis() - start) + " ms");
                    """);

            return cl.toBytecode();
        } catch (Exception ignored) {

        }

        return classfileBuffer;
    }
}
