package com.example.eric.mymovies.search;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.example.eric.mymovies.common.Presenter;
import com.example.eric.mymovies.R;
import com.example.eric.mymovies.models.GithubRepo;
import com.example.eric.mymovies.webservices2.GithubService;
import com.example.eric.mymovies.webservices2.MoviesResponse;
import com.orhanobut.logger.Logger;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReposPresenter implements Presenter<ReposView> {

    private final GithubService mGithubService;
    private ReposView searchMvpView;

//    public ReposPresenter(Retrofit retrofit) {
//        mRetrofit = retrofit;
//    }

    public ReposPresenter(GithubService service) {
        mGithubService = service;
    }

    @Override
    public void attachView(ReposView view) {
        searchMvpView = view;
    }

    @Override
    public void detachView() {
        searchMvpView = null;
    }

    public void fetchRepos(@NonNull String query) {
        if (TextUtils.isEmpty(query) || query.trim().isEmpty()) return;

        searchMvpView.showIsFetching();
        Observable<List<GithubRepo>> call = mGithubService.getStarredRepositories(query);
        call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<GithubRepo>>() {
                    @Override
                    public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {
                        Logger.i("onSubscribe");
                    }

                    @Override
                    public void onNext(@io.reactivex.annotations.NonNull List<GithubRepo> githubRepos) {
                        searchMvpView.showRepos(githubRepos);
                    }

                    @Override
                    public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                        Logger.e(e, "onError");
                        searchMvpView.showMessage(R.string.error_something_wrong);
                    }

                    @Override
                    public void onComplete() {
                        Logger.i("onComplete");
                    }
                });
    }
}
