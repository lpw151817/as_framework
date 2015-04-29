package jerry.framework.request;

import android.app.ProgressDialog;
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
import jerry.framework.util.FileUtils;

/**
 * Created by JerryLiu on 2015/4/27.
 */
public class FileUploadRequest extends AsyncTask<Uri, Integer, String> {

    ProgressDialog pd;
    Context c;

    public FileUploadRequest(Context c) {
        this.pd = new ProgressDialog(c);
        this.c = c;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        this.pd.show();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        this.pd.dismiss();
        if (s != null) {
            System.out.println(s);
        }

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
            HttpPost httppost = new HttpPost("http://172.16.13.113/test/");

//            ================= method 1
            MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
            multipartEntityBuilder.addBinaryBody("data", f, ContentType.DEFAULT_BINARY, f.getName());
            multipartEntityBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
            httppost.setEntity(multipartEntityBuilder.build());

//            //=================method 2====================
//            //================= 已废除 ====================
//            FileBody fb = new FileBody(f);
//            MultipartEntity entity = new MultipartEntity();
//
//            entity.addPart("data", fb);
//            httppost.setEntity(entity);


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
