package org.dianahep.root4j.core;

import org.dianahep.root4j.RootClass;
import org.dianahep.root4j.RootObject;


public abstract class Hollow implements RootObject, org.dianahep.root4j.interfaces.TObject
{
   protected long index;
   protected int subIndex; // implemented as array, only int supported?

   public void setHollowIndex(long index)
   {
      this.index = index;
   }

   public RootClass getRootClass()
   {
      Class klass = getClass();
      RootClassLoader loader = (RootClassLoader) klass.getClassLoader();
      return loader.getRootClass(klass);
   }

   public void setSubIndex(long index)
   {
      this.subIndex = (int) index;
   }
}
