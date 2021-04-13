package com.example.jsonpractice.models;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class IO {

    public static Call getJsonCall(String address) {
        OkHttpClient httpClient = new OkHttpClient();
        Request request = new Request.Builder().
                url(address).
                build();
        return httpClient.newCall(request);
    }
}