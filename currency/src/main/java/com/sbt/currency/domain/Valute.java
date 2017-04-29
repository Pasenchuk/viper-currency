package com.sbt.currency.domain;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;

/**
 * Created by Pasenchuk Victor on 28/04/2017
 */
public class Valute {

    @Element(name = "NumCode")
    int numCode;

    @Element(name = "CharCode")
    String charCode;

    @Element(name = "Nominal")
    int nominal;

    @Element(name = "Name")
    String name;

    @Element(name = "Value")
    double value;
    
    @Attribute(name = "ID")
    String id;

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
