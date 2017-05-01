package com.sbt.currency.ui;

import com.sbt.currency.domain.Valute;

import java.util.List;

/**
 * Created by Pasenchuk Victor on 29/04/2017
 */

public interface CurrenciesView {

    void showPrimaryCurrency(Valute valute);

    void showSecondaryCurrency(Valute valute);

    void updateCurrencies(List<Valute> valutes);

    void updateCurrencies(double value);

}
