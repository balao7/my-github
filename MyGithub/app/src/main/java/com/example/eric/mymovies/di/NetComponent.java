package com.example.eric.mymovies.di;

import com.example.eric.mymovies.search.ReposFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules={AppModule.class, NetModule.class})
public interface NetComponent {
    void inject(ReposFragment fragment);
}
