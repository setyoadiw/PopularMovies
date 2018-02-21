package com.luminary.setyo.popularmovies.Fragment;


import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.luminary.setyo.popularmovies.Database.MovieContract;
import com.luminary.setyo.popularmovies.Model.MovieModel.ResultsItem;
import com.luminary.setyo.popularmovies.MovieAdapter;
import com.luminary.setyo.popularmovies.R;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {


    public FavoriteFragment() {
        // Required empty public constructor
    }

    RecyclerView recyclerView;
    List<ResultsItem> listMovie = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        //Buat variable viewnya dulu
        View fragmentView = inflater.inflate(R.layout.fragment_favorite, container, false);

        recyclerView = fragmentView.findViewById(R.id.recycler_view);
        //0.layout item
        //1.dataset atau model


        ambildatafavorit();

        //2.adapter

        recyclerView.setAdapter(new MovieAdapter(getActivity(),listMovie));
        //3.layout manager
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));

        return fragmentView;
    }

    private void ambildatafavorit() {
        getActivity().getSupportLoaderManager().initLoader(100,null,this);
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id){
            case 100:
                return new CursorLoader(getActivity(), MovieContract.MovieEntry
                        .CONTENT_URI,null,null,null,null);

                default:
                    throw new RuntimeException("Loader not Working");

        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        listMovie.clear();
        if (data.getCount()>0){
            if(data.moveToFirst()){
                do{
                    ResultsItem movie = new ResultsItem();
                    movie.setId(data.getInt(data.getColumnIndex(MovieContract.MovieEntry.COLUMN_ID)));
                    movie.setTitle(data.getString(data.getColumnIndex(MovieContract.MovieEntry.COLUMN_JUDUL)));
                    movie.setPosterPath(data.getString(data.getColumnIndex(MovieContract.MovieEntry.COLUMN_POSTER)));
                    movie.setOverview(data.getString(data.getColumnIndex(MovieContract.MovieEntry.COLUMN_OVERVIEW)));

                    listMovie.add(movie);

                    recyclerView.setAdapter(new MovieAdapter(getActivity(),listMovie));

                }while (data.moveToNext());
            }
        }

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }


    @Override
    public void onResume() {
        super.onResume();
        getActivity().getSupportLoaderManager().initLoader(100,null,this);

        recyclerView.setAdapter(new MovieAdapter(getActivity(),listMovie));
    }
}
