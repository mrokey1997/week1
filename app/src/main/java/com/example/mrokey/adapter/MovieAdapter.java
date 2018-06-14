package com.example.mrokey.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mrokey.entity.APIMovie;
import com.example.mrokey.movie.R;
import com.example.mrokey.retrofit.RetrofitClient;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {
    private List<APIMovie> movies;
    private Context context;

    public MovieAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<APIMovie> movies) {
        this.movies = movies;
        for (int i=0; i<this.movies.size(); i++) {
            APIMovie movie = this.movies.get(i);
            String path = movie.getPosterPath();
            movie.setPosterPath(createImageURL(path));
        }
        notifyDataSetChanged();
    }

    public void clearData() {
        movies.clear();
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.item_recycler_view, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        APIMovie movie = movies.get(position);
        //holder.img_poster.set
        holder.tv_title.setText(movie.getTitle());
        holder.tv_overview.setText(movie.getOverview());

        Glide.with(context).load(movie.getPosterPath()).into(holder.img_poster);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img_poster;
        TextView tv_title;
        TextView tv_overview;
        public ViewHolder(View itemView) {
            super(itemView);
            img_poster = (ImageView) itemView.findViewById(R.id.img_poster);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            tv_overview = (TextView) itemView.findViewById(R.id.tv_overview);
        }
    }

    /**
     * Create complete url of image
     * @param path
     * @return
     */
    private String createImageURL(String path) {
        if (path == null) return null;
        StringBuilder builder = new StringBuilder();
        builder.append(RetrofitClient.BASE_IMAGE_URL);
        builder.append(RetrofitClient.POSTER_SCALE);
        builder.append(path);
        return builder.toString();
    }

}
