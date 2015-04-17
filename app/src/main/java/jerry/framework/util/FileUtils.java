package jerry.framework.util;

import android.net.Uri;
import android.os.Environment;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * 文件工具类
 * Created by JerryLiu on 2015/4/17.
 */
public class FileUtils {
    /**
     * 判断是否有SD卡
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
     * 创建目录
     */
    public static void createDirFileWithoutFileName(String path) {
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdirs();
        }
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
     * 删除文件
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
     * 删除对应目录下的所有文件
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
     * 删除目录并删除对应目录下的所有文件
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
