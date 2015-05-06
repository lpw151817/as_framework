package jerry.framework.activity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import jerry.framework.R;
import jerry.framework.request.FileDownloadRequest;
import jerry.framework.request.FileUploadRequest;
import jerry.framework.request.MyCallBack;
import jerry.framework.util.PicUtils;

public class TestActivity extends BaseActivity implements View.OnClickListener, MyCallBack {


    TextView h;
    Button b, b4, b5;

    public  static final String URL="http://172.16.13.86/test/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_test);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void iniViews() {
        h = (TextView) findViewById(R.id.hw);
        b = (Button) findViewById(R.id.button);
        b4 = (Button) findViewById(R.id.button4);
        b5 = (Button) findViewById(R.id.button5);

    }

    @Override
    protected void iniEvent() {
        h.setOnClickListener(this);
        b.setOnClickListener(this);
        b4.setOnClickListener(this);
        b5.setOnClickListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PicUtils.INTENT_REQUEST_CODE_ALBUM:
                    showLoadingDialog(null, null);
                    new FileUploadRequest(TestActivity.this, TestActivity.this).execute(data.getData());


                    /**
                     * 该方法不推荐使用
                     */
//                    getVolleyTools().getQueue().add(new FileRequest("http://172.16.13.86/test/", new Response.Listener<String>() {
//                        @Override
//                        public void onResponse(String s) {
//                            showLog_i(s);
//                        }
//                    }, new Response.ErrorListener() {
//                        @Override
//                        public void onErrorResponse(VolleyError volleyError) {
//                            showLog_e(volleyError.toString());
//                        }
//                    }, FileUtils.getFileFromUri(data.getData(), TestActivity.this)));

                    break;
                case 123:
                    showLoadingDialog(null, null);
                    showLog_i(data.getDataString() + "<<<<<<<<<<<<<<<<<<");
                    new FileUploadRequest(TestActivity.this, TestActivity.this).execute(data.getData());
                    break;
                case 456:
                    showLog_i(data.getDataString());
                    break;
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.hw:
                PicUtils.selectPhoto(TestActivity.this);
                break;
            case R.id.button:
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");
                intent.addCategory(Intent.CATEGORY_OPENABLE);

                try {
                    startActivityForResult(Intent.createChooser(intent, "Select a File to Upload"), 123);
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(this, "Please install a File Manager.", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.button4:
                showLoadingDialog(null,null);
                new FileDownloadRequest(TestActivity.this, TestActivity.this).execute(this.URL+"1430289587715.jpg");
                break;
            case R.id.button5:
                Intent intent2 = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
//                Uri uri=Uri.fromFile(new File());
//                intent2.putExtra(MediaStore.EXTRA_OUTPUT,uri);
                startActivityForResult(intent2, 456);
                break;
        }
    }

    @Override
    public void response(int flag, String result) {
        dismissLoadingDialog();
        switch (flag) {
            default:
                showLog_i(result + "<<<<<<<<<<<<<<<");
        }
    }
}
