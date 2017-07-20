package org.dianahep.root4j.refactor;

import java.util.*;
import org.dianahep.root4j.core.*;
import java.io.*;

public class SRRoot extends SRType{
    String name;
    long entries;
    List<SRType> types;
    SRRoot(String name,long entries,List<SRType> types){
        super(name);
        this.entries=entries;
        this.types=types;
    }

    @Override List<Object> read()throws IOException{
        List<Object> temp = new ArrayList();
        for (SRType t : types){
            temp.add(t.read());
        }
        entries-=1L;
        return temp;
    }

    @Override SRType read(RootInput buffer)throws IOException{
        return null;
    }

    @Override List<SRType> readArray(RootInput buffer, int size)throws IOException{
        return null;
    }

    @Override List<SRType> readArray(int size)throws IOException{
        return null;
    }

    @Override boolean hasNext(){
        return entries>0;
    }
}
