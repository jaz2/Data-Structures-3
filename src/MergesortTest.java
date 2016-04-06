import java.io.IOException;
import java.io.RandomAccessFile;

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
		args[0] = "inputa4.txt";
		args[1] = "1"; // Buffer pool size
		args[2] = "statFile.txt";
		//" a"
		FileGenerator f = new FileGenerator();
		CheckFile c = new CheckFile();
		String[] args2 = new String[3];
		args2[0] = "-b";
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
}