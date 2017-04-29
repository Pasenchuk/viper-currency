package com.sbt.currency.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.sbt.currency.R;
import com.sbt.currency.app.CurrencyApp;
import com.sbt.currency.di.AppModule;
import com.sbt.currency.domain.ValCurs;
import com.sbt.currency.exceptions.RequestError;
import com.sbt.currency.interactors.CurrenciesInteractor;
import com.sbt.currency.interactors.Subscriber;
import com.sbt.currency.repository.LoggingRepository;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final AppModule appModule = ((CurrencyApp) getApplication()).getAppModule();

        final LoggingRepository loggingRepository = appModule.getLoggingRepository();
        final CurrenciesInteractor currenciesInteractor = new CurrenciesInteractor(appModule);

        currenciesInteractor.enqueueCurrencies(new Subscriber<ValCurs, RequestError>() {
            @Override
            public void onNext(ValCurs valCurs) {
                loggingRepository.log(valCurs.getValute().size());
            }

            @Override
            public void onError(RequestError requestError) {
                if (requestError.getMessage() != null)
                    loggingRepository.log("xml", requestError.getMessage());
                loggingRepository.log("xml", requestError.getKind().name());
            }
        });

    }
}
