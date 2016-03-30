import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

import org.junit.Before;
import org.junit.Test;

/**
 * 
 */

/**
 * @author Jazmine Zurita and Jessica McCready
 * @version March 22 2016
 */
public class BufferPoolTest {

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
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
		byte[] size = "Come world as you are".getBytes();
		file.write(size);
		BufferPool bp = new BufferPool(10);
		bp.Buffer b = new BufferPool.Buffer(file, 0, size.length);
	}
}
