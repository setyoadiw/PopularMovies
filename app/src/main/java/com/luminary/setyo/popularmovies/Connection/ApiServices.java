package com.luminary.setyo.popularmovies.Connection;

import com.luminary.setyo.popularmovies.Model.MovieModel.ResponseModel;
import com.luminary.setyo.popularmovies.Model.ReviewModel.ResponseReview;
import com.luminary.setyo.popularmovies.Model.VideoModel.ResponseVideo;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by luminary on 03/02/18.
 */

public interface ApiServices {
    @GET("3/movie/popular?api_key=8f0079291f0ab41d8e4d01294fc0c2ea&language=en-US&page=1")
    Call<ResponseModel> ambilDataPopularMovie();

    @GET("3/movie/top_rated?api_key=8f0079291f0ab41d8e4d01294fc0c2ea&language=en-US&page=1")
    Call<ResponseModel> ambilDataTopMovie();


    @GET("https://api.themoviedb.org/3/movie/{movie_id}/reviews?api_key=8f0079291f0ab41d8e4d01294fc0c2ea&language=en-US&page=1")
    Call<ResponseReview> ambilDataReview(@Path("movie_id") int id);


    @GET("https://api.themoviedb.org/3/movie/{movie_id2}/videos?api_key=8f0079291f0ab41d8e4d01294fc0c2ea&language=en-US")
    Call<ResponseVideo> ambilDataVideo(@Path("movie_id2") int idvideo);
}
