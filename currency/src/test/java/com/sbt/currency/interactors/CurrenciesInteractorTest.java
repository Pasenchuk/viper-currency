package com.sbt.currency.interactors;

import com.sbt.currency.BaseTest;
import com.sbt.currency.domain.ValCurs;
import com.sbt.currency.exceptions.RequestError;
import com.sbt.currency.mocks.Currencies;
import com.sbt.currency.mocks.MockModule;
import com.sbt.currency.repository.LocalRepository;
import com.sbt.currency.repository.LoggingRepository;
import com.sbt.currency.repository.NetworkRepository;

import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Created by Pasenchuk Victor on 01/05/2017
 */
public class CurrenciesInteractorTest extends BaseTest {


    private MockModule appModule;

    private CurrenciesInteractor currenciesInteractor;
    private LocalRepository localRepository;
    private LoggingRepository loggingRepository;
    private NetworkRepository networkRepository;

    @Mock
    private Subscriber<ValCurs, RequestError> subscriber;
    private InOrder inOrder;

    @BeforeClass
    @Override
    public void setUp() throws Exception {
        super.setUp();

        appModule = new MockModule();
        final CurrenciesInteractor interactor = new CurrenciesInteractor(appModule);
        currenciesInteractor = Mockito.spy(interactor);

        localRepository = currenciesInteractor.localRepository;
        loggingRepository = currenciesInteractor.loggingRepository;
        networkRepository = currenciesInteractor.networkRepository;
    }

    @BeforeMethod
    public void beforeMethod() throws Exception {
        Mockito.reset(subscriber, localRepository, loggingRepository, networkRepository);
        inOrder = Mockito.inOrder(localRepository, loggingRepository, networkRepository, subscriber);
        localRepository.clear();
    }

    @Test
    public void testEnqueueCurrencies() throws Exception {
        currenciesInteractor.enqueueCurrencies(subscriber);

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
