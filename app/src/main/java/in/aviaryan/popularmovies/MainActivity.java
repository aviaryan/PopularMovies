package in.aviaryan.popularmovies;

import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.action_sort_rating) {
            Collections.sort(MainActivityFragment.movies, new Comparator<Movie>() {
                @Override
                public int compare(Movie movie, Movie t1) {
                    if (movie.rating > t1.rating)
                        return -1;
                    else if (movie.rating < t1.rating)
                        return 1;
                    return 0;
                }
            });
        } else if (id == R.id.action_sort_popularity) {
            Collections.sort(MainActivityFragment.movies, new Comparator<Movie>() {
                @Override
                public int compare(Movie movie, Movie t1) {
                    if (movie.popularity > t1.popularity)
                        return -1;
                    else if (movie.popularity < t1.popularity)
                        return 1;
                    return 0;
                }
            });
        }

        if (id == R.id.action_sort_popularity || id == R.id.action_sort_rating){
            MainActivityFragment instance = MainActivityFragment.instance;
            instance.resetImageAdapter();
        }

        return super.onOptionsItemSelected(item);
    }
}
