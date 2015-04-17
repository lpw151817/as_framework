package jerry.framework;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.acra.ACRA;
import org.acra.ErrorReporter;
import org.acra.ReportField;
import org.acra.ReportingInteractionMode;
import org.acra.annotation.ReportsCrashes;
import org.acra.collector.CrashReportData;
import org.acra.sender.ReportSender;
import org.acra.sender.ReportSenderException;

import java.io.File;
import java.io.IOException;

import jerry.framework.util.Contants;
import jerry.framework.util.DateUtils;
import jerry.framework.util.FileUtils;
import jerry.framework.util.VolleyTools;

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
    private VolleyTools tools;
    private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

    @Override
    public void onCreate() {
        super.onCreate();
        this.tools = new VolleyTools(this);
        ACRA.init(this);
        ErrorReporter.getInstance().removeAllReportSenders();
        ErrorReporter.getInstance().setReportSender(new CrashReportSender(this));
    }

    public VolleyTools getTools() {
        return tools;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        tools.release();
    }

    public Gson getGson() {
        return gson;
    }

    private class CrashReportSender implements ReportSender {

        private Context context;

        public CrashReportSender(Context ctx) {
            this.context = ctx;
        }

        @Override
        public void send(CrashReportData arg0) throws ReportSenderException {

            // // ??email??
            // System.out.println("??email");
            // new EmailIntentSender(context).send(arg0);

            // // ??post???formuri
            // System.out.println("??post???formuri");
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

            // // ??CrashReport?google??
            // System.out.println("??CrashReport?google??");
            // new GoogleFormSender().send(arg0);

            // ????log
            String fileName = "CrashReport_" + DateUtils.getDateString() + ".txt";
            File logFile =FileUtils.createFile(Contants.crash_log_path_without_filename, fileName);

            try {
                FileUtils.writeFile(logFile,"ReportField.ANDROID_VERSION = "
                        + arg0.getProperty(ReportField.ANDROID_VERSION));

                FileUtils.writeFile(logFile,"ReportField.ANDROID_VERSION = "
                        + arg0.getProperty(ReportField.ANDROID_VERSION));
                FileUtils.writeFile(logFile,"ReportField.APP_VERSION_NAME = "
                        + arg0.getProperty(ReportField.APP_VERSION_NAME));
                FileUtils.writeFile(logFile,"ReportField.APP_VERSION_CODE = "
                        + arg0.getProperty(ReportField.APP_VERSION_CODE));
                FileUtils.writeFile(logFile,"ReportField.ANDROID_VERSION = "
                        + arg0.getProperty(ReportField.ANDROID_VERSION));
                FileUtils.writeFile(logFile,"ReportField.PHONE_MODEL = " + arg0.getProperty(ReportField.PHONE_MODEL));
                FileUtils.writeFile(logFile,"ReportField.USER_CRASH_DATE = "
                        + arg0.getProperty(ReportField.USER_CRASH_DATE));
                FileUtils.writeFile(logFile,"============= ReportField.THREAD_DETAILS ================");
                FileUtils.writeFile(logFile,"ReportField.THREAD_DETAILS = "
                        + arg0.getProperty(ReportField.THREAD_DETAILS));
                FileUtils.writeFile(logFile,"============= ReportField.STACK_TRACE ================");
                FileUtils.writeFile(logFile,"ReportField.STACK_TRACE = " + arg0.getProperty(ReportField.STACK_TRACE));
                FileUtils.writeFile(logFile,"============= ReportField.LOGCAT ================");
                FileUtils.writeFile(logFile,"ReportField.LOGCAT = " + arg0.getProperty(ReportField.LOGCAT));

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
