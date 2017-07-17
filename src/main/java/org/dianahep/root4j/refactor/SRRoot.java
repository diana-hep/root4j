package org.dianahep.root4j.refactor;

import java.util.*;
import org.dianahep.root4j.core.*;

public class SRRoot extends SRType{
    String name;
    long entries;
    List<SRType> types;
    SRRoot(String name,long entries,List<SRType> types){
        super(name);
        this.entries=entries;
        this.types=types;
    }

    @Override List<SRType> read(){
        List<SRType> temp = new ArrayList();
        for (SRType t : types){
            temp.add(types.read());
        }
        entries-=1L;
        return temp;
    }

    @Override SRType read(RootInput buffer){
        return null;
    }

    @Override List<SRType> readArray(RootInput buffer, int size){
        return null;
    }

    @Override List<SRType> readArray(int size){
        return null;
    }

    @Override boolean hasNext(){
        return entries>0;
    }
}
