package org.dianahep.root4j.refactor;

/**
 * Created by root on 25/6/17.
 */
public abstract class SRCollection extends SRType {
    String name;
    boolean isTop;
    long kMemberWiseStreaming = 0x4000;



    SRCollection(String name,boolean isTop){
        this.name=name;
        this.isTop=isTop;
    }
}
