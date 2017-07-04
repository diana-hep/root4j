package org.dianahep.root4j.refactor;

import org.dianahep.root4j.core.*;

public class SRNull extends SRType{
    SRNull(){
        super(null);
    }

    @Override void read(RootInput b){
        array.add((int)entry,null);
    }

    @Override void read(){
        array.add((int)entry,null);
    }

    @Override void readArray(RootInput buffer,int size){
        String arr[]=new String[size];
        for (int i=0;i<size;i++){
            arr[i] = null;
        }
        array.add((int)entry,arr);
    }

    @Override void readArray(int size){
        String arr[]=new String[size];
        for (int i=0;i<size;i++){
            arr[i]=null;
        }
        array.add((int)entry,null);
    }

    boolean hasNext(){
        return false;
    }
}
