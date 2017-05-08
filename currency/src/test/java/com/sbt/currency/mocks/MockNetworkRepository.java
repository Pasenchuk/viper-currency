package com.sbt.currency.mocks;

import com.sbt.currency.exceptions.RequestError;
import com.sbt.currency.interactors.Subscriber;
import com.sbt.currency.interactors.Subscribtion;
import com.sbt.currency.repository.CurrencyXmlRequest;
import com.sbt.currency.repository.NetworkRepository;

import org.mockito.Mockito;

import java.io.IOError;

/**
 * Created by Pasenchuk Victor on 08/05/2017
 */

public class MockNetworkRepository implements NetworkRepository {
    private boolean returnError = false;
    private String xmlForReturn = Currencies.CURRENCY_XML;


    private boolean subscribed;
    private boolean firstLoad = true;

    @Override
    public CurrencyXmlRequest getCurrencyXmlRequest() {
        final CurrencyXmlRequest currencyXmlRequest = new CurrencyXmlRequest() {

            @Override
            public Subscribtion fetchXmlData(final Subscriber<String, RequestError> subscriber) {

                if (firstLoad) {
                    firstLoad = false;
                    subscribed = true;
                    new Thread(() -> {
                        try {
                            Thread.sleep(250);
                            if (subscribed) {
                                if (returnError)
                                    subscriber.onError(new RequestError(new IOError(new IllegalStateException()), RequestError.Kind.IO_ERROR));
                                else
                                    subscriber.onNext(xmlForReturn);
                                subscribed = false;
                            }

                        } catch (InterruptedException e) {
                            subscribed = false;

                            subscriber.onError(new RequestError(e, RequestError.Kind.UNKNOWN_ERROR));
                        }
                    }).start();
                }
                return this;
            }

            @Override
            public boolean isSubscribed() {
                return subscribed;
            }

            @Override
            public void unsubscribe() {
                subscribed = false;
            }
        };
        return Mockito.spy(currencyXmlRequest);
    }

    public void setReturnError(boolean returnError) {
        this.returnError = returnError;
    }

    public void setXmlForReturn(String xmlForReturn) {
        this.xmlForReturn = xmlForReturn;
    }
}