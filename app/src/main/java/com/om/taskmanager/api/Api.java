package com.om.taskmanager.api;

import com.om.taskmanager.model.ImageResponse;
import com.om.taskmanager.model.Token;
import com.om.taskmanager.model.Users;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface Api {
    @POST("users/signup")
    Call<Void> register (@Body Users user);

    @Multipart
    @POST("upload")
    Call<ImageResponse> uploadImage(@Part MultipartBody.Part imageFile);

    @POST("users/login")
    Call<Token> login(@Body Users user);

}
