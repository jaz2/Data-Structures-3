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
	
	public void testInit() {
		Mergesort merge = new Mergesort();
		assertNotNull(merge);
		//Mergesort.main(null); TODO fix this
		assertFuzzyEquals("Hello, World", systemOut().getHistory());
	}
}