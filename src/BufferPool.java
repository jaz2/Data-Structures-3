import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * 
 */

/**
 * @author Jazmine Zurita and Jessica McCready
 * @version April 3 2016
 *
 */
public class BufferPool {

    /**
     * The buffer class 
     * @author Jazmine Zurita and Jessica McCready
     * @version April 6 2016
     *
     */
    public class Buffer {

        /**
         * The array of data in buffer
         */
        public byte[] data;

        /**
         * The block number
         */
        public int block;

        /**
         * The way we will distinguish files
         */
        public RandomAccessFile file;

        /**
         * Dirty bit boolean
         */
        public boolean dbit;

        /**
         * The buffer constructor 
         * @param fil the file 
         * @param num the number of blocks
         * @param size the size of the file
         */
        public Buffer(RandomAccessFile fil, int num, int size)
        {
            data = new byte[size];
            dbit = false;
            block = num;
            file = fil;
        }
        //an array data 4096 bytes
        //block number 
        //file identifier (file 1 or 2 or something)
        //check if its dirty or not
    }

    /**
     * Array holding the buffers
     */
    public Buffer[] blox;

    /**
     * for stats
     */
    public int hits;

    /**
     * Times read in buffer
     */
    public int reads;

    /**
     * Times written in buffer
     */
    public int writes;

    /**
     * Constructor for the bufferPool
     * @param numOfBlocks the number of blocks
     */
    public BufferPool(int numOfBlocks)
    { //make an array storing buffers
        blox = new Buffer[numOfBlocks];
        hits = 0;
        reads = 0;
        writes = 0;
    }

    /**
     * 
     * @param f the file to read
     * @param numBytesRead will always be 4 for this project
     * @param bytePos the position of the byte
     * @param bytes the array to return 
     * @throws IOException 
     */
    public void read(RandomAccessFile f, int numBytesRead, 
            int bytePos, byte[] bytes) throws IOException
    {
        //locToStart = blocknum * block_size + posInBlock;
        int blockN = bytePos / 4096; //block
        int posInBlock = bytePos % 4096; //pos in block
        int i = 0;
        while (i < blox.length
                && blox[i] != null 
                && !(blockN == blox[i].block 
                && f == blox[i].file))
        {
            i++;
        }
        if ( i < blox.length
                && blox[i] != null 
                /*&& blockN == blox[i].block 
                && f == blox[i].file*/)
        { //send back to merge sort
            System.arraycopy(blox[i].data, posInBlock, 
                    bytes, 0, numBytesRead);
            Buffer tem = blox[i];
            for (int j = i; j > 0; j--)
            {
                blox[j] = blox[j - 1];
            }
            blox[0] = tem;
            hits++;
        }
        else 
        { //read from file, place into buffer and send that back
            f.seek(blockN * 4096);
            Buffer b = new Buffer(f, blockN, 4096);
            f.read(b.data);
            System.arraycopy(b.data, posInBlock, 
                    bytes, 0, numBytesRead);
            flush(blox[blox.length - 1]);
            for (int k = blox.length - 1; k > 0; k--)
            {
                blox[k] = blox[k - 1];
            }
            blox[0] = b;
            reads++; 
        }
    } //for each buffer flush if it's dirty


    //first thing to do is take info and put it into buffers
    /**
     * Writes over the buffer, or the file
     * if buffer does not contain it
     * @param f the file to access
     * @param numBytesToWrite the number of bytes (4)
     * @param bytePos position to start
     * @param bytes the array to write in
     * @throws IOException 
     */
    public void write(RandomAccessFile f, int numBytesToWrite, 
            int bytePos, byte[] bytes) throws IOException
    {
        //when you flush, reset the dirty bit to false
        int blockN = bytePos / 4096;
        int posInBlock = bytePos % 4096;
        int i = 0;
        while ( i < blox.length 
                && blox[i] != null 
                && !(blockN == blox[i].block 
                && f == blox[i].file))
        {
            i++;
        }
        if ( i < blox.length                
                && blox[i] != null
                /*&& blockN == blox[i].block
                && f == blox[i].file*/)
        { 
            System.arraycopy(bytes, 0, blox[i].data, 
                    posInBlock, numBytesToWrite);
            blox[i].dbit = true;
            hits++;
            //send back to merge sort
        }
        else 
        {
            f.seek(blockN * 4096);
            Buffer b = new Buffer(f, blockN, 4096);
            f.read(b.data);
            System.arraycopy(bytes, 0, b.data, 
                    posInBlock, numBytesToWrite);
            b.dbit = true;
            flush(blox[blox.length - 1]);    
            for (int j = blox.length - 1; j > 0; j--)
            {
                blox[j] = blox[j - 1];
            }
            blox[0] = b;
            writes++;
        }
    } 

    /**
     * Flushes 
     * @param bu the buffer to flush
     * @throws IOException
     */
    public void flush(Buffer bu) throws IOException
    {
        if (bu != null && bu.dbit)
        {
            bu.file.seek(bu.block * 4096);
            bu.file.write(bu.data);
            bu.dbit = false;
            writes++;
        }
    }

    /**
     * make a write stats method here
     * want to keep adding to the stat file
     * make variables to count the number of read and writes
     * 
     * @param s the file to be taken in
     * @param x the time 
     * @throws IOException 
     */
    public void stats(File s, long x) throws IOException
    {
        FileWriter fw = new FileWriter(s, true);
        BufferedWriter w = new BufferedWriter(fw);
        w.write("\nCache hits: " + hits + "\n");
        w.write("Disk reads: " + reads + "\n");
        w.write("Disk writes: " + writes + "\n");
        w.write("Time to sort: " + x + "\n");
        w.write("---------------------------");
        w.close();
    }
}