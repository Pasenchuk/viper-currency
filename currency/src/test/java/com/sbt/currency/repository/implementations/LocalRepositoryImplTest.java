package com.sbt.currency.repository.implementations;

import android.content.Context;
import android.content.SharedPreferences;

import com.sbt.currency.SharedPreferencesMock;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;

import static com.sbt.currency.repository.LocalRepository.NO_ID;
import static com.sbt.currency.repository.implementations.LocalRepositoryImpl.CURRENCIES_PREFS;
import static org.mockito.Mockito.when;

/**
 * Created by Pasenchuk Victor on 06/05/2017
 */
public class LocalRepositoryImplTest {


    private LocalRepositoryImpl localRepository;

    @Mock
    private Context context;


    private HashMap<String, Object> prefs;


    @BeforeClass
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        final SharedPreferences sharedPreferences = new SharedPreferencesMock().getSharedPreferences();
        when(context.getSharedPreferences(CURRENCIES_PREFS, Context.MODE_PRIVATE)).thenReturn(sharedPreferences);

        localRepository = new LocalRepositoryImpl(context);


    }

    @Test
    public void testCurrencyXml() throws Exception {
        Assert.assertNull(localRepository.getCurrencyXml());

        localRepository.setCurrencyXml("asd");
        Assert.assertEquals(localRepository.getCurrencyXml(), "asd");


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
        Assert.assertEquals(localRepository.getSecondaryCurrencyId(), NO_ID);

        localRepository.setSecondaryCurrencyId(0);
        Assert.assertEquals(localRepository.getSecondaryCurrencyId(), 0);

        localRepository.setSecondaryCurrencyId(15);
        Assert.assertEquals(localRepository.getSecondaryCurrencyId(), 15);

    }


    @Test
    public void testAmount() throws Exception {
        Assert.assertEquals(localRepository.getAmount(), 0f);

        localRepository.setAmount(10.f);
        Assert.assertEquals(localRepository.getAmount(), 10f);

    }

}