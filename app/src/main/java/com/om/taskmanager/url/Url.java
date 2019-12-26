package com.om.taskmanager.url;

import com.om.taskmanager.api.Api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Url {
    public static final String BASE_URL = "http://10.0.2.2:3000/";

    public Api getInstance(){

        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Api userApi=retrofit.create(Api.class);
        return userApi;

    }


}
