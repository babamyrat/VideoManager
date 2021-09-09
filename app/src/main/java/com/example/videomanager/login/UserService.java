package com.example.videomanager.login;

import com.example.videomanager.model.LoginToken;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface UserService {
   @POST("token-auth/")
   @Headers("accept: application/json")
   Call<LoginToken> userLogin(@Body LoginRequest loginRequest);

   @GET("xrtc/user/me/")
   Call<LoginResponse> userMe(@Header("Authorization") String token);
}
