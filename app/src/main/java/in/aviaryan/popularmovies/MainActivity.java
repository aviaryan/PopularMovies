package in.aviaryan.popularmovies;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
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
        MainActivityFragment fragment = MainActivityFragment.instance;
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.action_sort_rating) {
            fragment.sortOrder = "vote_average.desc";
            fragment.moreParams = "vote_count.gte=50&include_video=false"; // you don't want shows with few votes
        } else if (id == R.id.action_sort_popularity) {
            fragment.sortOrder = "popularity.desc";
            fragment.moreParams = "";
        }

        if (id == R.id.action_sort_popularity || id == R.id.action_sort_rating){
            fragment.updateUI();
        }

        return super.onOptionsItemSelected(item);
    }
}
