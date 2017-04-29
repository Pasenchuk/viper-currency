package com.sbt.currency.interactors;

import com.sbt.currency.di.AppModule;
import com.sbt.currency.domain.ValCurs;
import com.sbt.currency.exceptions.RequestError;
import com.sbt.currency.repository.LocalRepository;
import com.sbt.currency.repository.LoggingRepository;
import com.sbt.currency.repository.NetworkRepository;

import org.simpleframework.xml.core.Persister;

/**
 * Created by Pasenchuk Victor on 29/04/2017
 */

public class CurrenciesInteractor {

    private final LocalRepository localRepository;
    private final NetworkRepository networkRepository;
    private final Persister persister;
    private final LoggingRepository loggingRepository;

    public CurrenciesInteractor(AppModule appModule) {
        localRepository = appModule.getLocalRepository();
        networkRepository = appModule.getNetworkRepository();
        loggingRepository = appModule.getLoggingRepository();
        persister = new Persister();
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
                        if (s != null) {
                            try {
                                subscriber.onNext(getCurrenciesFromXml(s));
                                localRepository.setCurrencyXml(s);
                            } catch (Exception e) {
                                loggingRepository.logError("Can't load XML from network");
                                subscriber.onError(new RequestError(null, RequestError.Kind.XML_PARSE_ERROR));
                            }
                        } else
                            subscriber.onError(new RequestError(null, RequestError.Kind.XML_PARSE_ERROR));
                    }

                    @Override
                    public void onError(RequestError requestError) {
                        subscriber.onError(requestError);
                    }
                });
    }

    private ValCurs getCurrenciesFromXml(String currencyXml) throws Exception {
        return persister.read(ValCurs.class, currencyXml);
    }

}
