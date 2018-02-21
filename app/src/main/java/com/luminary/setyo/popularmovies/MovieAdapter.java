package com.luminary.setyo.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.luminary.setyo.popularmovies.Model.MovieModel.ResultsItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 03/02/18.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MyHolder>{
    Context cotext;
    List<ResultsItem> listMove = new ArrayList<>();

    //construktor

    public MovieAdapter(Context cotext, List<ResultsItem> listMove) {
        this.cotext = cotext;
        this.listMove = listMove;
    }

    //sambungin layout
    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(cotext);
        View itemView = inflater.inflate(R.layout.movie_item, parent,false);

        return new MyHolder(itemView);
    }

    //set data
    @Override
    public void onBindViewHolder(MyHolder holder, final int position) {
        holder.textJudul.setText(listMove.get(position).getTitle());

        Picasso.with(cotext).load("https://image.tmdb.org/t/p/w500"+listMove.get(position).getPosterPath()).into(holder.imgMovie);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pindah = new Intent(cotext,DetailMovieActivity.class);

                //kirim data
                pindah.putParcelableArrayListExtra("DATA_MOVIE", (ArrayList<? extends Parcelable>) listMove);
                pindah.putExtra("POSISI",position);

                cotext.startActivity(pindah);
            }
        });

    }

    //jumlah list
    @Override
    public int getItemCount() {
        return listMove.size();
    }
    //sambungi komponen dalam layout
    public class MyHolder extends RecyclerView.ViewHolder {
        TextView textJudul;
        ImageView imgMovie;


        public MyHolder(View itemView) {
            super(itemView);
            textJudul = itemView.findViewById(R.id.tv_judul_film);
            imgMovie = itemView.findViewById(R.id.img_item_poster);
        }
    }
}
