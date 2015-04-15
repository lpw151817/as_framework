package jerry.framework.activity;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;

import jerry.framework.Application;
import jerry.framework.dao.BaseDAO;
import jerry.framework.util.VolleyTools;

/**
 * Created by JerryLiu on 2015/4/14.
 */
public class BaseActivity extends Activity {
    // 用于本地数据库存储
    protected BaseDAO dao;

    @Override
    protected void onStop() {
        super.onStop();
        getVolleyTools().getQueue().cancelAll(this);
    }

    public VolleyTools getVolleyTools() {
        return ((Application) getApplication()).getTools();
    }

    public Gson getGson() {
        return ((Application) getApplication()).getGson();
    }

    public void showToast(String s) {
        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
    }

    public void showLog(String s) {
        Log.i(this.getClass().getName(), s);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 释放掉dao
        if (dao != null) {
            dao.release();// 释放dao中的databasehelper
            dao = null;
        }

    }
}
