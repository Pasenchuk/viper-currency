package com.sbt.currency.di;

import com.sbt.currency.repository.LocalRepository;
import com.sbt.currency.repository.NetworkRepository;

/**
 * Created by Pasenchuk Victor on 29/04/2017
 */

public interface AppModule {
    LocalRepository getLocalRepository();

    NetworkRepository getNetworkRepository();
}
