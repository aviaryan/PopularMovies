package in.aviaryan.popularmovies;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private View mainFragementView;
    public String LOG_TAG = "ShowcaseFragement";

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mainFragementView = inflater.inflate(R.layout.fragment_main, container, false);
        // load image
        Picasso.with(getContext()).load("http://i.imgur.com/DvpvklR.png").into(
                (ImageView) mainFragementView.findViewById(R.id.imageView1) );
        Picasso.with(getContext()).load("http://i.imgur.com/DvpvklR.png").into(
                (ImageView) mainFragementView.findViewById(R.id.imageView2) );

        Log.v(LOG_TAG, "Image loaded");
        return mainFragementView;
    }
}
