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
		bp.write(file, 4, 1000, b);
		System.out.println(b.toString());
		bp.read(file, 4, 1000, bye);
		System.out.println(file.toString());
		System.out.println(bye.toString());
		assertTrue(b.equals(bye));
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
		buf.read(f, 4, 0, bytes);
		//System.out.println(buf.blocks[0].data);
		//[B@169646d3
		assertTrue(bytes.length == 4);
		byte b[] = {(byte)12, (byte)9, (byte)3, (byte)8};
		buf.write(f, 4, 0, b);
		buf.write(f, 4, 5000, b);
		buf.write(f, 4, 10000, b);
		buf.read(f, 4, 0, bytes);
		//assert true Arrays.equals  make sure two arrays have same thing
		buf.read(f, 4, 5000, bytes);
		buf.read(f, 4, 20000, bytes);	
	}
}