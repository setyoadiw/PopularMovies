package com.luminary.setyo.popularmovies;

/**
 * Created by luminary on 2/4/2018.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by root on 03/02/18.
 */

public class MovieModel {
    @SerializedName("poster_path")
    @Expose
    String posterMovie;

    @SerializedName("title")
    @Expose
    String JudulMovie;

    //1.construktor
    //2.setter and getter

    public MovieModel(String posterMovie, String judulMovie) {
        this.posterMovie = posterMovie;
        JudulMovie = judulMovie;
    }

    public MovieModel() {

    }

    public String getPosterMovie() {
        return posterMovie;
    }

    public void setPosterMovie(String posterMovie) {
        this.posterMovie = posterMovie;
    }

    public String getJudulMovie() {
        return JudulMovie;
    }

    public void setJudulMovie(String judulMovie) {
        JudulMovie = judulMovie;
    }
}