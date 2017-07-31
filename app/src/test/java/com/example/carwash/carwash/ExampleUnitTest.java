package com.example.carwash.carwash;

import com.example.carwash.carwash.utils.DateUtils;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public  void getMonth() {
        String month = DateUtils.getMonthFromDate(new Date());
        assertEquals("MAY",month);
    }

    @Test
    public void testValidDate() {
        try {
            Date date = DateUtils.parseToDate("2012-05-05", DateUtils.PATTERN_YYYY_MM_DD);
            assertNotNull(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}