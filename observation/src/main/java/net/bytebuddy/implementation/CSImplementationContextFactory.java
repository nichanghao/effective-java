package net.bytebuddy.implementation;

import lombok.AllArgsConstructor;
import net.bytebuddy.ClassFileVersion;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.scaffold.TypeInitializer;
import net.bytebuddy.implementation.auxiliary.AuxiliaryType;
import net.bytebuddy.utility.RandomString;

@AllArgsConstructor
public class CSImplementationContextFactory implements Implementation.Context.Factory {

    private final String suffix;

    @Override
    public Implementation.Context.ExtractableView make(final TypeDescription instrumentedType,
                                                       final AuxiliaryType.NamingStrategy auxiliaryTypeNamingStrategy,
                                                       final TypeInitializer typeInitializer,
                                                       final ClassFileVersion classFileVersion,
                                                       final ClassFileVersion auxiliaryClassFileVersion) {
        return this.make(
            instrumentedType, auxiliaryTypeNamingStrategy, typeInitializer, classFileVersion,
            auxiliaryClassFileVersion, Implementation.Context.FrameGeneration.GENERATE
        );
    }

    @Override
    public Implementation.Context.ExtractableView make(final TypeDescription instrumentedType,
                                                       final AuxiliaryType.NamingStrategy auxiliaryTypeNamingStrategy,
                                                       final TypeInitializer typeInitializer,
                                                       final ClassFileVersion classFileVersion,
                                                       final ClassFileVersion auxiliaryClassFileVersion,
                                                       final Implementation.Context.FrameGeneration frameGeneration) {

        /*
         * instrumentedType: 被增强/创建的目标类元数据（包含字段/方法/注解等结构信息）
         * auxiliaryTypeNamingStrategy: 动态生成辅助类（如混合类）的命名规则，默认使用哈希策略避免冲突
         * typeInitializer: 类初始化逻辑（静态代码块生成），可通过 TypeInitializer.None 禁用
         * classFileVersion: 主类字节码版本（如 JAVA_8=52），控制 invokedynamic 等特性的可用性
         * auxiliaryClassFileVersion: 辅助类字节码版本，通常与主类一致（用于版本隔离场景）
         * frameGeneration: 控制栈映射帧（StackMapTable）生成策略
         * suffix: 辅助类名的固定后缀（如 $auxiliary），用于快速识别生成的辅助类
         */

        return new Implementation.Context.Default(
            instrumentedType, classFileVersion, auxiliaryTypeNamingStrategy,
            typeInitializer, auxiliaryClassFileVersion, frameGeneration,
            suffix + RandomString.hashOf(instrumentedType.getTypeName())
        );
    }

}
