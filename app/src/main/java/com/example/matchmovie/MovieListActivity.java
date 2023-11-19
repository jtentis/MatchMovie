package com.example.matchmovie;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.matchmovie.models.MovieModel;
import com.example.matchmovie.request.Servicey;
import com.example.matchmovie.response.MovieSearchResponse;
import com.example.matchmovie.utils.Credentials;
import com.example.matchmovie.utils.MovieApi;
import com.example.matchmovie.viewmodels.MovieListViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.matchmovie.databinding.ActivityMainBinding;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.LongFunction;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieListActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    Button btn;
    //ViewModel
    private MovieListViewModel movieListViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = findViewById(R.id.button);

        movieListViewModel = new ViewModelProvider(this).get(MovieListViewModel.class);

        //chamando o observer
        ObserveAnyChange();

        //testando o método
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchMovieApi("Fast", 1);
            }
        });
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                GetRetrofitResponseAccordingToId();
//            }
//        });
    }

    //Observer
    private void ObserveAnyChange(){
        movieListViewModel.getMovies().observe(this, new Observer<List<MovieModel>>() {
            @Override
            public void onChanged(List<MovieModel> movieModels) {
                //Observando mudanças de dados
                if(movieModels != null){
                    for(MovieModel movieModel: movieModels){
                        Log.v("tag", "onChanged: "+movieModel.getTitle());
                    }
               }
            }
        });
    }


    //4- chamando o método na main activity
    private void searchMovieApi(String query, int pageNumber){
        movieListViewModel.searchMovieApi(query, pageNumber);
    }


//    private void GetRetrofitResponse() {
//        MovieApi movieApi = Servicey.getMovieApi();
//        Call<MovieSearchResponse> responseCall = movieApi
//                .searchMovie(
//                        Credentials.API_KEY,
//                        "As Vantagens",
//                        1
//                );
//        responseCall.enqueue(new Callback<MovieSearchResponse>() {
//            @Override
//            public void onResponse(Call<MovieSearchResponse> call, Response<MovieSearchResponse> response) {
//                if(response.code()==200){
//                    Log.v("tag", "the response" + response.body().toString());
//                    List<MovieModel> movies = new ArrayList<>(response.body().getMovies());
//                    for (MovieModel movie: movies){
//                        Log.v("tag", "A data de lançamento foi " + movie.getRelease_date());
////                        Log.v("tag", "A data de lançamento foi " + movie.getTitle());
//                    }
//                }
//                else{
//                    try{
//                        Log.v("tag", "Erro!" + response.errorBody().string());
//                    } catch (IOException e){
//                            e.printStackTrace();
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<MovieSearchResponse> call, Throwable t) {
//
//            }
//        });
//    }
//    private void GetRetrofitResponseAccordingToId(){
//        MovieApi movieApi = Servicey.getMovieApi();
//        Call<MovieModel> responseCall = movieApi
//                .getMovie(
//                        550,
//                        Credentials.API_KEY);
//        responseCall.enqueue(new Callback<MovieModel>() {
//            @Override
//            public void onResponse(Call<MovieModel> call, Response<MovieModel> response) {
//                if(response.code()==200){
//                    MovieModel movie = response.body();
//                    Log.v("tag", "O titulo do filme é: "+movie.getTitle());
//                }
//                else{
//                    try {
//                        Log.v("tag", "Erro!" +response.errorBody().string());
//                    } catch (IOException e) {
//                        throw new RuntimeException(e);
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<MovieModel> call, Throwable t) {
//
//            }
//        });
//    }

}