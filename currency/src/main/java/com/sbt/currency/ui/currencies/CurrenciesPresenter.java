package com.sbt.currency.ui.currencies;

import android.text.TextUtils;

import com.sbt.currency.R;
import com.sbt.currency.di.AppModule;
import com.sbt.currency.domain.DisplayCurrency;
import com.sbt.currency.domain.DisplayCurrencyFactory;
import com.sbt.currency.domain.ValCurs;
import com.sbt.currency.domain.Valute;
import com.sbt.currency.exceptions.RequestError;
import com.sbt.currency.interactors.CurrenciesInteractor;
import com.sbt.currency.interactors.Subscriber;
import com.sbt.currency.interactors.Subscribtion;
import com.sbt.currency.interactors.transformers.AmountFormatTransformer;
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
    private final AmountFormatTransformer amountFormatTransformer;
    Valute primaryCurrency, secondaryCurrency;
    private Subscribtion subscribtion;
    private List<Valute> rawCurrencies;
    private String query = "";
    private boolean hasData = false;

    public CurrenciesPresenter(CurrenciesView currenciesView, AppModule appModule) {
        this.currenciesView = currenciesView;

        currenciesInteractor = appModule.getCurrenciesInteractor();
        localRepository = appModule.getLocalRepository();
        loggingRepository = appModule.getLoggingRepository();

        defaultRubInstance = Valute.defaultRubInstance();
        amountFormatTransformer = new AmountFormatTransformer();

    }

    public void onStart() {

        presetMainCurrency();

        presetAmount();

        queryForCurrencies();

    }

    private void queryForCurrencies() {
        subscribtion = currenciesInteractor.enqueueCurrencies(new Subscriber<ValCurs, RequestError>() {
            @Override
            public void onNext(ValCurs valCurs) {
                onCurrenciesResponse(valCurs);
            }

            @Override
            public void onError(RequestError requestError) {

            }

            @Override
            public void onComplete() {
            }
        });
    }

    private void onCurrenciesResponse(ValCurs valCurs) {
        hasData = true;
        rawCurrencies = valCurs.getValute();
        if (currenciesView.isViewVisible()) {

            updateCurrencies();
        }
    }

    private void updateCurrencies() {

        initMainCurrencies();

        if (hasData) {
            showMainCurrencies();
            initListCurrencies();
        }
    }

    private void showMainCurrencies() {
        currenciesView.showPrimaryCurrency(DisplayCurrencyFactory.getPrimaryCurrency(primaryCurrency));
        currenciesView.showSecondaryCurrency(DisplayCurrencyFactory.getSecondaryCurrency(secondaryCurrency, primaryCurrency, localRepository.getAmount()));
    }

    private void initMainCurrencies() {
        final int primaryCurrencyId = localRepository.getPrimaryCurrencyId();
        final int secondaryCurrencyId = localRepository.getSecondaryCurrencyId();

        for (Valute rawCurrency : rawCurrencies) {
            if (rawCurrency.getNumCode() == primaryCurrencyId)
                primaryCurrency = rawCurrency;
            else if (rawCurrency.getNumCode() == secondaryCurrencyId)
                secondaryCurrency = rawCurrency;
        }
        if (primaryCurrency == null)
            primaryCurrency = defaultRubInstance;

        if (secondaryCurrency == null)
            if (rawCurrencies.size() > 0)
                secondaryCurrency = rawCurrencies.get(0);
            else
                hasData = false;
    }

    private void initListCurrencies() {
        final ArrayList<DisplayCurrency> displayCurrencies = new ArrayList<>(rawCurrencies.size() - 1);

        if (filterCurrency(defaultRubInstance))
            displayCurrencies.add(DisplayCurrencyFactory.getListCurrency(defaultRubInstance, primaryCurrency));

        for (Valute rawCurrency : rawCurrencies)
            if (filterCurrency(rawCurrency))
                displayCurrencies.add(DisplayCurrencyFactory.getListCurrency(rawCurrency, primaryCurrency));

        currenciesView.updateCurrencies(displayCurrencies);
    }

    boolean filterCurrency(Valute valute) {
        if (valute.getNumCode() == primaryCurrency.getNumCode())
            return false;
        else if (valute.getNumCode() == secondaryCurrency.getNumCode())
            return false;
        else if (TextUtils.isEmpty(query))
            return true;
        else if (valute.getCharCode().toLowerCase().contains(query.toLowerCase()))
            return true;
        else if (valute.getName().toLowerCase().contains(query.toLowerCase()))
            return true;
        else
            return false;
    }


    private void presetMainCurrency() {
        final int primaryCurrencyId = localRepository.getPrimaryCurrencyId();
        if (primaryCurrencyId == LocalRepository.NO_ID || primaryCurrencyId == defaultRubInstance.getNumCode())
            currenciesView.showPrimaryCurrency(DisplayCurrencyFactory.getPrimaryCurrency(defaultRubInstance));
    }

    private void presetAmount() {
        final float amount = localRepository.getAmount();
        setAmount(amount);
    }

    private void setAmount(double amount) {
        currenciesView.setAmount(amountFormatTransformer.write(amount));
    }


    public void onFabClicked() {
        final Valute currency = primaryCurrency;
        primaryCurrency = secondaryCurrency;
        secondaryCurrency = currency;
        updateCurrencies();
    }

    public void onAmountChanged(String amount) {
        if (hasData)
            if (TextUtils.isEmpty(amount.trim())) {
                localRepository.setAmount(0);
                currenciesView.showSecondaryCurrency(DisplayCurrencyFactory.getSecondaryCurrency(secondaryCurrency, primaryCurrency, 0));
            } else {
                try {
                    final float v = Float.parseFloat(amount);
                    if (v < 0)
                        currenciesView.showToast(R.string.non_positive_number);
                    else {
                        localRepository.setAmount(v);
                        currenciesView.showSecondaryCurrency(DisplayCurrencyFactory.getSecondaryCurrency(secondaryCurrency, primaryCurrency, v));
                    }
                } catch (NumberFormatException e) {
                    currenciesView.showToast(R.string.wrong_number);
                }
            }
    }

    public void onSearchChanged(String s) {
        query = s.trim();
        if (hasData)
            initListCurrencies();
    }

    public void onCurrencySelected(int numCode) {
        localRepository.setSecondaryCurrencyId(numCode);
        updateCurrencies();
    }
}
