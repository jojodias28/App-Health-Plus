package com.ibm.myfirstapp.data.remote;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Service {
    @POST("register/")
    Call<Response> saveUser(@Body Request request);

    @POST("register/login/")
    Call<Response> userLogin(@Body UserLogin login);
}
