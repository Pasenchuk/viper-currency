package com.sbt.currency.repository;

/**
 * Created by Pasenchuk Victor on 29/04/2017
 */

public interface LocalRepository {
    String getCurrencyXml();

    void setCurrencyXml(String currencyXmlKey);
}
