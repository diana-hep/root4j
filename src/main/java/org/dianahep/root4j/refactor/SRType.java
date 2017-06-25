package org.dianahep.root4j.refactor;

import org.dianahep.root4j.core.*;
import java.util.*;

/**
 * Created by root on 25/6/17.
 */
public abstract class SRType<T extends RootInput>{
    String name;
    void read(RootInput b){};
    void read(){};
    boolean hasNext(){};
    long entry = 0L;
    String toName = name.replace('.','_');
    ArrayList<T> readArray(int size){};
    ArrayList<T> readArray(RootInput buffer, int size){};

    SRType(String name){
        this.name=name;
    }

    SRType(){
        this.name=null;
    }
}
