package jerry.framework.util;

import android.net.Uri;
import android.os.Environment;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * �ļ�������
 * Created by JerryLiu on 2015/4/17.
 */
public class FileUtils {
    /**
     * �ж��Ƿ���SD��
     *
     * @return
     */
    public static boolean isSdcardExist() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return true;
        }
        return false;
    }

    /**
     * ����Ŀ¼
     */
    public static void createDirFileWithoutFileName(String path) {
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

//    /**
//     * ����Ŀ¼
//     */
//    public static void createDirFileWithFileName(String path) {
//        File dir = new File(path);
//        if (!dir.exists()) {
//            dir.getParentFile().mkdirs();
//        }
//    }

    /**
     * �����ļ�
     *
     * @return �����ɹ��򷵻�File����ʧ�ܷ���null
     */
    public static File createFile(String path, String fileName) {
        try {
            File f = new File(path, fileName);
            //�����Ŀ¼�������򴴽���Ŀ¼
            if (!f.getParentFile().exists()) {
                createDirFileWithoutFileName(path);
                f.createNewFile();
            } else {
                if (!f.exists()) f.createNewFile();
            }
            return f;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * ɾ���ļ�
     *
     * @param path
     * @param fileName
     * @return
     */
    public static boolean deleteFile(String path, String fileName) {
        File f = new File(path, fileName);
        if (!f.exists()) return false;
        if (f.isDirectory()) return false;
        return f.delete();
    }

    /**
     * ɾ����ӦĿ¼�µ������ļ�
     *
     * @param path
     * @return
     */
    public static boolean deleteAllFiles(String path) {
        File f = new File(path);
        if (!f.exists()) return false;
        if (!f.isDirectory()) return false;
        for (File file : f.listFiles()) {
            if (file.delete()) continue;
            else return false;
        }
        return true;
    }

    /**
     * ɾ��Ŀ¼��ɾ����ӦĿ¼�µ������ļ�
     *
     * @param path
     * @return
     */
    public static boolean deleteDir(String path) {
        File f = new File(path);
        if (!f.exists()) return false;
        if (!f.isDirectory()) return false;
        if (deleteAllFiles(path)) {
            if (f.delete()) return true;
            return false;
        } else return false;
    }

    /**
     * ��ȡ�ļ���Uri
     */
    public static Uri getUriFromFile(String path, String fileName) {
        File file = new File(path, fileName);
        return Uri.fromFile(file);
    }

    /**
     * д�ļ������Զ�����
     *
     * @param f
     * @param s ��Ҫд�������
     * @throws IOException
     */
    public static void writeFile(File f, String s) throws IOException {
        FileWriter filerWriter = new FileWriter(f, true);
        BufferedWriter bufWriter = new BufferedWriter(filerWriter);
        bufWriter.write(s);
        bufWriter.newLine();
        bufWriter.close();
        filerWriter.close();
    }
}
