package com.sbt.currency.interactors.transformers;

import org.simpleframework.xml.transform.Transform;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created by Pasenchuk Victor on 29/04/2017
 */
public class CurrencyFormatTransformer  extends AmountFormatTransformer {

    private static final int MAXIMUM_CURRENCY_FRACTION_DIGITS = 2;
    private static final int MINIMUM_CURRENCY_FRACTION_DIGITS = 2;


    public CurrencyFormatTransformer() {
        super();
        numberFormat.setMaximumFractionDigits(MAXIMUM_CURRENCY_FRACTION_DIGITS);
        numberFormat.setMinimumFractionDigits(MINIMUM_CURRENCY_FRACTION_DIGITS);
    }

}