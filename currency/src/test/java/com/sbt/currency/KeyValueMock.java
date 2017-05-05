package com.sbt.currency;

import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.mockito.stubbing.OngoingStubbing;

import java.util.HashMap;

/**
 * Created by Pasenchuk Victor on 06/05/2017
 */

public abstract class KeyValueMock<B> {


    private HashMap<String, Object> storage = new HashMap<>();

    public KeyValueMock() {
        MockitoAnnotations.initMocks(this);
    }

    protected <T> OngoingStubbing<T> keyValueGetterMock(OngoingStubbing<T> stubbing) {

        return stubbing.thenAnswer(new Answer<T>() {
            @Override
            public T answer(InvocationOnMock invocation) throws Throwable {
                Object[] args = invocation.getArguments();
                final String key = (String) args[0];

                if (storage.containsKey(key))
                    return (T) storage.get(key);
                return (T) args[1];
            }
        });
    }

    protected <T> OngoingStubbing<T> keyValueSetterMock(OngoingStubbing<T> stubbing, final B builder) {

        return stubbing.thenAnswer(new Answer<B>() {
            @Override
            public B answer(InvocationOnMock invocation) throws Throwable {
                Object[] args = invocation.getArguments();
                storage.put((String) args[0], (T) args[1]);
                return builder;
            }
        });
    }
}
