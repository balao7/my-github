package com.example.eric.mymovies.search;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.eric.mymovies.MyApp;
import com.example.eric.mymovies.common.BaseFragment;
import com.example.eric.mymovies.common.MyEndlessRVScrollListener;
import com.example.eric.mymovies.R;
import com.example.eric.mymovies.models.GithubRepo;
import com.example.eric.mymovies.models.Movie;
import com.example.eric.mymovies.webservices2.GithubService;
import com.example.eric.mymovies.webservices2.MoviesResponse;
import com.example.eric.mymovies.utils.MyJsonResponseUtils;
import com.orhanobut.logger.Logger;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReposFragment extends BaseFragment implements ReposView {
    private static final String ARG_QUERY = "ARG_QUERY";

    @Inject
    GithubService mGithubService;

    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
    @BindView(R.id.message_no_result)
    View messageNoResult;

    private String mQuery;

    private ReposAdapter mAdapter;
    private ReposFragmentCallback mListener;
    private ReposPresenter mPresenter;

    public static ReposFragment newInstance(String query) {

        Bundle args = new Bundle();
        args.putString(ARG_QUERY, query);

        ReposFragment fragment = new ReposFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        ((MyApp) getActivity().getApplication()).getNetComponent().inject(this);

        super.onCreate(savedInstanceState);
        String tmp = getArguments().getString(ARG_QUERY);
        if (!TextUtils.isEmpty(tmp)) mQuery = tmp;
        mAdapter = new ReposAdapter();
        mPresenter = new ReposPresenter(mGithubService);
        mPresenter.attachView(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_search, container, false);
        ButterKnife.bind(this, view);
        initViews();
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ReposFragmentCallback) {
            mListener = (ReposFragmentCallback) context;
        }
    }

    @Override
    public void onDestroy() {
        mPresenter.detachView();
        super.onDestroy();
    }

    private void initViews() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);
    }

    private void fetchRepos() {
        Logger.i("fetchRepos");
        mPresenter.fetchRepos(mQuery);
    }

    private void render(List<GithubRepo> repos) {
        mAdapter.addItems(repos);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void showRepos(List<GithubRepo> repos) {
        setFetching(false);
        hideKeyboard();
        if (mListener != null) mListener.onFetchedMovies();
            if (repos == null || repos.size() == 0) {
                messageNoResult.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
            } else {
                messageNoResult.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
            }
            render(repos);
    }

    private void setFetching(boolean b) {
    }

    @Override
    public void showMessage(int stringId) {
        setFetching(false);
        hideKeyboard();
    }

    @Override
    public void showIsFetching() {
        setFetching(true);
    }

    /**
     * Interface to communicate back to the activity
     */
    public interface ReposFragmentCallback {
        void onFetchedMovies();
    }

    public void resetSearch(String query) {
        mQuery = query;
        mAdapter.clear();
        fetchRepos();
    }
}
