package com.sbt.currency.ui.currencies;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.TextView;

import com.sbt.currency.BaseTest;
import com.sbt.currency.R;
import com.sbt.currency.domain.DisplayCurrency;
import com.sbt.currency.domain.DisplayCurrencyFactory;
import com.sbt.currency.domain.Valute;
import com.sbt.currency.interactors.CurrenciesInteractor;
import com.sbt.currency.mocks.Currencies;
import com.sbt.currency.mocks.MockModule;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Pasenchuk Victor on 08/05/2017
 */
@SuppressLint("SetTextI18n")
public class CurrenciesAdapterTest extends BaseTest {


    private List<DisplayCurrency> displayCurrenciesRub;
    private List<DisplayCurrency> displayCurrenciesKzt;

    @Mock
    private CurrenciesPresenter presenter;

    private CurrenciesAdapter currenciesAdapter;

    @SuppressLint("NewApi")
    @BeforeClass
    public void setUp() throws Exception {
        super.setUp();

        currenciesAdapter = new CurrenciesAdapter();
        currenciesAdapter.presenter = presenter;

        final List<Valute> valute = new CurrenciesInteractor(new MockModule())
                .getCurrenciesFromXml(Currencies.CURRENCY_XML)
                .getValute();

        displayCurrenciesRub = valute
                .stream()
                .map(v -> DisplayCurrencyFactory.getListCurrency(v, Currencies.RUB))
                .collect(Collectors.toList());

        displayCurrenciesKzt = valute
                .stream()
                .map(v -> DisplayCurrencyFactory.getListCurrency(v, valute.get(3)))
                .collect(Collectors.toList());
    }

    @Test
    public void testOnBindViewHolder() throws Exception {

        CurrenciesAdapter.CurrencyHolder holder = new CurrencyHolderMock().getHolder();
        currenciesAdapter.displayCurrencies = displayCurrenciesRub;
        currenciesAdapter.onBindViewHolder(holder, 0);
        Mockito.verify(holder.charCode).setText("USD");
        Mockito.verify(holder.name).setText("Доллар США");
        Mockito.verify(holder.nominal).setText("1");
        Mockito.verify(holder.exchangeValue).setText("58,54");
        Mockito.verify(holder.primaryCharCode).setText("RUB");

        holder = new CurrencyHolderMock().getHolder();
        currenciesAdapter.displayCurrencies = displayCurrenciesKzt;
        currenciesAdapter.onBindViewHolder(holder, 2);
        Mockito.verify(holder.charCode).setText("INR");
        Mockito.verify(holder.name).setText("Индийских рупий");
        Mockito.verify(holder.nominal).setText("100");
        Mockito.verify(holder.exchangeValue).setText("494,73");
        Mockito.verify(holder.primaryCharCode).setText("KZT");

    }

    @Test
    public void testGetItemId() throws Exception {
        currenciesAdapter.displayCurrencies = displayCurrenciesKzt;
        Assert.assertEquals(currenciesAdapter.getItemId(0), 840);

    }

    @Test
    public void testGetItemCount() throws Exception {
        currenciesAdapter.displayCurrencies = displayCurrenciesKzt;
        Assert.assertEquals(currenciesAdapter.getItemCount(), 4);

    }

    class CurrencyHolderMock {

        @Mock
        TextView charCode;
        @Mock
        TextView nominal;
        @Mock
        TextView name;
        @Mock
        TextView exchangeValue;
        @Mock
        TextView primaryCharCode;

        @Mock
        View itemView;

        public CurrenciesAdapter.CurrencyHolder getHolder() {
            MockitoAnnotations.initMocks(this);

            Mockito.when(itemView.findViewById(R.id.char_code)).thenReturn(charCode);
            Mockito.when(itemView.findViewById(R.id.nominal)).thenReturn(nominal);
            Mockito.when(itemView.findViewById(R.id.name)).thenReturn(name);
            Mockito.when(itemView.findViewById(R.id.exchange_value)).thenReturn(exchangeValue);
            Mockito.when(itemView.findViewById(R.id.primary_char_code)).thenReturn(primaryCharCode);

            return new CurrenciesAdapter.CurrencyHolder(itemView);
        }
    }

}