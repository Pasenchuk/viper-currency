package com.sbt.currency.di;

import android.content.Context;

import com.sbt.currency.repository.LoggingRepository;
import com.sbt.currency.repository.CurrencyXmlRequest;
import com.sbt.currency.repository.LocalRepository;
import com.sbt.currency.repository.implementations.LoggingRepositoryImpl;
import com.sbt.currency.repository.NetworkRepository;
import com.sbt.currency.repository.implementations.CurrencyXmlRequestImpl;
import com.sbt.currency.repository.implementations.LocalRepositoryImpl;

/**
 * Created by Pasenchuk Victor on 29/04/2017
 */

public class AppModuleImpl implements AppModule {

    private final LocalRepository localRepository;
    private final LoggingRepository loggingRepository;

    public AppModuleImpl(Context context) {
        localRepository = new LocalRepositoryImpl(context);
        loggingRepository = new LoggingRepositoryImpl();
    }

    @Override
    public LocalRepository getLocalRepository() {
        return localRepository;
    }

    public LoggingRepository getLoggingRepository() {
        return loggingRepository;
    }

    @Override
    public NetworkRepository getNetworkRepository() {
        return new NetworkRepository() {
            @Override
            public CurrencyXmlRequest getCurrencyXmlRequest() {
                return new CurrencyXmlRequestImpl();
            }
        };
    }
}
