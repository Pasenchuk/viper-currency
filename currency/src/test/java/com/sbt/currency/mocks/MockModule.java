package com.sbt.currency.mocks;

import android.content.Context;
import android.content.SharedPreferences;

import com.sbt.currency.di.AppModule;
import com.sbt.currency.exceptions.RequestError;
import com.sbt.currency.interactors.CurrenciesInteractor;
import com.sbt.currency.interactors.Subscriber;
import com.sbt.currency.interactors.Subscribtion;
import com.sbt.currency.repository.CurrencyXmlRequest;
import com.sbt.currency.repository.LocalRepository;
import com.sbt.currency.repository.LoggingRepository;
import com.sbt.currency.repository.NetworkRepository;
import com.sbt.currency.repository.implementations.LocalRepositoryImpl;

import static com.sbt.currency.repository.implementations.LocalRepositoryImpl.CURRENCIES_PREFS;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by Pasenchuk Victor on 07/05/2017
 */

public class MockModule implements AppModule {
    @Override
    public LocalRepository getLocalRepository() {

        final Context context = mock(Context.class);
        final SharedPreferences sharedPreferences = new SharedPreferencesMock().getSharedPreferences();
        when(context.getSharedPreferences(CURRENCIES_PREFS, Context.MODE_PRIVATE)).thenReturn(sharedPreferences);
        return new LocalRepositoryImpl(context);
    }

    @Override
    public NetworkRepository getNetworkRepository() {
        return new NetworkRepository() {
            boolean subscribed;
            boolean firstLoad = true;

            @Override
            public CurrencyXmlRequest getCurrencyXmlRequest() {
                return new CurrencyXmlRequest() {
                    
                    @Override
                    public Subscribtion fetchXmlData(final Subscriber<String, RequestError> subscriber) {

                        if (firstLoad) {
                            firstLoad = false;
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        Thread.sleep(1000);
                                        if (subscribed) {
                                            subscriber.onNext(
                                                    "<?xml version=\"1.0\" encoding=\"windows-1251\" ?>" +
                                                            "<ValCurs Date=\"06.05.2017\" name=\"Foreign Currency Market\">\n" +
                                                            "<Valute ID=\"R01010\">\n" +
                                                            "<NumCode>036</NumCode>\n" +
                                                            "<CharCode>AUD</CharCode>\n" +
                                                            "<Nominal>1</Nominal>\n" +
                                                            "<Name>Австралийский доллар</Name>\n" +
                                                            "<Value>43,3007</Value>\n" +
                                                            "</Valute>\n" +
                                                            "<Valute ID=\"R01020A\">\n" +
                                                            "<NumCode>944</NumCode>\n" +
                                                            "<CharCode>AZN</CharCode>\n" +
                                                            "<Nominal>1</Nominal>\n" +
                                                            "<Name>Азербайджанский манат</Name>\n" +
                                                            "<Value>34,3817</Value>\n" +
                                                            "</ValCurs>"
                                            );
                                            subscribed = false;
                                        }

                                    } catch (InterruptedException e) {
                                        subscribed = false;

                                        subscriber.onError(new RequestError(e, RequestError.Kind.UNKNOWN_ERROR));
                                    }
                                }
                            });
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
            }
        };
    }

    @Override
    public LoggingRepository getLoggingRepository() {
        return new LoggingRepository() {
            @Override
            public void log(String tag, Object message) {
                System.out.println("Tag: " + tag + " : " + String.valueOf(message));
            }

            @Override
            public void log(Object message) {
                System.out.println("Tag: " + new Exception().getStackTrace()[1].getClassName() + " : " + String.valueOf(message));
            }

            @Override
            public void logError(String tag, Object message) {
                System.out.println("ERROR: Tag: " + tag + " : " + String.valueOf(message));
            }

            @Override
            public void logError(Object message) {
                System.out.println("ERROR: Tag: " + new Exception().getStackTrace()[1].getClassName() + " : " + String.valueOf(message));
            }
        };
    }

    @Override
    public CurrenciesInteractor getCurrenciesInteractor() {
        return new CurrenciesInteractor(this);
    }
}
