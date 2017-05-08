package com.sbt.currency.interactors;

import com.sbt.currency.BaseTest;
import com.sbt.currency.domain.ValCurs;
import com.sbt.currency.exceptions.RequestError;
import com.sbt.currency.mocks.Currencies;
import com.sbt.currency.mocks.MockModule;
import com.sbt.currency.mocks.MockNetworkRepository;
import com.sbt.currency.repository.CurrencyXmlRequest;
import com.sbt.currency.repository.LocalRepository;
import com.sbt.currency.repository.LoggingRepository;

import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Created by Pasenchuk Victor on 01/05/2017
 */
public class CurrenciesInteractorTest extends BaseTest {


    private CurrenciesInteractor currenciesInteractor;
    private LocalRepository localRepository;
    private LoggingRepository loggingRepository;
    private MockNetworkRepository networkRepository;

    @Mock
    private Subscriber<ValCurs, RequestError> subscriber;
    private InOrder inOrder;


    @BeforeMethod
    public void beforeMethod() throws Exception {
        final CurrenciesInteractor interactor = new CurrenciesInteractor(new MockModule());
        currenciesInteractor = Mockito.spy(interactor);

        localRepository = currenciesInteractor.localRepository;
        loggingRepository = currenciesInteractor.loggingRepository;
        networkRepository = (MockNetworkRepository) currenciesInteractor.networkRepository;

        Mockito.reset(subscriber);

        inOrder = Mockito.inOrder(localRepository, loggingRepository, networkRepository, subscriber);
    }

    @Test
    public void testEnqueueCurrencies() throws Exception {
        networkRepository.setReturnError(false);
        networkRepository.setXmlForReturn(Currencies.CURRENCY_XML);

        runMockRequest();

        Mockito.verify(currenciesInteractor, Mockito.after(150)).onNetworkResult(Currencies.CURRENCY_XML, subscriber);
        Mockito.verify(subscriber, Mockito.after(150)).onComplete();

    }

    @Test
    public void testErrorEnqueueCurrencies() throws Exception {
        networkRepository.setReturnError(true);
        networkRepository.setXmlForReturn(Currencies.CURRENCY_XML);

        runMockRequest();

        Mockito.verify(loggingRepository, Mockito.after(150)).logError("Failed to load XML from server!");

        final ArgumentCaptor<RequestError> requestErrorArgumentCaptor = ArgumentCaptor.forClass(RequestError.class);
        Mockito.verify(subscriber, Mockito.after(150)).onError(requestErrorArgumentCaptor.capture());
        Assert.assertEquals(requestErrorArgumentCaptor.getValue().getKind(), RequestError.Kind.IO_ERROR);

    }

    @Test
    public void testCorruptXMLEnqueueCurrencies() throws Exception {
        networkRepository.setReturnError(false);
        networkRepository.setXmlForReturn("asd");

        runMockRequest();

        Mockito.verify(loggingRepository, Mockito.after(150)).logError("Can't parse XML from network");

        final ArgumentCaptor<RequestError> requestErrorArgumentCaptor = ArgumentCaptor.forClass(RequestError.class);
        Mockito.verify(subscriber, Mockito.after(150)).onError(requestErrorArgumentCaptor.capture());
        Assert.assertEquals(requestErrorArgumentCaptor.getValue().getKind(), RequestError.Kind.XML_PARSE_ERROR);

    }

    private void runMockRequest() {
        final CurrencyXmlRequest[] currencyXmlRequest = new CurrencyXmlRequest[1];
        Mockito.doAnswer(invocation -> {
            currencyXmlRequest[0] = (CurrencyXmlRequest) invocation.callRealMethod();
            return currencyXmlRequest[0];
        }).when(networkRepository).getCurrencyXmlRequest();

        currenciesInteractor.enqueueCurrencies(subscriber);

        Mockito.verify(currenciesInteractor).localLookup(subscriber);
        Mockito.verify(networkRepository).getCurrencyXmlRequest();

        Mockito.verify(currencyXmlRequest[0]).fetchXmlData(Mockito.any());
    }

    @Test
    public void testLocalLookup() throws Exception {
        localRepository.setCurrencyXml(Currencies.CURRENCY_XML);
        currenciesInteractor.localLookup(subscriber);

        Mockito.verify(subscriber).onNext(Mockito.any(ValCurs.class));
    }

    @Test
    public void testNullLocalLookup() throws Exception {
        currenciesInteractor.localLookup(subscriber);

        inOrder.verify(loggingRepository).log("No XML in preferences");
    }

    @Test
    public void testCorruptLocalLookup() throws Exception {
        localRepository.setCurrencyXml("asd");
        currenciesInteractor.localLookup(subscriber);

        inOrder.verify(loggingRepository).logError("Can't parse XML from preferences");
    }

    @Test
    public void testOnNetworkResult() throws Exception {
        currenciesInteractor.onNetworkResult(Currencies.CURRENCY_XML, subscriber);

        inOrder.verify(subscriber).onNext(Mockito.any(ValCurs.class));
        inOrder.verify(localRepository).setCurrencyXml(Currencies.CURRENCY_XML);
        inOrder.verify(subscriber).onComplete();
    }

    @Test
    public void testIncorrectXmlOnNetworkResult() throws Exception {

        currenciesInteractor.onNetworkResult("asd", subscriber);

        ArgumentCaptor<RequestError> arg = ArgumentCaptor.forClass(RequestError.class);

        inOrder.verify(loggingRepository).logError("Can't parse XML from network");
        inOrder.verify(subscriber).onError(arg.capture());
        Assert.assertEquals(arg.getValue().getKind(), RequestError.Kind.XML_PARSE_ERROR);

    }

    @Test
    public void testNullXmlOnNetworkResult() throws Exception {

        currenciesInteractor.onNetworkResult(null, subscriber);

        ArgumentCaptor<RequestError> arg = ArgumentCaptor.forClass(RequestError.class);

        inOrder.verify(loggingRepository).logError("NULL XML from network");
        inOrder.verify(subscriber).onError(arg.capture());
        Assert.assertEquals(arg.getValue().getKind(), RequestError.Kind.XML_PARSE_ERROR);

    }

}
