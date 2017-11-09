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

    @Override public Integer read(RootInput buffer)throws IOException{
        int t = buffer.readInt();
        entry+=1L;
        System.out.println(t);
        return t;
    }

    @Override public Integer read()throws IOException{
        RootInput buffer = b.setPosition(l,entry);
        return read(buffer);
    }

    @Override public List<Integer> readArray(RootInput buffer, int size)throws IOException{
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
        RootInput buffer = b.setPosition(l,entry);
        return readArray(buffer,size);
    }

    @Override public boolean hasNext(){
        return entry<b.getEntries();
    }

}
