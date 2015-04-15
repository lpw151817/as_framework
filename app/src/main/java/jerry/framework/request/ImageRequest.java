package jerry.framework.request;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;

/**
 * Created by JerryLiu on 2015/4/14.
 */
public class ImageRequest extends com.android.volley.toolbox.ImageRequest {
    //³¬Ê±Ê±¼ä
    private final int SOCKET_TIMEOUT = 5000;

    public ImageRequest(String url, Response.Listener<Bitmap> listener, int maxWidth, int maxHeight, Response.ErrorListener errorListener) {
        super(url, listener, maxWidth, maxHeight, Config.ARGB_8888, errorListener);
        this.setRetryPolicy(new DefaultRetryPolicy(SOCKET_TIMEOUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

}
