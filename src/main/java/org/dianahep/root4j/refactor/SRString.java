package org.dianahep.root4j.refactor;

import org.dianahep.root4j.interfaces.*;
import org.dianahep.root4j.core.*;
import java.io.*;

public class SRString extends SRSimpleType{
    String name;
    TBranch b;
    TLeaf l;

    SRString(String name,TBranch b,TLeaf l){
        super(name,b,l);
    }

    @Override void read(RootInput buffer)throws IOException{
        entry+= 1L;
        String r=buffer.readString();
        array.add((int)entry,r);
    }

    @Override void read()throws IOException{
        RootInput buffer = b.setPosition(l,entry);
        String data = buffer.readString();
        entry+= 1L;
        array.add((int)entry,data);
    }

    @Override void readArray(RootInput buffer,int size)throws IOException{
        entry+=1L;
        for (int i=0;i<size;i++){
            array.add(i,buffer.readString());
        }
    }

    @Override void readArray(int size) throws IOException{
        RootInput buffer = b.setPosition(l,entry);
        for (int i=0;i<size;i++){
            array.add(i,buffer.readString());
        }
        entry+=1L;
    }

    @Override boolean hasNext(){
        return entry<b.getEntries();
    }

}
