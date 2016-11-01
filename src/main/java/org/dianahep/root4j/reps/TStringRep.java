package org.dianahep.root4j.reps;

import org.dianahep.root4j.core.AbstractRootObject;
import org.dianahep.root4j.core.RootInput;

import java.io.IOException;


/**
 * @author Tony Johnson (tonyj@slac.stanford.edu)
 * @version $Id: TStringRep.java 8584 2006-08-10 23:06:37Z duns $
 */
public abstract class TStringRep extends AbstractRootObject implements org.dianahep.root4j.interfaces.TString
{
   private String string;

   public void readMembers(RootInput in) throws IOException
   {
      int l = in.readUnsignedByte();
      if (l == 255)
         l = in.readInt();

      byte[] chars = new byte[l];
      for (int i = 0; i < l; i++)
         chars[i] = in.readByte();
      string = new String(chars);
   }

   public String toString()
   {
      return string;
   }
}
