package com.luminary.setyo.popularmovies;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.luminary.setyo.popularmovies.Fragment.FavoriteFragment;
import com.luminary.setyo.popularmovies.Fragment.PopularFragment;
import com.luminary.setyo.popularmovies.Fragment.TopFragment;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

public class MainActivity extends AppCompatActivity {
    BottomBar bottomBar;



    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    //cara memanggil fragment
                    getSupportFragmentManager().beginTransaction().replace(R.id.layout_untuk_fragment,new PopularFragment())
                            .commit();

                    return true;
                case R.id.navigation_dashboard:
                    //cara memanggil fragment
                    getSupportFragmentManager().beginTransaction().replace(R.id.layout_untuk_fragment,new TopFragment())
                            .commit();

                    return true;
                case R.id.navigation_notifications:
                    //cara memanggil fragment
                    getSupportFragmentManager().beginTransaction().replace(R.id.layout_untuk_fragment,new FavoriteFragment())
                            .commit();


                    return true;
            }
            return false;
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

            getSupportFragmentManager().beginTransaction().replace(R.id.layout_untuk_fragment,new PopularFragment())
                    .commit();


        bottomBar = (BottomBar) findViewById(R.id.navigation);
        initBottombar();

    }


    private void initBottombar() {
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                if (tabId == R.id.tab_popular){
                    //tabFavorit.removeBadge();
                    getSupportFragmentManager().beginTransaction().replace(R.id.layout_untuk_fragment,new PopularFragment())
                            .commit();
                }
                else if (tabId == R.id.tab_toprated){
                    getSupportFragmentManager().beginTransaction().replace(R.id.layout_untuk_fragment,new TopFragment())
                            .commit();
                }else if(tabId == R.id.tab_favorite){
                    getSupportFragmentManager().beginTransaction().replace(R.id.layout_untuk_fragment,new FavoriteFragment())
                            .commit();
                }

            }
        });
    }



}
