package com.sbt.currency.ui.currencies;

import com.sbt.currency.BaseTest;
import com.sbt.currency.domain.ValCurs;
import com.sbt.currency.domain.Valute;
import com.sbt.currency.mocks.Currencies;
import com.sbt.currency.mocks.MockModule;
import com.sbt.currency.repository.LocalRepository;

import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Created by Pasenchuk Victor on 09/05/2017
 */
public class CurrenciesPresenterTest extends BaseTest {

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

        inOrder = Mockito.inOrder(currenciesView, currenciesPresenter, currenciesPresenter.currenciesInteractor);
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

    }

    @Test
    public void testShowMainCurrencies() throws Exception {

        currenciesPresenter.onCurrenciesResponse(currenciesFromXml);
        currenciesPresenter.showMainCurrencies();

    }

    @Test
    public void testInitMainCurrencies() throws Exception {
        currenciesPresenter.onCurrenciesResponse(currenciesFromXml);
        currenciesPresenter.initMainCurrencies();

    }

    @Test
    public void testInitListCurrencies() throws Exception {
        currenciesPresenter.onCurrenciesResponse(currenciesFromXml);
        currenciesPresenter.initListCurrencies();

    }

    @Test
    public void testFilterCurrency() throws Exception {
        currenciesPresenter.onCurrenciesResponse(currenciesFromXml);
        currenciesPresenter.filterCurrency(Valute.defaultRubInstance());

    }

    @Test
    public void testPresetMainCurrency() throws Exception {

        currenciesPresenter.presetMainCurrency();

    }

    @Test
    public void testPresetAmount() throws Exception {
        currenciesPresenter.presetAmount();

    }

    @Test
    public void testSetAmount() throws Exception {

        currenciesPresenter.setAmount(1);

    }

    @Test
    public void testOnFabClicked() throws Exception {

        currenciesPresenter.onCurrenciesResponse(currenciesFromXml);
        currenciesPresenter.onFabClicked();

    }

    @Test
    public void testOnAmountChanged() throws Exception {
        currenciesPresenter.onAmountChanged("12");

    }

    @Test
    public void testOnSearchChanged() throws Exception {

        currenciesPresenter.onSearchChanged("USD");

    }

    @Test
    public void testOnCurrencySelected() throws Exception {

        currenciesPresenter.onCurrenciesResponse(currenciesFromXml);
        currenciesPresenter.onCurrencySelected(LocalRepository.DOLLAR_ID);

    }

}