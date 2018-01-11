package org.dianahep.root4j.refactor;

import org.dianahep.root4j.core.*;
import org.dianahep.root4j.interfaces.*;
import java.io.*;
import java.util.*;

public class SRBoolean extends SRSimpleType {
    public static String name;
    public static TBranch b;
    public static TLeaf l;

    public SRBoolean(String name,TBranch b,TLeaf l){
        super(name,b,l);
    }

    @Override public void debugMe(String str) {
        logger.debug("SRBoolean:: "+name+" "+str);
    }

    @Override public Boolean read(RootInput buffer)throws IOException{
        debugMe("read(buffer)");
        boolean data = buffer.readBoolean();
        entry+=1L;
        return data;
    }

    @Override public Boolean read()throws IOException{
        debugMe("read");
        RootInput buffer = b.setPosition(l,entry);
        Boolean data = buffer.readBoolean();
        entry+=1L;
        return data;
    }

    @Override public List<Boolean> readArray(RootInput buffer, int size)throws IOException{
        debugMe("read("+buffer+", "+size+")");
        boolean t;
        List<Boolean> temp = new ArrayList();
        for (int i=0;i<size;i++){
            t = buffer.readBoolean();
            temp.add(t);
        }
        entry+=1L;
        return temp;
    }

    @Override public List<Boolean> readArray(int size)throws IOException{
        debugMe("readArray("+size+")");
        RootInput buffer = b.setPosition(l,entry);
        boolean t;
        List<Boolean> temp = new ArrayList();
        for (int i=0;i<size;i++){
            t = buffer.readBoolean();
            temp.add(t);
        }
        entry+=1L;
        return temp;
    }

    @Override public boolean hasNext(){
        return entry<b.getEntries();
    }

}
