package org.dianahep.root4j.refactor;

import org.dianahep.root4j.core.*;
import org.dianahep.root4j.interfaces.*;
import java.io.*;
import java.util.*;

public class SRDouble extends SRSimpleType {
    String name;
    TBranch b;
    TLeaf l;

    SRDouble(String name,TBranch b,TLeaf l){
        super(name,b,l);
    }

    double readroot(RootInput buffer)throws IOException{
        double t=buffer.readDouble();
        entry+=1L;
        return t;
    }

    double readroot()throws IOException{
        RootInput buffer = b.setPosition(l,entry);
        double t = buffer.readDouble();
        entry+=1L;
        return t;
    }

    @Override void readArray(RootInput buffer, int size)throws IOException{
        double t;
        List<Double> temp = new ArrayList();
        for (int i=0;i<size;i++){
            t=buffer.readDouble();
            temp.add(t);
        }
        array.add(temp);
        entry+=1L;
    }

    @Override void readArray(int size)throws IOException{
        double t;
        List<Double> temp = new ArrayList();
        RootInput buffer = b.setPosition(l,entry);
        for (int i=0;i<size;i++){
            t=buffer.readDouble();
            temp.add(t);
        }
        array.add(temp);
        entry+=1L;
    }

    @Override boolean hasNext(){
        return entry<b.getEntries();
    }

}
