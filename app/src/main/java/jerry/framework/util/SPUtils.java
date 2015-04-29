package jerry.framework.util;

import android.content.Context;
import android.content.SharedPreferences;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * ========================================================
 * <p/>
 * <p/>
 * 未测试
 * <p/>
 * <p/>
 * ========================================================
 * <p/>
 * <p/>
 * SharePreference工具类
 * <p/>
 * 可存储：
 * boolean
 * float
 * int
 * long
 * String
 * <p/>
 * 对象转换成String进行存储，默认进行toString()操作
 */
public class SPUtils {
    public static final String SP_FILE_NAME = "share_data";

    /**
     * 存储数据
     *
     * @param object 数据类型：boolean
     *               float
     *               int
     *               long
     *               String
     *               对象转换成String进行存储，默认进行toString()操作
     */
    public static void put(Context context, String key, Object object) {

        SharedPreferences sp = context.getSharedPreferences(SP_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        if (object instanceof String) {
            editor.putString(key, (String) object);
        } else if (object instanceof Integer) {
            editor.putInt(key, (Integer) object);
        } else if (object instanceof Boolean) {
            editor.putBoolean(key, (Boolean) object);
        } else if (object instanceof Float) {
            editor.putFloat(key, (Float) object);
        } else if (object instanceof Long) {
            editor.putLong(key, (Long) object);
        } else {
            editor.putString(key, object.toString());
        }

        SharedPreferencesCompat.apply(editor);
//        editor.commit();
    }

    /**
     * 获取数据
     *
     * @return 数据类型：boolean
     * float
     * int
     * long
     * String
     * 失败返回null
     */
    public static Object get(Context context, String key, Object defaultObject) {
        SharedPreferences sp = context.getSharedPreferences(SP_FILE_NAME, Context.MODE_PRIVATE);
        if (defaultObject == null) return null;
        if (defaultObject instanceof String) {
            return sp.getString(key, (String) defaultObject);
        } else if (defaultObject instanceof Integer) {
            return sp.getInt(key, (Integer) defaultObject);
        } else if (defaultObject instanceof Boolean) {
            return sp.getBoolean(key, (Boolean) defaultObject);
        } else if (defaultObject instanceof Float) {
            return sp.getFloat(key, (Float) defaultObject);
        } else if (defaultObject instanceof Long) {
            return sp.getLong(key, (Long) defaultObject);
        }
        return null;
    }

    /**
     * 删除
     */
    public static void remove(Context context, String... keys) {
        SharedPreferences sp = context.getSharedPreferences(SP_FILE_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        for (String k : keys) {
            editor.remove(k);
        }
        SharedPreferencesCompat.apply(editor);
//        editor.commit();
    }

    /**
     * 清空
     */
    public static void clear(Context context) {
        SharedPreferences sp = context.getSharedPreferences(SP_FILE_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        SharedPreferencesCompat.apply(editor);
//        editor.commit();
    }

    /**
     * 是否包含对应的key
     */
    public static boolean contains(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences(SP_FILE_NAME,
                Context.MODE_PRIVATE);
        return sp.contains(key);
    }

    /**
     * 获取所有数据
     */
    public static Map<String, ?> getAll(Context context) {
        SharedPreferences sp = context.getSharedPreferences(SP_FILE_NAME,
                Context.MODE_PRIVATE);
        return sp.getAll();
    }

    /**
     * 因为commit方法是同步的，并且我们很多时候的commit操作都是UI线程中，毕竟是IO操作，尽可能异步；
     * <p/>
     * 所以我们使用apply进行替代，apply异步的进行写入；
     * <p/>
     * 但是apply相当于commit来说是new API呢，为了更好的兼容，我们做了适配；
     */
    private static class SharedPreferencesCompat {
        private static final Method sApplyMethod = findApplyMethod();

        @SuppressWarnings({"unchecked", "rawtypes"})
        private static Method findApplyMethod() {
            try {
                Class clz = SharedPreferences.Editor.class;
                return clz.getMethod("apply");
            } catch (NoSuchMethodException e) {
            }

            return null;
        }

        public static void apply(SharedPreferences.Editor editor) {
            try {
                if (sApplyMethod != null) {
                    sApplyMethod.invoke(editor);
                    return;
                }
            } catch (IllegalArgumentException e) {
            } catch (IllegalAccessException e) {
            } catch (InvocationTargetException e) {
            }
            editor.commit();
        }
    }
}
