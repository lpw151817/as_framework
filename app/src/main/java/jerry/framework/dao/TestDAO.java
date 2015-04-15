package jerry.framework.dao;


import android.content.Context;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;

import jerry.framework.bean.Test;

/**
 * Created by JerryLiu on 2015/4/14.
 */
public class TestDAO extends BaseDAO<Test, Integer> {

    public TestDAO(Context context) {
        super(context);
    }

    @Override
    public Dao<Test, Integer> getDao() throws SQLException {
        return helper.getDao(Test.class);
    }

}