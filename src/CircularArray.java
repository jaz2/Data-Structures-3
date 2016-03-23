/**
 * 
 */

/**
 * @author Jazmine Zurita and Jessica McCready
 *
 */
public class CircularArray {
	
	private byte a[];
	
	/**
	 * the front of the array
	 */
	private int front;
	
	/**
	 * the rear of the array
	 */
	private int bottom;
	
	/**
	 * Max size
	 */
	private int max;

	/**
	 * The constructor for the circular array
	 * @param size size of the array
	 */
	public CircularArray(int size)
	{
		front = 1;
		bottom = 0;
		max = size + 1;
		a = new byte[max];
	}
	
	/**
	 * Adds onto the array
	 * @param e
	 */
	public boolean add(byte e)
	{
		if ((front - 2) % max == bottom)
		{
			return false;
		}
		
		return true;
	}
	
	public void remove(byte e)
	{}
}
