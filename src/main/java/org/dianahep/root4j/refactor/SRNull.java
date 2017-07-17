package org.dianahep.root4j.refactor;

import org.dianahep.root4j.core.*;
import java.util.*;

public class SRNull extends SRType{
    SRNull(){
        super(null);
    }

    @Override SRType read(RootInput b){
        return null;
    }

    @Override SRType read(){
        return null;
    }

    @Override List<SRType> readArray(RootInput buffer,int size){
        List<SRType> temp=new ArrayList();
        for (int i=0;i<size;i++){
            temp.add(null);
        }
        return temp;
    }

    @Override List<SRType> readArray(int size){
        List<SRType> temp = new ArrayList();
        for (int i=0;i<size;i++){
            temp.add(null);
        }
        return temp;
    }

    boolean hasNext(){
        return false;
    }
}
