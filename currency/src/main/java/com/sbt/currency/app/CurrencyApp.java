package com.sbt.currency.app;

import android.app.Application;

import com.sbt.currency.di.AppModule;
import com.sbt.currency.di.AppModuleImpl;

/**
 * Created by Pasenchuk Victor on 29/04/2017
 */

public class CurrencyApp extends Application {

    private AppModule appModule;

    @Override
    public void onCreate() {
        super.onCreate();
        appModule = new AppModuleImpl(this);
    }

    public AppModule getAppModule() {
        return appModule;
    }
}
