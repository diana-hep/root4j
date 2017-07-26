package org.dianahep.root4j.refactor;

import org.dianahep.root4j.core.*;
import java.io.*;

public abstract class SRType<T>{

    static String name;
    static long entry = 0L;
    static String toName = name.replace('.','_');

    abstract T read(RootInput b) throws IOException;

    abstract T read()throws IOException;

    boolean hasNext() {
        return false;
    };

    abstract T readArray(int size)throws IOException;

    abstract T readArray(RootInput buffer, int size)throws IOException;

    SRType(String name){
        this.name=name;
    }

    SRType(){
        this.name=null;
    }

}
