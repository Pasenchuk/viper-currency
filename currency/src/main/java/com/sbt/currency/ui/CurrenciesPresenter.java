package com.sbt.currency.ui;

import com.sbt.currency.di.AppModule;
import com.sbt.currency.domain.ValCurs;
import com.sbt.currency.exceptions.RequestError;
import com.sbt.currency.interactors.CurrenciesInteractor;
import com.sbt.currency.interactors.Subscriber;
import com.sbt.currency.interactors.Subscribtion;
import com.sbt.currency.repository.LocalRepository;
import com.sbt.currency.repository.LoggingRepository;

/**
 * Created by Pasenchuk Victor on 29/04/2017
 */

public class CurrenciesPresenter {

    private final CurrenciesView currenciesView;

    private final CurrenciesInteractor currenciesInteractor;
    private final LoggingRepository loggingRepository;
    private final LocalRepository localRepository;

    private Subscribtion subscribtion;

    public CurrenciesPresenter(CurrenciesView currenciesView, AppModule appModule) {
        this.currenciesView = currenciesView;

        currenciesInteractor = appModule.getCurrenciesInteractor();
        localRepository = appModule.getLocalRepository();
        loggingRepository = appModule.getLoggingRepository();

    }

    public void onStart() {

        subscribtion = currenciesInteractor.enqueueCurrencies(new Subscriber<ValCurs, RequestError>() {
            @Override
            public void onNext(ValCurs valCurs) {

            }

            @Override
            public void onError(RequestError requestError) {

            }

            @Override
            public void onComplete() {
            }
        });

    }



}
