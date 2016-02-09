package in.aviaryan.popularmovies;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridLayout;
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
            imageview.setPadding(8,8,8,8);
        } else {
            imageview = (ImageView) convertView;
        }

        Picasso.with(mContext).load(images[position]).into(imageview);
        return imageview;
    }

    private String[] images = {"http://i.imgur.com/DvpvklR.png", "http://i.imgur.com/DvpvklR.png",
            "http://i.imgur.com/DvpvklR.png"};
}
