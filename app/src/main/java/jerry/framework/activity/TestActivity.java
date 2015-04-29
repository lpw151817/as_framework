package jerry.framework.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import jerry.framework.R;
import jerry.framework.request.FileUploadRequest;
import jerry.framework.util.PicUtils;

public class TestActivity extends BaseActivity implements View.OnClickListener {


    TextView h;
    Button b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_test);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void iniViews() {
        h = (TextView) findViewById(R.id.hw);
        b = (Button) findViewById(R.id.button);
    }

    @Override
    protected void iniEvent() {
        h.setOnClickListener(this);
        b.setOnClickListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PicUtils.INTENT_REQUEST_CODE_ALBUM:

                    new FileUploadRequest(TestActivity.this).execute(data.getData());
                    break;
                case 123:
                    showLog_i(data.getDataString() + "<<<<<<<<<<<<<<<<<<");
                    new FileUploadRequest(TestActivity.this).execute(data.getData());
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
        }
    }
}
