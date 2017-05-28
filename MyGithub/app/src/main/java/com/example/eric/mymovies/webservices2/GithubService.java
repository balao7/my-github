package com.example.eric.mymovies.webservices2;

import com.example.eric.mymovies.models.GithubRepo;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GithubService {
    @GET("users/{user}/starred")
    Observable<List<GithubRepo>> getStarredRepositories(@Path("user") String userName);
}
