package org.dianahep.root4j.core;

// ROOT4J
import org.dianahep.root4j.RootFileReader;
import org.dianahep.root4j.RootObject;
import org.dianahep.root4j.RootClassNotFound;

// hadoop hdfs specific
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.conf.Configuration;

// compression
import org.tukaani.xz.XZInputStream;
import java.util.zip.Inflater;

// java specific
import java.io.IOException;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Hashtable;

/**
 * Extension of HDFS Data Input Stream, that is modelled based on RootRandomAccessFile.
 * @author Viktor Khristenko
 */
public class RootHDFSInputStream extends FSDataInputStream implements RootInput
{
    private Hashtable map = new Hashtable();
    private RootFileReader reader;
    private long offset;
    private RootInput top;
    private boolean isTop;
    private static int HDRSIZE = 9;
    private int last = -1;

    /**
     * Initialize
     */
   private RootHDFSInputStream(RootHDFSByteArrayInputStream stream, RootInput t) 
       throws IOException
   {
       super(stream);
       this.top = t;
       this.isTop = false;
   }
   
   public RootHDFSInputStream(FSDataInputStream fsInput, RootFileReader reader) 
        throws IOException
   {
       super(fsInput);
       this.reader = reader;
       this.top = this;
       this.isTop = true;
       // Read in "ROOT" string
       if ((readByte() != 'r') || (readByte() != 'o') || (readByte() != 'o') ||
            (readByte() != 't'))
            throw new IOException("not a ROOT file!");
    }

   /**
    * @return The RootClassFactory associated with this stream
    */
   public RootClassFactory getFactory()
   {
       if (this.isTop)
        return reader.getFactory();
       
       return top.getFactory();
   }

   public void setMap(int off) throws IOException
   {
       offset = getPos() - off;
   }

   public void setPosition(long pos) throws IOException
   {
       seek(pos + offset);
   }

   public long getPosition() throws IOException
   {
       return getPos() - offset;
   }

   /**
    * Returns the Root version which wrote this file
    */
   public int getRootVersion()
   {
       return reader.getVersion();
   }

   /**
    * Returns the RootInput at the top of top of the heirarchy of slices
    */
   public RootInput getTop()
   {
       return top;
   }

   public void checkLength(AbstractRootObject object) throws IOException
   {
       RootInputStream.checkLength(this, object);
   }

   public void clearMap() throws IOException
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

   public boolean[] readVarWidthArrayBoolean() throws IOException
   {
       return RootInputStream.readVarWidthArrayBoolean(this);
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

   public int readArray(boolean[] data) throws IOException
   {
       return RootInputStream.readArray(this, data);
   }

   public void readFixedArray(int[] data) throws IOException
   {
       RootInputStream.readFixedArray(this, data);
   }

   public void readFixedArray(byte[] data) throws IOException
   {
       RootInputStream.readFixedArray(this, data);
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

   public void readFixedArray(long[] data) throws IOException
   {
       RootInputStream.readFixedArray(this, data);
   }

   public void readFixedArray(boolean[] data) throws IOException
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

   public int readVersion(AbstractRootObject object) throws IOException
   {
       return RootInputStream.readVersion(this, object);
   }

   public enum ZAlgo {
       GLOBAL_SETTING,
       ZLIB,
       LZMA,
       OLD,
       UNDEFINED;

       public static ZAlgo getAlgo(int fCompress){
           int algo = (fCompress - (fCompress % 100) ) / 100;
           return ZAlgo.values()[algo];
       }

       public static int getLevel(int fCompress){
           return fCompress % 100;
       }

       public static ZAlgo getAlgo(byte[] header){
           if(header[0] == 'Z' && header[1] == 'L'){
               return ZLIB;
           }
           if(header[0] == 'X' && header[1] == 'Z'){
               return LZMA;
           }
           return UNDEFINED;
       }
   }

   /**
    * Returns a new RootInput stream which represents a slice of this
    * RootInput stream. The new RootInput maintains its own file position
    * independent of the parent stream. The slice starts from the current
    * file position and extends for size bytes.
    * @param size The size of the slice.
    * @return The slice
    */
   public RootInput slice(int size) throws IOException
   {
       byte[] buffer = new byte[size];
       readFixedArray(buffer);
       return new RootHDFSInputStream(
            new RootHDFSByteArrayInputStream(buffer, 0), getTop());
   }

   /**
    * Slice and decompress
    */
   public RootInput slice(int size, int decompressedSize) throws IOException
   {
      // Currently we read the whole buffer before starting to decompress.
      // It would be better to decompress each component as we read it, but perhaps
      // not possible if we need to support random access into the unpacked array.
      try
      {
         byte[] buf = new byte[size];
         readFixedArray(buf);
         byte[] out = new byte[decompressedSize];

         int nout = 0;
         ZAlgo algo = ZAlgo.getAlgo( buf );

         switch (algo) {
             case ZLIB:
             case UNDEFINED:
                 boolean hasHeader = algo != ZAlgo.UNDEFINED;
                 Inflater inf = new Inflater( !hasHeader );
                 try {
                     // Skip the header when we have to restart
                     for(int nin = HDRSIZE; nout < decompressedSize; nin += HDRSIZE){
                         inf.setInput( buf, nin, buf.length - nin );
                         int rc = inf.inflate( out, nout, out.length - nout );
                         if ( rc == 0 ) {
                             throw new IOException( "Inflate unexpectedly returned 0 (perhaps OutOfMemory?)" );
                         }
                         nout += rc;
                         nin += inf.getTotalIn();
                         inf.reset();
                     }
                 } finally {
                     inf.end();
                 }
                 break;
             case LZMA:
                 RootHDFSByteArrayInputStream byteStream = 
                     new RootHDFSByteArrayInputStream(buf, 0);
                 RootHDFSInputStream bufferStream = 
                     new RootHDFSInputStream(byteStream, getTop());
                 bufferStream.skip( HDRSIZE );
                 XZInputStream unc = new XZInputStream( bufferStream);
                 try{
                     int rc = unc.read( out, nout, out.length - nout );
                     nout += rc;
                     // Library recommendation for integrity check
                     if( unc.read() != -1 || nout != decompressedSize){
                         throw new IOException( "Failed to decompress all LZMA bytes." );
                     }
                 }
                 finally {
                     unc.close();
                     bufferStream.close();
                 }
                 break;
             default:
                 throw new IOException( "Unable to determine compression algorithm" );
         }
         return new RootHDFSInputStream(
                new RootHDFSByteArrayInputStream(out, 0), getTop());
      }
      catch (Exception x)
      {
         IOException xx = new IOException("Error during decompression (size="+size+"/"+decompressedSize+")");
         xx.initCause(x);
         throw xx;
      }
      catch (OutOfMemoryError x)
      {
         IOException xx = new IOException("Error during decompression (size="+size+"/"+decompressedSize+")");
         xx.initCause(x);
         throw xx;
      }
   }

   /**
    * Reads a double in strange byte order?
    */
   public double readTwistedDouble() throws IOException
   {
       return RootInputStream.readTwistedDouble(this);
   }

   /**
    * For debugging
    */
   public void dump() throws IOException
   {
       RootInputStream.dump(this,200);
   }

   /**
    * For skipping uninterpretable objects
    */
   public void skipObject() throws IOException
   {
       RootInputStream.skipObject(this);
   }

   public int getLast()
   {
       return this.last;
   }

   public void setLast(int last)
   {
       this.last = last;
   }
}
