package com.sbt.currency.domain;

/**
 * Created by Pasenchuk Victor on 02/05/2017
 */

public class DisplayCurrency {

    int numCode;

    String charCode = "";

    String name = "";

    String nominal = "";

    String displayValue = "";

    String displayNominalValue = "";

    String primaryCharCode = "";

    public int getNumCode() {
        return numCode;
    }

    public String getCharCode() {
        return charCode;
    }

    public String getName() {
        return name;
    }

    public String getNominal() {
        return nominal;
    }

    public String getExchangeValue() {
        return displayValue;
    }

    public String getDisplayNominalValue() {
        return displayNominalValue;
    }

    public String getPrimaryCharCode() {
        return primaryCharCode;
    }

}
