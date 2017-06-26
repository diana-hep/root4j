package org.dianahep.root4j.refactor;

import org.dianahep.root4j.interfaces.*;

public abstract class SRSimpleType extends SRType{
    String name;
    TBranch b;
    TLeaf l;

    SRSimpleType()
    {
        name=null;
        b=null;
        l=null;
    }

    SRSimpleType(String name,TBranch b,TLeaf l){
        this.name=name;
        this.b=b;
        this.l=l;
    }
}
