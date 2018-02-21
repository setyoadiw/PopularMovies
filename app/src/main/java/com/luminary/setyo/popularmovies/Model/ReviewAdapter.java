package com.luminary.setyo.popularmovies.Model;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.luminary.setyo.popularmovies.DetailMovieActivity;
import com.luminary.setyo.popularmovies.Model.ReviewModel.ResultsItem;
import com.luminary.setyo.popularmovies.MovieAdapter;
import com.luminary.setyo.popularmovies.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 20/02/18.
 */

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.MyHolder> {
    Context context;
    List<ResultsItem>listReview = new ArrayList<>();

    public ReviewAdapter(Context context, List<ResultsItem> reviewMovie) {
        this.context = context;
        this.listReview = reviewMovie;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.review_item, parent,false);

        return new MyHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        holder.textJudul.setText(listReview.get(position).getAuthor());
        holder.textKonten.setText(listReview.get(position).getContent());

    }

    @Override
    public int getItemCount() {
        return listReview.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        TextView textJudul;
        TextView textKonten;
        public MyHolder(View itemView) {
            super(itemView);

            textJudul = (TextView)itemView.findViewById(R.id.tvnama);
            textKonten = (TextView)itemView.findViewById(R.id.tvkonten);

        }
    }
}
