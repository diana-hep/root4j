package org.dianahep.root4j.refactor;

import org.dianahep.root4j.core.*;
import org.dianahep.root4j.interfaces.*;
import java.io.*;
import java.util.*;

public class SRDouble extends SRSimpleType {
    static String name;
    static TBranch b;
    static TLeaf l;

    public SRDouble(String name,TBranch b,TLeaf l){
        super(name,b,l);
    }

    @Override public Double read(RootInput buffer)throws IOException{
        double t=buffer.readDouble();
        entry+=1L;
        System.out.println(t);
        return t;
    }

    @Override public Double read()throws IOException{
        RootInput buffer = b.setPosition(l,entry);
        return read(buffer);
    }

    @Override public List<Double> readArray(RootInput buffer, int size)throws IOException{
        double t;
        List<Double> temp = new ArrayList();
        for (int i=0;i<size;i++){
            t=buffer.readDouble();
            temp.add(t);
        }
        entry+=1L;
        return temp;
    }

    @Override public List<Double> readArray(int size)throws IOException{
        RootInput buffer = b.setPosition(l,entry);
        return readArray(buffer,size);
    }

    @Override public boolean hasNext(){
        return entry<b.getEntries();
    }

}
