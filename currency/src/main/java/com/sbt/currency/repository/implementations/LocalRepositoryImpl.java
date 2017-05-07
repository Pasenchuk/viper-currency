package com.sbt.currency.repository.implementations;

import android.content.Context;
import android.content.SharedPreferences;

import com.sbt.currency.repository.LocalRepository;

/**
 * Created by Pasenchuk Victor on 29/04/2017
 */

public class LocalRepositoryImpl implements LocalRepository {

    public static final String CURRENCIES_PREFS = "CURRENCIES_PREFS";

    private static final String CURRENCY_XML_KEY = "CURRENCY_XML_KEY";
    private static final String PRIMARY_CURRENCY_KEY = "PRIMARY_CURRENCY_KEY";
    private static final String SECONDARY_CURRENCY_KEY = "SECONDARY_CURRENCY_KEY";

    private static final String AMOUNT_KEY = "AMOUNT_KEY";
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

    @Override
    public int getPrimaryCurrencyId() {
        return sharedPreferences.getInt(PRIMARY_CURRENCY_KEY, NO_ID);
    }

    @Override
    public void setPrimaryCurrencyId(int id) {
        sharedPreferences
                .edit()
                .putInt(PRIMARY_CURRENCY_KEY, id)
                .apply();
    }

    @Override
    public int getSecondaryCurrencyId() {
        return sharedPreferences.getInt(SECONDARY_CURRENCY_KEY, NO_ID);
    }

    @Override
    public void setSecondaryCurrencyId(int id) {
        sharedPreferences
                .edit()
                .putInt(SECONDARY_CURRENCY_KEY, id)
                .apply();
    }

    @Override
    public float getAmount() {
        return sharedPreferences.getInt(AMOUNT_KEY, 0);
    }

    @Override
    public void setAmount(float amount) {
        sharedPreferences
                .edit()
                .putFloat(AMOUNT_KEY, amount)
                .apply();
    }
}
