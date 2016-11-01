package org.dianahep.root4j.reps;

import org.dianahep.root4j.core.AbstractRootObject;
import org.dianahep.root4j.core.RootInput;
import org.dianahep.root4j.core.RootRandomAccessFile;
import org.dianahep.root4j.interfaces.TDirectory;
import org.dianahep.root4j.interfaces.TKey;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * @author Tony Johnson (tonyj@slac.stanford.edu)
 * @version $Id: TDirectoryRep.java 12934 2007-07-05 18:46:15Z tonyj $
 */
public abstract class TDirectoryRep extends AbstractRootObject implements TDirectory
{
   private java.util.Date fDatimeC;
   private java.util.Date fDatimeM;
   private Map hash;
   private Map hashT;
   private RootRandomAccessFile raf;
   private String fName;
   private String fTitle;
   private TKey fHeader;
   private TKey[] keys;
   private int fNbytes;
   private int fNbytesKeys;
   private int fNbytesName;
   private int fNkeys;
   private long fSeekDir;
   private long fSeekKeys;
   private long fSeekParent;

   public int getBits()
   {
      return 0;
   }

   public Date getDatimeC()
   {
      return fDatimeC;
   }

   public Date getDatimeM()
   {
      return fDatimeM;
   }

   public TKey getKey(int index)
   {
      return keys[index];
   }

   public TKey getKey(String name)
   {
      TKey key = (TKey) hash.get(name);
      if (key != null)
         return key;
      throw new RuntimeException("Key " + name + " not found in " + fHeader.getName());
   }
   
   public boolean hasKey(String name)
   {
      return hash.containsKey(name);
   }

   public TKey getKey(String name, int cycle)
   {
      TKey key = (TKey) hash.get(name + ";" + cycle);
      if (key != null)
         return key;
      throw new RuntimeException("Key " + name + ";" + cycle + " not found in " + fHeader.getName());
   }
   
   public boolean hasKey(String name, int cycle)
   {
      return hash.containsKey(name + ";" + cycle);
   }

   public TKey getKeyForTitle(String title)
   {
      TKey key = (TKey) hashT.get(title);
      if (key != null)
         return key;
      throw new RuntimeException("Key titled " + title + " not found in " + fHeader.getTitle());
   }

   public String getName()
   {
      return fName;
   }

   public int getNbytesKeys()
   {
      return fNbytesKeys;
   }

   public int getNbytesName()
   {
      return fNbytesName;
   }

   public long getSeekDir()
   {
      return fSeekDir;
   }

   public long getSeekKeys()
   {
      return fSeekKeys;
   }

   public long getSeekParent()
   {
      return fSeekParent;
   }

   public String getTitle()
   {
      return fTitle;
   }

   public int getUniqueID()
   {
      return 0;
   }

   public int nKeys()
   {
      return keys.length;
   }

   public void readMembers(RootInput in) throws IOException
   {
      int v = in.readVersion();
      fDatimeC = ((org.dianahep.root4j.interfaces.TDatime) in.readObject("TDatime")).getDate();
      fDatimeM = ((org.dianahep.root4j.interfaces.TDatime) in.readObject("TDatime")).getDate();

      fNbytesKeys = in.readInt();
      fNbytesName = in.readInt();

      if (v > 1000)
      {
         fSeekDir = in.readLong();
         fSeekParent = in.readLong();
         fSeekKeys = in.readLong();   
      }
      else
      {
         fSeekDir = in.readInt();
         fSeekParent = in.readInt();
         fSeekKeys = in.readInt();     
      }

      in.clearMap();
      in.setPosition(fSeekKeys);

      hash = new HashMap();
      hashT = new HashMap();

      fHeader = (org.dianahep.root4j.interfaces.TKey) in.readObject("TKey");
      fNkeys = in.readInt();
      keys = new TKey[fNkeys];
      for (int i = 0; i < fNkeys; i++)
      {
         keys[i] = (org.dianahep.root4j.interfaces.TKey) in.readObject("TKey");
         hash.put(keys[i].getName() + ";" + keys[i].getCycle(), keys[i]);

         TKey prevKey = (TKey) hash.get(keys[i].getName());
         if ((prevKey == null) || (prevKey.getCycle() < keys[i].getCycle()))
         {
            hash.put(keys[i].getName(), keys[i]);
            hashT.put(keys[i].getTitle(), keys[i]);
         }
      }
   }
}
