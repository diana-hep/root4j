package org.dianahep.root4j.refactor;

import org.dianahep.root4j.core.*;
import org.dianahep.root4j.interfaces.*;
import java.io.*;
import java.util.*;

public class SRFloat extends SRSimpleType {
    String name;
    TBranch b;
    TLeaf l;

    SRFloat(String name,TBranch b,TLeaf l){
        super(name,b,l);
    }

    @Override float read(RootInput buffer)throws IOException{
        float t = buffer.readFloat();
        entry+=1L;
        return t;
    }

    @Override float read()throws IOException{
        RootInput buffer = b.setPosition(l,entry);
        float t= buffer.readFloat();
        entry+=1L;
        return t;
    }

    @Override List<Float> readArray(RootInput buffer, int size)throws IOException{
        float t;
        List<Float> temp = new ArrayList();
        for (int i=0;i<size;i++) {
            t = buffer.readFloat();
            temp.add(t);
        }
        entry+=1L;
        return temp;
    }

    @Override List<Float> readArray(int size)throws IOException{
        RootInput buffer = b.setPosition(l,entry);
        List<Float> temp = new ArrayList();
        float t;
        for (int i=0;i<size;i++){
            t=buffer.readFloat();
            temp.add(t);
        }
        entry+=1L;
        return temp;
    }

    @Override boolean hasNext(){
        return entry<b.getEntries();
    }

}
