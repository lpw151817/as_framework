package jerry.framework.bean;

import com.google.gson.Gson;

/**
 * Created by JerryLiu on 2015/4/22.
 */
public abstract class BaseBean {
    public String toJson(Gson g) {
        return g.toJson(this);
    }


}
