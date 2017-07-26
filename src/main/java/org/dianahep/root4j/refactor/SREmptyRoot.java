package org.dianahep.root4j.refactor;

import org.dianahep.root4j.core.*;
import java.util.*;

public class SREmptyRoot extends SRType{
    static String name;
    static long entries;

    SREmptyRoot(String name,long entries){
        super(name);
        this.entries=entries;
    }

    @Override public SRType read(RootInput b){
        return null;
    }

    @Override public List<SRType> read(){
        entries-=1L;
        List<SRType> temp = new ArrayList();
        return temp;
    }

    @Override public List<SRType> readArray(RootInput b,int size){
        return null;
    }

    @Override public List<SRType> readArray(int size){
        return null;
    }

    @Override public boolean hasNext(){
        return entries>0;
    }
}
