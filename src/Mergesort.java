import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;



/**
 * { your description of the project here }
 */
// On my honor: 
// 
// - I have not used source code obtained from another student, 
// or any other unauthorized source, either modified or 
// unmodified. 
// 
// - All source code and documentation used in my program is 
// either my original work, or was derived by me from the 
// source code published in the textbook for this course. 
// 
// - I have not discussed coding details about this project with
// anyone other than my partner (in the case of a joint 
// submission), instructor, ACM/UPE tutors or the TAs assigned 
// to this course. I understand that I may discuss the concepts 
// of this program with other students, and that another student 
// may help me debug my program so long as neither of us writes 
// anything during the discussion or modifies any computer file 
// during the discussion. I have violated neither the spirit nor 
// letter of this restriction.
// Jazmine Zurita and Jessica McCready

/**
 * The class containing the main method, the entry point of the application.
 * 
 * @author Jazmine Zurita and Jessica McCready
 * @version March 29 2016
 */
public class Mergesort {
	//create a temp file
	//split file and put half in temp
	//split THAT first file and at some point 
	//eventually sort, prob during merge part
	//merge sort first half mer.. second and then merge together

	//need class for statistics

	public static BufferPool bp;

	/**
	 * The entry point of the application
	 * 
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		if (args == null)
		{
			System.out.println("Hello, World");
		}
		else 
		{
			RandomAccessFile tem = new RandomAccessFile("temp", "rw");
			RandomAccessFile f = new RandomAccessFile(args[0], "rw");
			bp = new BufferPool(Integer.parseInt(args[1]));

			long start = System.currentTimeMillis();
			sort(f, tem, 0, (int)f.length() / 4); 
			long end = System.currentTimeMillis();
			long time = end - start;
			
			for(int i = 0; i < bp.blox.length; i++)
			{
				bp.flush(bp.blox[i]);
			}
			
			RandomAccessFile st = new RandomAccessFile(args[2], "rw");
			st.writeChars(args[0] + "\n");
			bp.stats(st);
			st.writeChars(time + "\n");
			f.close();
			tem.close();
			st.close();
		}
	}

	/**
	 * The sort method
	 * @param A original file
	 * @param temp the temp file
	 * @param left the original start of file
	 * @param right end of file
	 * @throws IOException
	 */
	public static void sort(RandomAccessFile A, RandomAccessFile temp, int left, int right) throws IOException
	{
		byte[] dat = new byte[4];
		if (left == right) return;         // List has one record
		int mid = (left + right) / 2;          // Select midpoint
		sort(A, temp, left, mid);     // Mergesort first half
		sort(A, temp, mid + 1, right);  // Mergesort second half
		for (int i = left; i <= right; i++)    // Copy subarray to temp
		{
			//temp[i] = A[i];
			bp.read(A, 4, i * 4, dat);
			bp.write(temp, 4, i * 4, dat);
		}
		// Do the merge operation back to A
		int i1 = left;
		int i2 = mid + 1;
		for (int curr = left; curr <= right; curr++) {
			if (i1 == mid + 1)     
			{ // Left sublist exhausted
				//A[curr] = temp[i2++];
				bp.read(temp, 4, i2, dat);
				bp.write(A, 4, curr, dat);
				i2++;
			}
			else if (i2 > right)             
			{ // Right sublist exhausted
				//A[curr] = temp[i1++];
				bp.read(temp, 4, i1, dat);
				bp.write(A, 4, curr, dat);
				i1++;
			}
			else 
			{
				bp.read(temp, 4, i1 * 4, dat);
				short a1 = ByteBuffer.wrap(dat).getShort();

				bp.read(temp, 4, i2 * 4, dat);
				short a2 = ByteBuffer.wrap(dat).getShort();

				if (a1 <= a2 /*temp[i1].compareTo(temp[i2]) <= 0*/)  
				{ // Get smaller value
					//A[curr] = temp[i1++];
					bp.read(temp, 4, i1, dat);
					bp.write(A, 4, curr, dat);
					i1++;
				}
				else
				{
					//A[curr] = temp[i2++];
					bp.read(temp, 4, i2, dat);
					bp.write(A, 4, curr, dat);
					i2++;
				}
			}
			//merge sort first half 
			//merge sort second half
			//merge for(half length * 2)
			//{
			// compareTo and increment min 
			// pointer 
			//place min value in file
		}
	}
}