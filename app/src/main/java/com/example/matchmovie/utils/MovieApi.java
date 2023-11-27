package com.example.matchmovie.utils;

import com.example.matchmovie.models.MovieModel;
import com.example.matchmovie.response.MovieSearchResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieApi {
    //procurando filme pelo nome
    @GET("/3/search/movie")
    Call<MovieSearchResponse> searchMovie(
            @Query("api_key") String key,
            @Query("query") String query,
            @Query("page") int page
    );

    //mostrando filmes populares
    @GET("/3/movie/popular")
    Call<MovieSearchResponse> getPopular (
            @Query("api_key") String key,
            @Query("page") int page
    );


    //procurando filme pelo id
    //https://api.themoviedb.org/3/movie/550?api_key=2017240ed8d4e61fbe9ed801fe5da25a

    @GET("/3/movie/{movie_id}?")
    Call<MovieModel> getMovie(
            @Path("movie_id") int movie_id,
            @Query("api_key") String api_key
    );
}
