package com.eywa.myplant;

public interface HttpCallback {
    void onSuccess(String message, String idValue);
    void onFailure(String errorMessage);
}
