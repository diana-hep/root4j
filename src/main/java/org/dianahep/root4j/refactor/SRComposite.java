package org.dianahep.root4j.refactor;

import org.dianahep.root4j.interfaces.*;
import org.dianahep.root4j.core.*;
import java.util.*;
import java.io.*;

public class SRComposite<T> extends SRType{
    public static String name;
    public static TBranch b;
    public static List<SRType> members;
    public static boolean split;
    public static boolean isTop;
    public static boolean isBase;

    public SRComposite(String name,TBranch b,List<SRType> members,boolean split,boolean isTop,boolean isBase){
        super(name);
        this.b=b;
        this.members=members;
        this.split=split;
        this.isTop=isTop;
        this.isBase = isBase;
    }

    public SRComposite(String name,TBranch b,List<SRType> members,boolean split,boolean isTop){
        super(name);
        this.b=b;
        this.members=members;
        this.split=split;
        this.isTop=isTop;
        this.isBase = false;
    }

    @Override public void debugMe(String str) {
        logger.debug("SRComposite:: "+name+" "+str+" Event="+entry);
    }

    @Override public List<Object> readArray(int size)throws IOException{
        List<Object> data = new ArrayList();
        List<Object> temp = new ArrayList();
        if (split){
            debugMe("readArray(buffer," +size+") in split mode");
            if (members.size()==0){
                for (int i=0;i<size;i++){
                    data.add(temp);
                }
            }
            else {
                for (SRType m : members){
                    data.add(m.readArray(size));
                }

                //Transpose to Map?

            }
            entry+=1L;
            return data;
        }
        else {
            RootInput buffer = b.setPosition((TLeafElement)b.getLeaves().get(0), entry);
            debugMe("readArray("+size+") in non-split mode calls readArray(buffer, "+size+")");
            return readArray(buffer,size);
        }
    }

    @Override public List<Object> readArray(RootInput buffer,int size)throws IOException{
        List<Object> data = new ArrayList();
        List<Object> temp = new ArrayList();
        if (isBase){
            debugMe("readArray(buffer, "+size+") in isBase mode");
            if (members.size()==0){
                for (int i=0;i<size;i++){
                    data.add(temp);
                }
            }
            else {
                for (SRType m : members)
                    data.add(m.readArray(buffer,size));
                }

                //Transpose to Map?

            entry+=1L;
            return data;
        }
        else {
            debugMe("readArray(buffer, "+size+") in non-Base mode");
            for (int i=0;i<size;i++){
                int version = buffer.readVersion();
                if (version==0) {
                    buffer.readInt();
                }
                for (SRType m : members)
                    temp.add(m.read(buffer));
                }

                //Transpose to Map?

            data.add(temp);
            entry+=1L;
            return data;
        }
    }

    @Override public List<Object> read()throws IOException{
        List<Object> data = new ArrayList();
        if (split){
            debugMe("read in split mode");
            entry+=1L;
            for (SRType m : members){
                data.add(m.read());
            }

            //Transpose to map?

            return data;
        }
        else {
            debugMe("read in non-split mode");
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

                //Transpose to map?

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

                //Transpose to map?

                return data;
            }
        }
    }

    @Override public List<Object> read(RootInput buffer)throws IOException{
        debugMe("read(buffer)");
        List<Object> data = new ArrayList();
        entry+=1L;
        int version = buffer.readVersion();
        if (version==0){
            buffer.readInt();
        }
        for (SRType m : members){
            data.add(m.read(buffer));
        }

        //Transpose to map?

        return data;
    }

    @Override public boolean hasNext(){
        return entry<b.getEntries();
    }
}
