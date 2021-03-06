package rs.readahead.washington.mobile.data.rest;

import java.io.IOException;

import okhttp3.*;
import okhttp3.Response;
import rs.readahead.washington.mobile.MyApplication;
import rs.readahead.washington.mobile.util.C;


public class DFInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();
        Request request;

        if (MyApplication.isDomainFronting()) {
            request = originalRequest.newBuilder()
                    .url("https://www.google.com" + originalRequest.url().encodedPath()) // todo: maybe choose host on google..
                    .header("Host", C.APPSPOT_REFLECTOR)
                    .header("Whistler-host", originalRequest.url().toString())
                    .build();
        } else {
            request = originalRequest;
        }

        // todo: check redirect handling with this DF implementation..

        return chain.proceed(request);
    }
}
