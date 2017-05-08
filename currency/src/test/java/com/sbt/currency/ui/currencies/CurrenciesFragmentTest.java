package com.sbt.currency.ui.currencies;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sbt.currency.BaseTest;
import com.sbt.currency.R;
import com.sbt.currency.domain.DisplayCurrency;
import com.sbt.currency.domain.DisplayCurrencyFactory;
import com.sbt.currency.mocks.Currencies;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;

/**
 * Created by Pasenchuk Victor on 08/05/2017
 */
@SuppressLint("SetTextI18n")
public class CurrenciesFragmentTest extends BaseTest {

    @Mock
    CurrenciesAdapter currenciesAdapter;

    @Mock
    CurrenciesPresenter currenciesPresenter;

    @Mock
    TextView primaryCurrency;

    @Mock
    TextView secondaryCurrency;

    @Mock
    TextView calculationResult;

    @InjectMocks
    CurrenciesFragment currenciesFragment;

    @BeforeMethod
    public void setUpMethod() throws Exception {
        Mockito.reset(secondaryCurrency, calculationResult);
    }

    @Test
    public void testShowPrimaryCurrency() throws Exception {

        currenciesFragment.showPrimaryCurrency(DisplayCurrencyFactory.getPrimaryCurrency(Currencies.RUB));
        Mockito.verify(primaryCurrency).setText("RUB");

        currenciesFragment.showPrimaryCurrency(DisplayCurrencyFactory.getPrimaryCurrency(Currencies.USD));
        Mockito.verify(primaryCurrency).setText("USD");
    }

    @Test
    public void testRubConversions() throws Exception {

        currenciesFragment.showSecondaryCurrency(DisplayCurrencyFactory.getSecondaryCurrency(Currencies.EUR, Currencies.RUB, 1));
        Mockito.verify(secondaryCurrency).setText("EUR");
        Mockito.verify(calculationResult).setText("0,02");

        currenciesFragment.showSecondaryCurrency(DisplayCurrencyFactory.getSecondaryCurrency(Currencies.RUB, Currencies.EUR, 1));
        Mockito.verify(secondaryCurrency).setText("RUB");
        Mockito.verify(calculationResult).setText("64,24");

    }

    @Test
    public void testMultipleRubConversions() throws Exception {

        currenciesFragment.showSecondaryCurrency(DisplayCurrencyFactory.getSecondaryCurrency(Currencies.EUR, Currencies.RUB, 2));
        Mockito.verify(secondaryCurrency).setText("EUR");
        Mockito.verify(calculationResult).setText("0,03");

        currenciesFragment.showSecondaryCurrency(DisplayCurrencyFactory.getSecondaryCurrency(Currencies.RUB, Currencies.EUR, 2));
        Mockito.verify(secondaryCurrency).setText("RUB");
        Mockito.verify(calculationResult).setText("128,48");

    }


    @Test
    public void testCrossConversions() throws Exception {

        currenciesFragment.showSecondaryCurrency(DisplayCurrencyFactory.getSecondaryCurrency(Currencies.USD, Currencies.EUR, 1));
        Mockito.verify(secondaryCurrency).setText("USD");
        Mockito.verify(calculationResult).setText("1,10");

    }


    @Test
    public void testMultipleCrossConversions() throws Exception {

        currenciesFragment.showSecondaryCurrency(DisplayCurrencyFactory.getSecondaryCurrency(Currencies.USD, Currencies.EUR, 2));
        Mockito.verify(secondaryCurrency).setText("USD");
        Mockito.verify(calculationResult).setText("2,19");

    }

    @Test
    public void testAmountedSecondaryCurrency() throws Exception {

        currenciesFragment.showSecondaryCurrency(DisplayCurrencyFactory.getSecondaryCurrency(Currencies.INR, Currencies.RUB, 1));
        Mockito.verify(secondaryCurrency).setText("INR");
        Mockito.verify(calculationResult).setText("1,10");

        currenciesFragment.showSecondaryCurrency(DisplayCurrencyFactory.getSecondaryCurrency(Currencies.KZT, Currencies.INR, 1));
        Mockito.verify(secondaryCurrency).setText("KZT");
        Mockito.verify(calculationResult).setText("4,95");

    }

    @Test
    public void testMultipleAmountedSecondaryCurrency() throws Exception {

        currenciesFragment.showSecondaryCurrency(DisplayCurrencyFactory.getSecondaryCurrency(Currencies.INR, Currencies.RUB, 2));
        Mockito.verify(secondaryCurrency).setText("INR");
        Mockito.verify(calculationResult).setText("2,20");

        currenciesFragment.showSecondaryCurrency(DisplayCurrencyFactory.getSecondaryCurrency(Currencies.KZT, Currencies.INR, 2));
        Mockito.verify(secondaryCurrency).setText("KZT");
        Mockito.verify(calculationResult).setText("9,89");

    }

    @Test
    public void testUpdateCurrencies() throws Exception {
        final ArrayList<DisplayCurrency> displayCurrencies = new ArrayList<>();
        currenciesFragment.updateCurrencies(displayCurrencies);

        Mockito.verify(currenciesAdapter).setDisplayCurrencies(displayCurrencies);
    }

    @Test
    public void testOnCreateView() throws Exception {
        final LayoutInflater inflater = Mockito.mock(LayoutInflater.class);
        final ViewGroup viewGroup = Mockito.mock(ViewGroup.class);
        currenciesFragment.onCreateView(
                inflater,
                viewGroup,
                Mockito.mock(Bundle.class)
        );
        Mockito.verify(inflater).inflate(R.layout.fragment_curencies, viewGroup, false);
    }

    @Test
    public void testOnFabClicked() throws Exception {
        currenciesFragment.onFabClicked(Mockito.mock(View.class));
        Mockito.verify(currenciesPresenter).onFabClicked();
    }

}