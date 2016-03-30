import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

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
		System.out.println("Hello, World");
		RandomAccessFile tem = new RandomAccessFile("temp", "rw");
		RandomAccessFile f = new RandomAccessFile(args[1], "rw");
		bp = new BufferPool(Integer.parseInt(args[2]));

		sort(f, tem, 0, (int)f.length() / 4096);
	}

	public static void sort(RandomAccessFile A, RandomAccessFile temp, int left, int right)
	{
		byte[] dat = new byte[4];
		if (left == right) return;         // List has one record
		int mid = (left + right) / 2;          // Select midpoint
		sort(A, temp, left, mid);     // Mergesort first half
		sort(A, temp, mid + 1, right);  // Mergesort second half
		for (int i = left; i <= right; i++)    // Copy subarray to temp
		{
			//temp[i] = A[i];
			bp.read(A, 4, i * 4096, dat); //ask eric
			bp.write(temp, 4, i * 4096, dat);
		}
		// Do the merge operation back to A
		int i1 = left;
		int i2 = mid + 1;
		for (int curr = left; curr <= right; curr++) {
			if (i1 == mid + 1)     
			{ // Left sublist exhausted
				A[curr] = temp[i2++];
			}
			else if (i2 > right)             
			{ // Right sublist exhausted
				A[curr] = temp[i1++];
			}
			else if (temp[i1].compareTo(temp[i2]) <= 0)  
			{ // Get smaller value
				A[curr] = temp[i1++];
			}
			else
			{
				A[curr] = temp[i2++];
			}
			//merge sort first half 
			//merge sort second half
			//merge for(half length * 2)
			//{
			// compareTo and increment min 
			// pointer 
			//place min value in file

			//			List mergesort(List inlist) {
			//			  if (inlist.length() <= 1) return inlist;;
			//			  List L1 = half of the items from inlist;
			//			  List L2 = other half of the items from inlist;
			//			  return merge(mergesort(L1), mergesort(L2));
			//			}
		}
	}
}