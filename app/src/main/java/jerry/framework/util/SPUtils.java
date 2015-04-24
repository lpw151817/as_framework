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
 * δ����
 * <p/>
 * <p/>
 * ========================================================
 * <p/>
 * <p/>
 * ����SharePreference��������
 * <p/>
 * boolean
 * float
 * int
 * long
 * String
 * <p/>
 * �Զ������ת����String���б���
 */
public class SPUtils {
    /**
     * �������ֻ�������ļ���
     */
    public static final String SP_FILE_NAME = "share_data";

    /**
     * �������ݵķ�����������Ҫ�õ��������ݵľ������ͣ�Ȼ��������͵��ò�ͬ�ı��淽��
     *
     * @param object �������ĳ���Զ���ʵ�������Զ�תΪtoString()���д洢
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
     * �õ��������ݵķ��������Ǹ���Ĭ��ֵ�õ���������ݵľ������ͣ�Ȼ���������ڵķ�����ȡֵ
     *
     * @return �Զ�����󷵻�String;���ص�Object��Ҫǿ��ת���ɶ�Ӧ�Ļ�������
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
     * �Ƴ�ĳ��keyֵ�Ѿ���Ӧ��ֵ
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
     * �����������
     *
     * @param context
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
     * ��ѯĳ��key�Ƿ��Ѿ�����
     *
     * @param context
     * @param key
     * @return
     */
    public static boolean contains(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences(SP_FILE_NAME,
                Context.MODE_PRIVATE);
        return sp.contains(key);
    }

    /**
     * �������еļ�ֵ��
     *
     * @param context
     * @return
     */
    public static Map<String, ?> getAll(Context context) {
        SharedPreferences sp = context.getSharedPreferences(SP_FILE_NAME,
                Context.MODE_PRIVATE);
        return sp.getAll();
    }

    /**
     * ����һ�����SharedPreferencesCompat.apply������һ��������
     * <p/>
     * ��Ϊcommit������ͬ���ģ��������Ǻܶ�ʱ���commit��������UI�߳��У��Ͼ���IO�������������첽��
     * <p/>
     * ��������ʹ��apply���������apply�첽�Ľ���д�룻
     * <p/>
     * ����apply�൱��commit��˵��new API�أ�Ϊ�˸��õļ��ݣ������������䣻
     */
    private static class SharedPreferencesCompat {
        private static final Method sApplyMethod = findApplyMethod();

        /**
         * �������apply�ķ���
         *
         * @return
         */
        @SuppressWarnings({"unchecked", "rawtypes"})
        private static Method findApplyMethod() {
            try {
                Class clz = SharedPreferences.Editor.class;
                return clz.getMethod("apply");
            } catch (NoSuchMethodException e) {
            }

            return null;
        }

        /**
         * ����ҵ���ʹ��applyִ�У�����ʹ��commit
         *
         * @param editor
         */
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
