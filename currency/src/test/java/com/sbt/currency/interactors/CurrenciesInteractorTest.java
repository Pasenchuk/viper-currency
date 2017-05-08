package com.sbt.currency.interactors;

import com.sbt.currency.BaseTest;
import com.sbt.currency.domain.ValCurs;
import com.sbt.currency.exceptions.RequestError;
import com.sbt.currency.mocks.Currencies;
import com.sbt.currency.mocks.MockModule;
import com.sbt.currency.repository.LocalRepository;
import com.sbt.currency.repository.LoggingRepository;

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

    @Mock
    Subscriber<ValCurs, RequestError> subscriber;

    private CurrenciesInteractor currenciesInteractor;

    @BeforeClass
    @Override
    public void setUp() throws Exception {
        super.setUp();

        currenciesInteractor = new CurrenciesInteractor(new MockModule());
    }

    @BeforeMethod
    public void beforeMethod() throws Exception {
        Mockito.reset(subscriber);
    }

    @Test
    public void testEnqueueCurrencies() throws Exception {

    }

    @Test
    public void testOnNetworkResult() throws Exception {

        final LocalRepository localRepository = currenciesInteractor.localRepository;

        currenciesInteractor.onNetworkResult(Currencies.CURRENCY_XML, subscriber);

        final InOrder inOrder = Mockito.inOrder(localRepository, subscriber);
        inOrder.verify(subscriber).onNext(Mockito.any(ValCurs.class));
        inOrder.verify(localRepository).setCurrencyXml(Currencies.CURRENCY_XML);
        inOrder.verify(subscriber).onComplete();


    }

    @Test
    public void testIncorrectXmlOnNetworkResult() throws Exception {

        final LocalRepository localRepository = currenciesInteractor.localRepository;
        final LoggingRepository loggingRepository = currenciesInteractor.loggingRepository;

        Mockito.reset(localRepository);

        currenciesInteractor.onNetworkResult("asd", subscriber);

        final InOrder inOrder = Mockito.inOrder(localRepository, subscriber, loggingRepository);

        ArgumentCaptor<RequestError> arg = ArgumentCaptor.forClass(RequestError.class);

        inOrder.verify(loggingRepository).logError("Can't parse XML from network");
        inOrder.verify(subscriber).onError(arg.capture());
        Assert.assertEquals(arg.getValue().getKind(), RequestError.Kind.XML_PARSE_ERROR);

    }

    @Test
    public void testNullXmlOnNetworkResult() throws Exception {

        final LocalRepository localRepository = currenciesInteractor.localRepository;
        final LoggingRepository loggingRepository = currenciesInteractor.loggingRepository;

        Mockito.reset(localRepository);

        currenciesInteractor.onNetworkResult(null, subscriber);

        final InOrder inOrder = Mockito.inOrder(localRepository, subscriber, loggingRepository);

        ArgumentCaptor<RequestError> arg = ArgumentCaptor.forClass(RequestError.class);

        inOrder.verify(loggingRepository).logError("NULL XML from network");
        inOrder.verify(subscriber).onError(arg.capture());
        Assert.assertEquals(arg.getValue().getKind(), RequestError.Kind.XML_PARSE_ERROR);

    }

}
