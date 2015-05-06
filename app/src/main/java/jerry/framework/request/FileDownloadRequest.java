package jerry.framework.request;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;

import java.io.File;

import jerry.framework.util.FileUtils;
import jerry.framework.util.NetworkUtils;

/**
 * Created by JerryLiu on 2015/5/5.
 */
public class FileDownloadRequest extends AsyncTask<String, Integer, String> {

    MyCallBack cb;
    Context c;

    public FileDownloadRequest(Context c, MyCallBack cb) {
        this.c = c;
        this.cb = cb;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        System.out.println(s + "<<<<<<<<<<<<<<<<<<<<<<<");
        cb.response(2, s);
    }

    @Override
    protected String doInBackground(String... params) {

        DownloadManager dm = (DownloadManager) this.c.getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(params[0]);

        DownloadManager.Request request = new DownloadManager.Request(uri);


        if (FileUtils.isSdcardExist()) {
            String path = "test" + File.separator;
            if (FileUtils.createDirFileWithoutFileName(FileUtils.getSDCardPath() + path))
                request.setDestinationInExternalPublicDir(path, NetworkUtils.getFileNameFromURL(uri.getPath()));
            else return null;
        } else
            request.setDestinationInExternalFilesDir(this.c, "test" + File.separator, NetworkUtils.getFileNameFromURL(uri.getPath()));
        request.setVisibleInDownloadsUi(true);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
        long reference = dm.enqueue(request);
        return "success!!!!";
    }
}
