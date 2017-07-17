package org.dianahep.root4j.refactor;

import org.dianahep.root4j.core.*;
import org.dianahep.root4j.interfaces.*;
import java.io.*;
import java.util.*;

public class SRArray extends SRSimpleType{

    String name;
    TBranch b;
    TLeaf l;
    SRType t;
    int n;

    SRArray(String name,TBranch b,TLeaf l,SRType t,int n){
        super(name,b,l);
        this.n=n;
        this.t=t;
    }

    @Override List<Object> read(RootInput buffer)throws IOException{
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

    @Override List<Object> read()throws IOException{
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

    @Override List<Object> readArray(RootInput buffer, int size) throws IOException{
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

    @Override List<Object> readArray(int size)throws IOException{
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

    @Override boolean hasNext(){
        return entry<b.getEntries();
    }
}
