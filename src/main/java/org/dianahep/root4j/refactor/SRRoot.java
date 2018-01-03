package org.dianahep.root4j.refactor;

import java.util.*;
import org.dianahep.root4j.core.*;
import java.io.*;

public class SRRoot extends SRType{

    static String name;
    static long entries;
    static List<SRType> types;

    public SRRoot(String name,long entries,List<SRType> types){
        super(name);
        this.entries=entries;
        this.types=types;
    }

    @Override void debugMe(String str) {
        logger.debug("SRRoot:: "+name+" "+str);
    }

    @Override public List<Object> read()throws IOException{
        debugMe("read");
        List<Object> temp = new ArrayList();
        for (SRType t : types){
            temp.add(t.read());
        }
        entries-=1L;
        return temp;
    }

    @Override public SRType read(RootInput buffer)throws IOException{
        debugMe("read(buffer)");
        return null;
    }

    @Override public List<SRType> readArray(RootInput buffer, int size)throws IOException{
        debugMe("readArray(buffer,"+size+")");
        return null;
    }

    @Override public List<SRType> readArray(int size)throws IOException{
        debugMe("readArray("+size+")");
        return null;
    }

    @Override public boolean hasNext(){
        return entries>0;
    }
}
