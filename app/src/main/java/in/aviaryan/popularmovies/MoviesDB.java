package in.aviaryan.popularmovies;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import in.aviaryan.popularmovies.data.MovieContract;
import in.aviaryan.popularmovies.data.MovieContract.MovieEntry;

public class MoviesDB {
    public boolean isMovieFavorited(ContentResolver contentResolver, int id){
        boolean ret = false;
        Cursor cursor = contentResolver.query(Uri.parse("content://" + MovieContract.AUTHORITY + "/" + id), null, null, null, null, null);
        if (cursor != null && cursor.moveToNext()){
            ret = true;
        }
        return ret;
    }

    public void addMovie(ContentResolver contentResolver, Movie movie){
        ContentValues contentValues = new ContentValues();
        contentValues.put(MovieEntry.COLUMN_ID, movie.id);
        contentValues.put(MovieEntry.COLUMN_NAME, movie.display_name);
        contentValues.put(MovieEntry.COLUMN_OVERVIEW, movie.overview);
        contentValues.put(MovieEntry.COLUMN_POSTER, movie.poster_url);
        contentValues.put(MovieEntry.COLUMN_RATING, movie.rating + "");
        contentValues.put(MovieEntry.COLUMN_RELEASE, movie.released_date);
        contentResolver.insert(Uri.parse("content://" + MovieContract.AUTHORITY + "/movies"), contentValues);
    }

    public void removeMovie(ContentResolver contentResolver, int id){
        Uri uri = Uri.parse("content://" + MovieContract.AUTHORITY + "/" + id);
        contentResolver.delete(uri, null, new String[]{id+""});
    }
}