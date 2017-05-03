package com.sbt.currency.domain;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by Pasenchuk Victor on 28/04/2017
 */
@Root(name = "Valute")
public class Valute {

    public static final int ROUBLE_NUM_CODE = 810;

    @Element(name = "NumCode")
    int numCode;

    @Element(name = "CharCode")
    String charCode;

    @Element(name = "Nominal")
    int nominal;

    @Element(name = "Name")
    String name;

    @Element(name = "Value")
    Double value;

    @Attribute(name = "ID")
    String id;

    public static Valute defaultRubInstance() {
        final Valute valute = new Valute();

        valute.numCode = ROUBLE_NUM_CODE;
        valute.charCode = "RUB";
        valute.nominal = 1;
        valute.name = "Российский рубль";
        valute.value = 1.0;

        return valute;
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

    public int getNominal() {
        return nominal;
    }

    public void setNominal(int nominal) {
        this.nominal = nominal;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
