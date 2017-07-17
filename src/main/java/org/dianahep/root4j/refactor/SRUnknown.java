package org.dianahep.root4j.refactor;

import org.dianahep.root4j.core.*;
import java.util.*;

public class SRUnknown extends SRType {
    static String name;

    SRUnknown(String name) {
        super(name);
    }

    @Override String read(RootInput b){
        return null;
    }

    @Override String read(){
        return null;
    }

     @Override
     List<String> readArray(int size) {
        List<String> temp = new ArrayList();
        for (int i=0;i<size;i++)
        {
            temp.add(null);
        }
        return temp;
     }

     @Override
     List<String> readArray(RootInput buffer, int size){
        List<String> temp = new ArrayList();
        for (int i=0;i<size;i++){
            temp.add(null);
        }
        return temp;
     }

     @Override boolean hasNext(){
         return false;
     }
}
