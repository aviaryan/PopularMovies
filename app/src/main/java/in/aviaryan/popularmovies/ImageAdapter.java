package in.aviaryan.popularmovies;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class ImageAdapter extends BaseAdapter {
    private Context mContext;

    public ImageAdapter(Context c){
        mContext = c;
    }

    @Override
    public int getCount(){
        return images.size();
    }

    @Override
    public Object getItem(int position){
        return images.get(position);
    }

    public long getItemId(int position){
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        ImageView imageview;
        if (convertView == null){
            imageview = new ImageView(mContext);
            imageview.setPadding(0, 0, 0, 0);
            //imageview.setLayoutParams(new GridLayout.MarginLayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            imageview.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            imageview.setAdjustViewBounds(true);
        } else {
            imageview = (ImageView) convertView;
        }

        //Log.v("ImageAdapter", "Image added to view from adapter " + images.size() + " " + position);
        Picasso.with(mContext).load(images.get(position)).into(imageview);
        return imageview;
    }

    /*
    Custom methods
     */
    public void addItem(String url){
        images.add(url);
    }

    public void clearItems() {
        images.clear();
    }

    public ArrayList<String> images = new ArrayList<String>();
}
