package org.dianahep.root4j.refactor;

import org.dianahep.root4j.RootClassNotFound;
import org.dianahep.root4j.interfaces.TDirectory;
import org.dianahep.root4j.interfaces.TKey;
import org.dianahep.root4j.interfaces.TTree;

import java.io.IOException;

public class Find {

    public TTree findTree(TDirectory dir,TTree name)throws RootClassNotFound,IOException {
        for (int i=0;i<dir.nKeys();i++){
            TKey key = dir.getKey(i);
            if (key.getObjectClass().getClassName().equals("TDirectory")){
                if (findTree((TDirectory)key.getObject(), name)!= null){
                    return (TTree)name;
                }
                else {
                    return (TTree)null;
                }
            }
            else if (key.getObjectClass().getClassName().equals("TTree")){
                if (name!=null){
                    if (name==(TTree)key.getObject()){
                        return (TTree)key.getObject();
                    }
                    else {
                        return null;
                    }
                }
                else {
                    return (TTree)key.getObject();
                }
            }
        }
        return null;
    }
}
