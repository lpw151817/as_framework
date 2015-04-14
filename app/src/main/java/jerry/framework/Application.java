package jerry.framework;

import android.content.Context;

import org.acra.ACRA;
import org.acra.ErrorReporter;
import org.acra.ReportField;
import org.acra.ReportingInteractionMode;
import org.acra.annotation.ReportsCrashes;
import org.acra.collector.CrashReportData;
import org.acra.sender.ReportSender;
import org.acra.sender.ReportSenderException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import jerry.framework.util.Contants;

/**
 * Created by JerryLiu on 2015/4/13.
 */
@ReportsCrashes(formKey = "",// 发送crashReport到google账户中，默认为空
// = "dGVacG0ydVHnaNHjRjVTUTEtb3FPWGc6MQ",
// mailTo = "user@domain.com",// EmailIntentSender发送的目标emial
// formUri = "",//HttpSender默认发送的服务器uri地址
        customReportContent = {ReportField.APP_VERSION_NAME, ReportField.APP_VERSION_CODE,
                ReportField.ANDROID_VERSION, ReportField.PHONE_MODEL, ReportField.STACK_TRACE,
                ReportField.LOGCAT, ReportField.THREAD_DETAILS, ReportField.USER_CRASH_DATE}, mode = ReportingInteractionMode.SILENT)
public class Application extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();


        ACRA.init(this);
        ErrorReporter.getInstance().removeAllReportSenders();
        ErrorReporter.getInstance().setReportSender(new CrashReportSender(this));

    }


    private class CrashReportSender implements ReportSender {

        private Context context;

        public CrashReportSender(Context ctx) {
            this.context = ctx;
        }

        @Override
        public void send(CrashReportData arg0) throws ReportSenderException {

            // // 发送email报告
            // System.out.println("发送email");
            // new EmailIntentSender(context).send(arg0);

            // // 发送post请求到formuri
            // System.out.println("发送post请求到formuri");
            // Map<ReportField, String> params = new HashMap<ReportField,
            // String>();
            // params.put(ReportField.ANDROID_VERSION,
            // arg0.getProperty(ReportField.ANDROID_VERSION));
            // params.put(ReportField.APP_VERSION_NAME,
            // arg0.getProperty(ReportField.APP_VERSION_NAME));
            // params.put(ReportField.APP_VERSION_CODE,
            // arg0.getProperty(ReportField.APP_VERSION_CODE));
            // params.put(ReportField.PHONE_MODEL,
            // arg0.getProperty(ReportField.PHONE_MODEL));
            // params.put(ReportField.STACK_TRACE,
            // arg0.getProperty(ReportField.STACK_TRACE));
            // params.put(ReportField.LOGCAT,
            // arg0.getProperty(ReportField.LOGCAT));
            // params.put(ReportField.THREAD_DETAILS,
            // arg0.getProperty(ReportField.THREAD_DETAILS));
            // params.put(ReportField.USER_CRASH_DATE,
            // arg0.getProperty(ReportField.USER_CRASH_DATE));
            // new HttpSender(Method.POST, Type.JSON, params);

            // // 发送CrashReport到google账户
            // System.out.println("发送CrashReport到google账户");
            // new GoogleFormSender().send(arg0);

            // 保存本地log
            String fileName = "CrashReport_"
                    + new SimpleDateFormat("yyyy-MMddHH-mmss").format(new Date(System
                    .currentTimeMillis())) + ".txt";
            File logFile = new File(Contants.crash_log_path_without_filename, fileName);

            try {
                // 父目录是否存在
                if (!logFile.getParentFile().exists())
                    logFile.getParentFile().mkdirs();
                // 文件是否存在
                if (!logFile.exists())
                    logFile.createNewFile();

                // 写入内容
                FileWriter filerWriter = new FileWriter(logFile, true);
                BufferedWriter bufWriter = new BufferedWriter(filerWriter);
                bufWriter.write("ReportField.ANDROID_VERSION = "
                        + arg0.getProperty(ReportField.ANDROID_VERSION));
                bufWriter.newLine();
                bufWriter.write("ReportField.APP_VERSION_NAME = "
                        + arg0.getProperty(ReportField.APP_VERSION_NAME));
                bufWriter.newLine();
                bufWriter.write("ReportField.APP_VERSION_CODE = "
                        + arg0.getProperty(ReportField.APP_VERSION_CODE));
                bufWriter.newLine();
                bufWriter.write("ReportField.ANDROID_VERSION = "
                        + arg0.getProperty(ReportField.ANDROID_VERSION));
                bufWriter.newLine();
                bufWriter
                        .write("ReportField.PHONE_MODEL = " + arg0.getProperty(ReportField.PHONE_MODEL));
                bufWriter.newLine();
                bufWriter.write("ReportField.USER_CRASH_DATE = "
                        + arg0.getProperty(ReportField.USER_CRASH_DATE));
                bufWriter.newLine();
                bufWriter.write("============= ReportField.THREAD_DETAILS ================");
                bufWriter.newLine();
                bufWriter.write("ReportField.THREAD_DETAILS = "
                        + arg0.getProperty(ReportField.THREAD_DETAILS));
                bufWriter.newLine();
                bufWriter.write("============= ReportField.STACK_TRACE ================");
                bufWriter.newLine();
                bufWriter
                        .write("ReportField.STACK_TRACE = " + arg0.getProperty(ReportField.STACK_TRACE));
                bufWriter.newLine();
                bufWriter.write("============= ReportField.LOGCAT ================");
                bufWriter.newLine();
                bufWriter.write("ReportField.LOGCAT = " + arg0.getProperty(ReportField.LOGCAT));
                bufWriter.newLine();

                bufWriter.close();
                filerWriter.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
