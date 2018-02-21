package com.luminary.setyo.popularmovies.Connection;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by luminary on 03/02/18.
 */

public class RetrofitConfig {
    private static Retrofit getRetrofit(){

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    return retrofit;
    }

    public static  ApiServices getApiService(){
        return getRetrofit().create(ApiServices.class);
    }


}
