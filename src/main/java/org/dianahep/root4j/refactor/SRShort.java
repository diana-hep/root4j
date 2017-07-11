package org.dianahep.root4j.refactor;

import org.dianahep.root4j.core.*;
import org.dianahep.root4j.interfaces.*;
import java.io.*;
import java.util.*;

public class SRShort extends SRSimpleType {
    String name;
    TBranch b;
    TLeaf l;

    SRShort(String name,TBranch b,TLeaf l){
        super(name,b,l);
    }

    @Override void read(RootInput buffer)throws IOException{
        short temp = buffer.readShort();
        array.add(temp);
        entry+=1L;
    }

    @Override void read()throws IOException{
        RootInput buffer = b.setPosition(l,entry);
        short temp = buffer.readShort();
        array.add(temp);
        entry+=1L;
    }

    @Override void readArray(RootInput buffer, int size)throws IOException{
        short t;
        List<Short> temp = new ArrayList();
        for (int i=0;i<size;i++){
            t=buffer.readShort();
            temp.add(t);
        }
        array.add(temp);
        entry+=1L;
    }

    @Override void readArray(int size)throws IOException{
        RootInput buffer = b.setPosition(l,entry);
        List<Short> temp = new ArrayList();
        short t;
        for (int i=0;i<size;i++){
            t=buffer.readShort();
            temp.add(t);
        }
        array.add(temp);
        entry+=1L;
    }

    @Override boolean hasNext(){
        return entry<b.getEntries();
    }

}
