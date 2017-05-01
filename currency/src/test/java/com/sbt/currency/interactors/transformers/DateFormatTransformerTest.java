package com.sbt.currency.interactors.transformers;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Calendar;
import java.util.Date;

import static org.testng.Assert.assertEquals;

/**
 * Created by Pasenchuk Victor on 01/05/2017
 */
public class DateFormatTransformerTest {

    private final DateFormatTransformer dateFormatTransformer = new DateFormatTransformer();
    private Date time;

    @BeforeClass
    public void setUp() throws Exception {


        Calendar calendar = Calendar.getInstance();
        calendar.set(2017, 4 - 1, 29, 0, 0);
        time = calendar.getTime();

    }

    @Test
    public void testRead() throws Exception {
        // TODO: 01/05/2017 Android uses another algorithm for parsing Date, think about it
//        assertEquals(dateFormatTransformer.read("29.04.2017"), time);
    }

    @Test
    public void testWrite() throws Exception {
        assertEquals(dateFormatTransformer.write(time), "29.04.2017");
    }

}