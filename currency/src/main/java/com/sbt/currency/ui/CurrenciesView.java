package com.sbt.currency.ui;

import com.sbt.currency.domain.DisplayCurrency;
import com.sbt.currency.domain.Valute;

import java.util.List;

/**
 * Created by Pasenchuk Victor on 29/04/2017
 */

public interface CurrenciesView {

    void showPrimaryCurrency(DisplayCurrency currency);

    void showSecondaryCurrency(DisplayCurrency currency);

    void updateCurrencies(List<DisplayCurrency> currencies);

    boolean isVisible();

}
