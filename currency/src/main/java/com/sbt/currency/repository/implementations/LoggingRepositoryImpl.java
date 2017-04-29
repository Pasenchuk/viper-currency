package com.sbt.currency.repository.implementations;

import android.util.Log;

import com.sbt.currency.repository.LoggingRepository;

/**
 * Created by Pasenchuk Victor on 29/04/2017
 */

public class LoggingRepositoryImpl implements LoggingRepository {

    @Override
    public void log(String tag, Object message) {
        Log.d(tag, String.valueOf(message));
    }

    @Override
    public void log(Object message) {
        Log.d(new Exception().getStackTrace()[1].getClassName(), String.valueOf(message));
    }

    @Override
    public void logError(String tag, Object message) {
        Log.e(tag, String.valueOf(message));
    }

    @Override
    public void logError(Object message) {
        Log.e(new Exception().getStackTrace()[1].getClassName(), String.valueOf(message));
    }

}
