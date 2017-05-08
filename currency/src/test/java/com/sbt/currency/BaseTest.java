package com.sbt.currency;

import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeClass;

/**
 * Created by Pasenchuk Victor on 08/05/2017
 */

public abstract class BaseTest {

    @BeforeClass
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

}
