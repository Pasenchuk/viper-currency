package com.sbt.currency.ui.currencies;

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
import com.sbt.currency.repository.LocalRepository;
import com.sbt.currency.repository.LoggingRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pasenchuk Victor on 29/04/2017
 */

public class CurrenciesPresenter {

    final CurrenciesInteractor currenciesInteractor;
    final LoggingRepository loggingRepository;
    final LocalRepository localRepository;

    private final CurrenciesView currenciesView;
    private final Valute defaultRubInstance;
    Valute primaryCurrency, secondaryCurrency;
    Subscribtion subscribtion;
    List<Valute> rawCurrencies;
    String query = "";
    boolean hasData = false;

    public CurrenciesPresenter(CurrenciesView currenciesView, AppModule appModule) {
        this.currenciesView = currenciesView;

        currenciesInteractor = appModule.getCurrenciesInteractor();
        localRepository = appModule.getLocalRepository();
        loggingRepository = appModule.getLoggingRepository();

        defaultRubInstance = Valute.defaultRubInstance();

    }

    public void onStart() {

        presetMainCurrency();

        presetAmount();

        queryForCurrencies();

    }


    public void onResume() {
        updateCurrencies();
    }

    void queryForCurrencies() {
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

    void onCurrenciesResponse(ValCurs valCurs) {
        hasData = true;
        rawCurrencies = valCurs.getValute();

        initMainCurrencies();

        if (currenciesView.isViewVisible())
            updateCurrencies();
    }

    void updateCurrencies() {
        if (hasData) {
            showMainCurrencies();
            initListCurrencies();
        }
    }

    void showMainCurrencies() {
        currenciesView.showPrimaryCurrency(DisplayCurrencyFactory.getPrimaryCurrency(primaryCurrency));
        currenciesView.showSecondaryCurrency(DisplayCurrencyFactory.getSecondaryCurrency(secondaryCurrency, primaryCurrency, Double.valueOf(localRepository.getAmount())));
    }

    void initMainCurrencies() {
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

    void initListCurrencies() {
        final ArrayList<DisplayCurrency> displayCurrencies = new ArrayList<>(rawCurrencies.size() - 1);

        if (filterCurrency(defaultRubInstance))
            displayCurrencies.add(DisplayCurrencyFactory.getListCurrency(defaultRubInstance, primaryCurrency));

        for (Valute rawCurrency : rawCurrencies)
            if (filterCurrency(rawCurrency))
                displayCurrencies.add(DisplayCurrencyFactory.getListCurrency(rawCurrency, primaryCurrency));

        currenciesView.updateCurrencies(displayCurrencies);
    }

    boolean filterCurrency(Valute valute) {
        if (!hasData)
            return false;
        if (valute.getNumCode() == primaryCurrency.getNumCode())
            return false;
        if (valute.getNumCode() == secondaryCurrency.getNumCode())
            return false;
        if (query.length() == 0)
            return true;
        if (valute.getCharCode().toLowerCase().contains(query.toLowerCase()))
            return true;
        if (valute.getName().toLowerCase().contains(query.toLowerCase()))
            return true;

        return false;
    }


    void presetMainCurrency() {
        final int primaryCurrencyId = localRepository.getPrimaryCurrencyId();
        if (primaryCurrencyId == LocalRepository.NO_ID || primaryCurrencyId == defaultRubInstance.getNumCode())
            currenciesView.showPrimaryCurrency(DisplayCurrencyFactory.getPrimaryCurrency(defaultRubInstance));
    }

    void presetAmount() {
        setAmount(localRepository.getAmount());
    }

    void setAmount(String amount) {
        currenciesView.setAmount(amount);
    }


    public void onFabClicked() {
        final Valute currency = primaryCurrency;
        primaryCurrency = secondaryCurrency;
        secondaryCurrency = currency;
        updateCurrencies();
    }

    public void onAmountChanged(String amount) {
        if (hasData) {
            final String trimedAmount = amount.trim();
            if (amount.trim().length() == 0) {
                localRepository.setAmount("0");
                currenciesView.showSecondaryCurrency(DisplayCurrencyFactory.getSecondaryCurrency(secondaryCurrency, primaryCurrency, 0));
            } else {
                try {
                    final float v = Float.parseFloat(trimedAmount);
                    if (v < 0)
                        currenciesView.showToast(R.string.non_positive_number);
                    else {
                        localRepository.setAmount(trimedAmount);
                        currenciesView.showSecondaryCurrency(DisplayCurrencyFactory.getSecondaryCurrency(secondaryCurrency, primaryCurrency, v));
                    }
                } catch (NumberFormatException e) {
                    currenciesView.showToast(R.string.wrong_number);
                }
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
        initMainCurrencies();
        currenciesView.clearSearch();
        query = "";
        updateCurrencies();
    }
}
