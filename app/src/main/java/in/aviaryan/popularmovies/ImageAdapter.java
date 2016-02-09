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


public class ImageAdapter extends BaseAdapter {
    private Context mContext;

    public ImageAdapter(Context c){
        mContext = c;
    }

    public int getCount(){
        return images.length;
    }

    public Object getItem(int position){
        return null;
    }

    public long getItemId(int position){
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        ImageView imageview;
        if (convertView == null){
            imageview = new ImageView(mContext);
            //imageview.setLayoutParams(new GridLayout.LayoutParams());
            imageview.setScaleType(ImageView.ScaleType.FIT_CENTER);
            imageview.setPadding(1, 1, 1, 1);
            // imageview.setMaxHeight(300);
        } else {
            imageview = (ImageView) convertView;
        }

        //Log.v("XXX", "" + ((GridView) convertView).getWidth() );
        // TODO: resize image with screen to get clean view
        Picasso.with(mContext).load(images[position]).resize(220,220).into(imageview);
        return imageview;
    }

    private String theimage = "http://aviaryan.in/images/profile.png";
    private String[] images = {theimage, theimage, theimage, theimage, theimage};
}
