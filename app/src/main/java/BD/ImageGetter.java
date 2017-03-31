package BD;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.InputStream;

public
class ImageGetter extends AsyncTask<String, Void, Bitmap> {

    private String uri;
    private int id;
    public AsyncImage delegate;

    public interface AsyncImage {
        void imageLoadead(Bitmap bitmap, int mReqId);
    }

    public ImageGetter(AsyncImage deleg, String urimage, int mReqId){
        uri = urimage;
        id = mReqId;
        delegate = deleg;
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        Bitmap moviePoster = null;
        try {
            InputStream in = new java.net.URL(uri).openStream();
            moviePoster = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return moviePoster;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        delegate.imageLoadead(bitmap,id);
    }
}
