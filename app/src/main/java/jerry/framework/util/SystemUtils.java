package jerry.framework.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * Created by JerryLiu on 2015/4/17.
 */
public class SystemUtils {
    public static int getVersionCode(Context context)// ��ȡ�汾��(�ڲ�ʶ���)
    {
        try {
            PackageInfo pi = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return pi.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
