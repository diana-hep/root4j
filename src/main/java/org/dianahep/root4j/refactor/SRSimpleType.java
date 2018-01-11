package org.dianahep.root4j.refactor;

import org.dianahep.root4j.interfaces.*;

public abstract class SRSimpleType extends SRType{

    public static String name;
    public static TBranch b;
    public static TLeaf l;

    public SRSimpleType()
    {
        name=null;
        b=null;
        l=null;
    }

    public SRSimpleType(String name,TBranch b,TLeaf l){
        this.name=name;
        this.b=b;
        this.l=l;
    }
}
