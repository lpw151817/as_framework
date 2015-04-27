package jerry.framework.util;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import java.io.File;

/**
 * Created by JerryLiu on 2015/4/17.
 */
public class PicUtils {
//    // 图片在SD卡中的缓存路径
//    private static final String IMAGE_PATH_WITH_SD_CARD = FileUtils.getSDCardPath() + "Images" + File.separator;
//    // 图片在无SD卡中的缓存路径
//    private static final String IMAGE_PATH_WITHOUT_SD_CARD = Environment.getDataDirectory().getPath() + File.separator + "Images" + File.separator;


    // 相册的RequestCode
    public static final int INTENT_REQUEST_CODE_ALBUM = 0;
    // 照相的RequestCode
    public static final int INTENT_REQUEST_CODE_CAMERA = 1;

    /**
     * 通过手机相册获取图片
     */
    public static void selectPhoto(Activity activity) {
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                "image/*");
        activity.startActivityForResult(intent, INTENT_REQUEST_CODE_ALBUM);
    }

    /**
     * 通过手机照相获取图片
     *
     * @param activity
     * @return 照相后图片的路径 失败返回null
     */
    public static String takePicture(Activity activity) {
        String path;

        if (FileUtils.isSdcardExist())
            path = activity.getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES) + File.separator + "";
        else
            path = activity.getApplicationContext().getFilesDir() + File.separator+"Pictures"+File.separator;
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if(!FileUtils.createDirFileWithoutFileName(path)){
            return null;
        }
        File file = FileUtils.createFile(path, DateUtils.getDateString() + ".jpg");
        if (file != null) {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        }
        activity.startActivityForResult(intent, INTENT_REQUEST_CODE_CAMERA);
        return file.getPath();

    }

    /**
     * ===========================
     * <p/>
     * 暂未实现
     * <p/>
     * ===========================
     * <p/>
     * 裁剪图片
     */
    public static void cropPhoto() {

    }


    /**
     * 从文件中获取图片
     *
     * @param path 图片的路径
     */
    public static Bitmap getBitmapFromFile(String path) {
        return BitmapFactory.decodeFile(path);
    }

    /**
     * ===========================
     * <p/>
     * 暂未实现
     * <p/>
     * ===========================
     * <p/>
     * 从Uri中获取图片
     *
     * @param cr  ContentResolver对象
     * @param uri 图片的Uri
     */
    public static Bitmap getBitmapFromUri(ContentResolver cr, Uri uri) {
        //该方法不一定可靠
//        try {
//            return BitmapFactory.decodeStream(cr.openInputStream(uri));
//        } catch (FileNotFoundException e) {
//            return null;
//        }
        return null;
    }

}
