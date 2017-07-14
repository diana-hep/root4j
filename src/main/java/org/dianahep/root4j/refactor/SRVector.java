package org.dianahep.root4j.refactor;

import org.dianahep.root4j.core.*;
import org.dianahep.root4j.interfaces.*;
import java.util.*;
import java.io.*;

public class SRVector extends SRCollection{
    String name;
    TBranchElement b;
    SRType t;
    boolean split;
    boolean isTop;

    SRVector(String name,TBranchElement b,SRType t,boolean split,boolean isTop){
        super(name,isTop);
        this.b=b;
        this.t=t;
        this.split=split;
    }

    List<Vector> readArray(RootInput buffer,int size)throws IOException{
        if (split)
        {
            return null;
        }
        else {
            int byteCount = buffer.readInt();
            short version = buffer.readShort();
            if (version >0 && kMemberWiseStreaming > 0){

            }
        }
    }
}
