package in.aviaryan.popularmovies;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    public View mainFragmentView;
    public String LOG_TAG = "ShowcaseFragment";
    public static ArrayList<Movie> movies = new ArrayList<Movie>();
    private RequestQueue mRequestQueue;
    public ImageAdapter imageAdapter;
    public static MainActivityFragment instance;

    public MainActivityFragment() {
        instance = this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mainFragmentView = inflater.inflate(R.layout.fragment_main, container, false);
        mRequestQueue = Volley.newRequestQueue(getContext());

        // setup image adapter
        imageAdapter = new ImageAdapter(getContext());

        updateUI();

        GridView gridview = (GridView) mainFragmentView.findViewById(R.id.gridView);
        gridview.setAdapter(imageAdapter);
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent(getContext(), DetailActivity.class).putExtra(Intent.EXTRA_TEXT, position + "");
                startActivity(intent);
            }
        });

        return mainFragmentView;
    }

    public void updateUI(){
        if (movies.size() == 0)
            getMovies();
        else
            resetImageAdapter();
    }

    public void getMovies(){
        String url = "http://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&api_key=" + DataStore.API_KEY;
        JsonObjectRequest req = new JsonObjectRequest(url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray items = response.getJSONArray("results");
                            JSONObject movieObj;
                            for (int i=0; i<items.length(); i++){
                                movieObj = items.getJSONObject(i);
                                Movie movie = new Movie();
                                movie.id = movieObj.getInt("id");
                                movie.display_name = movieObj.getString("original_title");
                                movie.overview = movieObj.getString("overview");
                                movie.poster_url = "http://image.tmdb.org/t/p/w185/" + movieObj.getString("poster_path");
                                movie.released_date = movieObj.getString("release_date");
                                movie.rating = (float) movieObj.getDouble("vote_average");
                                movie.popularity = movieObj.getDouble("popularity");
                                movies.add(movie);
                                // Add images to adapter
                                imageAdapter.addItem(movie.poster_url);
                            }
                        } catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(LOG_TAG, "Error buddy");
            }
        });

        mRequestQueue.add(req);
    }

    public void resetImageAdapter(){
        imageAdapter.clearItems();
        for (int i = 0; i < movies.size(); i++)
            imageAdapter.addItem(movies.get(i).poster_url);
        imageAdapter.notifyDataSetChanged();
    }

    public void setGridColCount(int n){
        ((GridView) mainFragmentView.findViewById(R.id.gridView)).setNumColumns(n);
    }
}
