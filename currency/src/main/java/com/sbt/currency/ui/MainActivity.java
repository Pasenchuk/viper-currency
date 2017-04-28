package com.sbt.currency.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.sbt.currency.R;
import com.sbt.currency.app.CurrencyApp;
import com.sbt.currency.exceptions.RequestError;
import com.sbt.currency.interactors.Subscriber;
import com.sbt.currency.repository.NetworkRepository;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final NetworkRepository networkRepository = ((CurrencyApp) getApplication()).getAppModule().getNetworkRepository();

        networkRepository.getCurrencyXmlRequest().fetchXmlData(new Subscriber<String, RequestError>() {
            @Override
            public void onNext(String s) {
                Log.d("xml", s);
            }

            @Override
            public void onError(RequestError requestError) {

                Log.e("xml", requestError.getMessage());
                Log.e("xml", requestError.getKind().name());
            }
        });
    }
}
