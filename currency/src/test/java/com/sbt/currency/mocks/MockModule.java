package com.sbt.currency.mocks;

import android.content.Context;
import android.content.SharedPreferences;

import com.sbt.currency.di.AppModule;
import com.sbt.currency.interactors.CurrenciesInteractor;
import com.sbt.currency.repository.LocalRepository;
import com.sbt.currency.repository.LoggingRepository;
import com.sbt.currency.repository.NetworkRepository;
import com.sbt.currency.repository.implementations.LocalRepositoryImpl;

import org.mockito.Mockito;

import static com.sbt.currency.repository.implementations.LocalRepositoryImpl.CURRENCIES_PREFS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by Pasenchuk Victor on 07/05/2017
 */

public class MockModule implements AppModule {
    @Override
    public LocalRepository getLocalRepository() {

        final Context context = mock(Context.class);
        final SharedPreferences sharedPreferences = new SharedPreferencesMock().getSharedPreferences();
        when(context.getSharedPreferences(CURRENCIES_PREFS, Context.MODE_PRIVATE)).thenReturn(sharedPreferences);
        final LocalRepositoryImpl localRepository = new LocalRepositoryImpl(context);
        return Mockito.spy(localRepository);
    }

    @Override
    public MockNetworkRepository getNetworkRepository() {
        final MockNetworkRepository mockNetworkRepository = new MockNetworkRepository();
        return Mockito.spy(mockNetworkRepository);
    }

    @Override
    public LoggingRepository getLoggingRepository() {
        final LoggingRepository loggingRepository = new LoggingRepository() {
            @Override
            public void log(String tag, Object message) {
                System.out.println("Tag: " + tag + " : " + String.valueOf(message));
            }

            @Override
            public void log(Object message) {
                System.out.println("Tag: " + new Exception().getStackTrace()[1].getClassName() + " : " + String.valueOf(message));
            }

            @Override
            public void logError(String tag, Object message) {
                System.out.println("ERROR: Tag: " + tag + " : " + String.valueOf(message));
            }

            @Override
            public void logError(Object message) {
                System.out.println("ERROR: Tag: " + new Exception().getStackTrace()[1].getClassName() + " : " + String.valueOf(message));
            }
        };
        return Mockito.spy(loggingRepository);
    }

    @Override
    public CurrenciesInteractor getCurrenciesInteractor() {
        final CurrenciesInteractor interactor = new CurrenciesInteractor(this);
        return Mockito.spy(interactor);
    }
}
