package com.example.videomanager.data.api;

import com.example.videomanager.data.model.CallModel;
import com.example.videomanager.data.model.SingModel;
import com.example.videomanager.data.model.InfoModel;
import com.example.videomanager.data.model.TokenModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface UserService {
   @POST("token-auth/")
   @Headers("accept: application/json")
   Call<TokenModel> userLogin(@Body SingModel loginRequest);

   @GET("xrtc/user/me/")
   Call<InfoModel> userMe(@Header("Authorization") String token);

   @POST("xrtc/manager/set_device_id/")
   Call<CallModel> userCall(@Header("Authorization") String token);
}
