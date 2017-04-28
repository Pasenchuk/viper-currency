package com.sbt.currency.repository;

import com.sbt.currency.interactors.Subscriber;
import com.sbt.currency.interactors.Subscribtion;
import com.sbt.currency.exceptions.RequestError;

/**
 * Created by Pasenchuk Victor on 29/04/2017
 */

public interface CurrencyXmlRequest extends Subscribtion {
    void fetchXmlData(Subscriber<String, RequestError> subscriber);
}
