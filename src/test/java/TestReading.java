package hep.io.root;

import java.util.List;

import static org.junit.Assert.*;
import org.junit.Test;

import hep.io.root.*;
import hep.io.root.interfaces.*;

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
    public void testBacon() throws java.io.IOException, RootClassNotFound {
        RootFileReader reader = new RootFileReader("/home/pivarski/data/TTJets_13TeV_amcatnloFXFX_pythia8_2_77.root");
        TTree tree = (TTree)reader.get("Events");
        List leaves = (List)tree.getLeaves();
        for (Object leaf : leaves)
            System.out.println(((TLeaf)leaf).getName());
    }

    // @Test
    // public void testAOD() throws java.io.IOException, RootClassNotFound {
    //     RootFileReader reader = new RootFileReader("/home/pivarski/data/Mu_Run2010B-Apr21ReReco-v1_AOD.root");
    //     TTree tree = (TTree)reader.get("Events");
    //     List leaves = (List)tree.getLeaves();
    //     for (Object leaf : leaves)
    //         System.out.println(((TLeaf)leaf).getName());
    // }

}
