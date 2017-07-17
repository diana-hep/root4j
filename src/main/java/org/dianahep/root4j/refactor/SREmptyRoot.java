package org.dianahep.root4j.refactor;

import org.dianahep.root4j.core.*;
import java.util.*;

public class SREmptyRoot extends SRType{
    String name;
    long entries;

    SREmptyRoot(String name,long entries){
        super(name);
        this.entries=entries;
    }

    @Override SRType read(RootInput b){
        return null;
    }

    @Override List<SRType> read(){
        entries-=1L;
        List<SRType> temp = new ArrayList();
        return temp;
    }

    @Override List<SRType> readArray(RootInput b,int size){
        return null;
    }

    @Override List<SRType> readArray(int size){
        return null;
    }

    @Override boolean hasNext(){
        return entries>0;
    }
}
