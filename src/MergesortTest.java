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
	 * First case of Merge sort
	 */
	@Test
	public void testMerge1()
	{}

	/**
	 * Tests the stat file
	 * @throws Exception 
	 */
	@Test
	public void testStat() throws Exception
	{
		String[] args = new String[3];
		args[0] = "inputa4.txt";
		args[1] = "2"; // Buffer pool size
		args[2] = "statFile.txt";
		String numBlocks = "4"; // Test file size
		//" a"
		FileGenerator f = new FileGenerator();
		CheckFile c = new CheckFile();
		String[] args2 = new String[3];
		args2[0] = "a";
		args2[1] = args[0]; // Buffer pool size
		args2[2] = numBlocks;
		f.generateFile(args2);
		
		//(new FileGenerator()).generateFile(args);
		RandomAccessFile temp = new RandomAccessFile("test.txt", "rw");
		//call merge sort on file generate and give it a name
		//Mergesort.sort(f.generateFile(args), temp, 0, );
		//check file
		Mergesort.main(args);
		
		assertTrue(c.checkFile(args[0]));
	}
}