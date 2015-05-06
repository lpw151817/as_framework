package jerry.framework.request;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import ch.boye.httpclientandroidlib.HttpEntity;
import ch.boye.httpclientandroidlib.entity.ContentType;
import ch.boye.httpclientandroidlib.entity.mime.HttpMultipartMode;
import ch.boye.httpclientandroidlib.entity.mime.MultipartEntityBuilder;

/**
 * ==============================
 * 不推荐使用
 * ==============================
 *
 * Created by JerryLiu on 2015/5/6.
 */
@Deprecated
public class FileRequest extends Request<String> {

    Response.Listener<String> l;
    File f;
    private HttpEntity mEntity;
    //连接超时时间
    private final int SOCKET_TIMEOUT = 5000;

    public FileRequest(String url, Response.Listener<String> listener, Response.ErrorListener errorListener, File file) {
        super(Method.POST, url, errorListener);
        this.l = listener;
        this.f = file;
        this.setRetryPolicy(new DefaultRetryPolicy(SOCKET_TIMEOUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
        multipartEntityBuilder.addBinaryBody("data", file, ContentType.DEFAULT_BINARY, file.getName());
        multipartEntityBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
        this.mEntity = multipartEntityBuilder.build();
    }

    /**
     * 网络请求
     */
    @Override
    protected Response parseNetworkResponse(NetworkResponse networkResponse) {
        String parsed;
        try {
            parsed = new String(networkResponse.data, HttpHeaderParser.parseCharset(networkResponse.headers));
        } catch (UnsupportedEncodingException e) {
            parsed = new String(networkResponse.data);
        }
        return Response.success(parsed, HttpHeaderParser.parseCacheHeaders(networkResponse));

    }

    /**
     * 回调
     */
    @Override
    protected void deliverResponse(String s) {
        if (l != null)
            this.l.onResponse(s);
    }


    @Override
    public String getBodyContentType() {
        return mEntity.getContentType().getValue();
    }

    @Override
    public byte[] getBody() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            mEntity.writeTo(baos);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return baos.toByteArray();
    }


}
