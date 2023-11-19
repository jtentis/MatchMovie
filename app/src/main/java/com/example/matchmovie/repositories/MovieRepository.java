package com.example.matchmovie.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.matchmovie.models.MovieModel;

import java.util.List;

public class MovieRepository {
    //essa classe é o repositório
    private static MovieRepository instance;

    private MutableLiveData<List<MovieModel>> mMovies;

    public static MovieRepository getInstance(){
        if(instance == null){
            instance = new MovieRepository();

        }
        return instance;
    }
    private MovieRepository(){
        mMovies = new MutableLiveData<>();
    }
    public LiveData<List<MovieModel>> getMovies(){return mMovies;}
}
