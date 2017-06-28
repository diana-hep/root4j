package org.dianahep.root4j.refactor;

import org.dianahep.root4j.core.*;
import org.dianahep.root4j.interfaces.*;
import java.io.*;

public class SRByte extends SRSimpleType {
    String name;
    TBranch b;
    TLeaf l;

    SRByte(String name,TBranch b,TLeaf l){
        super(name,b,l);
    }

    @Override void read(RootInput buffer)throws IOException{
        array.add((int)entry,buffer.readByte());
        entry+=1L;
    }

    @Override void read()throws IOException{
        RootInput buffer = b.setPosition(l,entry);
        array.add((int)entry,buffer.readByte());
        entry+=1L;
    }

    @Override void readArray(RootInput buffer, int size)throws IOException{
        byte arr[]=new byte[size];
        for (int i=0;i<size;i++){
            arr[i]=buffer.readByte();
        }
        array.add((int)entry,arr);
        entry+=1L;
    }

    @Override void readArray(int size)throws IOException{
        RootInput buffer = b.setPosition(l,entry);
        byte arr[]=new byte[size];
        for (int i=0;i<size;i++){
            arr[i]=buffer.readByte();
        }
        array.add((int)entry,arr);
        entry+=1L;
    }

    @Override boolean hasNext(){
        return entry<b.getEntries();
    }

}
