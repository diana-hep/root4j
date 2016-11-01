package org.dianahep.root4j.core;

import org.dianahep.root4j.RootFileReader;
import org.dianahep.root4j.RootObject;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Hashtable;

/**
 * @author Tony Johnson (tonyj@slac.stanford.edu)
 * @version $Id
 */
public class RootRandomAccessFile extends RandomAccessFile implements RootInput
{
   private Hashtable map = new Hashtable();
   private RootFileReader reader;
   private long offset;
   private int last;

   public RootRandomAccessFile(File file, RootFileReader reader) throws IOException
   {
      super(file, "r");
      this.reader = reader;

      // Read the root header
      if ((readByte() != 'r') || (readByte() != 'o') || (readByte() != 'o') || (readByte() != 't'))
         throw new IOException("Not a root file: " + file);
   }

   public RootClassFactory getFactory()
   {
      return reader.getFactory();
   }

   public void setMap(int keylen) throws IOException
   {
      offset = getFilePointer() - keylen;
   }

   public void setPosition(long pos) throws IOException
   {
      seek(pos + offset);
   }

   public long getPosition() throws IOException
   {
      return getFilePointer() - offset;
   }

   public int getRootVersion()
   {
      return reader.getVersion();
   }

   public RootInput getTop()
   {
      return this;
   }

   public void checkLength(AbstractRootObject obj) throws IOException
   {
      RootInputStream.checkLength(this, obj);
   }

   public void clearMap()
   {
      map.clear();
      offset = 0;
   }

   public int[] readVarWidthArrayInt() throws IOException
   {
      return RootInputStream.readVarWidthArrayInt(this);
   }

   public byte[] readVarWidthArrayByte() throws IOException
   {
      return RootInputStream.readVarWidthArrayByte(this);
   }

   public short[] readVarWidthArrayShort() throws IOException
   {
      return RootInputStream.readVarWidthArrayShort(this);
   }

   public float[] readVarWidthArrayFloat() throws IOException
   {
      return RootInputStream.readVarWidthArrayFloat(this);
   }

   public double[] readVarWidthArrayDouble() throws IOException
   {
      return RootInputStream.readVarWidthArrayDouble(this);
   }

   public int readArray(int[] data) throws IOException
   {
      return RootInputStream.readArray(this, data);
   }

   public int readArray(byte[] data) throws IOException
   {
      return RootInputStream.readArray(this, data);
   }

   public int readArray(short[] data) throws IOException
   {
      return RootInputStream.readArray(this, data);
   }

   public int readArray(float[] data) throws IOException
   {
      return RootInputStream.readArray(this, data);
   }

   public int readArray(double[] data) throws IOException
   {
      return RootInputStream.readArray(this, data);
   }

   public void readFixedArray(int[] data) throws IOException
   {
      RootInputStream.readFixedArray(this, data);
   }
   
   public void readFixedArray(long[] data) throws IOException
   {
      RootInputStream.readFixedArray(this, data);
   }
   
   public void readFixedArray(byte[] data) throws IOException
   {
      read(data);
   }

   public void readFixedArray(short[] data) throws IOException
   {
      RootInputStream.readFixedArray(this, data);
   }

   public void readFixedArray(float[] data) throws IOException
   {
      RootInputStream.readFixedArray(this, data);
   }

   public void readFixedArray(double[] data) throws IOException
   {
      RootInputStream.readFixedArray(this, data);
   }

   public void readMultiArray(Object[] array) throws IOException
   {
      RootInputStream.readMultiArray(this, array);
   }

   public String readNullTerminatedString(int maxLength) throws IOException
   {
      return RootInputStream.readNullTerminatedString(this, maxLength);
   }

   public RootObject readObject(String type) throws IOException
   {
      return RootInputStream.readObject(this, type);
   }

   public RootObject readObjectRef() throws IOException
   {
      return RootInputStream.readObjectRef(this, map);
   }

   public String readString() throws IOException
   {
      return RootInputStream.readString(this);
   }

   public int readVersion() throws IOException
   {
      return RootInputStream.readVersion(this, null);
   }

   public int readVersion(AbstractRootObject obj) throws IOException
   {
      return RootInputStream.readVersion(this, obj);
   }

   public RootInput slice(int size) throws IOException
   {
      return RootInputStream.slice(this, size);
   }

   public RootInput slice(int inSize, int outSize) throws IOException
   {
      return RootInputStream.slice(this, inSize, outSize);
   }
   
   public double readTwistedDouble() throws IOException
   {
      return RootInputStream.readTwistedDouble(this);
   }
   public void dump() throws IOException
   {
      RootInputStream.dump(this,200);
   }   
   public void skipObject() throws IOException 
   {
      RootInputStream.skipObject(this);
   }

   public int getLast() {
       return last;
   }

   public void setLast(int last) {
       this.last = last;
   }
}
