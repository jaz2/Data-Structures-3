import java.io.File;
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

    /**
     * The bufferpool class
     */
    public static BufferPool bp;

    /**
     * The entry point of the application
     * 
     * @param args the arguments
     * @throws IOException 
     */
    public static void main(String[] args) throws IOException {
        if (args == null)
        {
            System.out.println("Hello, World");
        }
        else 
        {            
            RandomAccessFile f = new RandomAccessFile(args[0], "rw");
            File stat = new File(args[2]);
            RandomAccessFile tem = new RandomAccessFile("temp", "rw");
            bp = new BufferPool(Integer.parseInt(args[1]));

            long start = System.currentTimeMillis();
            sort(f, tem, 0, ((int)f.length() / 4) - 1); 
            long end = System.currentTimeMillis();
            long time = end - start;

            for (int i = 0; i < bp.blox.length; i++)
            {
                bp.flush(bp.blox[i]);
            }            
            
            bp.stats(stat, time);
            
            tem.close();
            f.close();
        }
    }

    /**
     * The sort method
     * @param a original file
     * @param temp the temp file
     * @param left the original start of file
     * @param right end of file
     * @throws IOException
     */
    public static void sort(RandomAccessFile a, RandomAccessFile temp, 
            int left, int right) throws IOException
    {
        byte[] dat = new byte[4];
        if (left == right) return;         // List has one record
        int mid = (left + right) / 2;          // Select midpoint
        sort(a, temp, left, mid);     // Mergesort first half
        sort(a, temp, mid + 1, right);  // Mergesort second half
        for (int i = left; i <= right; i++)    // Copy subarray to temp
        {
            //temp[i] = A[i];
            bp.read(a, 4, i * 4, dat);
            bp.write(temp, 4, i * 4, dat);
        }
        // Do the merge operation back to A
        int i1 = left;
        int i2 = mid + 1;
        for (int curr = left; curr <= right; curr++) {
            if (i1 == mid + 1)     
            { // Left sublist exhausted
                //A[curr] = temp[i2++];
                bp.read(temp, 4, i2 * 4, dat);
                bp.write(a, 4, curr * 4, dat);
                i2++; 
            }
            else if (i2 > right)             
            { // Right sublist exhausted
                //A[curr] = temp[i1++];
                bp.read(temp, 4, i1 * 4, dat);
                bp.write(a, 4, curr * 4, dat);
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
                    bp.read(temp, 4, i1 * 4, dat);
                    bp.write(a, 4, curr * 4, dat);
                    i1++; 
                }
                else
                {
                    //A[curr] = temp[i2++];
                    bp.read(temp, 4, i2 * 4, dat);
                    bp.write(a, 4, curr * 4, dat);
                    i2++;
                }
            }
        }
    }
}