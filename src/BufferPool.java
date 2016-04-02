import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * 
 */

/**
 * @author Jazz
 *
 */
public class BufferPool {
	//one buffer that manages two files
	//param in buffer: file, number to read (4), byte_pos (where it is)
	//byte_pos => x(pos)/1024(block size)
	//find loc of byte = byte_pos % block_size
	// byte_pos = blocknum * block_size + posInBlock
	//client would give an array and you can stuff it in, instead of making a new array
	//store block num and file it came from
	//make an array (circular array)
	//insert on bottom take from top
	//what if u use something already in buffer pool? have to swap 
	// each block is 4096 bytes

	//only writes to disk when something is changed and is flushed back (LRU)
	public class Buffer {

		/**
		 * The array of data in buffer
		 */
		public byte data[];

		/**
		 * The block number
		 */
		public int block;

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

		//compare it to the file you want
		//if it is the original file
		public boolean checkFile(RandomAccessFile f)
		{
			return (file.equals(f));
		}
	}

	/**
	 * Array holding the buffers
	 */
	public Buffer blox[]; //this will be the circular array
	
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
	 * @param file the file to read
	 * @param numBytesRead will always be 4 for this project
	 * @param bytePos the position of the byte
	 * @param bytes the array to return 
	 * @throws IOException 
	 */
	public void read(RandomAccessFile f, int numBytesRead, int bytePos, byte[] bytes) throws IOException
	{
		//locToStart = blocknum * block_size + posInBlock;
		int blockN = bytePos / 4096; //block
		int posInBlock = bytePos % 4096; //pos in block
		int i = 0;
		while (i < blox.length && blox[i] != null 
				&& blockN != blox[i].block && f.equals(blox[i].file))
		{
			i++;
		}
		if (i != blox.length && blox[i] != null)
		{ //send back to merge sort
			System.arraycopy(blox[i].data, posInBlock, bytes, 0, numBytesRead);
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
			Buffer b = new Buffer(f, i, 4096);
			f.read(b.data);
			System.arraycopy(b.data, posInBlock, bytes, 0, numBytesRead);
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
	 * @param file the file to access
	 * @param numBytesToWrite the number of bytes (4)
	 * @param bytePos position to start
	 * @param bytes the array to write in
	 * @throws IOException 
	 */
	public void write(RandomAccessFile f, int numBytesToWrite, int bytePos, byte[] bytes) throws IOException
	{
		//when you flush, reset the dirty bit to false
		int blockN = bytePos / 4096;
		int posInBlock = bytePos % 4096;
		int i = 0;
		while (i < blox.length && blox[i] != null 
				&& blockN != blox[i].block &&  f.equals(blox[i].file))
		{
			i++;
		}
		if (i != blox.length && blox[i] != null)
		{
			System.arraycopy(bytes, 0, blox[i].data, posInBlock, numBytesToWrite);
			blox[i].dbit = true;
			hits++;
			//send back to merge sort
		}
		else 
		{
			f.seek(blockN * 4096);
			Buffer b = new Buffer(f, i, 4096);
			f.read(b.data);
			System.arraycopy(bytes, 0, b.data, posInBlock, numBytesToWrite);
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
		if (bu != null && bu.dbit == true)
		{
			bu.file.seek(bu.block * 4096);
			bu.file.write(bu.data);
			System.out.println("Writes: " + bu.file);
			bu.dbit = false;
			writes++;
		}
	}

	/**
	 * make a write stats method here
	 * want to keep adding to the stat file
	 * make variables to count the number of read and writes
	 * 
	 * @param file the file to be taken in
	 * @throws IOException 
	 */
	public void stats(String s, long x) throws IOException
	{
		FileWriter fw = new FileWriter(s);
		BufferedWriter w = new BufferedWriter(fw);
		w.write(s);
		w.write("\nCache hits: " + hits + "\n");
		w.write("Disk reads: " + reads + "\n");
		w.write("Disk writes: " + writes + "\n");
		w.write("Time to sort: " + x + "\n");
		w.close();
	}
	
}
