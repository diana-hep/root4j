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

    @Override void read(){
        List<SRType> temp = new ArrayList();
        for (int i=0;i<types.size();i++){
            temp.add(types.get(i));
        }
        array.add(temp);
        entries-=1L;
    }

    @Override void read(RootInput buffer){
        array.add(null);
    }

    @Override void readArray(RootInput buffer, int size){
        array.add(null);
    }

    @Override void readArray(int size){
        array.add(null);
    }

    @Override boolean hasNext(){
        return entries>0;
    }
}
