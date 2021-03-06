package com.sbt.currency.mocks;

import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.OngoingStubbing;

import java.util.HashMap;

/**
 * Created by Pasenchuk Victor on 06/05/2017
 */

public abstract class KeyValueMock {

    private HashMap<String, Object> storage = new HashMap<>();

    public KeyValueMock() {
        MockitoAnnotations.initMocks(this);
    }

    protected <T> OngoingStubbing<T> keyValueGetterMock(OngoingStubbing<T> stubbing) {
        return stubbing.thenAnswer(invocation -> {
            Object[] args = invocation.getArguments();
            final String key = (String) args[0];

            if (storage.containsKey(key))
                return (T) storage.get(key);
            return (T) args[1];
        });
    }

    protected <T> OngoingStubbing<T> keyValueSetterMock(OngoingStubbing<T> stubbing) {
        return stubbing.thenAnswer(invocation -> {
            Object[] args = invocation.getArguments();
            storage.put((String) args[0], args[1]);
            return (T) invocation.getMock();
        });
    }

    protected OngoingStubbing clearStorage(OngoingStubbing stubbing) {

        return stubbing.thenAnswer(invocation -> {
            storage.clear();
            return invocation.getMock();
        });
    }


}
