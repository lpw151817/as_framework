package jerry.framework.util;

import android.os.Environment;

import java.io.File;

/**
 * Created by JerryLiu on 2015/4/14.
 */
public class Contants {
    public static final String crash_log_path_without_filename = Environment.getExternalStorageDirectory().getPath() + File.separator + "Log" + File.separator;
}
