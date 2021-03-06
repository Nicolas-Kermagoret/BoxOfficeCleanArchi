package com.example.nicolaskermagoret.boxofficeclean.getMovieList.views;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nicolaskermagoret.boxofficeclean.R;
import com.example.nicolaskermagoret.boxofficeclean.getMovieList.entity.SearchResultEntity;
import com.squareup.picasso.Picasso;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    private SearchResultEntity movieList;
    private Context context;

    public interface MoviesListItemListener {
        void itemClicked(String id, String title);
    }

    private MoviesListItemListener listener;

    public ListAdapter(SearchResultEntity movieList, MoviesListItemListener listener) {
        this.movieList = movieList;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();

        View view = LayoutInflater.from(context).inflate(R.layout.recycler_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.movieTitle.setText(movieList.getSearch().get(position).getTitle());
        holder.movieYear.setText(movieList.getSearch().get(position).getYear());
        if (movieList.getSearch().get(position).getPoster() == null) {
            Picasso.with(context)
                    .load(R.drawable.not_available)
                    .placeholder(R.drawable.not_available)
                    .fit()
                    .centerCrop()
                    .into(holder.imageView);
        } else {
            Picasso.with(context)
                    .load(context.getString(R.string.picture_path) + movieList.getSearch().get(position).getPoster())
                    .placeholder(R.drawable.not_available)
                    .fit()
                    .centerCrop()
                    .into(holder.imageView);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                listener.itemClicked(movieList.getSearch().get(position).getId(), movieList.getSearch().get(position).getTitle());
            }
        });
    }

    @Override
    public int getItemCount() {
        return movieList.getSearch().size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView movieTitle, movieYear;
        private ImageView imageView;

        public ViewHolder(View view) {
            super(view);

            movieTitle = (TextView) view.findViewById(R.id.movie_title_list);
            movieYear = (TextView) view.findViewById(R.id.movie_year_list);
            imageView = (ImageView) view.findViewById(R.id.poster_list);
        }
    }

}
