package org.dianahep.root4j.refactor;

import org.dianahep.root4j.core.*;
import org.dianahep.root4j.interfaces.*;
import java.io.*;
import java.util.*;

public class SRByte extends SRSimpleType {
    String name;
    TBranch b;
    TLeaf l;

    SRByte(String name,TBranch b,TLeaf l){
        super(name,b,l);
    }

    @Override Byte read(RootInput buffer)throws IOException{
        byte t = buffer.readByte();
        entry+=1L;
        return t;
    }

    @Override Byte read()throws IOException{
        RootInput buffer = b.setPosition(l,entry);
        byte t= buffer.readByte();
        entry+=1L;
        return t;
    }

    @Override List<Byte> readArray(RootInput buffer, int size)throws IOException{
        byte t;
        List<Byte> temp = new ArrayList();
        for (int i=0;i<size;i++){
            t= buffer.readByte();
            temp.add(t);
        }
        entry+=1L;
        return temp;
    }

    @Override List<Byte> readArray(int size)throws IOException{
        RootInput buffer = b.setPosition(l,entry);
        byte t;
        List<Byte> temp = new ArrayList();
        for (int i=0;i<size;i++){
            t=buffer.readByte();
            temp.add(t);
        }
        entry+=1L;
        return temp;
    }

    @Override boolean hasNext(){
        return entry<b.getEntries();
    }

}
