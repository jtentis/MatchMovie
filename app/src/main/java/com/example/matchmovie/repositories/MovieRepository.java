package com.example.matchmovie.repositories;

import android.graphics.Movie;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.matchmovie.models.MovieModel;
import com.example.matchmovie.request.MovieApiClient;

import java.util.List;

public class MovieRepository {
    //essa classe é o repositório
    private static MovieRepository instance;

    private MovieApiClient movieApiClient;

    private String mQuery;
    private int mPageNumber;
    public static MovieRepository getInstance(){
        if(instance == null){
            instance = new MovieRepository();

        }
        return instance;
    }
    private MovieRepository(){
        movieApiClient = MovieApiClient.getInstance();
    }

    public LiveData<List<MovieModel>> getMovies(){
        return movieApiClient.getMovies();}

    public LiveData<List<MovieModel>> getPop(){
        return movieApiClient.getMoviesPop();
    }

    //2- chamando o método do repositório
    public void searchMovieApi(String query, int pageNumber){
        mQuery = query;
        mPageNumber = pageNumber;
        movieApiClient.searchMoviesApi(query, pageNumber);
    }

    public void searchMoviePop(int pageNumber){
        mPageNumber = pageNumber;
        movieApiClient.searchMoviesApiPop(pageNumber);
    }
    public void searchNextPage(){
        searchMovieApi(mQuery, mPageNumber+1);
    }
}



