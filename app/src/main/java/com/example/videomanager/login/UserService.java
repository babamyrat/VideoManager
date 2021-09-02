package com.example.videomanager.login;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface UserService {
   @POST("token-auth/")
   @Headers("accept: application/json")
   Call<LoginResponse> userLogin(@Body LoginRequest loginRequest);
}
