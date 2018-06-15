package com.example.mrokey.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.mrokey.entity.APIMovie;
import com.example.mrokey.movie.R;
import com.example.mrokey.api.APILink;

import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {
    private List<APIMovie> movies;
    private Context context;
    private final int POPULAR_MOVIE = 0, REGULAR_MOVIE = 1;

    public MovieAdapter(Context context) {
        this.context = context;
    }

    /**
     * set data for adapter
     * create a complete path for poster and backdrop
     * @param movies list of movies
     */
    public void setData(List<APIMovie> movies) {
        this.movies = movies;
        for (int i=0; i<this.movies.size(); i++) {
            APIMovie movie = this.movies.get(i);

            String posterPath = movie.getPosterPath();
            String updatePosterPath = createImageURL(posterPath, APILink.POSTER_SIZE);
            movie.setPosterPath(updatePosterPath);

            String backdropPath = movie.getBackdropPath();
            String updateBackdropPath = createImageURL(backdropPath, APILink.BACKDROP_SIZE);
            movie.setBackdropPath(updateBackdropPath);
        }
        notifyDataSetChanged();
    }

    /**
     * Clear data of adapter
     */
    public void clearData() {
        movies.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (movies.get(position).getVoteAverage() >= APILink.VOTE_AVERAGE) {
            return POPULAR_MOVIE;
        } else return REGULAR_MOVIE;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView;
        switch (viewType) {
            case REGULAR_MOVIE: default:
                itemView = layoutInflater.inflate(R.layout.item_rv_regular_film, parent, false);
                break;
            case POPULAR_MOVIE:
                itemView = layoutInflater.inflate(R.layout.item_rv_popular_film, parent, false);
                break;
        }
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        APIMovie movie = movies.get(position);
        switch (holder.getItemViewType()) {
            case REGULAR_MOVIE: default:
                holder.tv_title.setText(movie.getTitle());
                holder.tv_overview.setText(movie.getOverview());
                Glide.with(context).load(movie.getPosterPath())
                        .apply(RequestOptions.bitmapTransform(new RoundedCornersTransformation(111, 0, RoundedCornersTransformation.CornerType.ALL)))
                        .into(holder.img_poster);
                break;
            case POPULAR_MOVIE:
                Glide.with(context).load(movie.getBackdropPath())
                        .apply(RequestOptions.bitmapTransform(new RoundedCornersTransformation(111, 0, RoundedCornersTransformation.CornerType.ALL)))
                        .into(holder.img_backdrop);
                break;
        }

    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img_poster;
        ImageView img_backdrop;
        TextView tv_title;
        TextView tv_overview;
        public ViewHolder(View itemView) {
            super(itemView);
            img_poster = (ImageView) itemView.findViewById(R.id.img_poster);
            img_backdrop = (ImageView) itemView.findViewById(R.id.img_backdrop);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            tv_overview = (TextView) itemView.findViewById(R.id.tv_overview);
        }
    }

    /**
     * Create complete path of image
     * @param path id of image
     * @return a complete path in string type
     */
    private String createImageURL(String path, String size) {
        if (path == null) return null;
        StringBuilder builder = new StringBuilder();
        builder.append(APILink.BASE_IMAGE_URL);
        builder.append(size);
        builder.append(path);
        return builder.toString();
    }

}
