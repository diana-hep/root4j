package org.dianahep.root4j.refactor;

import org.dianahep.root4j.core.*;
import org.dianahep.root4j.interfaces.*;
import java.io.*;
import java.util.*;

public class SRFloat extends SRSimpleType {
    static String name;
    static TBranch b;
    static TLeaf l;

    public SRFloat(String name,TBranch b,TLeaf l){
        super(name,b,l);
    }

    @Override public Float read(RootInput buffer)throws IOException{
        float t = buffer.readFloat();
        entry+=1L;
        return t;
    }

    @Override public Float read()throws IOException{
        RootInput buffer = b.setPosition(l,entry);
        return read(buffer);
    }

    @Override public List<Float> readArray(RootInput buffer, int size)throws IOException{
        float t;
        List<Float> temp = new ArrayList();
        for (int i=0;i<size;i++) {
            t = buffer.readFloat();
            temp.add(t);
        }
        entry+=1L;
        return temp;
    }

    @Override public List<Float> readArray(int size)throws IOException{
        RootInput buffer = b.setPosition(l,entry);
        return readArray(buffer,size);
    }

    @Override public boolean hasNext(){
        return entry<b.getEntries();
    }

}
