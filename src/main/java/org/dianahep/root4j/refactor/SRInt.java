package org.dianahep.root4j.refactor;

import org.dianahep.root4j.core.*;
import org.dianahep.root4j.interfaces.*;
import java.io.*;

public class SRInt extends SRSimpleType {
    String name;
    TBranch b;
    TLeaf l;

    SRInt(String name,TBranch b,TLeaf l){
        super(name,b,l);
    }

    @Override void read(RootInput buffer)throws IOException{
        array.add((int)entry,buffer.readInt());
        entry+=1L;
    }

    @Override void read()throws IOException{
        RootInput buffer = b.setPosition(l,entry);
        array.add((int)entry,buffer.readInt());
        entry+=1L;
    }

    @Override void readArray(RootInput buffer, int size)throws IOException{
        int arr[]=new int[size];
        for (int i=0;i<size;i++){
            arr[i]=buffer.readInt();
        }
        array.add((int)entry,arr);
        entry+=1L;
    }

    @Override void readArray(int size)throws IOException{
        int arr[]=new int[size];
        RootInput buffer = b.setPosition(l,entry);
        for (int i=0;i<size;i++){
            arr[i]=buffer.readInt();
        }
        array.add((int)entry,arr);
        entry+=1L;
    }

    @Override boolean hasNext(){
        return entry<b.getEntries();
    }

}
