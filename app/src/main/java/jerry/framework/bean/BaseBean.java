package jerry.framework.bean;

import com.google.gson.Gson;

/**
 * Created by JerryLiu on 2015/4/22.
 */
public abstract class BaseBean {
    public abstract String toJson(Gson g);

    public abstract <T extends BaseBean> T fromJson(String json, Gson g);
}
