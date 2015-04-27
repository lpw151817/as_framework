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
     * 获取SD卡的剩余容量 单位byte
     */
    public static long getSDCardAllSize() {
        if (isSdcardExist()) {
            StatFs stat = new StatFs(getSDCardPath());
            // 获取空闲的数据块的数量
            long availableBlocks = (long) stat.getAvailableBlocks() - 4;
            // 获取单个数据块的大小（byte）
            long freeBlocks = stat.getAvailableBlocks();
            return freeBlocks * availableBlocks;
        }
        return 0;
    }

    /**
     * 获取指定路径所在空间的剩余可用容量字节数，单位byte
     *
     * @return 容量字节 SDCard可用空间，内部存储可用空间
     */
    public static long getFreeBytes(String filePath) {
        // 如果是sd卡的下的路径，则获取sd卡可用容量
        if (filePath.startsWith(getSDCardPath())) {
            filePath = getSDCardPath();
        } else {// 如果是内部存储的路径，则获取内存存储的可用容量
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
     * 获取系统存储路径
     */
    public static String getRootDirectoryPath() {
        return Environment.getRootDirectory().getAbsolutePath();
    }

    /**
     * 创建目录
     *
     * @return 目录创建不成功返回false
     */
    public static boolean createDirFileWithoutFileName(String path) {
        File dir = new File(path);
        if (!dir.exists()) {
            return dir.mkdirs();
        }
        return true;
    }

//    /**
//     * 创建目录
//     */
//    public static void createDirFileWithFileName(String path) {
//        File dir = new File(path);
//        if (!dir.exists()) {
//            dir.getParentFile().mkdirs();
//        }
//    }

    /**
     * 创建文件
     *
     * @return 创建成功则返回File对象，失败返回null
     */
    public static File createFile(String path, String fileName) {
        try {
            File f = new File(path, fileName);
            //如果父目录不存在则创建父目录
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
     * 删除对应目录下的所有文件
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
     * 删除目录并删除对应目录下的所有文件
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
     * 获取文件的Uri
     */
    public static Uri getUriFromFile(String path, String fileName) {
        File file = new File(path, fileName);
        return Uri.fromFile(file);
    }

    /**
     * 写文件，并自动换行
     *
     * @param f
     * @param s 需要写入的内容
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
