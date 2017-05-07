package com.sbt.currency.ui;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sbt.currency.R;
import com.sbt.currency.app.CurrencyApp;
import com.sbt.currency.domain.DisplayCurrency;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CurrenciesFragment extends Fragment implements CurrenciesView {


    CurrenciesAdapter currenciesAdapter;

    CurrenciesPresenter currenciesPresenter;



    public CurrenciesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currenciesPresenter = new CurrenciesPresenter(this, ((CurrencyApp) getActivity().getApplication()).getAppModule());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_curencies, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        currenciesAdapter = new CurrenciesAdapter();

        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.currencies_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(currenciesAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        currenciesPresenter.onStart();
    }


    @Override
    public void showPrimaryCurrency(DisplayCurrency currency) {

    }

    @Override
    public void showSecondaryCurrency(DisplayCurrency currency) {

    }

    @Override
    public void updateCurrencies(List<DisplayCurrency> currencies) {
        if (CurrenciesFragment.this.isViewVisible())
            currenciesAdapter.setDisplayCurrencies(currencies);
    }

    @Override
    public boolean isViewVisible() {
        return isVisible();
    }

}
