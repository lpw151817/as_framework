package jerry.framework.dao;

import android.content.Context;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedDelete;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jerry.framework.util.OrmDatabaseHelper;

/**
 * 数据库基本增删改查操作
 *
 * @param <T>
 * @author Jerry
 * @see http://ormlite.com/javadoc/ormlite-core/com/j256/ormlite/stmt/Where.html
 */
public abstract class BaseDAO<T, ID> {
    protected OrmDatabaseHelper helper;
    protected Context c;

    public BaseDAO(Context context) {
        this.c = context;
        getHelper();
    }

    public OrmDatabaseHelper getHelper() {
        if (helper == null) {
            helper = OpenHelperManager.getHelper(c, OrmDatabaseHelper.class);
        }
        return helper;
    }

    public abstract Dao<T, Integer> getDao() throws SQLException;

    // ////////////////////增

    /**
     * @return The number of rows updated in the database. This should be 1.
     */
    public int insert(T t) throws SQLException {
        return getDao().create(t);
    }

    public int insert(List<T> ts) throws SQLException {
        int i = 0;
        for (T t : ts) {
            insert(t);
            i++;
        }
        return i;
    }

    // ////////////////////

    // //////////////////////查
    public List<T> query(PreparedQuery<T> preparedQuery) throws SQLException {
        return getDao().query(preparedQuery);
    }

    public List<T> query(String attributeName, String attributeValue) throws SQLException {
        QueryBuilder<T, Integer> queryBuilder = getDao().queryBuilder();
        queryBuilder.where().eq(attributeName, attributeValue);
        PreparedQuery<T> preparedQuery = queryBuilder.prepare();
        return query(preparedQuery);
    }

    public List<T> query(String[] attributeNames, String[] attributeValues) throws SQLException, IllegalArgumentException {
        if (attributeNames.length != attributeValues.length) {
            throw new IllegalArgumentException("params size is not equal");
        }
        QueryBuilder<T, Integer> queryBuilder = getDao().queryBuilder();
        Where<T, Integer> wheres = queryBuilder.where();
        for (int i = 0; i < attributeNames.length; i++) {
            if (i == 0) {
                wheres.eq(attributeNames[i], attributeValues[i]);
            } else {
                wheres.and().eq(attributeNames[i], attributeValues[i]);
            }
        }
        PreparedQuery<T> preparedQuery = queryBuilder.prepare();
        return query(preparedQuery);
    }

    public List<T> queryAll() throws SQLException {
        return getDao().queryForAll();
    }

    public List<T> query(Map<String, Object> map) throws SQLException {
        QueryBuilder<T, Integer> queryBuilder = getDao().queryBuilder();
        if (!map.isEmpty()) {
            Where<T, Integer> wheres = queryBuilder.where();
            Set<String> keys = map.keySet();
            ArrayList<String> keyss = new ArrayList<String>();
            keyss.addAll(keys);
            for (int i = 0; i < keyss.size(); i++) {
                if (i == 0) {
                    wheres.eq(keyss.get(i), map.get(keyss.get(i)));
                } else {
                    wheres.and().eq(keyss.get(i), map.get(keyss.get(i)));
                }
            }
        }
        PreparedQuery<T> preparedQuery = queryBuilder.prepare();
        return query(preparedQuery);
    }

