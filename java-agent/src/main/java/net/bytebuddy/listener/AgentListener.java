package net.bytebuddy.listener;

import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.utility.JavaModule;

public class AgentListener implements AgentBuilder.Listener {

    @Override
    public void onDiscovery(String typeName, ClassLoader classLoader, JavaModule module, boolean loaded) {

    }

    @Override
    public void onTransformation(final TypeDescription typeDescription,
                                 final ClassLoader classLoader,
                                 final JavaModule module,
                                 final boolean loaded,
                                 final DynamicType dynamicType) {

        System.out.println("On Transformation class: " + typeDescription.getName());
    }

    @Override
    public void onIgnored(final TypeDescription typeDescription,
                          final ClassLoader classLoader,
                          final JavaModule module,
                          final boolean loaded) {

    }

    @Override
    public void onError(final String typeName,
                        final ClassLoader classLoader,
                        final JavaModule module,
                        final boolean loaded,
                        final Throwable throwable) {
        System.err.println("Enhance class " + typeName + " error: " + throwable);
    }

    @Override
    public void onComplete(String typeName, ClassLoader classLoader, JavaModule module, boolean loaded) {
    }
}
