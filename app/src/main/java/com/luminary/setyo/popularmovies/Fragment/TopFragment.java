package com.luminary.setyo.popularmovies.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.luminary.setyo.popularmovies.Connection.RetrofitConfig;
import com.luminary.setyo.popularmovies.Model.MovieModel.ResponseModel;
import com.luminary.setyo.popularmovies.Model.MovieModel.ResultsItem;
import com.luminary.setyo.popularmovies.MovieAdapter;
import com.luminary.setyo.popularmovies.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class TopFragment extends Fragment {

    RecyclerView recyclerView;
    List<ResultsItem> listMovie = new ArrayList<>();


    public TopFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_top, container, false);

        initView(view);
        ambilDataOnline();

        return view;
    }

    private void ambilDataOnline() {

        Call<ResponseModel> requestData = RetrofitConfig.getApiService().ambilDataTopMovie();
        requestData.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                listMovie = response.body().getResults();
                recyclerView.setAdapter(new MovieAdapter(getActivity(),listMovie));
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(getActivity(),"Response failure",Toast.LENGTH_LONG).show();

            }
        });

//        Call<ResponseTop> requestData = RetrofitConfig.getApiService().ambilDataTopMovie();
//        requestData.enqueue(new Callback<ResponseTop>() {
//            @Override
//            public void onResponse(Call<ResponseTop> call, Response<ResponseTop> response) {
//                if(response.isSuccessful()){
//
////                    listMovie = response.body().getResults();
//                    recyclerView.setAdapter(new MovieAdapter(getActivity(),listMovie));
//
//                }else {
//                    Toast.makeText(getActivity(),"Response is not succesfull",Toast.LENGTH_LONG).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResponseTop> call, Throwable t) {
//                Toast.makeText(getActivity(),"Response failure",Toast.LENGTH_LONG).show();
//
//            }
//        });

    }

    private void initView(View v) {

        recyclerView = v.findViewById(R.id.recyclerviewtop);
        recyclerView.setAdapter(new MovieAdapter(getActivity(),listMovie));
        //3.layout manager
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
    }


}
