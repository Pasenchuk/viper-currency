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


    public Date getDate() {
        return date;
    }


    public String getName() {
        return name;
    }


}