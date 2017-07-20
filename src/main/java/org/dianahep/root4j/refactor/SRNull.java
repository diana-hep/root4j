package org.dianahep.root4j.refactor;

import org.dianahep.root4j.core.*;
import java.util.*;

public class SRNull extends SRType{
    SRNull(){
        super(null);
    }

    SRNull(String name){
        super(name);
    }

    @Override public SRType read(RootInput b){
        return null;
    }

    @Override public SRType read(){
        return null;
    }

    @Override public List<SRType> readArray(RootInput buffer,int size){
        List<SRType> temp=new ArrayList();
        for (int i=0;i<size;i++){
            temp.add(null);
        }
        return temp;
    }

    @Override public List<SRType> readArray(int size){
        List<SRType> temp = new ArrayList();
        for (int i=0;i<size;i++){
            temp.add(null);
        }
        return temp;
    }

    @Override public boolean hasNext(){
        return false;
    }
}
