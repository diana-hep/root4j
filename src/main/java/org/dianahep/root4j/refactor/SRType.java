package org.dianahep.root4j.refactor;

import org.dianahep.root4j.core.*;
import java.io.*;
import org.apache.log4j.*;

public abstract class SRType{

    protected Logger logger = LogManager.getLogger("org.dianahep.root4j.refactor.TypeSystem");

    static String name = "";

    static long entry = 0L;

    String toName() { return this.name.replace('.','_'); }

    abstract void debugMe(String str) throws IOException;

    abstract <T> T read(RootInput b) throws IOException;

    abstract <T> T read()throws IOException;

    boolean hasNext() {
        return false;
    };

    abstract <T> T readArray(int size)throws IOException;

    abstract <T> T readArray(RootInput buffer, int size)throws IOException;

    SRType(String name){
        this.name=name;
    }

    SRType(){
        this.name="";
    }

}
