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

    @Override Boolean read(RootInput buffer)throws IOException{
        boolean t = buffer.readBoolean();
        entry+=1L;
        return t;
    }

    @Override Boolean read()throws IOException{
        RootInput buffer = b.setPosition(l,entry);
        boolean t= buffer.readBoolean();
        entry+=1L;
        return t;
    }

    @Override List<Boolean> readArray(RootInput buffer, int size)throws IOException{
        boolean t;
        List<Boolean> temp = new ArrayList();
        for (int i=0;i<size;i++){
            t = buffer.readBoolean();
            temp.add(t);
        }
        entry+=1L;
        return temp;
    }

    @Override List<Boolean> readArray(int size)throws IOException{
        RootInput buffer = b.setPosition(l,entry);
        boolean t;
        List<Boolean> temp = new ArrayList();
        for (int i=0;i<size;i++){
            t=buffer.readBoolean();
            temp.add(t);
        }
        entry+=1L;
        return temp;
    }

    @Override boolean hasNext(){
        return entry<b.getEntries();
    }

}
