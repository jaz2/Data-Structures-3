import java.io.IOException;

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
	 */
	@Test
	public void testStat()
	{
		String[] args = new String[3];
		args[0] = "inputa4.txt";
		args[1] = "2"; // Buffer pool size
		args[2] = "statFile.txt";
		String numBlocks = "4"; // Test file size
		(new FileGenerator()).generateFile(args);
		//call merge sort on file generate and give it a name
		//check file
		Mergesort.main(args);
		assertTrue(new CheckFile(args[0]));
	}
}