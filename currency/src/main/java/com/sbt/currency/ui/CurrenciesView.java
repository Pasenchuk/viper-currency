package com.sbt.currency.ui;

import com.sbt.currency.domain.Valute;

import java.util.List;

/**
 * Created by Pasenchuk Victor on 29/04/2017
 */

public interface CurrenciesView {
    void showMainCurrency(Valute valute);

    void showSecondCurrency(Valute valute);

    void updateCurrencies(List<Valute> valutes, double value);

    void updateCurrencies(double value);
}
