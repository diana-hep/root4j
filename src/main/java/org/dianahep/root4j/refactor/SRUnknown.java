package org.dianahep.root4j.refactor;

import org.dianahep.root4j.core.*;
import java.util.*;

public class SRUnknown extends SRType {

    static String name;

    public SRUnknown(String name) {
        super(name);
    }

    @Override public void debugMe(String str) {
        logger.debug("SRUnknown:: "+name+" "+str);
    }

    @Override public String read(RootInput b){
        debugMe("read(buffer)");
        return null;
    }

    @Override public String read(){
        debugMe("read");
        return null;
    }

     @Override
     public List<String> readArray(int size) {
        debugMe("readArray("+size+")");
        List<String> temp = new ArrayList();
        for (int i=0;i<size;i++)
        {
            temp.add(null);
        }
        return temp;
     }

     @Override
     public List<String> readArray(RootInput buffer, int size){
        debugMe("readArray(buffer, "+size+")");
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
