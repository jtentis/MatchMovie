package com.example.matchmovie.adapters;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public interface OnMovieListener {

    View onCreateView(LayoutInflater Inflater, ViewGroup container, Bundle savedInstanceState);

    void onMovieClick(int position);
    void onCategoryClick(String category);
}
