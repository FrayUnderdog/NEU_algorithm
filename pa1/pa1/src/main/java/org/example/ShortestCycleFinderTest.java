package org.example;

import org.junit.Test;
import static org.junit.Assert.*;
import java.io.*;

public class ShortestCycleFinderTest {

    @Test
    public void testCase1() throws Exception {
        String filePath = getClass().getClassLoader().getResource("testcase-1.txt").getPath();
        assertEquals("The length of the shortest cycle is: 14", runShortestCycleFinder(filePath));
    }

    @Test
    public void testCase2() throws Exception {
        String filePath = getClass().getClassLoader().getResource("testcase-2.txt").getPath();
        assertEquals("The length of the shortest cycle is: 9", runShortestCycleFinder(filePath));
    }

    @Test
    public void testCase3() throws Exception {
        String filePath = getClass().getClassLoader().getResource("testcase-3.txt").getPath();
        assertEquals("The length of the shortest cycle is: 17", runShortestCycleFinder(filePath));
    }

    private String runShortestCycleFinder(String filePath) throws Exception {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        ShortestCycleFinder.main(new String[]{filePath});

        System.setOut(originalOut);
        return outputStream.toString().trim();
    }
}
