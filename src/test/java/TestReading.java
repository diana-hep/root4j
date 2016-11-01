package org.dianahep.root4j;

import java.util.List;

import static org.junit.Assert.*;
import org.junit.Test;

import org.dianahep.root4j.core.RootInput;
import org.dianahep.root4j.*;
import org.dianahep.root4j.interfaces.*;

public class TestReading {
    @Test
    public void testVerySimple() throws java.io.IOException, RootClassNotFound {
        RootFileReader reader = new RootFileReader("src/test/resources/verysimple.root");
        TTree tree = (TTree)reader.get("ntuple");
        List leaves = (List)tree.getLeaves();

        TLeafF leaf0 = (TLeafF)leaves.get(0);
        TLeafF leaf1 = (TLeafF)leaves.get(1);
        TLeafF leaf2 = (TLeafF)leaves.get(2);

        assertEquals(leaf0.getName(), "x");
        assertEquals(leaf1.getName(), "y");
        assertEquals(leaf2.getName(), "z");

        assertEquals(leaf0.getValue(0), 1.0, 1e-6);
        assertEquals(leaf1.getValue(0), 2.0, 1e-6);
        assertEquals(leaf2.getValue(0), 3.0, 1e-6);

        assertEquals(leaf0.getValue(1), 4.0, 1e-6);
        assertEquals(leaf1.getValue(1), 5.0, 1e-6);
        assertEquals(leaf2.getValue(1), 6.0, 1e-6);

        assertEquals(leaf0.getValue(2), 7.0, 1e-6);
        assertEquals(leaf1.getValue(2), 8.0, 1e-6);
        assertEquals(leaf2.getValue(2), 9.0, 1e-6);

        assertEquals(leaf0.getValue(3), 10.0, 1e-6);
        assertEquals(leaf1.getValue(3), 11.0, 1e-6);
        assertEquals(leaf2.getValue(3), 12.0, 1e-6);

        assertEquals(leaf0.getValue(4), 1.0, 1e-6);
        assertEquals(leaf1.getValue(4), 2.0, 1e-6);
        assertEquals(leaf2.getValue(4), 3.0, 1e-6);
    }

    @Test
    public void testSimple() throws java.io.IOException, RootClassNotFound {
        RootFileReader reader = new RootFileReader("src/test/resources/simple.root");
        TTree tree = (TTree)reader.get("tree");
        List leaves = (List)tree.getLeaves();

        TLeafI leaf0 = (TLeafI)leaves.get(0);
        TLeafF leaf1 = (TLeafF)leaves.get(1);
        TLeafC leaf2 = (TLeafC)leaves.get(2);

        assertEquals(leaf0.getName(), "one");
        assertEquals(leaf1.getName(), "two");
        assertEquals(leaf2.getName(), "three");

        assertEquals(leaf0.getValue(0), 1);
        assertEquals(leaf1.getValue(0), 1.1, 1e-6);
        assertEquals(leaf2.getValue(0), "uno");

        assertEquals(leaf0.getValue(1), 2);
        assertEquals(leaf1.getValue(1), 2.2, 1e-6);
        assertEquals(leaf2.getValue(1), "dos");

        assertEquals(leaf0.getValue(2), 3);
        assertEquals(leaf1.getValue(2), 3.3, 1e-6);
        assertEquals(leaf2.getValue(2), "tres");

        assertEquals(leaf0.getValue(3), 4);
        assertEquals(leaf1.getValue(3), 4.4, 1e-6);
        assertEquals(leaf2.getValue(3), "quatro");
    }

    @Test
    public void testBacon() throws java.io.IOException, RootClassNotFound, NoSuchMethodException, IllegalAccessException, java.lang.reflect.InvocationTargetException {
        java.io.File file = new java.io.File("/home/pivarski/data/TTJets_13TeV_amcatnloFXFX_pythia8_2_77.root");
        if (file.exists()) {
            // gzip 0: 0.07068413925
            // gzip 1: 0.07774616925
            // gzip 2: 0.0736906025
            // gzip 9: 0.0706459315

            double total = 0.0;

            RootFileReader reader = new RootFileReader(file);
            TTree tree = (TTree)reader.get("Events");
            // List leaves = (List)tree.getLeaves();
        
            TBranch branch = tree.getBranch("Muon").getBranchForName("pt");
            TLeaf leaf = (TLeaf)branch.getLeaves().get(0);

            long[] startingEntries = branch.getBasketEntry();

            for (int i = 0;  i < startingEntries.length - 1;  i++) {
                // System.out.println(String.format("BASKET %d", i));

                long endEntry = startingEntries[i + 1];

                // all but the last one
                for (long entry = startingEntries[i];  entry < endEntry - 1;  entry++) {
                    // System.out.println(String.format("entry %d endEntry %d", entry, endEntry));

                    RootInput in = branch.setPosition(leaf, entry + 1);
                    long endPosition = in.getPosition();
                    in = branch.setPosition(leaf, entry);
                    while (in.getPosition() < endPosition) {
                        total += in.readFloat();
                        // System.out.print(in.readFloat());
                        // System.out.print(" ");
                    }
                    // System.out.println();
                }

                // the last one
                RootInput in = branch.setPosition(leaf, endEntry - 1);
                long endPosition = in.getLast();
                while (in.getPosition() < endPosition) {
                    total += in.readFloat();
                    // System.out.print(in.readFloat());
                    // System.out.print(" ");
                }
                // System.out.println();
            }

            assertEquals(total, 1212110.9802200794, 1e-12);
        }
    }

    @Test
    public void testAOD() throws java.io.IOException, RootClassNotFound {
        java.io.File file = new java.io.File("/home/pivarski/data/Mu_Run2010B-Apr21ReReco-v1_AOD.root");
        if (file.exists()) {
            double total = 0.0;

            RootFileReader reader = new RootFileReader(file);
            TTree tree = (TTree)reader.get("Events");
            // List leaves = (List)tree.getLeaves();
            // for (Object leaf : leaves)
            //     System.out.println(((TLeaf)leaf).getName());

            TBranch branch = tree.getBranch("recoMuons_muons__RECO.").getBranchForName("obj").getBranchForName("pt_");
            TLeaf leaf = (TLeaf)branch.getLeaves().get(0);

            long[] startingEntries = branch.getBasketEntry();

            for (int i = 0;  i < startingEntries.length - 1;  i++) {
                // System.out.println(String.format("BASKET %d", i));

                long endEntry = startingEntries[i + 1];

                // all but the last one
                for (long entry = startingEntries[i];  entry < endEntry - 1;  entry++) {
                    // System.out.println(String.format("entry %d endEntry %d", entry, endEntry));

                    RootInput in = branch.setPosition(leaf, entry + 1);
                    long endPosition = in.getPosition();
                    in = branch.setPosition(leaf, entry);
                    while (in.getPosition() < endPosition) {
                        total += in.readFloat();
                        // System.out.print(in.readFloat());
                        // System.out.print(" ");
                    }
                    // System.out.println();
                }

                // the last one
                RootInput in = branch.setPosition(leaf, endEntry - 1);
                long endPosition = in.getLast();
                while (in.getPosition() < endPosition) {
                    total += in.readFloat();
                    // System.out.print(in.readFloat());
                    // System.out.print(" ");
                }
                // System.out.println();
            }

            assertEquals(total, 149084.45634351671, 1e-12);
        }
    }

}
