package com.sbt.currency.interactors.transformers;

import org.simpleframework.xml.transform.Transform;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created by Pasenchuk Victor on 29/04/2017
 */
public class AmountFormatTransformer implements Transform<Double> {


    final NumberFormat numberFormat;

    public AmountFormatTransformer() {
        numberFormat = NumberFormat.getInstance(new Locale("Ru"));
    }

    @Override
    public Double read(String value) throws Exception {
        Number number = numberFormat.parse(value);
        return number.doubleValue();
    }

    @Override
    public String write(Double value) {
        return numberFormat.format(value);
    }

}