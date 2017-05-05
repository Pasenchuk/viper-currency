package com.sbt.currency.repository.implementations;

import android.content.Context;
import android.content.SharedPreferences;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.mockito.stubbing.OngoingStubbing;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;

import static com.sbt.currency.repository.LocalRepository.NO_ID;
import static com.sbt.currency.repository.implementations.LocalRepositoryImpl.CURRENCIES_PREFS;
import static org.mockito.Matchers.anyFloat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.anyBoolean;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;

/**
 * Created by Pasenchuk Victor on 06/05/2017
 */
public class LocalRepositoryImplTest {


    private LocalRepositoryImpl localRepository;

    @Mock
    private SharedPreferences sharedPreferences;
    @Mock
    private SharedPreferences.Editor editor;
    @Mock
    private Context context;


    private HashMap<String, Object> prefs;


    @BeforeClass
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        when(context.getSharedPreferences(CURRENCIES_PREFS, Context.MODE_PRIVATE)).thenReturn(sharedPreferences);

        localRepository = new LocalRepositoryImpl(context);

        prefs = new HashMap<>();

        keyValueGetterMock(when(sharedPreferences.getBoolean(anyString(), anyBoolean())));
        keyValueGetterMock(when(sharedPreferences.getFloat(anyString(), anyFloat())));
        keyValueGetterMock(when(sharedPreferences.getInt(anyString(), anyInt())));
        keyValueGetterMock(when(sharedPreferences.getLong(anyString(), anyLong())));
        keyValueGetterMock(when(sharedPreferences.getString(anyString(), anyString())));

        when(sharedPreferences.edit()).thenReturn(editor);

        keyValueSetterMock(when(editor.putBoolean(anyString(), anyBoolean())));
        keyValueSetterMock(when(editor.putFloat(anyString(), anyFloat())));
        keyValueSetterMock(when(editor.putInt(anyString(), anyInt())));
        keyValueSetterMock(when(editor.putLong(anyString(), anyLong())));
        keyValueSetterMock(when(editor.putString(anyString(), anyString())));

        when(sharedPreferences.getBoolean(anyString(), anyBoolean())).thenAnswer(new Answer<String>() {
            @Override
            public String answer(InvocationOnMock invocation) throws Throwable {
                Object[] args = invocation.getArguments();
                final String key = (String) args[0];
                return key;
            }
        });
    }

    private <T> OngoingStubbing<T> keyValueGetterMock(OngoingStubbing<T> stubbing) {

        return stubbing.thenAnswer(new Answer<T>() {
            @Override
            public T answer(InvocationOnMock invocation) throws Throwable {
                Object[] args = invocation.getArguments();
                final String key = (String) args[0];

                if (prefs.containsKey(key))
                    return (T) prefs.get(key);
                return (T) args[1];
            }
        });
    }

    private <T> OngoingStubbing<T> keyValueSetterMock(OngoingStubbing<T> stubbing) {

        return stubbing.thenAnswer(new Answer<SharedPreferences.Editor>() {
            @Override
            public SharedPreferences.Editor answer(InvocationOnMock invocation) throws Throwable {
                Object[] args = invocation.getArguments();
                prefs.put((String) args[0], (T) args[1]);
                return editor;
            }
        });
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