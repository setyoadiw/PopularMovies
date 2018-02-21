package com.luminary.setyo.popularmovies;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.luminary.setyo.popularmovies.Connection.RetrofitConfig;
import com.luminary.setyo.popularmovies.Connection.YoutubeConfig;
import com.luminary.setyo.popularmovies.Database.MovieContract;
import com.luminary.setyo.popularmovies.Model.MovieModel.ResultsItem;
import com.luminary.setyo.popularmovies.Model.ReviewAdapter;
import com.luminary.setyo.popularmovies.Model.ReviewModel.ResponseReview;
import com.luminary.setyo.popularmovies.Model.VideoModel.ResponseVideo;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailMovieActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener{
    int posisi;
    TextView ratingScore;
    String idvideo = null;
    int idmovie =0;
    boolean statusFavorit = false;
    ImageView imgDetail;
    TextView txtDetail;
    List<ResultsItem> listMovie = new ArrayList<>();
    List<com.luminary.setyo.popularmovies.Model.ReviewModel.ResultsItem> reviewMovie = new ArrayList<>();


    private FloatingActionButton fab;

    SharedPreferences pref;
    List<com.luminary.setyo.popularmovies.Model.VideoModel.ResultsItem> video = new ArrayList<>();

    private static final int RECOVERY_REQUEST = 1;
    private YouTubePlayerView youTubeView;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);



        //Terima data
        listMovie = getIntent().getParcelableArrayListExtra("DATA_MOVIE");
        posisi = getIntent().getIntExtra("POSISI",0);



        //baca pref
        pref = PreferenceManager.getDefaultSharedPreferences(this);

        statusFavorit = pref.getBoolean("STATUS_FAVORIT"+listMovie.get(posisi).getId(),false);





        fab = (FloatingActionButton) findViewById(R.id.fab);
        updateStatus(statusFavorit);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                //jika button diklik hati terisi
                //jika diklik kembali hati kosong
                //variable boolean statusfarovirt default false
                //kalau dklik false jadi true

                //ganti status
                if (statusFavorit==false){
                    statusFavorit=true;

                    //insert
                    insertDatabase();

                }else if (statusFavorit==true){
                    statusFavorit=false;
                    //delete

                    deleteDb();

                }

                //ganti ikon
                updateStatus(statusFavorit);

                //tulis data
                SharedPreferences.Editor editor = pref.edit();
                editor.putBoolean("STATUS_FAVORIT"+listMovie.get(posisi).getId(),statusFavorit);
                editor.commit();

            }
        });

        imgDetail = (ImageView)findViewById(R.id.img_detail);
        txtDetail = (TextView)findViewById(R.id.txt_detail);


        //getSupportActionBar().setTitle(listMovie.get(posisi).getTitle());
        TextView tvJudul = (TextView)findViewById(R.id.tvjudul);
        tvJudul.setText(listMovie.get(posisi).getTitle());
        txtDetail.setText(listMovie.get(posisi).getOverview().toString());

        //Picasso.with(this).load()
        Picasso.with(this).load("https://image.tmdb.org/t/p/w500"+listMovie.get(posisi).getPosterPath()).into(imgDetail);

        //mengambil review

        idmovie = Integer.parseInt(String.valueOf(listMovie.get(posisi).getId()));
        ambilReview(idmovie);

        ambilIdVideo(idmovie);
        youTubeView = (YouTubePlayerView) findViewById(R.id.youtube_view);
        youTubeView.initialize(YoutubeConfig.YOUTUBE_API_KEY, this);

        ratingScore =(TextView)findViewById(R.id.rating);
        ratingScore.setText("Rating : "+listMovie.get(posisi).getVoteAverage());

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }


    private void ambilIdVideo(final int idmovie) {

        Call<ResponseVideo> requestDataVideo = RetrofitConfig.getApiService().ambilDataVideo(idmovie);
        requestDataVideo.enqueue(new Callback<ResponseVideo>() {
            @Override
            public void onResponse(Call<ResponseVideo> call, Response<ResponseVideo> responsevideo) {

                video = responsevideo.body().getResults();
                try {
                    idvideo = video.get(1).getKey();
                }catch (IndexOutOfBoundsException e){
                    idvideo = "kosong";
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseVideo> call, Throwable t) {
                Toast.makeText(DetailMovieActivity.this, "Error Video", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void ambilReview(int idmovie) {

        Call<ResponseReview> requestData = RetrofitConfig.getApiService().ambilDataReview(idmovie);
        requestData.enqueue(new Callback<ResponseReview>() {
            @Override
            public void onResponse(Call<ResponseReview> call, Response<ResponseReview> response) {

                reviewMovie = response.body().getResults();

                TextView textTilereview = (TextView)findViewById(R.id.textTileReview);

                String cek =null;
                try {
                    cek= reviewMovie.get(0).getContent();
                }catch (IndexOutOfBoundsException e){
                    e.printStackTrace();
                    cek ="kosong";
                }

                if (cek.equals("kosong")){
                    textTilereview.setText("Tidak ada review.");
                }else {
                    textTilereview.setText("Review : ");
                }



                //mensett review
                int totalresult = response.body().getTotalResults();

                //adapter masukkan ke recyclerview
                RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recyclerview_review);
                recyclerView.setAdapter(new ReviewAdapter(DetailMovieActivity.this,reviewMovie));
                recyclerView.setLayoutManager(new LinearLayoutManager(DetailMovieActivity.this));
//                ArrayAdapter adapter = new ArrayAdapter(DetailMovieActivity.this,android.R.layout.simple_list_item_1,listviewitem);
//                listview.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<ResponseReview> call, Throwable t) {
                Toast.makeText(DetailMovieActivity.this,"Response failure",Toast.LENGTH_LONG).show();

            }
        });

    }

    private void deleteDb() {
        int id = getContentResolver().delete(MovieContract.MovieEntry.CONTENT_URI.buildUpon()
                .appendPath(String.valueOf(listMovie.get(posisi).getId())).build(),null,null
        );

        if (id>0){
            Toast.makeText(this, "Data berhasil Dihapus", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, "Data gagal dihapus", Toast.LENGTH_SHORT).show();
        }

    }

    private void insertDatabase() {
        ContentValues values = new ContentValues();
        values.put(MovieContract.MovieEntry.COLUMN_ID,listMovie.get(posisi).getId());
        values.put(MovieContract.MovieEntry.COLUMN_JUDUL,listMovie.get(posisi).getTitle());
        values.put(MovieContract.MovieEntry.COLUMN_POSTER,listMovie.get(posisi).getPosterPath());
        values.put(MovieContract.MovieEntry.COLUMN_OVERVIEW,listMovie.get(posisi).getOverview());

        Uri uri = getContentResolver().insert(MovieContract.MovieEntry.CONTENT_URI,values);

        if (ContentUris.parseId(uri)>0){
            Toast.makeText(this, "Data berhasil Disimpan", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, "Data gagal disimapn", Toast.LENGTH_SHORT).show();
        }

    }

    private void updateStatus(boolean status) {
        if (status==false){

            fab.setImageResource(R.drawable.ic_not_favorite);



        }else if (status==true){

            fab.setImageResource(R.drawable.ic_favorite);
        }
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {



        if (!b) {
        //    youTubePlayer.cueVideo(idvideo); // Plays https://www.youtube.com/watch?v=fhWaJi1Hsfo
            youTubePlayer.loadVideo(idvideo);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RECOVERY_REQUEST){
            //retry
            getYoutubePlayerProvider().initialize(YoutubeConfig.YOUTUBE_API_KEY,this);
        }
    }

    private YouTubePlayer.Provider getYoutubePlayerProvider() {
        return youTubeView;
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult error) {

        if (error.isUserRecoverableError()){
            error.getErrorDialog(this,RECOVERY_REQUEST).show();
        }
        else {
            String eror = String.format(getString(R.string.player_error),error.toString());
            Toast.makeText(this, "Video Gagal", Toast.LENGTH_SHORT).show();
        }

    }

    public void intentYoutube(View view) {

        try {

            if (idvideo.equals("kosong")){
                Toast.makeText(this, "Tidak Ada Trailer untuk Video ini", Toast.LENGTH_SHORT).show();
            }else {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube://" + idvideo)));
            }

        }catch (NullPointerException ex){
            ex.printStackTrace();
        }

        
    }
}
