package org.dianahep.root4j.refactor;

import org.dianahep.root4j.core.*;
import org.dianahep.root4j.interfaces.*;
import java.io.*;
import java.util.*;

public class SRLong extends SRSimpleType {
    static String name;
    static TBranch b;
    static TLeaf l;

    public SRLong(String name,TBranch b,TLeaf l){
        super(name,b,l);
    }

    @Override void debugMe(String str) {
        logger.debug("SRLong:: "+name+" "+str);
    }

    @Override public Long read(RootInput buffer)throws IOException{
        long t = buffer.readLong();
        entry+=1L;
        System.out.println(t);
        return t;
    }

    @Override public Long read()throws IOException{
        RootInput buffer = b.setPosition(l,entry);
        return read(buffer);
    }

    @Override public List<Long> readArray(RootInput buffer, int size)throws IOException{
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
        RootInput buffer = b.setPosition(l,entry);
        return readArray(buffer,size);
    }

    @Override public boolean hasNext(){
        return entry<b.getEntries();
    }

}
