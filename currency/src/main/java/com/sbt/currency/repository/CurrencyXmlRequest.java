package com.sbt.currency.repository;

import com.sbt.currency.exceptions.RequestError;
import com.sbt.currency.interactors.Subscriber;
import com.sbt.currency.interactors.Subscribtion;

/**
 * Created by Pasenchuk Victor on 29/04/2017
 */

public interface CurrencyXmlRequest extends Subscribtion {
    Subscribtion fetchXmlData(Subscriber<String, RequestError> subscriber);
}
