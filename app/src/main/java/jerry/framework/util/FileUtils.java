package jerry.framework.util;

import android.net.Uri;
import android.os.Environment;
import android.os.StatFs;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

/**
 * �ļ�������
 * Created by JerryLiu on 2015/4/17.
 */
public class FileUtils {
    /**
     * �ж��Ƿ���SD��
     */
    public static boolean isSdcardExist() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * ��ȡSD��·��
     */
    public static String getSDCardPath() {
        return Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator;
    }

    /**
     * ��ȡSD����ʣ������ ��λbyte
     */
    public static long getSDCardAllSize() {
        if (isSdcardExist()) {
            StatFs stat = new StatFs(getSDCardPath());
            // ��ȡ���е����ݿ������
            long availableBlocks = (long) stat.getAvailableBlocks() - 4;
            // ��ȡ�������ݿ�Ĵ�С��byte��
            long freeBlocks = stat.getAvailableBlocks();
            return freeBlocks * availableBlocks;
        }
        return 0;
    }

    /**
     * ��ȡָ��·�����ڿռ��ʣ����������ֽ�������λbyte
     *
     * @return �����ֽ� SDCard���ÿռ䣬�ڲ��洢���ÿռ�
     */
    public static long getFreeBytes(String filePath) {
        // �����sd�����µ�·�������ȡsd����������
        if (filePath.startsWith(getSDCardPath())) {
            filePath = getSDCardPath();
        } else {// ������ڲ��洢��·�������ȡ�ڴ�洢�Ŀ�������
            filePath = Environment.getDataDirectory().getAbsolutePath();
        }
        StatFs stat = new StatFs(filePath);
        long availableBlocks = (long) stat.getAvailableBlocks() - 4;
        return stat.getBlockSize() * availableBlocks;
    }

    public static double byte2Mb(long bytes) {
        return bytes / 1024 / 1024;
    }

    /**
     * ��ȡϵͳ�洢·��
     */
    public static String getRootDirectoryPath() {
        return Environment.getRootDirectory().getAbsolutePath();
    }

    /**
     * ����Ŀ¼
     *
     * @return Ŀ¼�������ɹ�����false
     */
    public static boolean createDirFileWithoutFileName(String path) {
        File dir = new File(path);
        if (!dir.exists()) {
            return dir.mkdirs();
        }
        return true;
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
                if (createDirFileWithoutFileName(path))
                    f.createNewFile();
                else return null;
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
     */
    public static boolean deleteFile(String path, String fileName) {
        File f = new File(path, fileName);
        if (!f.exists()) return false;
        if (f.isDirectory()) return false;
        return f.delete();
    }

    /**
     * ɾ����ӦĿ¼�µ������ļ�
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
     */
    public static void writeFile(File f, String s) throws IOException {
        FileWriter filerWriter = new FileWriter(f, true);
        BufferedWriter bufWriter = new BufferedWriter(filerWriter);
        bufWriter.write(s);
        bufWriter.newLine();
        bufWriter.close();
        filerWriter.close();
    }

    /**
     * ���ļ�
     */
    public static String readFile(String path, String filename) {
        File f = new File(path, filename);
        if (!f.exists()) return null;
        try {
            String readedStr = "";
            BufferedReader br;
            InputStream inputStream = new FileInputStream(f);
            br = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            String tmp;
            while ((tmp = br.readLine()) != null) {
                readedStr += tmp + "\r\n";
            }
            br.close();
            inputStream.close();
            return readedStr;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
