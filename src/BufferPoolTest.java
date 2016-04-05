import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import student.TestCase;

/**
 * 
 */

/**
 * @author Jazmine Zurita and Jessica McCready
 * @version March 22 2016
 */
public class BufferPoolTest extends TestCase {

	public BufferPool buf;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		buf = new BufferPool(3);
	} //to randomaccessfile write something and then flush it 
	//make sure it's there
	//give buffer pool at least 1 buffer

	//do write TESTS!!!!!
	/**
	 * Tests the simple case of writing 
	 * @throws IOException 
	 */
	@Test
	public void testWriteYe() throws IOException
	{		
		RandomAccessFile file = new RandomAccessFile("something.txt", "rw");
		byte[] bye = new byte[4];
		byte[] size = "Hello world".getBytes();
		byte[] b = {3, 2, 61, 2};
		file.write(size);
		BufferPool bp = new BufferPool(10);
		bp.write(file, 4, 0, b);		
		bp.read(file, 4, 0, bye);
		assertTrue(Arrays.equals(b, bye));
	}
	
	/**
	 * tests read method
	 * @throws IOException
	 */
	@Test
	public void testRead() throws IOException
	{
		RandomAccessFile f = new RandomAccessFile("file", "rw");
		byte bytes[] = new byte[4];
		byte b[] = {12, 9, 3, 8};
		buf.write(f, 4, 0, b);
		buf.write(f, 4, 5000, b);
		buf.write(f, 4, 9000, b);
		buf.read(f, 4, 0, bytes);
		assertTrue(Arrays.equals(b, bytes));
		buf.read(f, 4, 5000, bytes);
		buf.read(f, 4, 9000, bytes);
		for(int i = 0; i < buf.blox.length; i++)
		{
			buf.flush(buf.blox[i]);
		}					
		buf = new BufferPool(3);
		//System.out.println(buf.blox[2].data[1]);
		//assertFalse(Arrays.equals(b, bytes));
		assertNull(bytes[1]);
	}
	
	/**
	 * Checks if the block has the data equal
	 *  to the array 
	 * @throws IOException 
	 */
	@Test
	public void testReadBlock() throws IOException
	{
		RandomAccessFile f = new RandomAccessFile("file", "rw");
		byte bytes[] = new byte[4];
		byte b[] = {12, 9, 3, 8};
		byte a[] = {14, 99, 5, 8};
		buf.write(f, 4, 0, a);
		buf.write(f, 4, 5000, a);
		buf.write(f, 4, 10000, a);
		buf.write(f, 4, 0, b);
		buf.write(f, 4, 5000, a);
		buf.write(f, 4, 10000, a);
		buf.read(f, 4, 0, bytes);
		assertTrue(Arrays.equals(b, bytes));
		buf.read(f, 4, 5000, bytes);
		assertTrue(Arrays.equals(a, bytes));
		//assertTrue(Arrays.equals(bytes, buf.blox[0].data));
	}
	
	/**
	 * Checks if LRU is working as it should
	 * @throws IOException 
	 */
	@Test
	public void testLRU() throws IOException
	{
		RandomAccessFile f = new RandomAccessFile("file", "rw");
		byte bytes[] = new byte[4];
		byte a[] = {14, 99, 5, 8};
		byte b[] = {12, 9, 3, 8};
		byte c[] = {42, 93, 36, 8};
		BufferPool pool = new BufferPool(10);
		pool.write(f, 4, 0, a); 
		pool.read(f, 4, 0, bytes);
		assertTrue(Arrays.equals(a, bytes));
		
		pool.write(f, 4, 5000, b); 
		pool.read(f, 4, 5000, bytes);
		assertTrue(Arrays.equals(b, bytes));
		
		pool.write(f, 4, 10000, c);
				
		pool.read(f, 4, 10000, bytes);
		assertTrue(Arrays.equals(c, bytes));
		
		pool.write(f, 4, 5000, a);
		pool.read(f, 4, 5000, bytes);
		assertTrue(Arrays.equals(a, bytes));
		
		pool.write(f, 4, 0, a);
		pool.write(f, 4, 5000, b);
		pool.write(f, 4, 10000, c);
		pool.write(f, 4, 15000, c);
		pool.write(f, 4, 20000, a);
		pool.write(f, 4, 5000, a);
		pool.read(f, 4, 5000, bytes);
		assertTrue(Arrays.equals(a, bytes));
	}
}