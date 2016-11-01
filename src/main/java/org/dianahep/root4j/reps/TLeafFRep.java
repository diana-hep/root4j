package org.dianahep.root4j.reps;

import org.dianahep.root4j.core.AbstractRootObject;
import org.dianahep.root4j.core.RootInput;
import org.dianahep.root4j.interfaces.TBranch;
import org.dianahep.root4j.interfaces.TLeafF;
import org.dianahep.root4j.interfaces.TLeafI;

import java.io.IOException;

import org.apache.bcel.Constants;
import org.apache.bcel.generic.ConstantPoolGen;
import org.apache.bcel.generic.InstructionFactory;
import org.apache.bcel.generic.InstructionList;
import org.apache.bcel.generic.Type;


/**
 * @author Tony Johnson
 * @version $Id: TLeafFRep.java 8584 2006-08-10 23:06:37Z duns $
 */
public abstract class TLeafFRep extends AbstractRootObject implements TLeafF, Constants
{
   private Object lastValue;
   private TBranch branch;
   private float lastFloat;
   private long lastFloatIndex;
   private long lastValueIndex;

   public void setBranch(TBranch branch)
   {
      this.branch = branch;
      lastValueIndex = -1;
      lastFloatIndex = -1;
   }

   public float getValue(long index) throws IOException
   {
      try
      {
         if (index == lastFloatIndex)
            return lastFloat;

         RootInput in = branch.setPosition(this, lastFloatIndex = index);
         return lastFloat = in.readFloat();
      }
      catch (IOException x)
      {
         lastFloatIndex = -1;
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
            return lastValue = new Float(in.readFloat());
         else if (arrayDim == 1)
         {
            TLeafI count = (TLeafI) getLeafCount();
            int len = (count == null) ? getLen() : count.getValue(index);
            float[] array = new float[len];
            in.readFixedArray(array);
            return lastValue = array;
         }
         else
         {
            return lastValue = readMultiArray(in, Float.TYPE, index);
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
         il.append(factory.createInvoke(leafClassName, "getValue", Type.FLOAT, new Type[]
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
