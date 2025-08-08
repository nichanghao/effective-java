package net.sunday.effective.observation.agent.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.ClassFileLocator;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.dynamic.scaffold.TypeValidation;
import net.bytebuddy.implementation.CSAuxiliaryTypeNamingStrategy;
import net.bytebuddy.implementation.CSImplementationContextFactory;
import net.bytebuddy.pool.TypePool;

public class AgentUtils {

    /**
     * Get the byte array of a class by it name.
     */
    public static byte[] getClassBytes(String className) throws IOException {

        String classResourceName = className.replaceAll("\\.", "/") + ".class";
        try (InputStream resourceAsStream =
                 Thread.currentThread().getContextClassLoader().getResourceAsStream(classResourceName)) {

            assert resourceAsStream != null;

            ByteArrayOutputStream os = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len;

            // read bytes from the input stream and store them in buffer
            while ((len = resourceAsStream.read(buffer)) != -1) {
                // write bytes from the buffer into output stream
                os.write(buffer, 0, len);
            }

            return os.toByteArray();

        }

    }

    public static void generateInternalClass(String templateClassName, Map<String, byte[]> classesTypeMap) {
        TypePool typePool = TypePool.Default.of(AgentUtils.class.getClassLoader());
        String internalInterceptorName = getInternalClassName(templateClassName);
        TypeDescription templateTypeDescription = typePool.describe(templateClassName).resolve();

        final DynamicType.Unloaded<Object> make = new ByteBuddy().redefine(
                                                                     templateTypeDescription, ClassFileLocator.ForClassLoader
                                                                         .of(AgentUtils.class.getClassLoader())
                                                                 )
                                                                 .name(internalInterceptorName)
                                                                 .make();

        classesTypeMap.put(internalInterceptorName, make.getBytes());
        make.close();
    }

    public static AgentBuilder newAgentBuilder() {
        return new AgentBuilder.Default(
            new ByteBuddy()
                // 设置为 true 时，ByteBuddy 会在字节码生成阶段执行严格的类型验证，但生成效率会降低，建议 debug 时使用。
                .with(TypeValidation.ENABLED)
                // 命名策略
                .with(new CSAuxiliaryTypeNamingStrategy())
                .with(new CSImplementationContextFactory("$cs$"))
        );
    }

    public static String getInternalClassName(String templateClassName) {
        return templateClassName + "_Internal";
    }

}
