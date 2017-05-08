package com.sbt.currency.ui.currencies;

import android.text.Editable;

import com.sbt.currency.BaseTest;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * Created by Pasenchuk Victor on 08/05/2017
 */
public class TextChangeListenerTest extends BaseTest {

    @Mock
    private TextChangeListener.TextListener listener;

    @InjectMocks
    private TextChangeListener textChangeListener;

    @BeforeClass
    @Override
    public void setUp() throws Exception {
        super.setUp();
    }

    @Test
    public void testBeforeTextChanged() throws Exception {
        textChangeListener.beforeTextChanged("a", 0, 1, 0);

    }

    @Test
    public void testOnTextChanged() throws Exception {
        textChangeListener.onTextChanged("a", 0, 1, 0);
        Mockito.verify(listener).onTextChanged("a");
    }

    @Test
    public void testAfterTextChanged() throws Exception {
        final Editable editable = Mockito.mock(Editable.class);
        Mockito.when(editable.toString()).thenReturn("a");
        textChangeListener.afterTextChanged(editable);
    }

    @Test
    public void testNullListener() throws Exception {
        final TextChangeListener textChangeListener = new TextChangeListener(null);

        textChangeListener.beforeTextChanged("a", 0, 1, 0);
        textChangeListener.onTextChanged("a", 0, 1, 0);

        final Editable editable = Mockito.mock(Editable.class);
        Mockito.when(editable.toString()).thenReturn("a");
        textChangeListener.afterTextChanged(editable);
    }


}