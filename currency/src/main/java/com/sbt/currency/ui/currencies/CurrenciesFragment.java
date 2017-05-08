package com.sbt.currency.ui.currencies;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

    TextView primaryCurrency;
    TextView secondaryCurrency;
    TextView calculationResult;
    EditText amountField;


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

        currenciesAdapter = new CurrenciesAdapter(currenciesPresenter);

        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.currencies_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(currenciesAdapter);

        view.findViewById(R.id.currencies_fab).setOnClickListener(this::onFabClicked);
        ((EditText) view.findViewById(R.id.amount_field)).addTextChangedListener(new TextChangeListener(currenciesPresenter::onAmountChanged));
        ((EditText) view.findViewById(R.id.search_field)).addTextChangedListener(new TextChangeListener(currenciesPresenter::onSearchChanged));

        primaryCurrency = (TextView) view.findViewById(R.id.primary_currency);
        secondaryCurrency = (TextView) view.findViewById(R.id.secondary_currency);
        calculationResult = (TextView) view.findViewById(R.id.calculation_result);
        amountField = (EditText) view.findViewById(R.id.amount_field);
    }

    @Override
    public void onStart() {
        super.onStart();
        currenciesPresenter.onStart();
    }


    @Override
    public void showPrimaryCurrency(DisplayCurrency currency) {
        primaryCurrency.setText(currency.getCharCode());
    }

    @Override
    public void setAmount(String amount) {
        amountField.setText(amount);
    }

    @Override
    public void showSecondaryCurrency(DisplayCurrency currency) {
        secondaryCurrency.setText(currency.getCharCode());
        calculationResult.setText(currency.getExchangeValue());
    }

    @Override
    public void updateCurrencies(List<DisplayCurrency> currencies) {
        currenciesAdapter.setDisplayCurrencies(currencies);
    }

    @Override
    public boolean isViewVisible() {
        return isVisible();
    }

    @Override
    public void showToast(int id) {
        Toast.makeText(getActivity(), id, Toast.LENGTH_SHORT).show();
    }

    public void onFabClicked(View view) {
        currenciesPresenter.onFabClicked();
    }

}
