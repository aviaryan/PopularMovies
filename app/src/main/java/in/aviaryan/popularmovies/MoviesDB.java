package in.aviaryan.popularmovies;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import java.util.ArrayList;

import in.aviaryan.popularmovies.data.MovieContract;
import in.aviaryan.popularmovies.data.MovieContract.MovieEntry;

public class MoviesDB {
    static final String AUTHORITY_Uri = "content://" + MovieContract.AUTHORITY;

    public boolean isMovieFavorited(ContentResolver contentResolver, int id){
        boolean ret = false;
        Cursor cursor = contentResolver.query(Uri.parse(AUTHORITY_Uri + "/" + id), null, null, null, null, null);
        if (cursor != null && cursor.moveToNext()){
            ret = true;
            cursor.close();
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
        contentResolver.insert(Uri.parse(AUTHORITY_Uri + "/movies"), contentValues);
    }

    public void removeMovie(ContentResolver contentResolver, int id){
        Uri uri = Uri.parse(AUTHORITY_Uri + "/" + id);
        contentResolver.delete(uri, null, new String[]{id + ""});
    }

    public ArrayList<Movie> getFavoriteMovies(ContentResolver contentResolver){
        Uri uri = Uri.parse(AUTHORITY_Uri + "/movies");
        Cursor cursor = contentResolver.query(uri, null, null, null, null, null);
        ArrayList <Movie> movies = new ArrayList<>();

        if (cursor != null && cursor.moveToFirst()){
            do {
                Movie movie = new Movie();
                movie.id = cursor.getInt(cursor.getColumnIndex(MovieEntry.COLUMN_ID));
                movie.display_name = cursor.getString(cursor.getColumnIndex(MovieEntry.COLUMN_NAME));
                movie.overview = cursor.getString(cursor.getColumnIndex(MovieEntry.COLUMN_OVERVIEW));
                movie.rating = Float.parseFloat(cursor.getString(cursor.getColumnIndex(MovieEntry.COLUMN_RATING)));
                movie.poster_url = cursor.getString(cursor.getColumnIndex(MovieEntry.COLUMN_POSTER));
                movie.released_date = cursor.getString(cursor.getColumnIndex(MovieEntry.COLUMN_RELEASE));
                movies.add(movie);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return movies;
    }
}