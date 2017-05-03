package com.sbt.currency.interactors.transformers;

import org.simpleframework.xml.transform.Transform;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created by Pasenchuk Victor on 29/04/2017
 */
public class CurrencyFormatTransformer implements Transform<Double> {

    private static final int MAXIMUM_CURRENCY_FRACTION_DIGITS = 2;
    private static final int MINIMUM_CURRENCY_FRACTION_DIGITS = 2;

    private final NumberFormat numberFormat;

    public CurrencyFormatTransformer() {
        numberFormat = NumberFormat.getInstance(new Locale("Ru"));
        numberFormat.setMaximumFractionDigits(MAXIMUM_CURRENCY_FRACTION_DIGITS);
        numberFormat.setMinimumFractionDigits(MINIMUM_CURRENCY_FRACTION_DIGITS);
    }

    @Override
    public Double read(String value) throws Exception {
        Number number = numberFormat.parse(value);
        return number.doubleValue();
    }

    @Override
    public String write(Double value)  {
        return numberFormat.format(value);
    }

}