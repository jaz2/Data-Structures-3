import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import student.TestCase;

/**
 * 
 */

/**
 * @author Jazmine Zurita and Jessica McCready
 * @version March 22 2016
 */
public class BufferPoolTest extends TestCase {

    /**
     * The buffer 
     */
    public BufferPool buf;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        buf = new BufferPool(3);
    } //to randomaccessfile write something and then flush it 
    //make sure it's there
    //give buffer pool at least 1 buffer

    //do write TESTS!!!!!
    /**
     * Tests the simple case of writing 
     * @throws IOException 
     */
    @Test
    public void testWriteYe() throws IOException
    {        
        RandomAccessFile file = new RandomAccessFile("something.txt", "rw");
        byte[] bye = new byte[4];
        byte[] size = "Hello world".getBytes();
        byte[] b = {3, 2, 61, 2};
        file.write(size);
        BufferPool bp = new BufferPool(10);
        bp.write(file, 4, 0, b);        
        bp.read(file, 4, 0, bye);
        assertTrue(Arrays.equals(b, bye));
        file.close();
    }

    /**
     * Checks if the block has the data equal
     *  to the array 
     * @throws IOException 
     */
    @Test
    public void testReadBlock() throws IOException
    {
        RandomAccessFile f = new RandomAccessFile("file", "rw");
        byte[] bytes = new byte[4];
        byte[] b = {72, 79, 63, 68};
        byte[] a = {54, 99, 75, 88};
        buf.write(f, 4, 0, a);
        buf.write(f, 4, 5000, a);
        buf.write(f, 4, 10000, a);
        buf.write(f, 4, 0, b);
        buf.write(f, 4, 5000, a);
        buf.write(f, 4, 10000, a);
        buf.read(f, 4, 0, bytes);
        assertTrue(Arrays.equals(b, bytes));
        buf.read(f, 4, 5000, bytes);
        for (int i = 0; i < buf.blox.length; i++)
        {
            buf.flush(buf.blox[i]);
        }                    
        buf = new BufferPool(3);
        f.close();
        assertTrue(Arrays.equals(a, bytes));
        //assertTrue(Arrays.equals(bytes, buf.blox[0].data));
    }

    /**
     * Checks if LRU is working as it should
     * @throws IOException 
     */
    @Test
    public void testLRU() throws IOException
    {
        RandomAccessFile f = new RandomAccessFile("testLRU", "rw");
        byte[] bytes = new byte[4];
        byte[] a = {64, 90, 85, 80};
        byte[] b = {72, 79, 83, 88};
        byte[] c = {66, 93, 86, 78};
        BufferPool pool = new BufferPool(10);
        pool.write(f, 4, 0, a); 
        pool.read(f, 4, 0, bytes);
        assertTrue(Arrays.equals(a, bytes));

        pool.write(f, 4, 5000, b); 
        pool.read(f, 4, 5000, bytes);
        assertTrue(Arrays.equals(b, bytes));

        pool.write(f, 4, 10000, c);

        pool.read(f, 4, 10000, bytes);
        assertTrue(Arrays.equals(c, bytes));

        pool.write(f, 4, 5000, a);
        pool.read(f, 4, 5000, bytes);
        assertTrue(Arrays.equals(a, bytes));

        pool.write(f, 4, 0, a);
        pool.write(f, 4, 5000, a);
        pool.write(f, 4, 10000, a);
        pool.write(f, 4, 15000, a);
        pool.write(f, 4, 20000, a);
        pool.write(f, 4, 25000, a);
        pool.write(f, 4, 30000, a);
        pool.write(f, 4, 35000, a);
        pool.write(f, 4, 40000, a);
        pool.write(f, 4, 45000, a);
        pool.write(f, 4, 45000, b);
        pool.read(f, 4, 45000, bytes);
        assertTrue(Arrays.equals(b, bytes));
        pool.write(f, 4, 5000, a);
        pool.read(f, 4, 5000, bytes);
        for (int i = 0; i < pool.blox.length; i++)
        {
            pool.flush(pool.blox[i]);
        }                    
        pool = new BufferPool(3);
        f.close();
        assertTrue(Arrays.equals(a, bytes));
    }

    /**
     * tests write from buffer
     * @throws IOException
     */
    public void testWritefromBuff() throws IOException 
    {
        RandomAccessFile f = new RandomAccessFile("file", "rw");
        byte[] bytes = new byte[4];
        byte[] b = {(byte)46, (byte)47, (byte)48, (byte)49};
        buf.write(f, 4, 5000, b);
        buf.flush(buf.blox[0]);
        f.seek(5000);
        f.read(bytes);
        assertTrue(Arrays.equals(bytes, b));

        f.close();
    }

    /**
     * tests read method
     * @throws IOException
     */
    public void testRead() throws IOException
    {
        RandomAccessFile f = new RandomAccessFile("file", "rw");
        byte[] bytes = new byte[4]; 
        byte[] b = {(byte)12, (byte)9, (byte)3, (byte)8};
        buf.write(f, 4, 0, b);
        buf.write(f, 4, 5000, b);
        buf.write(f, 4, 10000, b); 
        buf.read(f, 4, 0, bytes);
        assertTrue(Arrays.equals(b, bytes));

        for (int i = 0; i < buf.blox.length; i++)
        {
            buf.flush(buf.blox[i]);
            System.out.println(i);
        }
        buf.read(f, 4, 5000, bytes); 
        assertTrue(Arrays.equals(bytes, b));

        f.close();
    }
}