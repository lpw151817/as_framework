package jerry.framework.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageCache;
import com.android.volley.toolbox.Volley;

/**
 * Created by JerryLiu on 2015/4/14.
 */
public class VolleyTools {
    private RequestQueue queue;
    private ImageLoader imageLoader;

    public VolleyTools(Context context) {
        this.queue = Volley.newRequestQueue(context);
        this.imageLoader = new ImageLoader(this.queue, new ImgCache());
    }

    public RequestQueue getQueue() {
        return queue;
    }

    public ImageLoader getImageLoader() {
        return imageLoader;
    }

    public void release() {
        this.queue.stop();
        this.queue = null;
        this.imageLoader = null;
    }


    private class ImgCache implements ImageCache {
        private LruCache<String, Bitmap> cache;
        private int size = 20;

        public ImgCache() {
            cache = new LruCache<String, Bitmap>(size);
        }

        @Override
        public Bitmap getBitmap(String arg0) {
            return cache.get(arg0);
        }

        @Override
        public void putBitmap(String arg0, Bitmap arg1) {
            cache.put(arg0, arg1);
        }

    }
}
