package org.dianahep.root4j.refactor;

import org.dianahep.root4j.core.*;
import java.util.*;
import java.io.*;

public abstract class SRType<T>{
    String name;
    long entry = 0L;
    String toName = name.replace('.','_');

    void read(RootInput b) throws IOException{};

    void read()throws IOException{};

    boolean hasNext() {
        return false;
    };

    List<T> readArray(int size)throws IOException{};

    List<T> readArray(RootInput buffer, int size)throws IOException{};

    SRType(String name){
        this.name=name;
    }

    SRType(){
        this.name=null;
    }

}
