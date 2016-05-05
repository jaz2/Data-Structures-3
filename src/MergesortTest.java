import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Arrays;

import org.junit.Test;

import student.TestCase;

/**
 * @author CS3114 staff
 * @version 1
 */
public class MergesortTest extends TestCase {

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
        args[0] = "original.txt";
        args[1] = "3"; // Buffer pool size
        args[2] = "statFile.txt";
        //" a"
        FileGenerator f = new FileGenerator();
        CheckFile c = new CheckFile();
        String[] args2 = new String[3];
        args2[0] = "-a";
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
    
    /**
     * Manually written file to test
     * @throws Exception
     */
    public void testSortTo() throws Exception
    {
        RandomAccessFile f = new RandomAccessFile("sortfile", "rw");
        RandomAccessFile t = new RandomAccessFile("tfile", "rw");
        CheckFile c = new CheckFile();
        BufferPool buf = new BufferPool(3);
        f.writeChars("ALPHABET");
        byte[] bytes = new byte[4];
        byte[] b = {88, 70, 73, 80};
        byte[] a = {66, 78, 66, 79};
        
        Mergesort.sort(f, t, 0, ((int)(f.length() / 4) - 1));    
        for (int i = 0; i < buf.blox.length; i++)
        {
            buf.flush(buf.blox[i]);
        }                    
        buf = new BufferPool(3);
        assertTrue(c.checkFile("sortfile"));
        f.close();
    }
    
    /**
     * Tests when b file is sorted
     * @throws Exception
     */
    @Test
    public void testSortB() throws Exception
    {
        String[] args = new String[3];
        args[0] = "original.txt";
        args[1] = "3"; // Buffer pool size
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
    
    /**
     * Tests when another file is sorted
     * @throws Exception
     */
    @Test
    public void testSortFileFalse() throws Exception
    {
        String[] args = new String[3];
        args[0] = "original.txt";
        args[1] = "3"; // Buffer pool size
        args[2] = "statFile.txt";
        //" a"
        FileGenerator f = new FileGenerator();
        CheckFile c = new CheckFile();
        String[] args2 = new String[3];
        args2[0] = "-d";
        args2[1] = args[0]; 
        args2[2] = args[1];
        f.generateFile(args2);
        assertFalse(args[0].isEmpty());
    }
    
    /**
     * Test when not sorted for check
     * file
     * 
     * @throws Exception
     */
    public void testSortCheckFalse() throws Exception
    {
        RandomAccessFile f = new RandomAccessFile("sortfile", "rw");
        RandomAccessFile t = new RandomAccessFile("tfile", "rw");
        CheckFile c = new CheckFile();
        BufferPool buf = new BufferPool(3);
        f.writeChars("ALPHABET");
        byte[] bytes = new byte[4];
        byte[] b = {88, 70, 73, 80};
        byte[] a = {66, 78, 66, 79};
   
        for (int i = 0; i < buf.blox.length; i++)
        {
            buf.flush(buf.blox[i]);
        }                    
        buf = new BufferPool(3);
        assertFalse(c.checkFile("sortfile"));
        f.close();
    }
}