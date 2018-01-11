package org.dianahep.root4j.refactor;

import org.dianahep.root4j.core.*;
import java.util.*;

public class SREmptyRoot extends SRType{
    public static String name;
    public static long entries;

    public SREmptyRoot(String name,long entries){
        super(name);
        this.entries=entries;
    }

    @Override public void debugMe(String str) {
        logger.debug("SREmptyRoot:: "+name+" "+str);
    }

    @Override public SRType read(RootInput b){
        debugMe("read(buffer)");
        return null;
    }

    @Override public List<SRType> read(){
        debugMe("read");
        entries-=1L;
        List<SRType> temp = new ArrayList();
        return temp;
    }

    @Override public List<SRType> readArray(RootInput b,int size){
        debugMe("readArray(buffer,"+size+")");
        return null;
    }

    @Override public List<SRType> readArray(int size){
        debugMe("readArray("+size+")");
        return null;
    }

    @Override public boolean hasNext(){
        return entries>0;
    }
}
