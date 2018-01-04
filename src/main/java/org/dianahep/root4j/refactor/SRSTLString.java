package org.dianahep.root4j.refactor;

import org.dianahep.root4j.core.*;
import org.dianahep.root4j.interfaces.*;
import java.io.*;
import java.util.*;

public class SRSTLString extends SRCollection {

    static String name;
    static TBranch b;
    static boolean isTop;

    public SRSTLString(String name,TBranch b,boolean isTop){
        super(name,isTop);
        this.b=b;
    }

    @Override void debugMe(String str) {
        logger.debug("SRSTLtring:: "+name+" "+str+" Event="+entry);
    }

    @Override public String read(RootInput buffer)throws IOException{
        debugMe("read(buffer)");
        if (isTop){
            buffer.readInt();
            buffer.readShort();
        }
        String temp = buffer.readString();
        entry+=1L;
        return temp;
    }

    @Override public String read()throws IOException{
        debugMe("read");
        RootInput buffer = b.setPosition((TLeafElement)b.getLeaves().get(0), entry);
        if (isTop){
            buffer.readInt();
            buffer.readShort();
        }
        String temp = buffer.readString();
        entry+=1L;
        return temp;
    }

    @Override public boolean hasNext(){
        return entry<b.getEntries();
    }

    @Override public List<String> readArray(RootInput buffer, int size)throws IOException{
        debugMe("readArray(buffer,"+size+")");
        buffer.readInt();
        buffer.readShort();
        String t;
        List<String> temp = new ArrayList();
        for (int i=0;i<size;i++){
            t=buffer.readString();
            temp.add(t);
        }
        entry+=1L;
        return temp;
    }

    @Override public List<String> readArray(int size)throws IOException{
        debugMe("readArray("+size+")");
        RootInput buffer = b.setPosition((TLeafElement)b.getLeaves().get(0), entry);
        buffer.readInt();
        buffer.readShort();
        List<String> temp = new ArrayList();
        String t;
        for (int i=0;i<size;i++){
            t=buffer.readString();
            temp.add(t);
        }
        entry+=1L;
        return temp;
    }
}
