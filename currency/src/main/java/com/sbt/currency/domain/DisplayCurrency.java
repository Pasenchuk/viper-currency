package com.sbt.currency.domain;

import com.sbt.currency.interactors.transformers.CurrencyFormatTransformer;

/**
 * Created by Pasenchuk Victor on 02/05/2017
 */

public class DisplayCurrency {

    private int numCode;

    private String charCode = "";

    private String name = "";

    private String nominal = "";

    private String displayValue = "";

    private String displayNominalValue = "";

    private String primaryCharCode;
    private CurrencyFormatTransformer currencyFormatTransformer = new CurrencyFormatTransformer();

    public DisplayCurrency(Valute currency) {
        numCode = currency.getNumCode();
        charCode = currency.getCharCode();
        name = currency.getName();
        nominal = String.valueOf(currency.getNominal());
    }

    public DisplayCurrency(Valute currency, Valute convertTo) {
        this(currency);

        primaryCharCode = convertTo.getCharCode();
        displayNominalValue = currencyFormatTransformer.write(getExchangeNominalValue(currency, convertTo));
    }

    public DisplayCurrency(Valute currency, Valute convertTo, double amount) {
        this(currency, convertTo);
        
        displayValue = currencyFormatTransformer.write(getExchangeValue(currency, convertTo, amount));
    }


    static double getExchangeValue(Valute currency, Valute convertTo, double amount) {
        return (currency.value / currency.nominal) / (convertTo.value / convertTo.nominal) * amount;
    }

    static double getExchangeNominalValue(Valute currency, Valute convertTo) {
        return currency.value / convertTo.value;
    }

    public int getNumCode() {
        return numCode;
    }

    public void setNumCode(int numCode) {
        this.numCode = numCode;
    }

    public String getCharCode() {
        return charCode;
    }

    public void setCharCode(String charCode) {
        this.charCode = charCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNominal() {
        return nominal;
    }

    public void setNominal(String nominal) {
        this.nominal = nominal;
    }

    public String getExchangeValue() {
        return displayValue;
    }

    public String getDisplayNominalValue() {
        return displayNominalValue;
    }

    public void setDisplayNominalValue(String displayNominalValue) {
        this.displayNominalValue = displayNominalValue;
    }

    public void setDisplayValue(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getPrimaryCharCode() {
        return primaryCharCode;
    }

    public void setPrimaryCharCode(String primaryCharCode) {
        this.primaryCharCode = primaryCharCode;
    }

}