    /**
     * 查询比lowMap大，比highMap小的数据
     */
    public List<T> query(Map<String, Object> map, Map<String, Object> lowMap, Map<String, Object> highMap) throws SQLException {
        QueryBuilder<T, Integer> queryBuilder = getDao().queryBuilder();
        Where<T, Integer> wheres = queryBuilder.where();
        if (!map.isEmpty()) {
            Set<String> keys = map.keySet();
            ArrayList<String> keyss = new ArrayList<String>();
            keyss.addAll(keys);
            for (int i = 0; i < keyss.size(); i++) {
                if (i == 0) {
                    wheres.eq(keyss.get(i), map.get(keyss.get(i)));
                } else {
                    wheres.and().eq(keyss.get(i), map.get(keyss.get(i)));
                }
            }
        }
        if (lowMap != null && !lowMap.isEmpty()) {
            Set<String> keys = lowMap.keySet();
            ArrayList<String> keyss = new ArrayList<String>();
            keyss.addAll(keys);
            if (map.isEmpty()) {
                for (int i = 0; i < keyss.size(); i++) {
                    if (i == 0) {
                        // Add a '>' clause so the column must be greater-than
                        // the value.
                        wheres.gt(keyss.get(i), lowMap.get(keyss.get(i)));
                    } else {
                        wheres.and().gt(keyss.get(i), lowMap.get(keyss.get(i)));
                    }
                }
            } else {
                for (int i = 0; i < keyss.size(); i++) {
                    wheres.and().gt(keyss.get(i), lowMap.get(keyss.get(i)));
                }
            }

        }

        if (highMap != null && !highMap.isEmpty()) {
            Set<String> keys = highMap.keySet();
            ArrayList<String> keyss = new ArrayList<String>();
            keyss.addAll(keys);
            if (map.isEmpty() && (lowMap == null || lowMap.isEmpty())) {
                for (int i = 0; i < keyss.size(); i++) {
                    if (i == 0) {
                        // Add a '<' clause so the column must be less-than the
                        // value.
                        wheres.lt(keyss.get(i), highMap.get(keyss.get(i)));
                    } else {
                        wheres.and().lt(keyss.get(i), highMap.get(keyss.get(i)));
                    }
                }
            } else {
                for (int i = 0; i < keyss.size(); i++) {
                    wheres.and().lt(keyss.get(i), highMap.get(keyss.get(i)));
                }
            }
        }
        PreparedQuery<T> preparedQuery = queryBuilder.prepare();
        return query(preparedQuery);
    }

    // ///////////////删

    /**
     * @return The number of rows updated in the database. This should be 1.
     */
    public int delete(PreparedDelete<T> preparedDelete) throws SQLException {
        return getDao().delete(preparedDelete);
    }

    /**
     * {@link #delete(PreparedDelete)}
     */
    public int delete(T t) throws SQLException {
        return getDao().delete(t);
    }

    /**
     * {@link #delete(PreparedDelete)}
     */
    public int delete(List<T> lst) throws SQLException {
        return getDao().delete(lst);
    }

    /**
     * @return The number of rows updated in the database.It maybe 0.
     */
    public int delete(String[] attributeNames, String[] attributeValues) throws SQLException, IllegalArgumentException {
        List<T> lst = query(attributeNames, attributeValues);
        if (null != lst && !lst.isEmpty()) {
            return delete(lst);
        }
        return 0;
    }

    /**
     * {@link #delete(PreparedDelete)}
     */
    public int delete(String attributeName, String attributeValue) throws SQLException, IllegalArgumentException {
        List<T> lst = query(attributeName, attributeValue);
        if (null != lst) {
            return delete(lst);
        }
        return 0;
    }

    public int deleteAll() throws SQLException {
        List<T> lst = queryAll();
        if (lst != null && lst.size() != 0)
            return getDao().delete(lst);
        else
            return 0;
    }

    // //////////////

    // /////////改

    /**
     * @return The number of rows updated in the database. This should be 1.
     */
    public int update(T t) throws SQLException {


        return getDao().update(t);
    }

    // //////////////

    public boolean isTableExsits() throws SQLException {
        return getDao().isTableExists();
    }

    /**
     * @return Returns the number of rows in the table associated with the data
     * class. Depending on the size of the table and the database type,
     * this may be expensive and take a while.
     */
    public long countOf() throws SQLException {
        return getDao().countOf();
    }

    public void release() {
        if (helper != null) {
            OpenHelperManager.releaseHelper();// �ͷŵ�helper
            helper = null;
        }
    }

    @Override
    protected void finalize() throws Throwable {
        release();
        super.finalize();
    }

}
