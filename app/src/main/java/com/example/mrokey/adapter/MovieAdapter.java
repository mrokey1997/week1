package com.example.mrokey.adapter;

import android.content.Context;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
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
    private ItemClickListener itemClickListener;

    private final int POPULAR_MOVIE = 0, REGULAR_MOVIE = 1;

    /**
     * Constructor new a MovieAdapter with Context
     * @param context Context
     */
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


    /**
     * set listener
     * @param itemClickListener ItemClickListener
     */
    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    /**
     * Classify item view type
     * @param position position of movie in movies list
     * @return type of movie in int type
     */
    @Override
    public int getItemViewType(int position) {
        if (movies.get(position).getVoteAverage() >= APILink.VOTE_AVERAGE) {
            return POPULAR_MOVIE;
        } else return REGULAR_MOVIE;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView;
        if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            switch (viewType) {
                case REGULAR_MOVIE:
                default:
                    itemView = layoutInflater.inflate(R.layout.item_rv_regular_film, parent, false);
                    break;
                case POPULAR_MOVIE:
                    itemView = layoutInflater.inflate(R.layout.item_rv_popular_film, parent, false);
                    break;
            }
        } else {
            itemView = layoutInflater.inflate(R.layout.item_rv_film_land, parent, false);
        }
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        APIMovie movie = movies.get(position);
        if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            switch (holder.getItemViewType()) {
                case REGULAR_MOVIE:
                default:
                    holder.tv_title.setText(movie.getTitle());
                    holder.tv_overview.setText(movie.getOverview());
                    Glide.with(context).load(movie.getPosterPath())
                            .apply(RequestOptions.bitmapTransform(new RoundedCornersTransformation(111, 0, RoundedCornersTransformation.CornerType.ALL)))
                            .into(holder.img_poster);
                    break;
                case POPULAR_MOVIE:
                    Glide.with(context).load(movie.getBackdropPath())
                            .apply(RequestOptions.bitmapTransform(new RoundedCornersTransformation(111, 11, RoundedCornersTransformation.CornerType.ALL)))
                            .into(holder.img_backdrop);
                    break;
            }
        } else {
            holder.tv_title_land.setText(movie.getTitle());
            holder.tv_overview_land.setText(movie.getOverview());
            Glide.with(context).load(movie.getBackdropPath())
                    .apply(RequestOptions.bitmapTransform(new RoundedCornersTransformation(111, 11, RoundedCornersTransformation.CornerType.ALL)))
                    .into(holder.img_backdrop_land);
        }
    }

    @Override
    public int getItemCount() {
        return movies.size();
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

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView img_backdrop;

        ImageView img_poster;
        TextView tv_title;
        TextView tv_overview;

        ImageView img_backdrop_land;
        TextView tv_title_land;
        TextView tv_overview_land;

        public ViewHolder(View itemView) {
            super(itemView);
            img_backdrop = (ImageView) itemView.findViewById(R.id.img_backdrop);

            img_poster = (ImageView) itemView.findViewById(R.id.img_poster);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            tv_overview = (TextView) itemView.findViewById(R.id.tv_overview);

            img_backdrop_land = (ImageView) itemView.findViewById(R.id.img_backdrop_land);
            tv_title_land = (TextView) itemView.findViewById(R.id.tv_title_land);
            tv_overview_land = (TextView) itemView.findViewById(R.id.tv_overview_land);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onClickItem(movies.get(getAdapterPosition()));
        }
    }

}
