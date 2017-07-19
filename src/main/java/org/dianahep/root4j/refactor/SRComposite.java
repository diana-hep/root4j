package org.dianahep.root4j.refactor;

import org.dianahep.root4j.interfaces.*;
import org.dianahep.root4j.core.*;
import java.util.*;
import java.io.*;

public class SRComposite<T> extends SRType{
    String name;
    TBranch b;
    List<SRType> members;
    boolean split;
    boolean isTop;
    boolean isBase;

    SRComposite(String name,TBranch b,List<SRType> members,boolean split,boolean isTop,boolean isBase){
        super(name);
        this.b=b;
        this.members=members;
        this.split=split;
        this.isTop=isTop;
        this.isBase = isBase;
    }

    SRComposite(String name,TBranch b,List<SRType> members,boolean split,boolean isTop){
        super(name);
        this.b=b;
        this.members=members;
        this.split=split;
        this.isTop=isTop;
        this.isBase = false;
    }

    @Override List<Object> readArray(int size)throws IOException{
        List<Object> data = new ArrayList();
        List<Object> temp = new ArrayList();
        if (split){
            if (members.size()==0){
                for (int i=0;i<size;i++){
                    data.add(temp);
                }
            }
            else {
                for (SRType m : members){
                    data.add(m.readArray(size));
                }

                //Transpose to Map

            }
            entry+=1L;
            return data;
        }
        else {
            RootInput buffer = b.setPosition((TLeafElement)b.getLeaves().get(0), entry);
            return readArray(buffer,size);
        }
    }

    List<Object> readArray(RootInput buffer,int size)throws IOException{
        List<Object> data = new ArrayList();
        List<Object> temp = new ArrayList();
        if (isBase){
            if (members.size()==0){
                for (int i=0;i<size;i++){
                    data.add(temp);
                }
            }
            else {
                for (SRType m : members)
                    data.add(m.readArray(buffer,size));
                }

                //Transpose to Map

            entry+=1L;
            return data;
        }
        else {
            for (int i=0;i<size;i++){
                int version = buffer.readVersion();
                if (version==0) {
                    buffer.readInt();
                }
                for (SRType m : members)
                    temp.add(m.read(buffer));
                }

                //Transpose to Map

            data.add(temp);
            entry+=1L;
            return data;
        }
    }

    @Override List<Object> read()throws IOException{
        List<Object> data = new ArrayList();
        if (split){
            entry+=1L;
            for (SRType m : members){
                data.add(m.read());
            }

            //Transpose to map

            return data;
        }
        else {
            if (members.size()==0){
                entry+=1L;
                List<Object> temp = new ArrayList();
                return temp;
            }
            RootInput buffer = b.setPosition((TLeaf)b.getLeaves().get(0),entry);
            if (isTop){
                entry+=1L;
                for (SRType m : members){
                    data.add(m.read(buffer));
                }

                //Transpose to map

                return data;
            }
            else {
                int version = buffer.readVersion();
                if (version == 0) {
                    buffer.readInt();
                }
                entry+=1L;
                for (SRType m : members){
                    data.add(m.read(buffer));
                }

                //Transpose to map

                return data;
            }
        }
    }

    @Override List<Object> read(RootInput buffer)throws IOException{
        List<Object> data = new ArrayList();
        entry+=1L;
        int version = buffer.readVersion();
        if (version==0){
            buffer.readInt();
        }
        for (SRType m : members){
            data.add(m.read(buffer));
        }

        //Transpose to map

        return data;
    }

    @Override boolean hasNext(){
        return entry<b.getEntries();
    }
}
