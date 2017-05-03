package com.sbt.currency.domain;

/**
 * Created by Pasenchuk Victor on 02/05/2017
 */

public class DisplayCurrency {

    private int numCode;

    private String charCode;

    private String name;

    private String nominal;

    private String displayValue;

    private String primaryCharCode;

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
