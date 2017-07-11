package org.dianahep.root4j.refactor;

import org.dianahep.root4j.interfaces.*;
import org.dianahep.root4j.core.*;
import java.io.*;
import java.util.*;

public class SRString extends SRSimpleType{
    String name;
    TBranch b;
    TLeaf l;

    SRString(String name,TBranch b,TLeaf l){
        super(name,b,l);
    }

    @Override void read(RootInput buffer)throws IOException{
        String r=buffer.readString();
        array.add(r);
        entry+=1L;
    }

    @Override void read()throws IOException{
        RootInput buffer = b.setPosition(l,entry);
        String data = buffer.readString();
        array.add(data);
        entry+=1L;
    }

    @Override void readArray(RootInput buffer,int size)throws IOException{
        List<String> temp = new ArrayList();
        String t;
        for (int i=0;i<size;i++){
            t=buffer.readString();
            temp.add(t);
        }
        array.add(temp);
        entry+=1L;
    }

    @Override void readArray(int size) throws IOException{
        RootInput buffer = b.setPosition(l,entry);
        String arr[]=new String[size];
        for (int i=0;i<size;i++){
            arr[i]=buffer.readString();
        }
        array.add((int)entry,arr);
        entry+=1L;
    }

    @Override boolean hasNext(){
        return entry<b.getEntries();
    }

}
