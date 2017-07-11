package org.dianahep.root4j.refactor;

import org.dianahep.root4j.core.*;
import org.dianahep.root4j.interfaces.*;
import java.io.*;
import java.util.*;

public class SRBoolean extends SRSimpleType {
    String name;
    TBranch b;
    TLeaf l;

    SRBoolean(String name,TBranch b,TLeaf l){
        super(name,b,l);
    }

    @Override void read(RootInput buffer)throws IOException{
        boolean t = buffer.readBoolean();
        array.add(t);
        entry+=1L;
    }

    @Override void read()throws IOException{
        RootInput buffer = b.setPosition(l,entry);
        boolean t= buffer.readBoolean();
        array.add(t);
        entry+=1L;
    }

    @Override void readArray(RootInput buffer, int size)throws IOException{
        boolean t;
        List<Boolean> temp = new ArrayList();
        for (int i=0;i<size;i++){
            t = buffer.readBoolean();
            temp.add(t);
        }
        array.add(temp);
        entry+=1L;
    }

    @Override void readArray(int size)throws IOException{
        RootInput buffer = b.setPosition(l,entry);
        boolean t;
        List<Boolean> temp = new ArrayList();
        for (int i=0;i<size;i++){
            t=buffer.readBoolean();
            temp.add(t);
        }
        array.add(temp);
        entry+=1L;
    }

    @Override boolean hasNext(){
        return entry<b.getEntries();
    }

}
