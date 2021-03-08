package com.soapdemo.photohunter.util;

public interface HttpJsonCallback<T>
{
    void onResponse(T jsonOject);
}
