package jerry.framework.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;

import jerry.framework.Application;
import jerry.framework.dao.BaseDAO;
import jerry.framework.util.VolleyTools;

/**
 * Created by JerryLiu on 2015/4/14.
 */
public abstract class BaseActivity extends Activity {
    // 用于本地数据库存储
    protected BaseDAO dao;

    /**
     * 初始化Views和Event
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        iniViews();
        iniEvent();
    }

    @Override
    protected void onStop() {
        super.onStop();
        getVolleyTools().getQueue().cancelAll(this);
    }

    protected abstract void iniViews();

    protected abstract void iniEvent();

    protected VolleyTools getVolleyTools() {
        return ((Application) getApplication()).getTools();
    }

    protected Gson getGson() {
        return ((Application) getApplication()).getGson();
    }

    protected void showShortToast(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    protected void showShortToast(int resID) {
        Toast.makeText(this, resID, Toast.LENGTH_SHORT).show();
    }

    protected void showLongToast(int resID) {
        Toast.makeText(this, resID, Toast.LENGTH_LONG).show();
    }

    protected void showLongToast(String s) {
        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
    }

    protected void showLog(String s) {
        Log.i(this.getClass().getName(), s);
    }


    protected void startActivity(Class<?> targetActivity, Bundle bundle) {
        Intent intent = new Intent(this, targetActivity);
        if (bundle != null) intent.putExtras(bundle);
        startActivity(intent);
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
