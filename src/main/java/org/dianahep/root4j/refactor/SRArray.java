package org.dianahep.root4j.refactor;

import org.dianahep.root4j.core.*;
import org.dianahep.root4j.interfaces.*;
import java.io.*;
import java.util.*;

public class SRArray extends SRSimpleType{

    static String name;
    static TBranch b;
    static TLeaf l;
    static SRType t;
    static int n;

    public SRArray(String name,TBranch b,TLeaf l,SRType t,int n){
        super(name,b,l);
        this.n=n;
        this.t=t;
    }

    @Override public void debugMe(String str) {
        logger.debug("SRArray:: "+name+" "+str);
    }

    @Override public List<Object> read(RootInput buffer)throws IOException{
        debugMe("read(buffer)");
        List<Object> data = new ArrayList();
        if (n==-1) {
            for (int i = 0; i < (Integer) l.getLeafCount().getWrappedValue(entry); i++) {
               data.add(t.read(buffer));
            }
        }
        else{
                for(int i=0;i<n;i++) {
                    data.add(t.read(buffer));
                }
            }
        entry+=1L;
        return data;
    }

    @Override public List<Object> read()throws IOException{
        debugMe("read");
        RootInput buffer = b.setPosition(l,entry);
        List<Object> data = new ArrayList();
        if (n==-1) {
            for (int i = 0; i < (Integer) l.getLeafCount().getWrappedValue(entry); i++) {
                data.add(t.read(buffer));
            }
        }
        else{
            for(int i=0;i<n;i++) {
                data.add(t.read(buffer));
            }
        }
        entry+=1L;
        return data;
    }

    @Override public List<Object> readArray(RootInput buffer, int size) throws IOException{
        debugMe("readArray(buffer,"+size+")");
        List<Object> data = new ArrayList();
        for (int i=0;i<size;i++){
            if (n==-1){
                for (int j=0; j<(Integer)l.getLeafCount().getWrappedValue(entry);j++){
                    data.add(t.read(buffer));
                }
            }
            else {
                for (int j=0;j<size;j++){
                    data.add(t.read(buffer));
                }
            }
        }
        entry+=1L;
        return data;
    }

    @Override public List<Object> readArray(int size)throws IOException{
        debugMe("readArray("+size+")");
        RootInput buffer = b.setPosition(l,entry);
        List<Object> data = new ArrayList();
        for (int i=0;i<size;i++){
            if (n==-1){
                for (int j=0; j<(Integer)l.getLeafCount().getWrappedValue(entry);j++){
                    data.add(t.read(buffer));
                }
            }
            else {
                for (int j=0;j<size;j++){
                    data.add(t.read(buffer));
                }
            }
        }
        entry+=1L;
        return data;
    }

    @Override public boolean hasNext(){
        return entry<b.getEntries();
    }
}
