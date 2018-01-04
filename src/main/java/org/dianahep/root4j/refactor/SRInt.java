package org.dianahep.root4j.refactor;

import org.dianahep.root4j.core.*;
import org.dianahep.root4j.interfaces.*;
import java.io.*;
import java.util.*;

public class SRInt extends SRSimpleType {
    static String name;
    static TBranch b;
    static TLeaf l;

    public SRInt(String name,TBranch b,TLeaf l){
        super(name,b,l);
    }

    @Override void debugMe(String str) {
        logger.debug("SRInt:: "+name+" "+str);
    }

    @Override public Integer read(RootInput buffer)throws IOException{
        debugMe("read(buffer)");
        int r = buffer.readInt();
        entry+=1L;
        debugMe("read Int ="+r);
        return r;
    }

    @Override public Integer read()throws IOException{
        debugMe("read");
        RootInput buffer = b.setPosition(l,entry);
        int r = buffer.readInt();
        entry+=1L;
        debugMe("read Int ="+r);
        return r;
    }

    @Override public List<Integer> readArray(RootInput buffer, int size)throws IOException{
        debugMe("readArray(buffer,"+size+")");
        int t;
        List<Integer> temp = new ArrayList();
        for (int i=0;i<size;i++){
            t=buffer.readInt();
            temp.add(t);
        }
        entry+=1L;
        return temp;
    }

    @Override public List<Integer> readArray(int size)throws IOException{
        debugMe("readArray("+size+")");
        RootInput buffer = b.setPosition(l,entry);
        int t;
        List<Integer> temp = new ArrayList();
        for (int i=0;i<size;i++){
            t=buffer.readInt();
            temp.add(t);
        }
        entry+=1L;
        return temp;
    }

    @Override public boolean hasNext(){
        return entry<b.getEntries();
    }

}
