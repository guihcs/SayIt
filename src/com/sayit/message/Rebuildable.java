package com.sayit.message;

public interface Rebuildable<T> {

    T fromRequest(Request request);
    Request toRequest();
}
