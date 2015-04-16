package jerry.framework.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;


/**
 * Created by JerryLiu on 2015/4/14.
 */
public class Utils {
    public static int getVersionCode(Context context)// ��ȡ�汾��(�ڲ�ʶ���)
    {
        try {
            PackageInfo pi = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return pi.versionCode;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
            return 0;
        }
    }
}