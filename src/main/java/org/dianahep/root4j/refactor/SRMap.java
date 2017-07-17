package org.dianahep.root4j.refactor;

import org.dianahep.root4j.core.*;
import org.dianahep.root4j.interfaces.*;
import java.io.*;
import java.util.*;

public class SRMap extends SRCollection {
    String name;
    TBranchElement b;
    SRType keyType;
    SRType valueType;
    boolean split;
    boolean isTop;

    SRMap(String name,TBranchElement b,SRType keyType,SRType valueType,boolean split,boolean isTop){
        super(name,isTop);
        this.b = b;
        this.keyType = keyType;
        this.valueType = valueType;
        this.split = split;
    }

    SRMap(String name,TBranchElement b,SRComposite types,boolean split,boolean isTop){
        super(name,isTop);
        SRCollection srcollection = new SRCollection(name,isTop);
        this.b=b;
        this.keyType = types.members.get(0);
        this.valueType = types.members.get(1);
        this.split=split;
    }

    @Override Map<SRType,SRType> readArray(RootInput buffer, int size)throws IOException {
        Map<SRType,SRType> data = new HashMap();
        int nn;
        if (split){
            return null;
        }
        else {
            int byteCount = buffer.readInt();
            short version = buffer.readShort();
            if (version > 0 && kMemberWiseStreaming >0){
                return null;
            }
            else {
                for (int i=0;i<size;i++){
                    nn=buffer.readInt();
                    data.put(keyType.read(buffer),valueType.read(buffer));
                }
                entry+=1;
                return data;
            }
        }
    }

    @Override Map<SRType,SRType> readArray(int size)throws IOException {
        RootInput buffer = b.setPosition((TLeafElement)b.getLeaves().get(0),entry);
        return readArray(buffer,size);
    }

    @Override Map<SRType,SRType> read()throws IOException{
        Map<SRType,SRType> data = new HashMap();
        if (split){
            TLeaf leaf = (TLeaf)b.getLeaves().get(0);
            RootInput buffer = b.setPosition(leaf,entry);
            int size = buffer.readInt();
            return null;
        }
        else {
            RootInput buffer = b.setPosition((TLeaf)b.getLeaves().get(0),entry);
            int byteCount = buffer.readInt();
            short version = buffer.readShort();
            if (version>0 && kMemberWiseStreaming>0){
                return null;
            }
            else {
                int size = buffer.readInt();
                entry+=1L;
                for (int i=0;i<size;i++){
                    data.put(keyType.read(buffer),valueType.read(buffer));
                }
                return data;
            }
        }
    }

    @Override Map<SRType,SRType> read(RootInput buffer)throws IOException{
        Map<SRType,SRType> data = new HashMap();
        if (split){
            return null;
        }
        else {
            if (isTop) {
                int byteCount = buffer.readInt();
                short version = buffer.readShort();
                if (version > 0 && kMemberWiseStreaming > 0) {
                    return null;
                } else {
                    int size = buffer.readInt();
                    entry += 1L;
                    for (int i = 0; i < size; i++) {
                        data.put(keyType.read(buffer), valueType.read(buffer));
                    }
                    return data;
                }
            }
            else {
                int size=buffer.readInt();
                entry+=1L;
                for (int i=0;i<size;i++){
                    data.put(keyType.read(buffer),valueType.read(buffer));
                }
                return data;
            }
        }
    }

    @Override boolean hasNext(){
        return entry<b.getEntries();
    }

}