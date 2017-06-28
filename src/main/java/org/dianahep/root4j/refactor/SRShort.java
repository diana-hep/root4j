package org.dianahep.root4j.refactor;

import org.dianahep.root4j.core.*;
import org.dianahep.root4j.interfaces.*;
import java.io.*;

public class SRShort extends SRSimpleType {
    String name;
    TBranch b;
    TLeaf l;

    SRShort(String name,TBranch b,TLeaf l){
        super(name,b,l);
    }

    @Override void read(RootInput buffer)throws IOException{
        array.add((int)entry,buffer.readShort());
        entry+=1L;
    }

    @Override void read()throws IOException{
        RootInput buffer = b.setPosition(l,entry);
        array.add((int)entry,buffer.readShort());
        entry+=1L;
    }

    @Override void readArray(RootInput buffer, int size)throws IOException{
        Short arr[]=new Short[size];
        for (int i=0;i<size;i++){
            arr[i]=buffer.readShort();
        }
        array.add((int)entry,arr);
        entry+=1L;
    }

    @Override void readArray(int size)throws IOException{
        RootInput buffer = b.setPosition(l,entry);
        Short arr[]=new Short[size];
        for (int i=0;i<size;i++){
            arr[i]=buffer.readShort();
        }
        array.add((int)entry,arr);
        entry+=1L;
    }

    @Override boolean hasNext(){
        return entry<b.getEntries();
    }

}
