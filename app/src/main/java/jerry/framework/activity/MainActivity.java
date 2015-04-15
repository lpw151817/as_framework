package jerry.framework.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jerry.framework.R;
import jerry.framework.bean.Test;
import jerry.framework.dao.TestDAO;
import jerry.framework.util.VolleyTools;

/**
 * <b>AndroidAnnotation's usage:</b><br/>
 * http://blog.csdn.net/limb99/article/details/9067827<br/>
 * <b>You also can study it by:</b><br/>
 * https://github.com/excilys/androidannotations/wiki<br/>
 * <br/>
 * <b>Volley's usage:</b><br/>
 * You can get it by the example below.<br/>
 * <br/>
 *
 * @author Jerry
 */
@EActivity(R.layout.activity_main)
public class MainActivity extends BaseActivity {

    String url;
    @ViewById(R.id.test)
    TextView textView;
    @ViewById(R.id.image)
    NetworkImageView imageView;
    @ViewById(R.id.imageview)
    ImageView img;

    VolleyTools tool;

    // AfterViews注释定义的方法会在OnCreate方法的setContentView后执行
    @AfterViews
    void ini() {

        try {
            dao = new TestDAO(this);
            System.out.println(dao.isTableExsits());
            dao.deleteAll();
            System.out.println("===============test insert");
            System.out.println("insert:1 1 ");
            dao.insert(new Test(1, 1));
            System.out.println("insert:2 2");
            dao.insert(new Test(2, 2));

            System.out.println("===============test query");
            System.out.println("query state=1");
            for (Test test : (List<Test>) dao.query("state", "1")) {
                System.out.println(test);
            }
            System.out.println("query all");
            for (Test test : (List<Test>) dao.queryAll()) {
                System.out.println(test);
            }
            System.out.println("insert all");
            List<Test> ls = new ArrayList<Test>();
            ls.add(new Test(1, 3));
            ls.add(new Test(1, 4));
            dao.insert(ls);
            System.out.println("query all");
            for (Test test : (List<Test>) dao.queryAll()) {
                System.out.println(test);
            }

            System.out.println("query userprofileId=1,state=1");
            for (Test test : (List<Test>) dao.query(new String[]{"userprofileId",
                    "state"}, new String[]{"1", "1"})) {
                System.out.println(test);
            }
            System.out.println("query userprofileId=2,state=1");
            for (Test test : (List<Test>) dao.query(new String[]{"userprofileId",
                    "state"}, new String[]{"2", "1"})) {
                System.out.println(test);
            }

            Map<String, Object> map = new HashMap<String, Object>();
            map.put("userprofileId", 4);
            for (Test test : (List<Test>) dao.query(map)) {
                System.out.println(test);
            }

//            Map<String, Object> map = new HashMap<String, Object>();
//            map.put("state", 1);
//            Map<String, Object> low = new HashMap<String, Object>();
//            low.put("userprofileId", 1);
//            Map<String, Object> high = new HashMap<String, Object>();
//            high.put("userprofileId", 4);
//            for (Test test : dao.query(map, low, high)) {
//                System.out.println(test);
//            }
//
//            System.out.println("===============test update");
//            List<Test> list = dao.queryAll();
//
//            Test test_o = list.get(0);
//
//            test_o.setState(555);
//
//            dao.update(test_o);
//            System.out.println("query all");
//            for (Test test : (List<Test>) dao.queryAll()) {
//                System.out.println(test);
//            }
//
//            System.out.println("======================test delete");
//            System.out.println("query all");
//            for (Test test : (List<Test>) dao.queryAll()) {
//                System.out.println(test);
//            }
//            System.out.println("delete state=3");
//            dao.delete("state", "3");
//            for (Test test : (List<Test>) dao.queryAll()) {
//                System.out.println(test);
//            }
//            System.out.println("delete state=2 userprofileId=56");
//            dao.delete(new String[]{"state", "userprofileId"}, new
//                    String[]{"2", "56"});
//            for (Test test : (List<Test>) dao.queryAll()) {
//                System.out.println(test);
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }


//        // 得到VolleyTools的实例，已定义在Application，从BaseActivity中取出
//        tool = getVolleyTools();
//
//        // 数据请求
//        // post
//        url = "http://114.215.178.134:8080/tyc/app/Product_getProducts";
//        Test value = new Test(1, 2);
//
//        Map<String, String> param = new HashMap<String, String>();
//        param.put("input", getGson().toJson(value));
//
//
//        tool.getQueue().add(new StringRequest(url, new Response.Listener<String>() {
//
//            @Override
//            public void onResponse(String arg0) {
//                textView.setText(arg0);
//            }
//        }, new Response.ErrorListener() {
//
//            @Override
//            public void onErrorResponse(VolleyError arg0) {
//                System.out.println(arg0.toString());
//                textView.setText(arg0.toString());
//            }
//        }, param));
//
//        // get
//        url = "http://114.215.178.134:8080/tyc/app/Product_getProducts?input={%22state%22:1,%22userprofileId%22:2}";
//        tool.getQueue().add(new StringRequest(url, new Response.Listener<String>() {
//
//            @Override
//            public void onResponse(String arg0) {
//                textView.setText(arg0);
//            }
//        }, new ErrorListener() {
//
//            @Override
//            public void onErrorResponse(VolleyError arg0) {
//                System.out.println(arg0.toString());
//            }
//        }));
//
//        // 图片请求
//        url = "http://news.baidu.com/resource/img/logo_news_137_46.png";
//        // 方法一：ImageRequest能够处理单张图片
//        tool.getQueue().add(new ImageRequest(url, new Listener<Bitmap>() {
//
//            @Override
//            public void onResponse(Bitmap arg0) {
//                img.setImageBitmap(arg0);
//            }
//        }, 300, 200, new ErrorListener() {
//
//            @Override
//            public void onErrorResponse(VolleyError arg0) {
//                System.out.println(arg0.toString());
//            }
//        }));
//
//        // 方法二，对应imageview绑定对应的网络图片
//        // tool.getImageLoader().get(url, ImageLoader.getImageListener(img,
//        // R.drawable.ic_launcher, 0));
//
//        // 方法三，使用NetworkImageView
//        imageView.setImageUrl(url, tool.getImageLoader());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }
}
