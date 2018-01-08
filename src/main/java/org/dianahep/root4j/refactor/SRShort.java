package org.dianahep.root4j.refactor;

import org.dianahep.root4j.core.*;
import org.dianahep.root4j.interfaces.*;
import java.io.*;
import java.util.*;

public class SRShort extends SRSimpleType {

    static String name;
    static TBranch b;
    static TLeaf l;

    public SRShort(String name,TBranch b,TLeaf l){
        super(name,b,l);
    }

    @Override public void debugMe(String str) {
        logger.debug("SRShort:: "+name+" "+str);
    }

    @Override public Short read(RootInput buffer)throws IOException{
        debugMe("read(buffer)");
        short data = buffer.readShort();
        entry+=1L;
        debugMe("read String ="+data);
        return data;
    }

    @Override public Short read()throws IOException{
        RootInput buffer = b.setPosition(l,entry);
        debugMe("read");
        short r = buffer.readShort();
        debugMe("read Short="+r);
        entry+=1L;
        return r;
    }

    @Override public List<Short> readArray(RootInput buffer, int size)throws IOException{
        debugMe("readArray(buffer,"+size+")");
        short t;
        List<Short> temp = new ArrayList();
        for (int i=0;i<size;i++){
            t=buffer.readShort();
            temp.add(t);
        }
        entry+=1L;
        return temp;
    }

    @Override public List<Short> readArray(int size)throws IOException{
        debugMe("readArray("+size+")");
        RootInput buffer = b.setPosition(l,entry);
        short t;
        List<Short> temp = new ArrayList();
        for (int i=0;i<size;i++){
            t=buffer.readShort();
            temp.add(t);
        }
        entry+=1L;
        return temp;
    }

    @Override public boolean hasNext(){
        return entry<b.getEntries();
    }

}
