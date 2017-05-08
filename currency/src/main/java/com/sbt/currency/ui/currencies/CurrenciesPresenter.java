package com.sbt.currency.ui.currencies;

import com.sbt.currency.di.AppModule;
import com.sbt.currency.domain.DisplayCurrency;
import com.sbt.currency.domain.DisplayCurrencyFactory;
import com.sbt.currency.domain.ValCurs;
import com.sbt.currency.domain.Valute;
import com.sbt.currency.exceptions.RequestError;
import com.sbt.currency.interactors.CurrenciesInteractor;
import com.sbt.currency.interactors.Subscriber;
import com.sbt.currency.interactors.Subscribtion;
import com.sbt.currency.repository.LocalRepository;
import com.sbt.currency.repository.LoggingRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pasenchuk Victor on 29/04/2017
 */

public class CurrenciesPresenter {

    private final CurrenciesView currenciesView;

    private final CurrenciesInteractor currenciesInteractor;
    private final LoggingRepository loggingRepository;
    private final LocalRepository localRepository;
    private final Valute defaultRubInstance;

    private Subscribtion subscribtion;
    private List<Valute> rawCurrencies;

    public CurrenciesPresenter(CurrenciesView currenciesView, AppModule appModule) {
        this.currenciesView = currenciesView;

        currenciesInteractor = appModule.getCurrenciesInteractor();
        localRepository = appModule.getLocalRepository();
        loggingRepository = appModule.getLoggingRepository();

        defaultRubInstance = Valute.defaultRubInstance();

    }

    public void onStart() {

        subscribtion = currenciesInteractor.enqueueCurrencies(new Subscriber<ValCurs, RequestError>() {
            @Override
            public void onNext(ValCurs valCurs) {
                if (currenciesView.isViewVisible()) {
                    rawCurrencies = valCurs.getValute();

                    rawCurrencies.add(defaultRubInstance);

                    final ArrayList<DisplayCurrency> displayCurrencies = new ArrayList<>(rawCurrencies.size());
                    for (Valute rawCurrency : rawCurrencies)
                        displayCurrencies.add(DisplayCurrencyFactory.getListCurrency(rawCurrency, defaultRubInstance));


                    currenciesView.updateCurrencies(displayCurrencies);
                }
            }

            @Override
            public void onError(RequestError requestError) {

            }

            @Override
            public void onComplete() {
            }
        });

    }


    public void onFabClicked() {

    }

    public void onAmountChanged(String amount) {

    }

    public void onSearchChanged(String s) {

    }

    public void onCurrencySelected(int numCode) {

    }
}
