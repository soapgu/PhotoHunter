package com.soapdemo.photohunter.util;

import java.io.InputStream;

public interface HttpInputStreamCallback {
    void onResponse(InputStream stream);
}
