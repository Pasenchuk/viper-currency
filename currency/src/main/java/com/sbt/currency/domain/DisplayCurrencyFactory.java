package com.sbt.currency.domain;

import com.sbt.currency.interactors.transformers.CurrencyFormatTransformer;

/**
 * Created by Pasenchuk Victor on 02/05/2017
 */

public class DisplayCurrencyFactory {

    private static CurrencyFormatTransformer currencyFormatTransformer = new CurrencyFormatTransformer();

    public static DisplayCurrency getPrimaryCurrency(Valute currency) {
        final DisplayCurrency displayCurrency = new DisplayCurrency();
        displayCurrency.numCode = currency.getNumCode();
        displayCurrency.charCode = currency.getCharCode();
        displayCurrency.name = currency.getName();
        displayCurrency.nominal = String.valueOf(currency.getNominal());
        return displayCurrency;
    }

    public static DisplayCurrency getListCurrency(Valute currency, Valute primaryCurrency) {
        final DisplayCurrency displayCurrency = getPrimaryCurrency(currency);

        displayCurrency.primaryCharCode = primaryCurrency.getCharCode();
        displayCurrency.displayNominalValue = currencyFormatTransformer.write(getExchangeNominalValue(currency, primaryCurrency));
        return displayCurrency;
    }

    public static DisplayCurrency getSecondaryCurrency(Valute currency, Valute primaryCurrency, double amount) {
        final DisplayCurrency displayCurrency = getListCurrency(currency, primaryCurrency);

        displayCurrency.displayValue = currencyFormatTransformer.write(getExchangeValue(currency, primaryCurrency, amount));
        return displayCurrency;
    }


    static double getExchangeValue(Valute currency, Valute convertTo, double amount) {
        return (currency.value / currency.nominal) / (convertTo.value / convertTo.nominal) * amount;
    }

    static double getExchangeNominalValue(Valute currency, Valute convertTo) {
        return currency.value / convertTo.value;
    }

}
