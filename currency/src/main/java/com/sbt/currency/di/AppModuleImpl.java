package com.sbt.currency.di;

import android.content.Context;

import com.sbt.currency.interactors.CurrenciesInteractor;
import com.sbt.currency.repository.LocalRepository;
import com.sbt.currency.repository.LoggingRepository;
import com.sbt.currency.repository.NetworkRepository;
import com.sbt.currency.repository.implementations.CurrencyXmlRequestImpl;
import com.sbt.currency.repository.implementations.LocalRepositoryImpl;
import com.sbt.currency.repository.implementations.LoggingRepositoryImpl;

/**
 * Created by Pasenchuk Victor on 29/04/2017
 */

public class AppModuleImpl implements AppModule {

    private final LocalRepository localRepository;
    private final LoggingRepository loggingRepository;
    private final CurrenciesInteractor currenciesInteractor;

    public AppModuleImpl(Context context) {
        localRepository = new LocalRepositoryImpl(context);
        loggingRepository = new LoggingRepositoryImpl();
        currenciesInteractor = new CurrenciesInteractor(this);
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
        return CurrencyXmlRequestImpl::new;
    }

    @Override
    public CurrenciesInteractor getCurrenciesInteractor() {
        return currenciesInteractor;
    }

}
