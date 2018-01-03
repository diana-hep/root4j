package org.dianahep.root4j.refactor;

import org.dianahep.root4j.core.*;
import java.util.*;

public class SRNull extends SRType{
    public SRNull(){
        super("Null");
    }

    public SRNull(String name){
        super(name);
    }

    @Override void debugMe(String str) {
        logger.debug("SRNull::no name "+str);
    }

    @Override public SRType read(RootInput b){
        debugMe("read(buffer)");
        return null;
    }

    @Override public SRType read(){
        debugMe("read");
        return null;
    }

    @Override public List<SRType> readArray(RootInput buffer,int size){
        debugMe("readArray("+size+")");
        List<SRType> temp=new ArrayList();
        for (int i=0;i<size;i++){
            temp.add(null);
        }
        return temp;
    }

    @Override public List<SRType> readArray(int size){
        debugMe("readArray(buffer, "+size+")");
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
