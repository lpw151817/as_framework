package jerry.framework.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Created by JerryLiu on 2015/4/17.
 */
public class PicUtils {
//    // SD卡下的图片文件存储路径
//    private static final String IMAGE_PATH_WITH_SD_CARD = FileUtils.getSDCardPath() + "Images" + File.separator;
//    // 无SD卡下的图片文件存储路径
//    private static final String IMAGE_PATH_WITHOUT_SD_CARD = Environment.getDataDirectory().getPath() + File.separator + "Images" + File.separator;


    // 图库的RequestCode
    public static final int INTENT_REQUEST_CODE_ALBUM = 0;
    // 相机的 RequestCode
    public static final int INTENT_REQUEST_CODE_CAMERA = 1;

    /**
     * 选择图库中的照片
     */
    public static void selectPhoto(Activity activity) {
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                "image/*");
        activity.startActivityForResult(intent, INTENT_REQUEST_CODE_ALBUM);
    }

    /**
     * 调用系统拍照程序进行拍照
     *
     * @param activity
     * @return 返回图片路径，拍照失败返回null
     */
    public static String takePicture(Activity activity) {
        String path;

        if (FileUtils.isSdcardExist())
            path = activity.getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES) + File.separator + "";
        else
            path = activity.getApplicationContext().getFilesDir() + File.separator + "Pictures" + File.separator;
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (!FileUtils.createDirFileWithoutFileName(path)) {
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
     * 图片剪裁
     */
    public static void cropPhoto() {

    }


    /**
     * 从文件中读取出bitmap
     */
    public static Bitmap getBitmapFromFile(String path) {
        return BitmapFactory.decodeFile(path);
    }

    /**
     * uri中读取出bitmap
     *
     * @return 失败返回null
     */
    public static Bitmap getBitmapFromUri(Context c, Uri uri) {
        try {
            return BitmapFactory.decodeStream(c.getContentResolver().openInputStream(uri));
        } catch (FileNotFoundException e) {
            return null;
        }
    }
}
