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

    SRMultiMap(String name,TBranchElement b,SRType keyType,SRType valueType,boolean split,boolean isTop){
        this.name = name;
        this.b = b;
        this.keyType = keyType;
        this.valueType = valueType;
        this.split = split;
        this.isTop = isTop;
    }

    SRMultiMap(String name,TBranchElement b,SRComposite types,boolean split,boolean isTop){
        super(name,isTop);
        this.b=b;
        this.keyType = types.members.get(0);
        this.valueType = types.members.get(1);
        this.split=split;
    }



    //Complete this
}
