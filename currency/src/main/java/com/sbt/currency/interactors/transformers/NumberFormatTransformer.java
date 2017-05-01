package com.sbt.currency.interactors.transformers;

import org.simpleframework.xml.transform.Transform;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created by Pasenchuk Victor on 29/04/2017
 */
public class NumberFormatTransformer implements Transform<Double> {

    private final NumberFormat numberFormat;

    public NumberFormatTransformer() {
        numberFormat = NumberFormat.getInstance(new Locale("Ru"));
    }

    @Override
    public Double read(String value) throws Exception {
        Number number = numberFormat.parse(value);
        return number.doubleValue();
    }

    @Override
    public String write(Double value) throws Exception {
        return numberFormat.format(value);
    }

}