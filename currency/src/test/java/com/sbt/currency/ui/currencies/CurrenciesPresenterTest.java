package com.sbt.currency.ui.currencies;

import com.sbt.currency.BaseTest;
import com.sbt.currency.R;
import com.sbt.currency.domain.DisplayCurrency;
import com.sbt.currency.domain.ValCurs;
import com.sbt.currency.mocks.Currencies;
import com.sbt.currency.mocks.MockModule;
import com.sbt.currency.repository.LocalRepository;

import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

/**
 * Created by Pasenchuk Victor on 09/05/2017
 */
public class CurrenciesPresenterTest extends BaseTest {

    @Captor
    ArgumentCaptor<List<DisplayCurrency>> listArgumentCaptor;
    @Mock
    private CurrenciesView currenciesView;
    private CurrenciesPresenter currenciesPresenter;
    private InOrder inOrder;
    private ValCurs currenciesFromXml;

    @BeforeMethod
    public void setUpMethod() throws Exception {
        Mockito.reset(currenciesView);

        Mockito.when(currenciesView.isViewVisible()).thenReturn(true);

        final CurrenciesPresenter presenter = new CurrenciesPresenter(currenciesView, new MockModule());
        this.currenciesPresenter = Mockito.spy(presenter);

        inOrder = Mockito.inOrder(currenciesView, currenciesPresenter, currenciesPresenter.currenciesInteractor, currenciesPresenter.localRepository);
        currenciesFromXml = currenciesPresenter.currenciesInteractor.getCurrenciesFromXml(Currencies.CURRENCY_XML);
    }

    @Test
    public void testOnStart() throws Exception {
        currenciesPresenter.onStart();
        inOrder.verify(currenciesPresenter).presetMainCurrency();
        inOrder.verify(currenciesPresenter).presetAmount();
        inOrder.verify(currenciesPresenter).queryForCurrencies();
    }

    @Test
    public void testQueryForCurrencies() throws Exception {
        currenciesPresenter.queryForCurrencies();
        inOrder.verify(currenciesPresenter.currenciesInteractor).enqueueCurrencies(Mockito.any());
    }

    @Test
    public void testOnCurrenciesResponse() throws Exception {
        currenciesPresenter.onCurrenciesResponse(currenciesFromXml);
        inOrder.verify(currenciesPresenter).updateCurrencies();

        Assert.assertTrue(currenciesPresenter.hasData);
        Assert.assertEquals(currenciesPresenter.rawCurrencies.size(), 4);

    }

    @Test
    public void testUpdateCurrencies() throws Exception {
        currenciesPresenter.onCurrenciesResponse(currenciesFromXml);

        currenciesPresenter.updateCurrencies();

        inOrder.verify(currenciesPresenter).initMainCurrencies();
        inOrder.verify(currenciesPresenter).showMainCurrencies();
        inOrder.verify(currenciesPresenter).initListCurrencies();

    }

    @Test
    public void testInitMainCurrencies() throws Exception {
        currenciesPresenter.onCurrenciesResponse(currenciesFromXml);
        currenciesPresenter.initMainCurrencies();

        // TODO: 09/05/2017 verify
    }

    @Test
    public void testInitListCurrencies() throws Exception {
        currenciesPresenter.onCurrenciesResponse(currenciesFromXml);

        currenciesPresenter.initListCurrencies();

        inOrder.verify(currenciesView).updateCurrencies(listArgumentCaptor.capture());

        List<List<DisplayCurrency>> allValues = listArgumentCaptor.getAllValues();
        List<DisplayCurrency> value = allValues.get(allValues.size() - 1);
        Assert.assertEquals(value.size(), 3);

    }

    @Test
    public void testPresetAmount() throws Exception {
        currenciesPresenter.presetAmount();
        inOrder.verify(currenciesPresenter).setAmount("1");
    }

    @Test
    public void testSetAmount() throws Exception {
        currenciesPresenter.setAmount("10");
        inOrder.verify(currenciesView).setAmount("10");

    }

    @Test
    public void testOnFabClicked() throws Exception {

        currenciesPresenter.onCurrenciesResponse(currenciesFromXml);
        currenciesPresenter.onFabClicked();
        inOrder.verify(currenciesPresenter).updateCurrencies();

    }

    @Test
    public void testOnAmountChanged() throws Exception {
        currenciesPresenter.onCurrenciesResponse(currenciesFromXml);

        currenciesPresenter.onAmountChanged("12");
        inOrder.verify(currenciesPresenter.localRepository).setAmount("12");
        inOrder.verify(currenciesView).showSecondaryCurrency(Mockito.any());

        currenciesPresenter.onAmountChanged("-12");
        inOrder.verify(currenciesView).showToast(R.string.non_positive_number);

        currenciesPresenter.onAmountChanged("-asd");
        inOrder.verify(currenciesView).showToast(R.string.wrong_number);

        currenciesPresenter.onAmountChanged("   ");
        inOrder.verify(currenciesPresenter.localRepository).setAmount("0");
        inOrder.verify(currenciesView).showSecondaryCurrency(Mockito.any());

        currenciesPresenter.onAmountChanged("");
        inOrder.verify(currenciesPresenter.localRepository).setAmount("0");
        inOrder.verify(currenciesView).showSecondaryCurrency(Mockito.any());

        currenciesPresenter.onAmountChanged("0");
        inOrder.verify(currenciesPresenter.localRepository).setAmount("0");
        inOrder.verify(currenciesView).showSecondaryCurrency(Mockito.any());

    }

    @Test
    public void testOnSearchChanged() throws Exception {
        currenciesPresenter.onCurrenciesResponse(currenciesFromXml);

        currenciesPresenter.onSearchChanged("inr");
        inOrder.verify(currenciesPresenter).initListCurrencies();
        inOrder.verify(currenciesView, Mockito.times(2)).updateCurrencies(listArgumentCaptor.capture());

        List<List<DisplayCurrency>> allValues = listArgumentCaptor.getAllValues();
        List<DisplayCurrency> value = allValues.get(allValues.size() - 1);
        Assert.assertEquals(value.get(0).getCharCode(), "INR");

    }

    @Test
    public void testOnNameSearchChanged() throws Exception {
        currenciesPresenter.onCurrenciesResponse(currenciesFromXml);

        currenciesPresenter.onSearchChanged("тенге");
        inOrder.verify(currenciesPresenter).initListCurrencies();
        inOrder.verify(currenciesView, Mockito.times(2)).updateCurrencies(listArgumentCaptor.capture());

        List<List<DisplayCurrency>> allValues = listArgumentCaptor.getAllValues();
        List<DisplayCurrency> value = allValues.get(allValues.size() - 1);
        Assert.assertEquals(value.get(0).getCharCode(), "KZT");

    }

    @Test
    public void testOnCurrencySelected() throws Exception {

        currenciesPresenter.onCurrenciesResponse(currenciesFromXml);

        currenciesPresenter.onCurrencySelected(LocalRepository.DOLLAR_ID);
        inOrder.verify(currenciesPresenter).updateCurrencies();

    }

}