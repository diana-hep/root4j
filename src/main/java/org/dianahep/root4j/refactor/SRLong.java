package org.dianahep.root4j.refactor;

import org.dianahep.root4j.core.*;
import org.dianahep.root4j.interfaces.*;
import java.io.*;
import java.util.*;

public class SRLong extends SRSimpleType {
    String name;
    TBranch b;
    TLeaf l;

    SRLong(String name,TBranch b,TLeaf l){
        super(name,b,l);
    }

    @Override void read(RootInput buffer)throws IOException{
        long t = buffer.readLong();
        array.add(t);
        entry+=1L;
    }

    @Override void read()throws IOException{
        RootInput buffer = b.setPosition(l,entry);
        long t = buffer.readLong();
        array.add(t);
        entry+=1L;
    }

    @Override void readArray(RootInput buffer, int size)throws IOException{
        long t;
        List<Long> temp = new ArrayList();
        for (int i=0;i<size;i++){
            t=buffer.readLong();
            temp.add(t);
        }
        array.add(temp);
        entry+=1L;
    }

    @Override void readArray(int size)throws IOException{
        RootInput buffer = b.setPosition(l,entry);
        Long t;
        List<Long> temp = new ArrayList();
        for (int i=0;i<size;i++){
            t=buffer.readLong();
            temp.add(t);
        }
        array.add(temp);
        entry+=1L;
    }

    @Override boolean hasNext(){
        return entry<b.getEntries();
    }

}
