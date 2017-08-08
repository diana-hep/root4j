package org.dianahep.root4j.refactor;

import org.dianahep.root4j.core.*;
import java.util.*;

public class SRUnknown extends SRType {

    static String name;

    public SRUnknown(String name) {
        super(name);
    }

    @Override public String read(RootInput b){
        return null;
    }

    @Override public String read(){
        return null;
    }

     @Override
     public List<String> readArray(int size) {
        List<String> temp = new ArrayList();
        for (int i=0;i<size;i++)
        {
            temp.add(null);
        }
        return temp;
     }

     @Override
     public List<String> readArray(RootInput buffer, int size){
        List<String> temp = new ArrayList();
        for (int i=0;i<size;i++){
            temp.add(null);
        }
        return temp;
     }

     @Override public boolean hasNext(){
         return false;
     }
}
