package jerry.framework.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by JerryLiu on 2015/4/17.
 */
public class SystemUtils {
    public static int getVersionCode(Context context)// 获取版本号(内部识别号)
    {
        try {
            PackageInfo pi = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return pi.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return 0;
        }
    }


    public enum NetWorkState {
        WIFI, MOBILE, NONE;
    }

    /**
     * 获取当前的网络状态
     *
     * @return
     */
    public static NetWorkState getConnectState(Context mContext) {
        ConnectivityManager manager = (ConnectivityManager) mContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        manager.getActiveNetworkInfo();
        NetworkInfo.State wifiState = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
        NetworkInfo.State mobileState = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();
        if (wifiState != null && mobileState != null && NetworkInfo.State.CONNECTED != wifiState && NetworkInfo.State.CONNECTED == mobileState) {
            return NetWorkState.MOBILE;
        } else if (wifiState != null && mobileState != null && NetworkInfo.State.CONNECTED != wifiState && NetworkInfo.State.CONNECTED != mobileState) {
            return NetWorkState.NONE;
        } else if (wifiState != null && NetworkInfo.State.CONNECTED == wifiState) {
            return NetWorkState.WIFI;
        }
        return NetWorkState.NONE;
    }
}
