package com.sbt.currency.interactors.transformers;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * Created by Pasenchuk Victor on 01/05/2017
 */
public class CurrencyFormatTransformerTest {

    private final CurrencyFormatTransformer currencyFormatTransformer = new CurrencyFormatTransformer();

    @Test
    public void testRead() throws Exception {

        assertEquals(currencyFormatTransformer.read("0"), 0d);
        assertEquals(currencyFormatTransformer.read("1"), 1d);
        assertEquals(currencyFormatTransformer.read("-1"), -1d);
        assertEquals(currencyFormatTransformer.read("1,0"), 1d);
        assertEquals(currencyFormatTransformer.read("42,6125"), 42.6125);
    }

    @Test
    public void testWrite() throws Exception {

        assertEquals(currencyFormatTransformer.write(0d), "0");
        assertEquals(currencyFormatTransformer.write(1d), "1");
        assertEquals(currencyFormatTransformer.write(-1d), "-1");
        assertEquals(currencyFormatTransformer.write(1.0), "1");
        assertEquals(currencyFormatTransformer.write(42.6125), "42,61");

    }

}