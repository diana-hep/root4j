package org.dianahep.root4j.refactor;

import org.dianahep.root4j.interfaces.*;
import org.dianahep.root4j.core.*;
import java.util.*;
import java.io.*;

public class SRComposite extends SRType{
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

    List<List<Vector>> readArray(int size)throws IOException{
        List<List<Vector>> data = new ArrayList();
        List<Vector> temp = new ArrayList();
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
            }
            entry+=1L;
            return data;
        }
        else {
            RootInput buffer = b.setPosition((TLeafElement)b.getLeaves().get(0), entry);
            readArray(buffer,size);
        }
    }

    List<List<Vector>> readArray(RootInput buffer,int size)throws IOException{
        List<List<Vector>> data = new ArrayList();
        List<Vector> temp = new ArrayList();
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
            data.add(temp);
            entry+=1L;
            return data;
        }
    }

    @Override void read()throws IOException{
        List<SRType> data = new ArrayList();
        if (split){
            entry+=1L;
            for (int i=0;i<members.size();i++){
                data.add(members.get(i));
            }
        }
        else {
            if (members.isEmpty()){
                entry+=1L;
                data.add(null);
            }
            RootInput buffer = b.setPosition((TLeaf)b.getLeaves().get(0),entry);
            if (isTop){
                entry+=1L;
                for (int i=0;i<members.size();i++){
                    data.add(members.get(i));
                }
            }
            else {
                int version = buffer.readVersion();
                if (version == 0) {
                    buffer.readInt();
                }
                entry+=1L;
                for (int i=0;i<members.size();i++){
                    data.add(members.get(i));
                }
            }
        }
        array.add(data);
    }

    @Override void read(RootInput buffer)throws IOException{
        List<SRType> data = new ArrayList();
        entry+=1L;
        int version = buffer.readVersion();
        if (version==0){
            buffer.readInt();
        }
        for (int i=0;i<members.size();i++){
            data.add(members.get(i));
        }
        array.add(data);
    }

    @Override boolean hasNext(){
        return entry<b.getEntries();
    }
}
