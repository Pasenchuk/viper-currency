package com.sbt.currency.models;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

/**
 * Created by Pasenchuk Victor on 28/04/2017
 */

@Root(name = "ValCurs")
public class ValCurs {

    @ElementList(name = "Valute", inline = true, required = false)
    List<Valute> valute;


    @Attribute(name = "Date", required = false)
    String date;


    @Attribute(name = "name", required = false)
    String name;


    public List<Valute> getValute() {
        return this.valute;
    }

    public void setValute(List<Valute> _value) {
        this.valute = _value;
    }


    public String getDate() {
        return this.date;
    }

    public void setDate(String _value) {
        this.date = _value;
    }


    public String getName() {
        return this.name;
    }

    public void setName(String _value) {
        this.name = _value;
    }


}