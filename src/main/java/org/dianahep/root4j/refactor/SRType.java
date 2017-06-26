package org.dianahep.root4j.refactor;

import org.dianahep.root4j.core.*;
import java.util.*;
import java.io.*;

public abstract class SRType<T extends RootInput>{
    String name;
    List<T> array;
    long entry = 0L;
    String toName = name.replace('.','_');

    void read(RootInput b) throws IOException{};

    void read()throws IOException{};

    boolean hasNext() {
        return false;
    };

    void readArray(int size)throws IOException{};

    void readArray(RootInput buffer, int size)throws IOException{};

    SRType(String name,List<T> array){
        this.name=name;
        this.array=array;
    }

    SRType(){
        this.name=null;
        this.array=null;
    }
}
