package in.aviaryan.popularmovies;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

// thanks https://www.caveofprogramming.com/guest-posts/custom-listview-with-imageview-and-textview-in-android.html
public class TrailerAdapter extends BaseAdapter {

    private Context mContext;

    public TrailerAdapter(Context context){
        mContext = context;
    }

    @Override
    public int getCount() {
        return trailers.size();
    }

    @Override
    public Object getItem(int i) {
        return trailers.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        View trailerRow;
        if (convertView == null) {
            trailerRow = View.inflate(mContext, R.layout.trailer_list_row, null);
        } else {
            trailerRow = convertView;
        }

        ((TextView) trailerRow.findViewById(R.id.trailerLabel)).setText(trailers.get(i).label);
        Picasso.with(mContext).load("http://img.youtube.com/vi/" + trailers.get(i).url + "/default.jpg")
                .placeholder(R.mipmap.ic_launcher)
                .into((ImageView) trailerRow.findViewById(R.id.trailerImage));
        // youtube thumbnail - http://stackoverflow.com/questions/2068344/how

        final String url = trailers.get(i).url;
        trailerRow.findViewById(R.id.trailerImage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //view.setBackgroundColor(Color.LTGRAY);
                DetailActivityFragment.instance.watchYoutubeVideo(url);
            }
        });
        return trailerRow;
    }

    public void addItem(Trailer trailer){
        trailers.add(trailer);
    }

    public ArrayList<Trailer> trailers = new ArrayList<Trailer>();
}
