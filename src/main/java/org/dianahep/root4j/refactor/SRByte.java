package org.dianahep.root4j.refactor;

import org.dianahep.root4j.core.*;
import org.dianahep.root4j.interfaces.*;
import java.io.*;
import java.util.*;

public class SRByte extends SRSimpleType {

    static String name;
    static TBranch b;
    static TLeaf l;

    public SRByte(String name,TBranch b,TLeaf l){
        super(name,b,l);
    }

    @Override public void debugMe(String str) {
        logger.debug("SRByte:: "+name+" "+str);
    }

    @Override public Byte read(RootInput buffer)throws IOException{
        debugMe("read(buffer)");
        byte t = buffer.readByte();
        entry+=1L;
        return t;
    }

    @Override public Byte read()throws IOException{
        debugMe("read");
        RootInput buffer = b.setPosition(l,entry);
        byte t = buffer.readByte();
        entry+=1L;
        return t;
    }

    @Override public List<Byte> readArray(RootInput buffer, int size)throws IOException{
        debugMe("readArray(buffer,"+size+")");
        byte t;
        List<Byte> temp = new ArrayList();
        for (int i=0;i<size;i++){
            t= buffer.readByte();
            temp.add(t);
        }
        entry+=1L;
        return temp;
    }

    @Override public List<Byte> readArray(int size)throws IOException{
        debugMe("readArray("+size+")");
        RootInput buffer = b.setPosition(l,entry);
        byte t;
        List<Byte> temp = new ArrayList();
        for (int i=0;i<size;i++){
            t= buffer.readByte();
            temp.add(t);
        }
        entry+=1L;
        return temp;
    }

    @Override public boolean hasNext(){
        return entry<b.getEntries();
    }

}
