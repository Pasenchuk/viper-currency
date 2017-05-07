package com.sbt.currency.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.sbt.currency.R;
import com.sbt.currency.ui.currencies.CurrenciesFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) 
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.currencies_container, new CurrenciesFragment())
                    .commit();
    }

}
