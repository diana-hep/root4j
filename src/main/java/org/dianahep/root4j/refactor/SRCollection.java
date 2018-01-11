package org.dianahep.root4j.refactor;

public abstract class SRCollection extends SRType {
    public static String name;
    public static boolean isTop;
    public static long kMemberWiseStreaming = 0x4000;


    public SRCollection(String name,boolean isTop){
        super(name);
        this.isTop=isTop;
    }

    public SRCollection(){
        name=null;
        isTop=false;
    }
}
