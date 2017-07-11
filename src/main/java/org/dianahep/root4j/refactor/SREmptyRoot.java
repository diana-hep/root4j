package org.dianahep.root4j.refactor;

import org.dianahep.root4j.core.*;

public class SREmptyRoot extends SRType{
    String name;
    long entries;

    SREmptyRoot(String name,long entries){
        super(name);
        this.entries=entries;
    }

    @Override void read(RootInput b){
        array.add(null);
    }

    @Override void read(){
        entries-=1L;
        array.add(null);
    }

    @Override void readArray(RootInput b,int size){
        array.add(null);
    }

    @Override void readArray(int size){
        array.add(null);
    }

    @Override boolean hasNext(){
        return entries>0;
    }
}
