package org.dianahep.root4j.refactor;

import org.dianahep.root4j.core.*;
import org.dianahep.root4j.interfaces.*;
import java.io.*;

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

    @Override void read(RootInput buffer)throws IOException{
        if (n==-1) {
            for (int i = 0; i < (Integer) l.getLeafCount().getWrappedValue(entry); i++) {
                t.read(buffer);
            }
        }
        else{
                for(int i=0;i<n;i++) {
                    t.read(buffer);
                }
            }
        entry+=1L;
    }

    @Override void read()throws IOException{
        RootInput buffer = b.setPosition(l,entry);
        if (n==-1) {
            for (int i = 0; i < (Integer) l.getLeafCount().getWrappedValue(entry); i++) {
                t.read(buffer);
            }
        }
        else{
            for(int i=0;i<n;i++) {
                t.read(buffer);
            }
        }
        entry+=1L;
    }

    @Override void readArray(RootInput buffer, int size) throws IOException{
        for (int i=0;i<size;i++){
            if (n==-1){
                for (int j=0; j<(Integer)l.getLeafCount().getWrappedValue(entry);j++){
                    t.read(buffer);
                }
            }
            else {
                for (int j=0;j<size;j++){
                    t.read(buffer);
                }
            }
        }
        entry+=1L;
    }

    @Override void readArray(int size)throws IOException{
        RootInput buffer = b.setPosition(l,entry);
        for (int i=0;i<size;i++){
            if (n==-1){
                for (int j=0; j<(Integer)l.getLeafCount().getWrappedValue(entry);j++){
                    t.read(buffer);
                }
            }
            else {
                for (int j=0;j<size;j++){
                    t.read(buffer);
                }
            }
        }
        entry+=1L;
    }

    @Override boolean hasNext(){
        return entry<b.getEntries();
    }
}
