package com.sbt.currency.repository;

/**
 * Created by Pasenchuk Victor on 29/04/2017
 */

public interface LocalRepository {

    int NO_ID = -1, DOLLAR_ID = 840;

    String getCurrencyXml();

    void setCurrencyXml(String currencyXmlKey);

    int getPrimaryCurrencyId();

    void setPrimaryCurrencyId(int id);

    int getSecondaryCurrencyId();

    void setSecondaryCurrencyId(int id);

    float getAmount();

    void setAmount(float amount);

    void clear();

}
