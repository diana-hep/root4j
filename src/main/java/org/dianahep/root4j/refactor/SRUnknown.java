package org.dianahep.root4j.refactor;

import org.dianahep.root4j.core.*;
import java.util.*;

public class SRUnknown extends SRType {
    static String name;

    SRUnknown(String name) {
        super(name);
    }

    @Override
    void read(RootInput b){
        array.add((int)entry,null);
    }

    @Override
    void read(){
        array.add((int)entry,null);
    }

     @Override
     void readArray(int size) {
        array.add((int)entry,null);
     }

     @Override void readArray(RootInput buffer, int size){
        array.add((int)entry,null);
     }

     @Override boolean hasNext(){
         return false;
     }
}
