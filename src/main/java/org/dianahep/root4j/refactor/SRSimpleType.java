package org.dianahep.root4j.refactor;

import org.dianahep.root4j.interfaces.*;

/**
 * Created by root on 25/6/17.
 */

public abstract class SRSimpleType extends SRType{
    String name;
    TBranch b;
    TLeaf l;

    SRSimpleType(String name,TBranch b,TLeaf l){
        this.name=name;
        this.b=b;
        this.l=l;
    }
}
