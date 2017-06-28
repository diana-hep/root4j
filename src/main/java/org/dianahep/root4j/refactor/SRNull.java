package org.dianahep.root4j.refactor;

import org.dianahep.root4j.core.*;

public class SRNull extends SRType {

    SRNull(){
        super("Null");
    }

    @Override void read(RootInput buffer){
        array.add((int)entry,null);
    }

    @Override void read(){
        array.add((int)entry,null);
    }

    @Override void readArray(RootInput buffer,int size){
        array.add((int)entry,null);
    }

    @Override void readArray(int size){
        array.add((int)entry,null);
    }

    @Override boolean hasNext(){
        return false;
    }
}
