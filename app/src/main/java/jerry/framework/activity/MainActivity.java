package jerry.framework.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.NetworkImageView;

import java.util.HashMap;
import java.util.Map;

import jerry.framework.R;
import jerry.framework.bean.Test;
import jerry.framework.request.ImageRequest;
import jerry.framework.request.StringRequest;
import jerry.framework.util.VolleyTools;

/**
 * <b>Volley's usage:</b><br/>
 * You can get it by the example below.<br/>
 * <br/>
 *
 * @author Jerry
 */
public class MainActivity extends BaseActivity {

    String url;
    TextView textView;
    NetworkImageView imageView;
    ImageView img;

    VolleyTools tool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void iniViews() {
        textView = (TextView) findViewById(R.id.test);
        imageView = (NetworkImageView) findViewById(R.id.image);
        img = (ImageView) findViewById(R.id.imageview);


        // 得到VolleyTools的实例，已定义在Application，从BaseActivity中取出
        tool = getVolleyTools();

        // 数据请求
        // post
        url = "http://114.215.178.134:8080/tyc/app/Product_getProducts";
        Test value = new Test(1, 2);

        Map<String, String> param = new HashMap<String, String>();
        param.put("input", getGson().toJson(value));


        tool.getQueue().add(new StringRequest(url, new Response.Listener<String>() {

            @Override
            public void onResponse(String arg0) {
                textView.setText(arg0);
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError arg0) {
                System.out.println(arg0.toString());
                textView.setText(arg0.toString());
            }
        }, param));

        // get
        url = "http://114.215.178.134:8080/tyc/app/Product_getProducts?input={%22state%22:1,%22userprofileId%22:2}";
        tool.getQueue().add(new StringRequest(url, new Response.Listener<String>() {

            @Override
            public void onResponse(String arg0) {
                textView.setText(arg0);
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError arg0) {
                System.out.println(arg0.toString());
            }
        }));

        // 图片请求
        url = "http://news.baidu.com/resource/img/logo_news_137_46.png";
        // 方法一：ImageRequest能够处理单张图片
        tool.getQueue().add(new ImageRequest(url, new Response.Listener<Bitmap>() {

            @Override
            public void onResponse(Bitmap arg0) {
                img.setImageBitmap(arg0);
            }
        }, 300, 200, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError arg0) {
                System.out.println(arg0.toString());
            }
        }));

        // 方法二，对应imageview绑定对应的网络图片
        // tool.getImageLoader().get(url, ImageLoader.getImageListener(img,
        // R.drawable.ic_launcher, 0));

        // 方法三，使用NetworkImageView
        imageView.setImageUrl(url, tool.getImageLoader());
    }

    @Override
    protected void iniEvent() {

    }
}
