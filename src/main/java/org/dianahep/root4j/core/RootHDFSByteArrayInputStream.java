package org.dianahep.root4j.core;

// java.io
import java.io.ByteArrayInputStream;
import java.io.IOException;

// hadoop
import org.apache.hadoop.fs.PositionedReadable;
import org.apache.hadoop.fs.Seekable;

/**
 * A ByteArrayInputStream that will reveal its current
 * position.
 * @author Viktor Khristenko
 */
public class RootHDFSByteArrayInputStream extends ByteArrayInputStream implements
    PositionedReadable,Seekable
{
   private int offset;

   /**
    * @param buf The buffer from which to read
    */
   public RootHDFSByteArrayInputStream(byte[] buf, int offset)
   {
      super(buf);
      this.offset = offset;
   }

   void setOffset(int offset)
   {
      this.offset = offset;
   }

   void setPosition(long pos)
   {
      this.pos = (int) pos - offset;
   }

   long getPosition()
   {
      return pos + offset;
   }

   public long getPos() 
   {
        return getPosition();
   }

   public void seek(long pos)
   {
        setPosition(pos);
   }

   /**
    * Read up to length; from a given position, return number of bytes read; doesn't 
    * change the offset
    */
   public int read(long position, byte[] buffer, int off, int length) throws IOException
   {
       // keep the current position
       long oldPos = getPosition();

        setPosition(position);
        int nbytes = read(buffer, offset, length);

        // restore the position
        setPosition(oldPos);
        return nbytes;
   }

   public void readFully(long position, byte[] buffer, int off, int length) throws IOException
   {
        read(position, buffer, off, length);
   }

   public void readFully(long position, byte[] buffer) throws IOException
   {
        read(position, buffer, 0, buffer.length);
   }

   // TODO - what is this method doing???
   public boolean seekToNewSource(long targetPos) throws IOException
   {
       return true;
   }
}
