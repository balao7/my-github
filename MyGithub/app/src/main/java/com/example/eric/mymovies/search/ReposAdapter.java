package com.example.eric.mymovies.search;

import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.eric.mymovies.R;
import com.example.eric.mymovies.models.GithubRepo;
import com.example.eric.mymovies.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReposAdapter extends RecyclerView.Adapter<ReposAdapter.RepoItemViewHolder> {
    private List<GithubRepo> mItems = new ArrayList<>();

    @Override
    public RepoItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.list_item_movie, parent, false);
        return new RepoItemViewHolder(item);
    }

    @Override
    public void onBindViewHolder(RepoItemViewHolder holder, int position) {
        GithubRepo item = mItems.get(position);
        holder.titleV.setText(item.name);
        if (!TextUtils.isEmpty(item.description)) {
            holder.descV.setText(item.description);
        }
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void addItems(List<GithubRepo> movies) {
        int oldSize = mItems.size();
        mItems.addAll(movies);
        notifyItemRangeChanged(oldSize, movies.size());
    }

    public void clear() {
        mItems = new ArrayList<>();
        notifyDataSetChanged();
    }

    static class RepoItemViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.text_title)
        TextView titleV;
        @BindView(R.id.image_poster)
        ImageView imageV;
        @BindView(R.id.text_desc)
        TextView descV;

        RepoItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
