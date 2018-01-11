package org.dianahep.root4j.refactor;

import org.dianahep.root4j.core.*;
import org.dianahep.root4j.interfaces.*;
import java.io.*;
import java.util.*;

public class SRLong extends SRSimpleType {
    public static String name;
    public static TBranch b;
    public static TLeaf l;

    public SRLong(String name,TBranch b,TLeaf l){
        super(name,b,l);
    }

    @Override public void debugMe(String str) {
        logger.debug("SRLong:: "+name+" "+str);
    }

    @Override public Long read(RootInput buffer)throws IOException{
        debugMe("read(buffer)");
        long data = buffer.readLong();
        entry+=1L;
        return data;
    }

    @Override public Long read()throws IOException{
        debugMe("read");
        RootInput buffer = b.setPosition(l,entry);
        long data = buffer.readLong();
        entry+=1L;
        return data;
    }

    @Override public List<Long> readArray(RootInput buffer, int size)throws IOException{
        debugMe("readArray(buffer,"+size+")");
        long t;
        List<Long> temp = new ArrayList();
        for (int i=0;i<size;i++){
            t=buffer.readLong();
            temp.add(t);
        }
        entry+=1L;
        return temp;
    }

    @Override public List<Long> readArray(int size)throws IOException{
        debugMe("readArray("+size+")");
        RootInput buffer = b.setPosition(l,entry);
        long t;
        List<Long> temp = new ArrayList();
        for (int i=0;i<size;i++){
            t=buffer.readLong();
            temp.add(t);
        }
        entry+=1L;
        return temp;
    }

    @Override public boolean hasNext(){
        return entry<b.getEntries();
    }

}
