package com.sbt.currency.domain;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;

/**
 * Created by Pasenchuk Victor on 28/04/2017
 */
public class Valute {

    @Element(name = "NumCode", required = false)
    String numCode;


    @Element(name = "CharCode", required = false)
    String charCode;


    @Element(name = "Nominal", required = false)
    String nominal;


    @Element(name = "Name", required = false)
    String name;


    @Element(name = "Value", required = false)
    String value;


    @Attribute(name = "ID", required = false)
    String iD;


    public String getNumCode() {
        return this.numCode;
    }

    public void setNumCode(String _value) {
        this.numCode = _value;
    }


    public String getCharCode() {
        return this.charCode;
    }

    public void setCharCode(String _value) {
        this.charCode = _value;
    }


    public String getNominal() {
        return this.nominal;
    }

    public void setNominal(String _value) {
        this.nominal = _value;
    }


    public String getName() {
        return this.name;
    }

    public void setName(String _value) {
        this.name = _value;
    }


    public String getValue() {
        return this.value;
    }

    public void setValue(String _value) {
        this.value = _value;
    }


    public String getID() {
        return this.iD;
    }

    public void setID(String _value) {
        this.iD = _value;
    }


}
