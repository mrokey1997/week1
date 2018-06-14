package com.example.mrokey.model;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mrokey.movie.R;

import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {
    private ArrayList<Movie> movies;
    private Context context;

    public MovieAdapter(ArrayList<Movie> movies, Context context) {
        this.movies = movies;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.item_recycler_view, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //holder.img_poster.set
        holder.img_poster.setImageResource(movies.get(position).getId());
        holder.tv_name.setText(movies.get(position).getTitle());
        holder.tv_overview.setText(movies.get(position).getOverview());
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img_poster;
        TextView tv_name;
        TextView tv_overview;
        public ViewHolder(View itemView) {
            super(itemView);
            img_poster = (ImageView) itemView.findViewById(R.id.imageview_poster);
            tv_name = (TextView) itemView.findViewById(R.id.textview_name);
            tv_overview = (TextView) itemView.findViewById(R.id.textview_overview);
        }
    }

}
