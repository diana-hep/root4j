package org.dianahep.root4j.refactor;

import org.dianahep.root4j.interfaces.*;
import org.dianahep.root4j.core.*;
import java.io.*;
import java.util.*;

public class SRString extends SRSimpleType{

    static String name;
    static TBranch b;
    static TLeaf l;

    public SRString(String name,TBranch b,TLeaf l){
        super(name,b,l);
    }

    @Override public void debugMe(String str) {
        logger.debug("SRString:: "+name+" "+str);
    }

    @Override public String read(RootInput buffer)throws IOException{
        debugMe("read(buffer)");
        String r=buffer.readString();
        entry+=1L;
        debugMe("read String ="+r);
        return r;
    }

    @Override public String read()throws IOException{
        debugMe("read");
        RootInput buffer = b.setPosition(l,entry);
        String data = buffer.readString();
        debugMe("read String ="+data);
        entry+=1L;
        return data;
    }

    @Override public List<String> readArray(RootInput buffer,int size)throws IOException{
        debugMe("readArray(buffer,"+size+")");
        List<String> temp = new ArrayList();
        String t;
        for (int i=0;i<size;i++){
            t=buffer.readString();
            temp.add(t);
        }
        entry+=1L;
        return temp;
    }

    @Override public List<String> readArray(int size) throws IOException{
        debugMe("readArray("+size+")");
        RootInput buffer = b.setPosition(l,entry);
        List<String> temp = new ArrayList();
        String t;
        for (int i=0;i<size;i++){
            t=buffer.readString();
            temp.add(t);
        }
        entry+=1L;
        return temp;
    }

    @Override public boolean hasNext(){
        return entry<b.getEntries();
    }

}
