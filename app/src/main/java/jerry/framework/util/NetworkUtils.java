package jerry.framework.util;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by JerryLiu on 2015/4/24.
 */
public class NetworkUtils {
    public enum NetWorkState {
        WIFI, MOBILE, NONE;
    }

    /**
     * 判断网络连接状态
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

    /**
     * 打开网络设置
     */
    public static void openWirelessSetting(Activity activity) {
        Intent intent = new Intent("/");
        ComponentName cm = new ComponentName("com.android.settings", "com.android.settings.WirelessSettings");
        intent.setComponent(cm);
        intent.setAction("android.intent.action.VIEW");
        activity.startActivityForResult(intent, 0);
    }
}
