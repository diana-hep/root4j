package org.dianahep.root4j.refactor;

import org.dianahep.root4j.*;
import org.dianahep.root4j.interfaces.*;
import java.util.*;
import java.io.*;

public class streamerArrange {

    static RootFileReader reader;

    streamerArrange(RootFileReader reader){
        this.reader = reader;
    }

    public Map<String,TStreamerInfo> arrangeStreamers()throws IOException{
        List<TStreamerInfo> lll = reader.streamerInfo();
        Map<String,TStreamerInfo> streamers = new HashMap();
        TStreamerInfo s;
        TStreamerInfo streamer;
        String temp1;
        for (int i=0;i<lll.size();i++){
            s=lll.get(i);
            streamers.put(s.getName(),(TStreamerInfo)s);
        }
        return streamers;
    }

    public TStreamerInfo iterate(int indx,TStreamerInfo s1,TStreamerInfo s2,Map<String,TStreamerInfo> streamers) {
        if (indx == s1.getElements().size()){
            return s1;
        }
        else if ((((TStreamerElement)s1.getElements().get(indx)).getTypeName()).equals((((TStreamerElement)s2.getElements().get(indx)).getTypeName()))){
            return iterate(indx+1,s1,s2,streamers);
        }
        else {
            TStreamerInfo tn1;
            try {
                tn1 = streamers.get(((TStreamerElement)(s1.getElements().get(indx))).getTypeName());
            }
            catch (ArrayIndexOutOfBoundsException e){
                tn1 = null;
            }
            TStreamerInfo tn2;
            try {
                tn2 = streamers.get(((TStreamerElement)(s2.getElements().get(indx))).getTypeName());
            }
            catch (ArrayIndexOutOfBoundsException e){
                tn2 = null;
            }
            if (tn1==null && tn2!=null){
                return s2;
            }
            else if (tn1!=null && tn2==null){
                return s1;
            }
            else if (tn1==null && tn2==null){
                return iterate(indx+1,s1,s2,streamers);
            }
            else {
                return s1;
            }
        }
    }

    public TStreamerInfo selectOne(TStreamerInfo s1,TStreamerInfo s2,Map<String,TStreamerInfo> streamers){
        if (s1.getElements().size()!=s2.getElements().size()){
            return s1;
        }
        else {
            return iterate(0,s1,s2,streamers);
        }
    }

    public Map<String,TStreamerInfo> returning()throws IOException{
        Map<String,TStreamerInfo> streamers = arrangeStreamers();
        int i=0;
        TStreamerInfo temp = null;
        for (Map.Entry<String, TStreamerInfo> entry : streamers.entrySet()) {
            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
            i++;
            if (i==1){
                streamers.put(entry.getKey(),entry.getValue());
            }
            else {
                streamers.put(entry.getKey(),selectOne(temp,entry.getValue(),streamers));
            }
            temp = entry.getValue();
        }
        return streamers;
    }
}
