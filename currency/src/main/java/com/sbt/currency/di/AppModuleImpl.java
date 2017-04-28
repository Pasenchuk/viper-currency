package com.sbt.currency.di;

import android.content.Context;

import com.sbt.currency.repository.CurrencyXmlRequest;
import com.sbt.currency.repository.LocalRepository;
import com.sbt.currency.repository.NetworkRepository;
import com.sbt.currency.repository.implementations.CurrencyXmlRequestImpl;
import com.sbt.currency.repository.implementations.LocalRepositoryImpl;

/**
 * Created by Pasenchuk Victor on 29/04/2017
 */

public class AppModuleImpl implements AppModule {

    private final LocalRepository localRepository;

    public AppModuleImpl(Context context) {
        localRepository = new LocalRepositoryImpl(context);
    }

    @Override
    public LocalRepository getLocalRepository() {
        return localRepository;
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
