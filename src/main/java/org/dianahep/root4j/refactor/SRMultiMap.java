package org.dianahep.root4j.refactor;

import org.dianahep.root4j.core.*;
import org.dianahep.root4j.interfaces.*;

public class SRMultiMap extends SRCollection{
    String name;
    TBranchElement b;
    SRType keyType;
    SRType valueType;
    boolean split;
    boolean isTop;

    SRMultiMap(String name,TBranchElement b,SRComposite types,boolean split,boolean isTop){
        super(name,isTop);
        this.b=b;
        this.keyType =
    }

    //Complete this
}
