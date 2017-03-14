package com.example.admin.weatherprac3.base.network;

import com.example.admin.weatherprac3.base.domain.RepoDTO;

import retrofit2.Call;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by admin on 2017-03-08.
 */

public interface ApiInterface {
    Retrofit retrofit=new Retrofit.Builder()
            .baseUrl("http://api.openweathermap.org")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    @GET("/data/2.5/weather")
    Call<RepoDTO> repo(
            @Query("appid")String appid,
            @Query("q")String city
    );
}
