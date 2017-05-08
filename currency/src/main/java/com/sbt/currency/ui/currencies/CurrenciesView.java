package com.sbt.currency.ui.currencies;

import com.sbt.currency.domain.DisplayCurrency;

import java.util.List;

/**
 * Created by Pasenchuk Victor on 29/04/2017
 */

public interface CurrenciesView {

    void showPrimaryCurrency(DisplayCurrency currency);

    void setAmount(String amount);

    void showSecondaryCurrency(DisplayCurrency currency);

    void updateCurrencies(List<DisplayCurrency> currencies);

    boolean isViewVisible();

    void showToast(int id);

}
