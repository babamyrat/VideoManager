package com.example.videomanager.login;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserService {
   @POST("token-auth/")
   Call<LoginRequest> userLogin(@Body LoginRequest loginRequest);


}
