package jerry.framework.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

//表名，若无，则使用类名作为表名字
@DatabaseTable
public class Test extends BaseBean {
    // 其他属性
    @DatabaseField
    private int state;

    // 主键
    @DatabaseField(id = true)
    private int userprofileId;

    public Test(int state, int userprofileId) {
        this.state = state;
        this.userprofileId = userprofileId;
    }

    // 必须带有无参的构造
    public Test() {
    }

    @Override
    public String toString() {
        return "Test [state=" + state + ", userprofileId=" + userprofileId + "]";
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getUserprofileId() {
        return userprofileId;
    }

    public void setUserprofileId(int userprofileId) {
        this.userprofileId = userprofileId;
    }


}
