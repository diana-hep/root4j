package hep.io.root;

import static org.junit.Assert.*;
import org.junit.Test;

import hep.io.root.core.NameMangler;
import hep.io.root.core.TListIterator;

public class TestReading {
    @Test
    public void testHello() {
        System.out.println(NameMangler.instance().mangleInterfaceName("what.ever"));
        
        TListIterator tmp = new TListIterator(new String[]{"one", "two"}, 2, 0);

        assertEquals("hi", "hi");
    }
}
