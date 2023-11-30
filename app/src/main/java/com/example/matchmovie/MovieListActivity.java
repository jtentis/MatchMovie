package com.example.matchmovie;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.matchmovie.adapters.MovieRecyclerView;
import com.example.matchmovie.adapters.OnMovieListener;
import com.example.matchmovie.models.MovieModel;
import com.example.matchmovie.request.Servicey;
import com.example.matchmovie.response.MovieSearchResponse;
import com.example.matchmovie.utils.Credentials;
import com.example.matchmovie.utils.MovieApi;
import com.example.matchmovie.viewmodels.MovieListViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.matchmovie.databinding.ActivityMainBinding;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.LongFunction;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieListActivity extends AppCompatActivity implements OnMovieListener {

    //recylcer view
    private RecyclerView recyclerView;
    private MovieRecyclerView movieRecyclerAdapter;
    GridLayoutManager gridLayoutManager;

//    private ActivityMainBinding binding;

    //ViewModel
    private MovieListViewModel movieListViewModel;

    boolean isPopular = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);

        SetupSearchView();

        movieListViewModel = new ViewModelProvider(this).get(MovieListViewModel.class);

//        View btn_visitante = findViewById(R.id.btn_visitante);
        ImageView btn_pop= (ImageView) findViewById(R.id.btn_pop);
        ImageView btn_now_playing= (ImageView) findViewById(R.id.btn_now_playing);
        ImageView btn_top_rated= (ImageView) findViewById(R.id.btn_top_rated);
        ImageView btn_upcoming= (ImageView) findViewById(R.id.btn_upcoming);

        btn_pop.setOnClickListener(new View.OnClickListener() {
            private Context context;

            @Override
            public void onClick(View v) {
//                v.startAnimation(AnimationUtils.loadAnimation(context, R.anim.image_click));
                movieListViewModel.searchMoviePop(1);
            }
        });
        btn_now_playing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movieListViewModel.searchMovieNowPlaying(1);
            }
        });
        btn_top_rated.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movieListViewModel.searchMovieTopRated(1);
            }
        });
        btn_upcoming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movieListViewModel.searchMovieUpcoming(1);
            }
        });


        ConfigureRecyclerView();
        ObserveAnyChange();
        ObservePopularMovies();
        ObserveNowPlaying();
        ObserveTopRated();
        ObserveUpcoming();

        // pegando dados e executando para filmes populares como pagina incial
        movieListViewModel.searchMoviePop(1);

    }

    private void ObservePopularMovies() {
        movieListViewModel.getPop().observe(this, new Observer<List<MovieModel>>() {
            @Override
            public void onChanged(List<MovieModel> movieModels) {
                //Observando mudanças de dados
                if(movieModels != null){
                    for(MovieModel movieModel: movieModels){
                        Log.v("tag", "Título: "+movieModel.getTitle());
                        movieRecyclerAdapter.setmMovies(movieModels);
                    }
                }
            }
        });
    }
    private void ObserveNowPlaying() {
        movieListViewModel.getNowPlaying().observe(this, new Observer<List<MovieModel>>() {
            @Override
            public void onChanged(List<MovieModel> movieModels) {
                //Observando mudanças de dados
                if(movieModels != null){
                    for(MovieModel movieModel: movieModels){
                        Log.v("tag", "Título: "+movieModel.getTitle());
                        movieRecyclerAdapter.setmMovies(movieModels);
                    }
                }
            }
        });
    }
    private void ObserveTopRated() {
        movieListViewModel.getTopRated().observe(this, new Observer<List<MovieModel>>() {
            @Override
            public void onChanged(List<MovieModel> movieModels) {
                //Observando mudanças de dados
                if(movieModels != null){
                    for(MovieModel movieModel: movieModels){
                        Log.v("tag", "Título: "+movieModel.getTitle());
                        movieRecyclerAdapter.setmMovies(movieModels);
                    }
                }
            }
        });
    }
    private void ObserveUpcoming() {
        movieListViewModel.getUpcoming().observe(this, new Observer<List<MovieModel>>() {
            @Override
            public void onChanged(List<MovieModel> movieModels) {
                //Observando mudanças de dados
                if(movieModels != null){
                    for(MovieModel movieModel: movieModels){
                        Log.v("tag", "Título: "+movieModel.getTitle());
                        movieRecyclerAdapter.setmMovies(movieModels);
                    }
                }
            }
        });
    }

    //Observer
    private void ObserveAnyChange(){
        movieListViewModel.getMovies().observe(this, new Observer<List<MovieModel>>() {
            @Override
            public void onChanged(List<MovieModel> movieModels) {
                //Observando mudanças de dados
                if(movieModels != null){
                    for(MovieModel movieModel: movieModels){
                        Log.v("tag", "Título: "+movieModel.getTitle());
                        movieRecyclerAdapter.setmMovies(movieModels);
                    }
               }
            }
        });
    }

    //5- inicializando o recycler view e adicionando dados a ele
    private void ConfigureRecyclerView(){
        movieRecyclerAdapter = new MovieRecyclerView(this);
        recyclerView.setAdapter(movieRecyclerAdapter);
        gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);

        //paginação
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if(!recyclerView.canScrollVertically(1)){
                    movieListViewModel.searchNextPage();
                }
            }
        });
    }

    @Override
    public void onMovieClick(int position) {
//        Toast.makeText(this, "Posição " +position, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, MovieDetails.class);
        intent.putExtra("movie", movieRecyclerAdapter.getSelectedMovie(position));
        startActivity(intent);
    }

    @Override
    public void onCategoryClick(String category) {

    }

    private void SetupSearchView(){
        final SearchView searchView = findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                movieListViewModel.searchMovieApi(
                        query,
                        1
                );
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isPopular = false;
            }
        });
    };

}