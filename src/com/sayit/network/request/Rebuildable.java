package com.sayit.network.request;

public interface Rebuildable<T> {

    T fromRequest();

    Request toRequest();
}
