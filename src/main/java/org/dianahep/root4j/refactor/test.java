package org.dianahep.root4j.refactor;

import org.dianahep.root4j.RootClassNotFound;
import org.dianahep.root4j.RootFileReader;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.PathFilter;
import java.io.*;
import java.util.*;
import java.util.Arrays;
import java.util.stream.Stream;

public class test {

//Trying to integrate HDFS into the program to load local files

    public static void main(String[] args)throws IOException,RootClassNotFound{
        Configuration conf = new Configuration();
        conf.addResource("/etc/hadoop/conf/core-site.xml");
        conf.addResource("/etc/hadoop/conf/hdfs-site.xml");
        Path hPath = new Path("test_root4j.root");
        FileSystem fs = hPath.getFileSystem(conf);
        PathFilter pathFilter = new PathFilter(){
            public boolean accept(Path ppp){
                return ppp.getName().endsWith(".root");
            }
        };
        List<Path> inputPathFiles = new ArrayList();
        List<Path> inputPathFilestemp = iterate(hPath,fs);
        for (int i=0;i<inputPathFilestemp.size();i++){
            if (pathFilter.accept(inputPathFilestemp.get(i))){
                inputPathFiles.add(inputPathFilestemp.get(i));
            }
        }
        String topass = inputPathFiles.get(0).toString();
        RootFileReader reader = new RootFileReader(topass);
        Find it = new Find();
        streamerArrange streamerarrange = new streamerArrange(reader);
        //if (treeName.equals(null)){
        buildATT att = new buildATT(it.findTree(reader.getTopDir()),streamerarrange.returning(),null);
        //}
        ///else {
        //    buildATT att = new buildATT((TTree)reader.getKey(treeName).getObject(),streamerarrange.returning(),null);
        //}
        SRType attre = att.recolumns();
        printATT printer = new printATT(attre);
        printer.toPrint(attre,0,"");
    }

    public static List<Path> iterate(Path p,FileSystem fs)throws IOException{
        if (fs.isDirectory(p)){
            FileStatus f[] = fs.listStatus(p);
            Stream<FileStatus> f1 = Arrays.stream(f);
            return null;
            //return f1.flatMap(x -> iterate(x.getPath(),fs));
        }
        else {
            List<Path> toreturn = new ArrayList();
            toreturn.add(p);
            return toreturn;
        }
    }

}