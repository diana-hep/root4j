package org.dianahep.root4j.core;

import org.dianahep.root4j.RootClass;
import org.dianahep.root4j.RootObject;

/**
 * A base class for a member of a ClonesArray read in split mode
 * @author Tony Johnson
 */
public abstract class Clone implements RootObject, org.dianahep.root4j.interfaces.TObject
{
   public abstract void setData(int index, Clones clones);

   public int getBits()
   {
      return 0;
   }

   public RootClass getRootClass()
   {
      Class klass = getClass();
      RootClassLoader loader = (RootClassLoader) klass.getClassLoader();
      return loader.getRootClass(klass);
   }

   public int getUniqueID()
   {
      return 0;
   }
}
