package in.aviaryan.popularmovies;

import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import in.aviaryan.popularmovies.data.MovieContract;
import in.aviaryan.popularmovies.data.MovieContract.MovieEntry;

public class DetailActivity extends AppCompatActivity {

    private RequestQueue mRequestQueue;
    public TrailerAdapter trailerAdapter;
    private static String LOG_TAG = "DetailView";
    public LinearLayout trailersList, reviewsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Get movie
        Intent intent = getIntent();
        final Movie movie = (Movie) intent.getParcelableExtra(Intent.EXTRA_TEXT);

        // Set starred icon if favorite
        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        MoviesDB moviesDB = new MoviesDB();
        boolean favStatus = moviesDB.isMovieFavorited(getApplicationContext().getContentResolver(), movie.id);
        if (favStatus)
            fab.setImageDrawable(getDrawable(android.R.drawable.btn_star_big_on));

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentResolver contentResolver = getApplicationContext().getContentResolver();
                MoviesDB mdb = new MoviesDB();
                String message;
                if (mdb.isMovieFavorited(contentResolver, movie.id)){
                    message = "Removed from Favorites";
                    mdb.removeMovie(contentResolver, movie.id);
                    fab.setImageDrawable(getDrawable(android.R.drawable.btn_star_big_off));
                } else {
                    mdb.addMovie(contentResolver, movie);
                    message = "Added to favorites";
                    fab.setImageDrawable(getDrawable(android.R.drawable.btn_star_big_on));
                }
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            }
        });

        // Populate the display fields
        ((TextView) findViewById(R.id.detailTextView)).setText(movie.display_name);
        Picasso.with(getBaseContext()).load(movie.poster_url).into((ImageView) findViewById(R.id.posterImageView));
        ((TextView) findViewById(R.id.overviewTextView)).setText(movie.overview);
        ((RatingBar) findViewById(R.id.rating)).setRating(movie.rating / 2f);
        ((TextView) findViewById(R.id.ratingTextView)).setText((float) Math.round(movie.rating*10d)/10d + "/10");

        SimpleDateFormat df = new SimpleDateFormat("dd MMM, yyyy");
        SimpleDateFormat dfInput = new SimpleDateFormat("yyyy-MM-dd");
        String releasedDate;
        try {
            releasedDate = df.format(dfInput.parse(movie.released_date));
        } catch (ParseException e){
            e.printStackTrace();
            releasedDate = movie.released_date;
        }
        ((TextView) findViewById(R.id.releaseDate)).setText(releasedDate);

        // get Trailers
        trailerAdapter = new TrailerAdapter(this);
        mRequestQueue = Volley.newRequestQueue(this);
        trailersList = (LinearLayout) findViewById(R.id.trailersList);
        getTrailers(movie.id);

        // get reviews
        reviewsList = (LinearLayout) findViewById(R.id.reviewsList);
        getReviews(movie.id);
    }

    public void getTrailers(int id){
        String url = "http://api.themoviedb.org/3/movie/" + id + "/videos?api_key=" + DataStore.API_KEY;
        JsonObjectRequest req = new JsonObjectRequest(url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray items = response.getJSONArray("results");
                            JSONObject trailerObj;
                            for (int i=0; i<items.length(); i++){
                                trailerObj = items.getJSONObject(i);
                                Trailer trailer = new Trailer();
                                trailer.id = trailerObj.getString("id");
                                trailer.url = trailerObj.getString("key");
                                trailer.label = trailerObj.getString("name");
                                trailerAdapter.addItem(trailer);
                            }
                        } catch (JSONException e){
                            e.printStackTrace();
                        }
                        for (int i = 0; i < trailerAdapter.getCount(); i++){
                            trailersList.addView(trailerAdapter.getView(i, null, null));
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(LOG_TAG, "Error in JSON Parsing");
            }
        });

        mRequestQueue.add(req);
    }

    public void getReviews(int id){
        String url = "http://api.themoviedb.org/3/movie/" + id + "/reviews?api_key=" + DataStore.API_KEY;
        JsonObjectRequest req = new JsonObjectRequest(url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray items = response.getJSONArray("results");
                            JSONObject reviewObj;
                            View view;
                            for (int i=0; i<items.length(); i++){
                                reviewObj = items.getJSONObject(i);
                                Review review = new Review();
                                review.author = reviewObj.getString("author");
                                review.url = reviewObj.getString("url");
                                review.content = reviewObj.getString("content");
                                reviewsList.addView(view = createReviewView(review));
                                collapseReviewView(view);
                            }
                        } catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(LOG_TAG, "Error in JSON Parsing");
            }
        });

        mRequestQueue.add(req);
    }

    public View createReviewView(Review review){
        View view;
        view  = View.inflate(this, R.layout.review, null);
        ((TextView) view.findViewById(R.id.reviewAuthor)).setText(review.author);
        TextView contentView = (TextView) view.findViewById(R.id.reviewContent);
        //TextView statusView = (TextView) view.findViewById(R.id.statusCollapsed);
        contentView.setText(review.content);
        return view;
    }

    public void collapseReviewView(final View view){
        final TextView contentView = (TextView) view.findViewById(R.id.reviewContent);
        contentView.post(new Runnable() { // run on UI thread for getLineCount
            @Override
            public void run() {
                if (contentView.getLineCount() <= 5) {
                    ((TextView) view.findViewById(R.id.statusCollapsed)).setVisibility(View.GONE);
                } else {
                    contentView.setMaxLines(5);
                    view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            TextView statusView = (TextView) view.findViewById(R.id.statusCollapsed);
                            TextView contentView2 = (TextView) view.findViewById(R.id.reviewContent);
                            if (statusView.getText().equals(getString(R.string.text_more))) {
                                contentView2.setMaxLines(10000);
                                statusView.setText(getString(R.string.text_less));
                            } else {
                                contentView2.setMaxLines(5);
                                statusView.setText(getString(R.string.text_more));
                            }
                        }
                    });
                }
            }
        });
    }

    public void watchYoutubeVideo(String id){
        // http://stackoverflow.com/a/12439378/2295672
        try{
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + id));
            startActivity(intent);
        }catch (ActivityNotFoundException ex){
            Intent intent=new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://www.youtube.com/watch?v="+id));
            startActivity(intent);
        }
    }
}
