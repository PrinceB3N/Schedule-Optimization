package edu.ucsb.cs.cs48.schedoptim;

import android.util.Log;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }
    @Test
    public void testtest(){
        System.out.println(JSONUtils.getObjectFromJSON(Schedule.class,"~/data/data/edu.ucsb.cs.cs48.schedoptim/files","test.json"));

    }

}