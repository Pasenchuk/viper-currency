package com.sbt.currency.repository;

/**
 * Created by Pasenchuk Victor on 29/04/2017
 */

public interface LoggingRepository {
    void log(String tag, Object message);

    void log(Object message);

    void logError(String tag, Object message);

    void logError(Object message);
}
