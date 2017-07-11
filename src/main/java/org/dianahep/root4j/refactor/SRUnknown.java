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
        array.add(null);
    }

    @Override
    void read(){
        array.add(null);
    }

     @Override
     void readArray(int size) {
        List<String> temp = new ArrayList();
        for (int i=0;i<size;i++)
        {
            temp.add(null);
        }
        array.add(temp);
     }

     @Override void readArray(RootInput buffer, int size){
        List<String> temp = new ArrayList();
        for (int i=0;i<size;i++){
            temp.add(null);
        }
        array.add(temp);
     }

     @Override boolean hasNext(){
         return false;
     }
}
