package in.aviaryan.popularmovies;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

public class DetailActivity extends AppCompatActivity {

    private static String LOG_TAG = "DetailView";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Movie movie = getIntent().getParcelableExtra(Intent.EXTRA_TEXT);
        DetailActivityFragment detailFragment = (DetailActivityFragment) getSupportFragmentManager().findFragmentById(R.id.detailFragment);
        detailFragment.movie = movie;

        Log.v(LOG_TAG, "Activity on create finished");
    }
}
