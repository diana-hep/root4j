package org.dianahep.root4j.refactor;

import org.dianahep.root4j.RootClassNotFound;
import org.dianahep.root4j.interfaces.TDirectory;
import org.dianahep.root4j.interfaces.TKey;
import org.dianahep.root4j.interfaces.TTree;

import java.io.IOException;

public class Find {

    public TTree findTree(TDirectory dir)throws RootClassNotFound,IOException {
        for (int i=0;i<dir.nKeys();i++){
            TKey key = dir.getKey(i);
            if (key.getObjectClass().getClassName().equals("TDirectory")){
                return findTree((TDirectory)key.getObject());
            }
            else if (key.getObjectClass().getClassName().equals("TTree")){
                return (TTree)key.getObject();
            }
        }
        return null;
    }
}
