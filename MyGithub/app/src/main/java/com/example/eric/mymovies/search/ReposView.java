package com.example.eric.mymovies.search;

import com.example.eric.mymovies.models.GithubRepo;
import com.example.eric.mymovies.webservices2.ConfigurationResponse;
import com.example.eric.mymovies.webservices2.MoviesResponse;

import java.util.List;

import retrofit2.Response;

public interface ReposView {
    void showRepos(List<GithubRepo> response);

    void showMessage(int stringId);

    void showIsFetching();
}
