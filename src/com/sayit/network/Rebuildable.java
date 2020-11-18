package com.sayit.network;

public interface Rebuildable<T> {

    T fromRequest(Request request);

    Request toRequest();
}
