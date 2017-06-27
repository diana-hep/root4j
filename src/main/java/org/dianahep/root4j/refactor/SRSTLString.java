package org.dianahep.root4j.refactor;

import org.dianahep.root4j.core.*;
import org.dianahep.root4j.interfaces.*;
import java.io.*;

public class SRSTLString extends SRCollection {
    String name;
    TBranch b;
    boolean isTop;

    SRSTLString(String name,TBranch b,boolean isTop){
        super(name,isTop);
        this.b=b;
    }

    @Override void read(RootInput buffer)throws IOException{
        if (isTop){
            array.add((int)entry,buffer.readInt());
            entry+=1L;
            array.add((int)entry,buffer.readShort());
            entry+=1L;
        }
        array.add((int)entry,buffer.readString());
        entry+=1L;
    }

    @Override void read()throws IOException{
        RootInput buffer = b.setPosition((TLeafElement)b.getLeaves().get(0), entry);
        if (isTop){
            array.add((int)entry,buffer.readInt());
            entry+=1L;
            array.add((int)entry,buffer.readShort());
            entry+=1L;
        }
        array.add((int)entry,buffer.readString());
        entry+=1L;
    }

    @Override boolean hasNext(){
        return entry<b.getEntries();
    }

    @Override void readArray(RootInput buffer, int size)throws IOException{
        array.add((int)entry,buffer.readInt());
        entry+=1L;
        array.add((int)entry,buffer.readShort());
        entry+=1L;
        for (int i=0;i<size;i++){
            array.add((int)entry+i,buffer.readString());
        }
        entry+=1L;
    }

    @Override void readArray(int size)throws IOException{
        RootInput buffer = b.setPosition((TLeafElement)b.getLeaves().get(0), entry);
        array.add((int)entry,buffer.readInt());
        entry+=1L;
        array.add((int)entry,buffer.readShort());
        entry+=1L;
        for (int i=0;i<size;i++){
            array.add((int)entry+i,buffer.readString());
        }
        entry+=1L;
    }
}
