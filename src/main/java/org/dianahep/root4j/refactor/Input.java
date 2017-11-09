package org.dianahep.root4j.refactor;

import java.net.URL;
import org.dianahep.root4j.*;
import java.io.*;

public class Input {

    public static void main(String args[])throws IOException,RootClassNotFound{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter path of ROOT file");
        String input = br.readLine();
        URL path = new File(input).toURI().toURL();
        RootTableScan(path);
    }

    public static void RootTableScan(URL filepath)throws IOException,RootClassNotFound{
        RootFileReader reader = new RootFileReader(filepath);
        Find it = new Find();
        streamerArrange arrangestreamers = new streamerArrange(reader);
        if (it.findTree(reader.getTopDir(),null)==null){
            System.out.println("No TTree found");
            System.exit(0);
        }
        else {
            buildATT att = new buildATT(it.findTree(reader.getTopDir(),null),arrangestreamers.returning(),null);
        }
    }

}


