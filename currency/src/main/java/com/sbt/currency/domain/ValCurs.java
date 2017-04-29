package com.sbt.currency.domain;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.Date;
import java.util.List;

/**
 * Created by Pasenchuk Victor on 28/04/2017
 */

@Root(name = "ValCurs")
public class ValCurs {

    @ElementList(name = "Valute", inline = true, required = false)
    List<Valute> valute;


    @Attribute(name = "Date", required = false)
    Date date;


    @Attribute(name = "name", required = false)
    String name;

    public List<Valute> getValute() {
        return valute;
    }

    public void setValute(List<Valute> valute) {
        this.valute = valute;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}