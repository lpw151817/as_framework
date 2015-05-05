package jerry.framework.util;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.os.StatFs;
import android.provider.MediaStore;

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
 * 文件工具类
 * Created by JerryLiu on 2015/4/17.
 */
public class FileUtils {
    /**
     * 判断是否有SD卡
     */
    public static boolean isSdcardExist() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * 获取SD卡路径
     */
    public static String getSDCardPath() {
        return Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator;
    }

    /**
     * 获取SD卡大小，单位是byte
     */
    public static long getSDCardAllSize() {
        if (isSdcardExist()) {
            StatFs stat = new StatFs(getSDCardPath());
            long availableBlocks = (long) stat.getAvailableBlocks() - 4;
            long freeBlocks = stat.getAvailableBlocks();
            return freeBlocks * availableBlocks;
        }
        return 0;
    }

    /**
     * 获取对应路径剩余大小，单位是byte
     *
     * @return 如果是SD卡的路径则返回SD卡的剩余，如果不是，则返回对应数据文件夹的大小
     */
    public static long getFreeBytes(String filePath) {
        if (filePath.startsWith(getSDCardPath())) {
            filePath = getSDCardPath();
        } else {
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
     * 获取root文件夹地址
     */
    public static String getRootDirectoryPath() {
        return Environment.getRootDirectory().getAbsolutePath();
    }

    /**
     * 创建文件夹
     *
     * @return 创建失败返回false
     */
    public static boolean createDirFileWithoutFileName(String path) {
        File dir = new File(path);
        if (!dir.exists()) {
            return dir.mkdirs();
        }
        return true;
    }

//    public static void createDirFileWithFileName(String path) {
//        File dir = new File(path);
//        if (!dir.exists()) {
//            dir.getParentFile().mkdirs();
//        }
//    }

    /**
     * 创建文件
     *
     * @return 创建成功返回对应file对象，创建失败返回null
     */
    public static File createFile(String path, String fileName) {
        try {
            File f = new File(path, fileName);
            //判断父文件夹是否存在
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
     * 删除文件
     */
    public static boolean deleteFile(String path, String fileName) {
        File f = new File(path, fileName);
        if (!f.exists()) return false;
        if (f.isDirectory()) return false;
        return f.delete();
    }

    /**
     * 删除文件夹中所有文件
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
     * 删除文件夹，并删除其中所有文件
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
     * 从file对象中获取Uri
     */
    public static Uri getUriFromFile(String path, String fileName) {
        File file = new File(path, fileName);
        return Uri.fromFile(file);
    }

    public static File getFileFromUri(Uri contentUri, Context c) {
        if (contentUri.getScheme().equals(ContentResolver.SCHEME_CONTENT)) {
            String res = null;
            String[] proj = {MediaStore.Images.Media.DATA};
            Cursor cursor = c.getContentResolver().query(contentUri, proj, null, null, null);
            if (cursor.moveToFirst()) {
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                res = cursor.getString(column_index);
            }
            cursor.close();
            return new File(res);
        } else {
            return new File(contentUri.getPath());
        }

    }

    /**
     * 写文件，并自动换行
     *
     * @param f
     * @param s 需要写入的一行内容
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
     * 读文件
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
