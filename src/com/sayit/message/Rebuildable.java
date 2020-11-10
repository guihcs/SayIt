package com.sayit.message;

import com.sayit.network.Request;

public interface Rebuildable<T> {

    T fromRequest(Request request);
    Request toRequest();
}
