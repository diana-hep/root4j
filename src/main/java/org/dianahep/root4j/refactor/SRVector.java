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

    @Override
    boolean hasNext(){
        return entry<b.getEntries();
    }

    List<List<Vector>> transpose(List<List<Vector>> table) {
        List<List<Vector>> ret = new ArrayList();
        final int N = table.get(0).size();
        for (int i = 0; i < N; i++) {
            List<Vector> col = new ArrayList();
            for (List<Vector> row : table) {
                col.add(row.get(i));
            }
            ret.add(col);
        }
        return ret;
    }

    List<List<Vector>> readArray(RootInput buffer,int size)throws IOException{
        entry+=1L;
        SRNull srnull = new SRNull();
        List<List<Vector>> temp = new ArrayList();
        List<Vector> temp1 = new ArrayList();
        List<List<Vector>> temp2 = new ArrayList();
        if (split)
        {
            return null;
        }
        else {
            int byteCount = buffer.readInt();
            short version = buffer.readShort();
            if (version >0 && kMemberWiseStreaming > 0){
                if (t.equals(srnull) || t instanceof SRUnknown){
                    for (int i=0;i<size;i++){
                        temp.add(temp1);
                    }
                    return temp;
                }
                else {
                    short memberVersion = buffer.readShort();
                    if (memberVersion == 0){
                        buffer.readInt();
                    }
                    SRComposite composite = (SRComposite)t;
                    int nn=0;
                    for (int i=0;i<size;i++){
                        nn = buffer.readInt();
                        if (nn==0){
                            temp.add(temp1);
                        }
                        else{
                            for (SRType x : composite.members){
                                temp2.add(x.readArray(buffer,nn));
                            }
                        }
                    }
                    temp.add(temp2);
                    return temp;
                }
            }
        }
    }
}
