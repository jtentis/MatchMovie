package com.example.matchmovie.viewmodels;

import android.graphics.Movie;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.matchmovie.models.MovieModel;

import java.lang.invoke.MutableCallSite;
import java.util.List;

public class MovieListViewModel extends ViewModel {
    //classe usada para o VIEWMODEL
    private MutableLiveData<List<MovieModel>> mMovies = new MutableLiveData<>();

    //Constructor
    public MovieListViewModel() {

    }
    public LiveData<List<MovieModel>> getMovies(){
        return mMovies;
    }


}
