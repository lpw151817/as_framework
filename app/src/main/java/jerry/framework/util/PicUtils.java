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
    // ͼƬ��SD���еĻ���·��
    private static final String IMAGE_PATH_WITH_SD_CARD = Environment.getExternalStorageDirectory().getPath() + File.separator + "Images" + File.separator;
    // ͼƬ����SD���еĻ���·��
    private static final String IMAGE_PATH_WITHOUT_SD_CARD = Environment.getDataDirectory().getPath() + File.separator + "Images" + File.separator;


    // ����RequestCode
    public static final int INTENT_REQUEST_CODE_ALBUM = 0;
    // �����RequestCode
    public static final int INTENT_REQUEST_CODE_CAMERA = 1;

    /**
     * ͨ���ֻ�����ȡͼƬ
     */
    public static void selectPhoto(Activity activity) {
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                "image/*");
        activity.startActivityForResult(intent, INTENT_REQUEST_CODE_ALBUM);
    }

    /**
     * ͨ���ֻ������ȡͼƬ
     *
     * @param activity
     * @return �����ͼƬ��·��
     */
    public static String takePicture(Activity activity) {
        String path;
        if (FileUtils.isSdcardExist()) path = IMAGE_PATH_WITH_SD_CARD;
        else path = IMAGE_PATH_WITHOUT_SD_CARD;

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        FileUtils.createDirFileWithoutFileName(path);
        File file = FileUtils.createFile(path, DateUtils.getDateString() + ".jpg");
        if (file != null) {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        }
        activity.startActivityForResult(intent, INTENT_REQUEST_CODE_CAMERA);
        return file.getPath();
    }

    /**
     * �ü�ͼƬ
     */
    public static void cropPhoto() {

    }


    /**
     * ���ļ��л�ȡͼƬ
     *
     * @param path ͼƬ��·��
     */
    public static Bitmap getBitmapFromFile(String path) {
        return BitmapFactory.decodeFile(path);
    }

    /**
     * ��Uri�л�ȡͼƬ
     *
     * @param cr  ContentResolver����
     * @param uri ͼƬ��Uri
     */
    public static Bitmap getBitmapFromUri(ContentResolver cr, Uri uri) {
        //�÷�����һ���ɿ�
//        try {
//            return BitmapFactory.decodeStream(cr.openInputStream(uri));
//        } catch (FileNotFoundException e) {
//            return null;
//        }
        return null;
    }

}
