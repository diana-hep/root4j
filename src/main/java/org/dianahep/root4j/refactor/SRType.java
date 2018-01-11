package org.dianahep.root4j.refactor;

import org.dianahep.root4j.core.*;
import java.io.*;
import org.apache.log4j.*;

public abstract class SRType{

    protected Logger logger = LogManager.getLogger("org.dianahep.root4j.refactor.TypeSystem");

    public static String name = "";

    public static long entry = 0L;

    public String toName() { return this.name.replace('.','_'); }

    public abstract void debugMe(String str) throws IOException;

    public abstract <T> T read(RootInput b) throws IOException;

    public abstract <T> T read()throws IOException;

    public boolean hasNext() {
        return false;
    };

    public abstract <T> T readArray(int size)throws IOException;

    public abstract <T> T readArray(RootInput buffer, int size)throws IOException;

    public SRType(String name){
        this.name=name;
    }

    public SRType(){
        this.name="";
    }

}
