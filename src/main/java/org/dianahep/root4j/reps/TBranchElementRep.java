package org.dianahep.root4j.reps;

import org.dianahep.root4j.RootClass;
import org.dianahep.root4j.RootClassNotFound;
import org.dianahep.root4j.RootMember;
import org.dianahep.root4j.core.AbstractRootObject;
import org.dianahep.root4j.core.GenericRootClass;
import org.dianahep.root4j.core.HollowBuilder;
import org.dianahep.root4j.core.RootClassFactory;
import org.dianahep.root4j.core.RootInput;
import org.dianahep.root4j.interfaces.TLeaf;
import org.dianahep.root4j.interfaces.TLeafElement;
import org.dianahep.root4j.interfaces.TObjArray;

import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Field;


/**
 * @author Tony Johnson (tonyj@slac.stanford.edu)
 * @version $Id: TBranchElementRep.java 8584 2006-08-10 23:06:37Z duns $
 */
public abstract class TBranchElementRep extends AbstractRootObject implements org.dianahep.root4j.interfaces.TBranchElement
{
   private Class cloneClass;
   private RootInput rin;
   private int curIndex;
   private int fWriteBasket;
   private int[] cBasketBytes;
   private long[] cBasketEntry;
   private long[] cBasketSeek;
   
   private int[] saveIntSpace(String source, int size)
   {
      try
      {
         if (size == 0) return null;
         int[] result = new int[size];
         Field field = getClass().getDeclaredField(source);
         Object array = field.get(this);
         for (int i=0; i<size; i++) result[i] = Array.getInt(array,i);
         return result;
      }
      catch (Exception x)
      {
         throw new RuntimeException("Wierd error while compressing "+source,x);
      }
   }
   private long[] saveLongSpace(String source, int size)
   {
      try
      {
         if (size == 0) return null;
         long[] result = new long[size];
         Field field = getClass().getDeclaredField(source);
         Object array = field.get(this);
         for (int i=0; i<size; i++) result[i] = Array.getLong(array,i);
         return result;
      }
      catch (Exception x)
      {
         throw new RuntimeException("Wierd error while compressing "+source,x);
      }
   }
   
   /**
    * If this branch represents a (split) TClonesArray this will return
    * the class used to represent the elements of the array.
    */
   public Class getCloneClass()
   {
      try
      {
         if (cloneClass == null)
         {
            HollowBuilder builder = new HollowBuilder(this, "fTracks.", true);
            String name = "org.dianahep.root4j.hollow." + getClonesName();
            RootClassFactory factory = rin.getFactory();
            GenericRootClass gc = (GenericRootClass) factory.create(getClonesName());
            cloneClass = factory.getLoader().loadSpecial(builder, name, gc);
            
            // Populate the leafs.
            builder.populateStatics(cloneClass, factory);
         }
         return cloneClass;
      }
      catch (RootClassNotFound x)
      {
         throw new RuntimeException("Error looking up class for TBranchClones " + x.getClassName(),x);
      }
   }
   
   public void read(RootInput in) throws IOException
   {
      super.read(in);
      rin = in.getTop();
      
      // Clean-up unnecessarily large arrays
      cBasketBytes = saveIntSpace("fBasketBytes",fWriteBasket);
      cBasketEntry = saveLongSpace("fBasketEntry",fWriteBasket+1);
      cBasketSeek = saveLongSpace("fBasketSeek",fWriteBasket);
      
      // The leaves need to know which branch they are on
      TObjArray leaves = getLeaves();
      if (leaves != null)
      {
         for (int i = 0; i < leaves.size(); i++)
         {
            TLeaf leaf = (TLeaf) leaves.get(i);
            leaf.setBranch(this);
         }
      }
      curIndex = -1;
      
      String className = getClassName();
      int fId = getID();
      
      try
      {
         RootClass rc = in.getFactory().create(className);
         RootClass[] sup = rc.getSuperClasses();
         fId -= sup.length;
         if (fId >= 0)
         {
            RootMember[] members = rc.getMembers();
            // some CMSSW files have fId >= length of members
            // what does that mean???
            if (fId < members.length)
            {
               RootMember member = members[fId];
               if (leaves != null)
               {
                  for (int i = 0; i < leaves.size(); i++)
                  {
                     TLeafElement leaf = (TLeafElement) leaves.get(i);
                     leaf.setMember(member);
                  }
               }
            }
         }
      }
      catch (RootClassNotFound x)
      {
         IOException io = new IOException("Could not find root class " + className);
         io.initCause(x);
         throw io;
      }
   }
}
