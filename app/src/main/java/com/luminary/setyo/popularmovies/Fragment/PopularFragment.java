package com.luminary.setyo.popularmovies.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
public class PopularFragment extends Fragment {
    Button btToast ;


    public PopularFragment() {
        // Required empty public constructor
    }

    RecyclerView recyclerView;
    List<ResultsItem> listMovie = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        //Buat variable viewnya dulu
        View fragmentView = inflater.inflate(R.layout.fragment_popular, container, false);

        //btToast = fragmentView.findViewById(R.id.btToast);
//        btToast.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Toast.makeText(getActivity(),"getActivity untuk menggantikan contex",Toast.LENGTH_LONG).show();
//                //Contex kalau di fragment menggunakan getActivity()
//            }
//        });

        recyclerView = fragmentView.findViewById(R.id.recycler_view);
        //0.layout item
        //1.dataset atau model


        //fori
//        for (int i = 0; i <20 ; i++) {
//
//            //pake constuktor
//            MovieModel movie = new MovieModel("http://www.diocesequixada.org/file/2017/09/batman.png","Batman");
//            listMovie.add(movie);
//
//            //pake setter
//            MovieModel movie2 = new MovieModel();
//            movie2.setJudulMovie("Dilan");
//            movie2.setPosterMovie("http://www.diocesequixada.org/file/2017/09/batman.png");
//            listMovie.add(movie2);
//
//        }

        ambilDataOnline();

        //2.adapter

        recyclerView.setAdapter(new MovieAdapter(getActivity(),listMovie));
        //3.layout manager
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));

        return fragmentView;
    }

    private void ambilDataOnline() {
        Call<ResponseModel> requestData = RetrofitConfig.getApiService().ambilDataPopularMovie();
        requestData.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                if(response.isSuccessful()){

                    listMovie = response.body().getResults();
                    recyclerView.setAdapter(new MovieAdapter(getActivity(),listMovie));

                }else {
                    Toast.makeText(getActivity(),"Response is not succesfull",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
//                Toast.makeText(getActivity(),"Response failure",Toast.LENGTH_LONG).show();

            }
        });
    }

}
