package net.bytebuddy.implementation;

import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.implementation.auxiliary.AuxiliaryType;
import net.bytebuddy.utility.RandomString;

public class CSAuxiliaryTypeNamingStrategy implements AuxiliaryType.NamingStrategy {


    @Override
    public String name(final TypeDescription instrumentedType, final AuxiliaryType auxiliaryType) {
        return instrumentedType.getName() + "$" + RandomString.hashOf(auxiliaryType.getSuffix());
    }
}
