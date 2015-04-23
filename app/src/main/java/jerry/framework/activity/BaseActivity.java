package jerry.framework.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.Arrays;
import java.util.List;

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

    /**
     * e.g.
     * fromJson(js, Test.class);
     */
    protected <T extends Object> T fromJson(String json, Class<T> target) {
        return getGson().fromJson(json, target);
    }

    /**
     * e.g.
     * fromJson2List(js,Test[].class)
     */
    protected <T extends Object> List<T> fromJson2List(String s, Class<T[]> clazz) {
        return Arrays.asList(new Gson().fromJson(s, clazz));
    }

    protected String toJson(Object o) {
        return getGson().toJson(o);
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


    /**
     * 跳转Activity
     *
     * @param bundle 可为null
     */
    protected void startActivity(Class<?> targetActivity, Bundle bundle) {
        Intent intent = new Intent(this, targetActivity);
        if (bundle != null) intent.putExtras(bundle);
        startActivity(intent);
    }

    /**
     * Activity注销功能
     *
     * @param loginActivity 登陆的Activity
     * @param bundle        可为null
     */
    protected void logoutActivity(Class<?> loginActivity, Bundle bundle) {
        Intent intent = new Intent(this, loginActivity);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
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
