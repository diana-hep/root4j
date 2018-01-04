package org.dianahep.root4j.refactor;

import org.dianahep.root4j.core.*;
import org.dianahep.root4j.interfaces.*;
import java.util.*;
import java.io.*;

public class SRVector<T> extends SRCollection{

    static String name;
    static TBranchElement b;
    static SRType t;
    static boolean split;
    static boolean isTop;

    public SRVector(String name,TBranchElement b,SRType t,boolean split,boolean isTop){
        super(name,isTop);
        this.b=b;
        this.t=t;
        this.split=split;
    }

    @Override void debugMe(String str) {
        logger.debug("SRVector:: "+name+" "+str+" Event="+entry);
    }

    @Override
    public boolean hasNext(){
        return entry<b.getEntries();
    }

    @Override public List<Object> readArray(RootInput buffer,int size)throws IOException{
        List<Object> data = new ArrayList();
        List<Object> temp1 = new ArrayList();
        entry+=1L;
        SRNull srnull = new SRNull();
        int nn;
        if (split)
        {
            debugMe("readArray(buffer," +size+") in split mode");
            return null;
        }
        else {
            debugMe("readArray(buffer, "+size+") in non-split mode");
            int byteCount = buffer.readInt();
            short version = buffer.readShort();
            if (version >0 && kMemberWiseStreaming > 0){
                if (t.equals(srnull) || t instanceof SRUnknown){
                    for (int i=0;i<size;i++){
                        data.add(temp1);
                    }
                    return data;
                }
                else {
                    short memberVersion = buffer.readShort();
                    if (memberVersion == 0){
                        buffer.readInt();
                    }
                    SRComposite<T> composite = (SRComposite)t;
                    for (int i=0;i<size;i++){
                        nn = buffer.readInt();
                        if (nn==0){
                            data.add(temp1);
                        }
                        else{
                            for (SRType x : composite.members){
                                data.add(x.readArray(buffer,nn));
                            }

                            //Transpose to map and return a map?
                        }
                    }
                    entry+=1L;
                    return data;
                }
            }
            else {
                for (int i=0;i<size;i++){
                    nn=buffer.readInt();
                    debugMe("readArray(buffer, "+size+") object-wise current "+nn);
                    for (int j=0;j<nn;j++){
                        data.add(t.read(buffer));
                    }
                }
                entry+=1L;
                return data;
            }
        }
    }

    @Override public List<Object> readArray(int size)throws IOException{
        debugMe("readArray("+size+") calls readArray(buffer, "+size+")");
        RootInput buffer = b.setPosition((TLeafElement)b.getLeaves().get(0),entry);
        return readArray(buffer,size);
    }

    @Override public List<Object> read()throws IOException{
        List<Object> data = new ArrayList();
        List<Object> empty = new ArrayList();
        TLeaf leaf = (TLeaf)b.getLeaves().get(0);
        RootInput buffer = b.setPosition(leaf,entry);
        int size= buffer.readInt();
        SRNull srnull = new SRNull();
        if (split){
            debugMe("read in split mode");
            if (t.equals(srnull) || t instanceof SRUnknown){
                data.add(empty);
            }
            else {
                SRComposite<T> composite = (SRComposite)t;
                for (SRType x : composite.members){
                    data.add(x.readArray(size));
                }

                //Transpose to map and return a map?

            }
            entry+=1L;
            return data;
        }
        else {
            debugMe("read in non-split mode");
            int byteCount = buffer.readInt();
            int version = buffer.readVersion();
            if (version>0 && kMemberWiseStreaming>0){
                if (t.equals(srnull) || t instanceof SRUnknown){
                    data.add(empty);
                }
                else {
                    SRComposite<T> composite = (SRComposite)t;
                    short memberVersion = buffer.readShort();
                    if (memberVersion == 0){
                        buffer.readInt();
                    }
                    for (SRType x : composite.members){
                        data.add(x.readArray(buffer,size));
                    }

                    //Transpose to map and return as map?

                }
                entry +=1L;
                return data;
            }
            else {
                logger.debug("read in non-split mode object-wise "+size);
                entry+=1L;
                for (int i=0;i<size;i++){
                    data.add(t.read(buffer));
                }
                return data;
            }
        }
    }

    @Override public List<Object> read(RootInput buffer)throws IOException{
        SRNull srnull = new SRNull();
        List<Object> empty = new ArrayList();
        List<Object> data = new ArrayList();
        if (split){
            return null;
        }
        else {
            if (isTop){
                int byteCount = buffer.readInt();
                int version = buffer.readVersion();
                if (version>0 && kMemberWiseStreaming>0){
                    if (t.equals(srnull) || t instanceof SRUnknown){
                        data.add(empty);
                    }
                    else {
                        SRComposite<T> composite = (SRComposite)t;
                        short memberVersion = buffer.readShort();
                        int size = buffer.readInt();
                        for (SRType x: composite.members){
                            data.add(x.readArray(buffer,size));
                        }

                        //Transpose to map and return as map?

                    }
                    entry+=1L;
                    return data;
                }
                else {
                    int size = buffer.readInt();
                    debugMe("read(buffer) in object-wise mode "+size+" as TOP");
                    entry+=1L;
                    for (int i=0;i<size;i++){
                        data.add(t.read(buffer));
                    }
                    return data;
                }
            }
            else {
                int size = buffer.readInt();
                debugMe("read(buffer) in object-wise mode "+size+" as _not_ TOP");
                entry+=1L;
                for (int i=0;i<size;i++){
                    data.add(t.read(buffer));
                }
                return data;
            }
        }
    }
}
