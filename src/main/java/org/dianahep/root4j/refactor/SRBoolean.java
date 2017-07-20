package org.dianahep.root4j.refactor;

import jdk.nashorn.internal.codegen.types.BooleanType;
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

    @Override public Boolean read(RootInput buffer)throws IOException{
        boolean t = buffer.readBoolean();
        entry+=1L;
        return t;
    }

    @Override public Boolean read()throws IOException{
        RootInput buffer = b.setPosition(l,entry);
        return read(buffer);
    }

    @Override public List<Boolean> readArray(RootInput buffer, int size)throws IOException{
        boolean t;
        List<Boolean> temp = new ArrayList();
        for (int i=0;i<size;i++){
            t = buffer.readBoolean();
            temp.add(t);
        }
        entry+=1L;
        return temp;
    }

    @Override public List<Boolean> readArray(int size)throws IOException{
        RootInput buffer = b.setPosition(l,entry);
        return readArray(buffer,size);
    }

    @Override public boolean hasNext(){
        return entry<b.getEntries();
    }

}
