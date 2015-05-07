package jerry.framework.request;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;

import java.io.File;
import java.io.IOException;

import ch.boye.httpclientandroidlib.HttpResponse;
import ch.boye.httpclientandroidlib.HttpVersion;
import ch.boye.httpclientandroidlib.client.HttpClient;
import ch.boye.httpclientandroidlib.client.methods.HttpPost;
import ch.boye.httpclientandroidlib.entity.ContentType;
import ch.boye.httpclientandroidlib.entity.mime.HttpMultipartMode;
import ch.boye.httpclientandroidlib.entity.mime.MultipartEntityBuilder;
import ch.boye.httpclientandroidlib.impl.client.DefaultHttpClient;
import ch.boye.httpclientandroidlib.params.CoreProtocolPNames;
import ch.boye.httpclientandroidlib.params.HttpConnectionParams;
import ch.boye.httpclientandroidlib.params.HttpParams;
import jerry.framework.activity.TestActivity;
import jerry.framework.util.FileUtils;

/**
 * Created by JerryLiu on 2015/4/27.
 */
public class FileUploadRequest extends AsyncTask<Uri, Integer, String> {

    Context c;
    MyCallBack myCallBack;

    public FileUploadRequest(Context c, MyCallBack cb) {
        this.c = c;
        this.myCallBack = cb;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if (s != null) {
            System.out.println(s);
        }
        this.myCallBack.response(TestActivity.UPLOAD_CALLBACK_FLAG, s);
    }

    @Override
    protected String doInBackground(Uri... params) {

        try {

            File f = FileUtils.getFileFromUri(params[0], this.c);
            if (f == null) {
                return "fail in get file.......<<<<<<<<<<";
            }
            HttpClient httpclient = new DefaultHttpClient();
            httpclient.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
            HttpPost httppost = new HttpPost(TestActivity.URL);

            MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
            multipartEntityBuilder.addBinaryBody("data", f, ContentType.DEFAULT_BINARY, f.getName());
            multipartEntityBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
            httppost.setEntity(multipartEntityBuilder.build());

            HttpParams p = httpclient.getParams();
            HttpConnectionParams.setConnectionTimeout(p, 3000);
            HttpResponse response = httpclient.execute(httppost);

            if (response.getStatusLine().getStatusCode() == 200)
                return response.toString();
            else
                return "failed";
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
