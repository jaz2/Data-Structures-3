/**
 * 
 */

/**
 * @author Jazmine Zurita and Jessica McCready
 *
 *first just do an array then for performance do circular
 */
public class CircularArray {
	
	private Buffer a[];
	
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
		a = new Buffer[max];
	}
	
	/**
	 * Adds onto the array
	 * @param e
	 */
	public void add(byte e)
	{
		if ((bottom + 2) % max == front)
		{ //if it's full
			//LRU
			
		}	
		bottom = (bottom + 1) % max;
		a[bottom] = e;
	}
	
	public void remove(byte e)
	{}
	
	public void clear()
	{
		front = 1;
		bottom = 0;
	}
}
