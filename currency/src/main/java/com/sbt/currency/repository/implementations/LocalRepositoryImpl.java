package com.sbt.currency.repository.implementations;

import android.content.Context;
import android.content.SharedPreferences;

import com.sbt.currency.repository.LocalRepository;

/**
 * Created by Pasenchuk Victor on 29/04/2017
 */

public class LocalRepositoryImpl implements LocalRepository {
    private static final String CURRENCIES_PREFS = "CURRENCIES_PREFS";

    private static final String CURRENCY_XML_KEY = "CURRENCY_XML_KEY";
    private final SharedPreferences sharedPreferences;

    public LocalRepositoryImpl(Context context) {
        sharedPreferences = context.getSharedPreferences(CURRENCIES_PREFS, Context.MODE_PRIVATE);
    }

    @Override
    public String getCurrencyXml() {
        return sharedPreferences.getString(CURRENCY_XML_KEY, null);
    }

    @Override
    public void setCurrencyXml(String currencyXmlKey) {
        sharedPreferences
                .edit()
                .putString(CURRENCY_XML_KEY, currencyXmlKey)
                .apply();
    }
}
