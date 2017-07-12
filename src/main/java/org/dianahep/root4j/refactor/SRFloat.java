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

    @Override void read(RootInput buffer)throws IOException{
        float t = buffer.readFloat();
        array.add(t);
        entry+=1L;
    }

    @Override void read()throws IOException{
        RootInput buffer = b.setPosition(l,entry);
        float t= buffer.readFloat();
        array.add(t);
        entry+=1L;
    }

    @Override void readArray(RootInput buffer, int size)throws IOException{
        float t;
        List<Float> temp = new ArrayList();
        for (int i=0;i<size;i++) {
            t = buffer.readFloat();
            temp.add(t);
        }
        array.add(temp);
        entry+=1L;
    }

    @Override void readArray(int size)throws IOException{
        RootInput buffer = b.setPosition(l,entry);
        List<Float> temp = new ArrayList();
        float t;
        for (int i=0;i<size;i++){
            t=buffer.readFloat();
            temp.add(t);
        }
        array.add(temp);
        entry+=1L;
    }

    @Override boolean hasNext(){
        return entry<b.getEntries();
    }

}
