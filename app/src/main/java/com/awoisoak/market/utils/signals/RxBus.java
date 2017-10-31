package com.awoisoak.market.utils.signals;

import com.jakewharton.rxrelay2.PublishRelay;
import com.jakewharton.rxrelay2.Relay;

import io.reactivex.Observable;

/**
 * RXJava alternative to bus events library
 */

public final class RxBus {
    private final Relay<Object> bus = PublishRelay.create().toSerialized();
    static RxBus rxbus;

    private RxBus() {
    }

    public static RxBus getInstance() {
        if (rxbus == null) {
            rxbus = new RxBus();
        }
        return rxbus;
    }

    public void send(Object event) {
        bus.accept(event);
    }

    public Observable<Object> toObservable() {
        return bus;
    }

    public boolean hasObservers() {
        return bus.hasObservers();
    }
}