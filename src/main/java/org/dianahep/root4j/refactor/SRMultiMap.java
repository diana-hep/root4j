package org.dianahep.root4j.refactor;

import org.dianahep.root4j.core.*;
import org.dianahep.root4j.interfaces.*;
import java.util.*;
import java.io.*;

public class SRMultiMap<T> extends SRCollection{
    static String name;
    static TBranchElement b;
    static SRType keyType;
    static SRType valueType;
    static boolean split;
    static boolean isTop;

    public SRMultiMap(String name,TBranchElement b,SRType keyType,SRType valueType,boolean split,boolean isTop){
        this.name = name;
        this.b = b;
        this.keyType = keyType;
        this.valueType = valueType;
        this.split = split;
        this.isTop = isTop;
    }

    public SRMultiMap(String name,TBranchElement b,SRComposite<T> types,boolean split,boolean isTop){
        super(name,isTop);
        this.b=b;
        this.keyType = types.members.get(0);
        this.valueType = types.members.get(1);
        this.split=split;
    }

    @Override public Map<Object,Object> readArray(RootInput buffer,int size)throws IOException{
        Map<Object,Object> data = new HashMap();
        int nn;
        if (split){
            return null;
        }
        else {
            int byteCount = buffer.readInt();
            short version = buffer.readShort();
            if (version>0 && kMemberWiseStreaming>0){
                return null;
            }
            else {
                for (int i=0;i<size;i++){
                    nn = buffer.readInt();
                    data.put(keyType.read(buffer),valueType.read(buffer));
                }

                //groupBy and unzip

                entry+=1L;
                return data;
            }
        }
    }

    @Override public Map<Object,Object> readArray(int size)throws IOException{
        RootInput buffer = b.setPosition((TLeafElement)b.getLeaves().get(0),entry);
        return readArray(buffer,size);
    }

    @Override public Map<Object,Object> read()throws IOException{
        Map<Object,Object> data = new HashMap();
        TLeaf leaf = (TLeaf)b.getLeaves().get(0);
        RootInput buffer = b.setPosition(leaf,entry);
        if (split){
            int size = buffer.readInt();
            return null;
        }
        else {
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

                // groupBy and unzip

                return data;
            }
        }
    }

    @Override public Map<Object,Object> read(RootInput buffer)throws IOException{
        Map<Object,Object> data = new HashMap();
        if (split){
            return null;
        }
        else {
            if (isTop){
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

                    //groupBy and unzip

                    return data;
                }
            }
            else {
                int size = buffer.readInt();
                entry+=1L;
                for (int i=0;i<size;i++){
                    data.put(keyType.read(buffer),valueType.read(buffer));
                }

                //groupBy and unzip

                return data;
            }
        }
    }

    @Override public boolean hasNext(){
        return entry<b.getEntries();
    }
}
