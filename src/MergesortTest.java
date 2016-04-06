import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Arrays;

import org.junit.Test;

import student.TestCase;

/**
 * @author CS3114 staff
 * @version 1
 */

public class MergesortTest 
extends TestCase {

	/**
	 * This method sets up the tests that follow.
	 */
	public void setUp() {
		// no op
	}

	/**
	 * Tests when null
	 * @throws IOException
	 */
	@Test
	public void testInit() throws IOException {
		Mergesort merge = new Mergesort();
		assertNotNull(merge);
		Mergesort.main(null);
		assertFuzzyEquals("Hello, World", systemOut().getHistory());
	}

	/**
	 * Tests the stat file
	 * @throws Exception 
	 */
	@Test
	public void testSort() throws Exception
	{
		String[] args = new String[3];
		args[0] = "original.txt";
		args[1] = "1"; // Buffer pool size
		args[2] = "statFile.txt";
		//" a"
		FileGenerator f = new FileGenerator();
		CheckFile c = new CheckFile();
		String[] args2 = new String[3];
		args2[0] = "-a";
		args2[1] = args[0]; 
		args2[2] = args[1];
		f.generateFile(args2);
		
		//(new FileGenerator()).generateFile(args);
		//RandomAccessFile temp = new RandomAccessFile("test.txt", "rw");
		//RandomAccessFile file = new RandomAccessFile(args[0], "rw");
		//call merge sort on file generate and give it a name
		//Mergesort.sort(file, temp, 0, (int)file.length()/ 4);
		//check file
		Mergesort.main(args);
		
		assertTrue(c.checkFile(args[0]));
	}
	
	public void testSortTo() throws Exception
	{
		RandomAccessFile f = new RandomAccessFile("sortfile", "rw");
		RandomAccessFile t = new RandomAccessFile("tfile", "rw");
		CheckFile c = new CheckFile();
		BufferPool buf = new BufferPool(3);
		f.writeChars("GYFHAJCB");
		byte bytes[] = new byte[4];
		byte b[] = {88, 70, 73, 80};
		byte a[] = {66, 78, 66, 79};
		/////buf.write(f, 4, 0, b);
		//buf.write(f, 4, 5000, b);
		//buf.write(f, 4, 9000, b);
		////buf.write(f, 4, 0, a);
		/////buf.read(f, 4, 0, bytes);
		/////assertTrue(Arrays.equals(a, bytes));
		//buf.read(f, 4, 5000, bytes);
		//buf.read(f, 4, 9000, bytes);
		Mergesort.sort(f, t, 0, (int)f.length()/4);	
		for(int i = 0; i < buf.blox.length; i++)
		{
			buf.flush(buf.blox[i]);
		}					
		buf = new BufferPool(3);
		//System.out.println(buf.blox[2].data[1]);
		//assertFalse(Arrays.equals(b, bytes));
		assertTrue(c.checkFile("sortfile"));
		f.close();
	}
}