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
		//byte_pos = blocknum * block_size + posInBlock
		//client would give an array and you can stuff it in, instead of making a new array
		//store block num and file it came from
		//make an array (circular array)
		//insert on bottom take from top
		//what if u use something already in buffer pool? have to swap 
		// each block is 4096 bytes
	
	boolean dbit;
	
	/**
	 * Constructor for the bufferpool
	 */
	public BufferPool()
	{}
	
	//first thing to do is take info and put it into buffers
	
	/**
	 * Inserts into the buffer from the file
	 */
	public void insertF(RandomAccessFile file, int numBytesRead, int bytePos) //need a dirty bit
	{} //might be two inserts 
	
	/**
	 * Inserts into the array
	 */
	public void checkDirty(byte e)
	{
		//find a way to compare the two to check
		if (this.equals(e))
		{
			dbit = false;
		}
		dbit = true;
	}
	
	
	/**
	 * 
	 * @param file the file to read
	 * @param numBytesRead will always be 4 for this project
	 * @param bytePos the position of the byte
	 */
	public void getBytes(RandomAccessFile file, int numBytesRead, int bytePos)
	{
		//figure out if its in the buffer pool or not
		//if it is don't touch file
		int loc = bytePos % 1024;
		if (loc < 1024)
		{}
		//find the block number to determine if it is in buffer pool
		//if it is, access
		//if it is not in the buffer pool ???????? (access the file)
	}
}
