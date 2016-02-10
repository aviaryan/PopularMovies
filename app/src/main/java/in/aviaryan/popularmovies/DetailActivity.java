package in.aviaryan.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // show the data
        Intent intent = getIntent();
        String text = "" + intent.getStringExtra(Intent.EXTRA_TEXT);
        Movie movie = MainActivityFragment.movies.get(Integer.parseInt(text));

        ((TextView) findViewById(R.id.detailTextView)).setText(movie.display_name);
        Picasso.with(getBaseContext()).load(movie.poster_url).into((ImageView) findViewById(R.id.posterImageView));
        ((TextView) findViewById(R.id.overviewTextView)).setText(movie.overview);
        ((RatingBar) findViewById(R.id.rating)).setRating(movie.rating / 2f);
        ((TextView) findViewById(R.id.ratingTextView)).setText(movie.rating + "/10");
    }

}
