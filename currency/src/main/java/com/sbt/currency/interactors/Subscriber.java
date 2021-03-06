package com.sbt.currency.interactors;

/**
 * Created by Pasenchuk Victor on 28/04/2017
 */

public interface Subscriber<T, E extends Throwable> {

    void onNext(T t);

    void onError(E e);

    void onComplete();
}
