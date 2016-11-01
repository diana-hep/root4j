package org.dianahep.root4j.core;

import org.dianahep.root4j.RootClassNotFound;

/**
 * Creates RootClass objects
 * @author Tony Johnson (tonyj@slac.stanford.edu)
 * @version $Id: RootClassFactory.java 13617 2009-04-09 22:48:46Z tonyj $
 */
public interface RootClassFactory
{
   RootClassLoader getLoader();

   BasicRootClass create(String name) throws RootClassNotFound;
}
