package org.dianahep.root4j.refactor;

import org.dianahep.root4j.core.*;
import java.util.*;

public class SRUnknown extends SRType {
    static String name;

    SRUnknown(String name) {
        this.name=name;
    }

    @Override
    void read(RootInput b){
        return;
    }

    @Override
    void read(){
        return;
    }

     @Override
     void readArray(int size) {
         for (int i = 0; i < size; i++) {
             array.add(i, null);
         }
     }

     @Override void readArray(RootInput buffer, int size){
        for (int i=0;i<size;i++){
            array.add(i,null);
        }
     }

     @Override boolean hasNext(){
         return false;
     }
}
