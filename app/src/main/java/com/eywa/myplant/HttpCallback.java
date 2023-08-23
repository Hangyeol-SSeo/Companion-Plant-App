package com.eywa.myplant;

public interface HttpCallback {
    void onSuccess(String idValue);
    void onFailure(String errorMessage);
}
