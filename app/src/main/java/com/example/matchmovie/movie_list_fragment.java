package com.example.matchmovie;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.matchmovie.adapters.MovieRecyclerView;
import com.example.matchmovie.adapters.OnMovieListener;
import com.example.matchmovie.models.MovieModel;
import com.example.matchmovie.viewmodels.MovieListViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class movie_list_fragment extends Fragment implements OnMovieListener {

    private RecyclerView recyclerView;
    private MovieRecyclerView movieRecyclerAdapter;
    GridLayoutManager gridLayoutManager;
    //ViewModel
    private MovieListViewModel movieListViewModel;
    boolean isPopular = true;
    FirebaseAuth mAuth;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.activity_main, container, false);
        recyclerView = getView().findViewById(R.id.recyclerView);

        SetupSearchView();

//        if(usuario == null){
//            Intent intent = new Intent(MovieListActivity.getActivity(), LoginScreen.class);
//            startActivity(intent);
//            finish();
//        }

        TextView filter=(TextView)getView().findViewById(R.id.txt_filter);
        movieListViewModel = new ViewModelProvider(getActivity()).get(MovieListViewModel.class);

        // pegando dados e executando para filmes populares como pagina incial
        movieListViewModel.searchMoviePop(1);
        filter.setText("Populares");
//        PopPagination();

        ImageView btn_pop= (ImageView) getView().findViewById(R.id.btn_pop);
        ImageView btn_now_playing= (ImageView) getView().findViewById(R.id.btn_now_playing);
        ImageView btn_top_rated= (ImageView) getView().findViewById(R.id.btn_top_rated);
        ImageView btn_upcoming= (ImageView) getView().findViewById(R.id.btn_upcoming);

        BottomNavigationView navbar = getView().findViewById(R.id.navigationView);

        navbar.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.profile) {
                    Intent intent = new Intent(getActivity(), ProfileScreen.class);
                    startActivity(intent);
                    return true;
                } else if (itemId == R.id.match) {
                    Intent intent = new Intent(getActivity(), MatchScreen.class);
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });

        btn_pop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                PopPagination();
                movieListViewModel.searchMoviePop(1);
                filter.setText("Populares");
                btn_pop.setBackground(getActivity().getDrawable(R.drawable.bg_red_rounded_icons));
                btn_now_playing.setBackground(getActivity().getDrawable(R.drawable.bg_black_rounded_icons));
                btn_upcoming.setBackground(getActivity().getDrawable(R.drawable.bg_black_rounded_icons));
                btn_top_rated.setBackground(getActivity().getDrawable(R.drawable.bg_black_rounded_icons));
            }
        });
        btn_now_playing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                NowPlayingPagination();
                movieListViewModel.searchMovieNowPlaying(1);
                filter.setText("Nos Cinemas");
                btn_now_playing.setBackground(getActivity().getDrawable(R.drawable.bg_red_rounded_icons));
                btn_pop.setBackground(getActivity().getDrawable(R.drawable.bg_black_rounded_icons));
                btn_upcoming.setBackground(getActivity().getDrawable(R.drawable.bg_black_rounded_icons));
                btn_top_rated.setBackground(getActivity().getDrawable(R.drawable.bg_black_rounded_icons));
            }
        });
        btn_top_rated.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                TopRatedPagination();
                movieListViewModel.searchMovieTopRated(1);
                filter.setText("Mais Votados");
                btn_top_rated.setBackground(getActivity().getDrawable(R.drawable.bg_red_rounded_icons));
                btn_now_playing.setBackground(getActivity().getDrawable(R.drawable.bg_black_rounded_icons));
                btn_pop.setBackground(getActivity().getDrawable(R.drawable.bg_black_rounded_icons));
                btn_upcoming.setBackground(getActivity().getDrawable(R.drawable.bg_black_rounded_icons));
            }
        });
        btn_upcoming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                UpcomingPagination();
                movieListViewModel.searchMovieUpcoming(1);
                filter.setText("Em Breve");
                btn_upcoming.setBackground(getActivity().getDrawable(R.drawable.bg_red_rounded_icons));
                btn_now_playing.setBackground(getActivity().getDrawable(R.drawable.bg_black_rounded_icons));
                btn_pop.setBackground(getActivity().getDrawable(R.drawable.bg_black_rounded_icons));
                btn_top_rated.setBackground(getActivity().getDrawable(R.drawable.bg_black_rounded_icons));
            }
        });

        ConfigureRecyclerView();
        ObserveAnyChange();
        ObservePopularMovies();
        ObserveNowPlaying();
        ObserveTopRated();
        ObserveUpcoming();

        return view;
    }

    private void ObservePopularMovies() {
        movieListViewModel.getPop().observe(getActivity(), new Observer<List<MovieModel>>() {
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
        movieListViewModel.getNowPlaying().observe(getActivity(), new Observer<List<MovieModel>>() {
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
        movieListViewModel.getTopRated().observe(getActivity(), new Observer<List<MovieModel>>() {
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
        movieListViewModel.getUpcoming().observe(getActivity(), new Observer<List<MovieModel>>() {
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
        movieListViewModel.getMovies().observe(getActivity(), new Observer<List<MovieModel>>() {
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
    private void ConfigureRecyclerView() {
        movieRecyclerAdapter = new MovieRecyclerView(this);
        recyclerView.setAdapter(movieRecyclerAdapter);
        gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);
    }

    //paginação
    private void SearchViewPagination(){
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if (!recyclerView.canScrollVertically(1)) {
                    movieListViewModel.searchNextPage();
                }
            }
        });
    }

//    private void PopPagination(){
//        //paginação
//        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
//                if(!recyclerView.canScrollVertically(1)){
//                    movieListViewModel.searchNextPagePop();
//                }
//            }
//        });
//    }
//
//    private void TopRatedPagination(){
//        //paginação
//        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
//                if(!recyclerView.canScrollVertically(1)){
//                    movieListViewModel.searchNextPageTopRated();
//                }
//            }
//        });
//    }
//
//    private void UpcomingPagination(){
//        //paginação
//        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
//                if(!recyclerView.canScrollVertically(1)){
//                    movieListViewModel.searchNextPageUpcoming();
//                }
//            }
//        });
//    }
//
//    private void NowPlayingPagination(){
//        //paginação
//        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
//                if(!recyclerView.canScrollVertically(1)){
//                    movieListViewModel.searchNextPageNowPlaying();
//                }
//            }
//        });
//    }

    @Override
    public void onMovieClick(int position) {
//        Toast.makeText(getActivity(), "Posição " +position, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getActivity(), MovieDetails.class);
        intent.putExtra("movie", movieRecyclerAdapter.getSelectedMovie(position));
        startActivity(intent);
    }

    @Override
    public void onCategoryClick(String category) {

    }

    private void SetupSearchView(){
        final SearchView searchView = getView().findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                SearchViewPagination();
                movieListViewModel.searchMovieApi(
                        query,
                        1
                );
                SearchViewPagination();
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