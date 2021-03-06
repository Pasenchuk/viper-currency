package com.sbt.currency.repository.implementations;

import android.content.Context;

import com.sbt.currency.BaseTest;
import com.sbt.currency.mocks.Currencies;
import com.sbt.currency.mocks.MockModule;
import com.sbt.currency.repository.LocalRepository;

import org.mockito.Mock;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static com.sbt.currency.repository.LocalRepository.DOLLAR_ID;
import static com.sbt.currency.repository.LocalRepository.NO_ID;

/**
 * Created by Pasenchuk Victor on 06/05/2017
 */
public class LocalRepositoryImplTest extends BaseTest {


    private LocalRepository localRepository;

    @Mock
    private Context context;


    @BeforeClass
    public void setUp() throws Exception {
        super.setUp();

        final MockModule mockModule = new MockModule();
        localRepository = mockModule.getLocalRepository();
    }

    @Test
    public void testCurrencyXml() throws Exception {
        Assert.assertNull(localRepository.getCurrencyXml());

        localRepository.setCurrencyXml(Currencies.CURRENCY_XML);
        Assert.assertEquals(localRepository.getCurrencyXml(), Currencies.CURRENCY_XML);


        localRepository.setCurrencyXml(null);
        Assert.assertNull(localRepository.getCurrencyXml());
    }

    @Test
    public void testPrimaryCurrencyId() throws Exception {
        Assert.assertEquals(localRepository.getPrimaryCurrencyId(), NO_ID);

        localRepository.setPrimaryCurrencyId(0);
        Assert.assertEquals(localRepository.getPrimaryCurrencyId(), 0);

        localRepository.setPrimaryCurrencyId(10);
        Assert.assertEquals(localRepository.getPrimaryCurrencyId(), 10);

    }

    @Test
    public void testSecondaryCurrencyId() throws Exception {
        Assert.assertEquals(localRepository.getSecondaryCurrencyId(), DOLLAR_ID);

        localRepository.setSecondaryCurrencyId(0);
        Assert.assertEquals(localRepository.getSecondaryCurrencyId(), 0);

        localRepository.setSecondaryCurrencyId(15);
        Assert.assertEquals(localRepository.getSecondaryCurrencyId(), 15);

    }


    @Test
    public void testAmount() throws Exception {
        Assert.assertEquals(localRepository.getAmount(), "1");

        localRepository.setAmount("10");
        Assert.assertEquals(localRepository.getAmount(), "10");

    }

    @Test
    public void testClear() throws Exception {
        localRepository.setAmount("10");
        localRepository.setCurrencyXml(Currencies.CURRENCY_XML);
        localRepository.setPrimaryCurrencyId(820);
        localRepository.setSecondaryCurrencyId(840);

        localRepository.clear();

        Assert.assertEquals(localRepository.getAmount(), "1");
        Assert.assertEquals(localRepository.getPrimaryCurrencyId(), NO_ID);
        Assert.assertEquals(localRepository.getSecondaryCurrencyId(), DOLLAR_ID);
        Assert.assertEquals(localRepository.getCurrencyXml(), null);

    }

}