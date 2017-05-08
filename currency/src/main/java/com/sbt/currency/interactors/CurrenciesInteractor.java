package com.sbt.currency.interactors;

import com.sbt.currency.di.AppModule;
import com.sbt.currency.domain.ValCurs;
import com.sbt.currency.exceptions.RequestError;
import com.sbt.currency.interactors.transformers.CurrencyFormatTransformer;
import com.sbt.currency.interactors.transformers.DateFormatTransformer;
import com.sbt.currency.repository.LocalRepository;
import com.sbt.currency.repository.LoggingRepository;
import com.sbt.currency.repository.NetworkRepository;

import org.simpleframework.xml.core.Persister;
import org.simpleframework.xml.transform.RegistryMatcher;

import java.util.Date;

/**
 * Created by Pasenchuk Victor on 29/04/2017
 */

public class CurrenciesInteractor {

    final LocalRepository localRepository;
    final LoggingRepository loggingRepository;
    private final NetworkRepository networkRepository;
    private final Persister serializer;

    public CurrenciesInteractor(AppModule appModule) {
        localRepository = appModule.getLocalRepository();
        networkRepository = appModule.getNetworkRepository();
        loggingRepository = appModule.getLoggingRepository();

        serializer = initSerializer();
    }

    private Persister initSerializer() {

        RegistryMatcher registryMatcher = new RegistryMatcher();
        registryMatcher.bind(Date.class, new DateFormatTransformer());
        registryMatcher.bind(Double.class, new CurrencyFormatTransformer());

        return new Persister(registryMatcher);
    }

    public Subscribtion enqueueCurrencies(final Subscriber<ValCurs, RequestError> subscriber) {
        final String localCurrencyXml = localRepository.getCurrencyXml();

        if (localCurrencyXml != null) {
            try {
                subscriber.onNext(getCurrenciesFromXml(localCurrencyXml));
            } catch (Exception e) {
                loggingRepository.logError("Can't load XML from preferences");
            }
        }
        loggingRepository.log("No XML in preferences");

        return networkRepository
                .getCurrencyXmlRequest()
                .fetchXmlData(new Subscriber<String, RequestError>() {
                    @Override
                    public void onNext(String s) {
                        onNetworkResult(s, subscriber);
                    }

                    @Override
                    public void onError(RequestError requestError) {
                        subscriber.onError(requestError);
                    }

                    @Override
                    public void onComplete() {
                        subscriber.onComplete();
                    }
                });
    }


    void onNetworkResult(String s, final Subscriber<ValCurs, RequestError> subscriber) {
        if (s != null) {
            try {
                subscriber.onNext(getCurrenciesFromXml(s));
                localRepository.setCurrencyXml(s);
                subscriber.onComplete();
            } catch (Exception e) {
                loggingRepository.logError("Can't parse XML from network");
                subscriber.onError(new RequestError(e, RequestError.Kind.XML_PARSE_ERROR));
            }
        } else {
            loggingRepository.logError("NULL XML from network");
            subscriber.onError(new RequestError(new IllegalStateException(), RequestError.Kind.XML_PARSE_ERROR));
        }
    }


    public ValCurs getCurrenciesFromXml(String currencyXml) throws Exception {
        return serializer.read(ValCurs.class, currencyXml);
    }

}
