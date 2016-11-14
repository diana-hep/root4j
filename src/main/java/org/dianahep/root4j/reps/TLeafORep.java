package org.dianahep.root4j.reps;

import org.dianahep.root4j.core.AbstractRootObject;
import org.dianahep.root4j.core.RootInput;
import org.dianahep.root4j.interfaces.TBranch;
import org.dianahep.root4j.interfaces.TLeafI;
import org.dianahep.root4j.interfaces.TLeafO;

import java.io.IOException;

import org.apache.bcel.Constants;
import org.apache.bcel.generic.ConstantPoolGen;
import org.apache.bcel.generic.InstructionFactory;
import org.apache.bcel.generic.InstructionList;
import org.apache.bcel.generic.Type;


/**
 * Class to represent Boolean data. 
 * All Booleans are internally represented as Byte!
 *
 * @author Tony Johnson
 * @version $Id: TLeafORep.java 10712 2007-04-25 21:42:28Z tonyj $
 */
public abstract class TLeafORep extends AbstractRootObject implements TLeafO, Constants
{
   private Object lastValue;
   private TBranch branch;
   private boolean lastBoolean;
   private long lastBooleanIndex;
   private long lastValueIndex;

   public void setBranch(TBranch branch)
   {
      this.branch = branch;
      lastValueIndex = -1;
      lastBooleanIndex = -1;
   }

   public boolean getValue(long index) throws IOException
   {
      try
      {
         if (index == lastBooleanIndex)
            return lastBoolean;

         RootInput in = branch.setPosition(this, lastBooleanIndex = index);
         return lastBoolean = in.readBoolean();
      }
      catch (IOException x)
      {
         lastBooleanIndex = -1;
         throw x;
      }
   }

   public Object getWrappedValue(long index) throws IOException
   {
      try
      {
         if (index == lastValueIndex)
            return lastValue;
         lastValueIndex = index;

         RootInput in = branch.setPosition(this, index);
         int arrayDim = getArrayDim();
         if (arrayDim == 0)
            return lastValue = in.readBoolean();
         else if (arrayDim == 1)
         {
            TLeafI count = (TLeafI) getLeafCount();
            int len = (count == null) ? getLen() : count.getValue(index);
            boolean[] array = new boolean[len];
            in.readFixedArray(array);
            return lastValue = array;
         }
         else
         {
            return lastValue = readMultiArray(in, Boolean.TYPE, index);
         }
      }
      catch (IOException x)
      {
         lastValueIndex = -1;
         throw x;
      }
   }

   public void generateReadCode(InstructionList il, InstructionFactory factory, ConstantPoolGen cp, String className)
   {
      String leafClassName = getClass().getName();
      int arrayDim = getArrayDim();
      if (arrayDim == 0)
         il.append(factory.createInvoke(leafClassName, "getValue", Type.BOOLEAN, new Type[]
               {
                  Type.LONG
               }, INVOKEVIRTUAL));
      else
         il.append(factory.createInvoke(leafClassName, "getWrappedValue", Type.OBJECT, new Type[]
               {
                  Type.LONG
               }, INVOKEVIRTUAL));
   }

   abstract Object[] readMultiArray(RootInput in, Class type, long index);
}
