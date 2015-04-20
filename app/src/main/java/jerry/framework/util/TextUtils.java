package jerry.framework.util;

import android.widget.EditText;

import java.util.Calendar;

/**
 * Created by JerryLiu on 2015/4/17.
 */
public class TextUtils {

    public static boolean isEmpty(EditText et) {
        return getText(et).isEmpty();
    }

    public static String getText(EditText et) {
        return et.getText().toString();
    }

    /**
     * 根据年月日获取年龄
     *
     * @param year
     *            年
     * @param month
     *            月
     * @param day
     *            日
     * @return
     */
    public static int getAge(int year, int month, int day) {
        int age = 0;
        Calendar calendar = Calendar.getInstance();
        if (calendar.get(Calendar.YEAR) == year) {
            if (calendar.get(Calendar.MONTH) == month) {
                if (calendar.get(Calendar.DAY_OF_MONTH) >= day) {
                    age = calendar.get(Calendar.YEAR) - year + 1;
                } else {
                    age = calendar.get(Calendar.YEAR) - year;
                }
            } else if (calendar.get(Calendar.MONTH) > month) {
                age = calendar.get(Calendar.YEAR) - year + 1;
            } else {
                age = calendar.get(Calendar.YEAR) - year;
            }
        } else {
            age = calendar.get(Calendar.YEAR) - year;
        }
        if (age < 0) {
            return 0;
        }
        return age;
    }
}
