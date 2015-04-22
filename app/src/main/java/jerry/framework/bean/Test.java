package jerry.framework.bean;

import com.google.gson.Gson;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

//���������ޣ���ʹ��������Ϊ������
@DatabaseTable
public class Test extends BaseBean {
    // ��������
    @DatabaseField
    private int state;

    @Override
    public Test fromJson(String json, Gson g) {
        return g.fromJson(json, Test.class);
    }

    @Override
    public String toJson( Gson g) {
        return  g.toJson(this);
    }

    // ����
    @DatabaseField(id = true)
    private int userprofileId;

    public Test(int state, int userprofileId) {
        this.state = state;
        this.userprofileId = userprofileId;
    }

    // ��������޲εĹ���
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
