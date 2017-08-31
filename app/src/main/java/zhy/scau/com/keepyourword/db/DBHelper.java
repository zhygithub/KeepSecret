package zhy.scau.com.keepyourword.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;


import java.util.ArrayList;
import java.util.List;

import zhy.scau.com.keepyourword.utils.BaseConstant;

/**
 * Created by 80234812 on 2017/8/15.
 *
 * 常用的数据库操作的工具类
 */
public class DBHelper {

    private Context mCtx;

    private XLZSQLiteHelper mXLZSQLiteHelper;

    public DBHelper(@NonNull Context context){
        mCtx = context;
        mXLZSQLiteHelper = new XLZSQLiteHelper(mCtx);
    }

    /**
     * 插入 操作
     * @param userId 用户id
     * @param pwName 密码名称
     * @param pwValue 密码值
     */
    public void insert(int userId, String pwName, String pwValue){
        SQLiteDatabase writableDatabase = mXLZSQLiteHelper.getWritableDatabase();
        writableDatabase.beginTransaction();

        ContentValues contentValues = new ContentValues();
        contentValues.put(PWDao.USERID , userId);
        contentValues.put(PWDao.PWNAME, pwName);
        contentValues.put(PWDao.PWVALUE, pwValue);
        writableDatabase.insert(XLZSQLiteHelper.PW_TABLE_NAME, null, contentValues);
        writableDatabase.setTransactionSuccessful();
        writableDatabase.endTransaction();

    }

    /**
     * 删除 操作
     * @param id 对应的id
     *
     */
    public void deleteById(int id){
        if(id < 0){
            return;
        }
        SQLiteDatabase writableDatabase = mXLZSQLiteHelper.getWritableDatabase();
        writableDatabase.beginTransaction();

        writableDatabase.delete(XLZSQLiteHelper.PW_TABLE_NAME, PWDao.ID + "= ?", new String[]{String.valueOf(id)});

        writableDatabase.setTransactionSuccessful();
        writableDatabase.endTransaction();

    }

    /**
     * 更新操作
     * @param id
     * @param userId
     * @param pwName
     * @param pwValue
     */
    public void updateById(int id, int userId, String pwName, String pwValue){
        if(id < 0){
            return;
        }

        SQLiteDatabase writableDatabase = mXLZSQLiteHelper.getWritableDatabase();
        writableDatabase.beginTransaction();

        ContentValues contentValues = new ContentValues();
        contentValues.put(PWDao.USERID , userId);
        contentValues.put(PWDao.PWNAME, pwName);
        contentValues.put(PWDao.PWVALUE, pwValue);
        writableDatabase.update(XLZSQLiteHelper.PW_TABLE_NAME, contentValues, PWDao.ID + "= ?", new String[]{String.valueOf(id)});

        writableDatabase.setTransactionSuccessful();
        writableDatabase.endTransaction();

    }

    /**
     * 查询操作
     * @param clumn_name
     * @param condition
     * @param args
     * @param having
     * @param orderBy
     * @param limit
     * @return
     */
    public List<PWBean> query(String[] clumn_name, String condition, String[] args, String having, String orderBy, String limit){
        SQLiteDatabase readableDatabase = mXLZSQLiteHelper.getReadableDatabase();
        Cursor cursor = readableDatabase.query(XLZSQLiteHelper.PW_TABLE_NAME, clumn_name, condition, args, having, orderBy, limit);

        List<PWBean> result = new ArrayList<>();
        PWBean userBean ;
        if(cursor != null && cursor.getCount() > 0){
            while (cursor.moveToNext()){
                userBean = parseUser(cursor);
                result.add(userBean);
            }

        }
        return result;
    }

    /**
     * 解析游标构建用户对象
     * @param cursor
     * @return
     */
    private PWBean parseUser(Cursor cursor) {
        if(cursor == null){
            return null;
        }
        PWBean result ;
        int id = BaseConstant.ILLAGEL_INT;
        int userId = BaseConstant.ILLAGEL_INT;
        String pwName = BaseConstant.NOTHING;
        String pwValue = BaseConstant.NOTHING;
        int IDindex = cursor.getColumnIndex(PWDao.ID);
        if(IDindex >= 0){
            id = cursor.getInt(IDindex);
        }

        int userIdIndex = cursor.getColumnIndex(PWDao.USERID);
        if(userIdIndex >= 0){
            userId = cursor.getInt(userIdIndex);

        }

        int pwNameIndex = cursor.getColumnIndex(PWDao.PWNAME);
        if(pwNameIndex >= 0){
            pwName = cursor.getString(pwNameIndex);
        }

        int pwValueIndex = cursor.getColumnIndex(PWDao.PWVALUE);
        if(pwValueIndex >= 0){
            pwValue = cursor.getString(pwValueIndex);

        }

        result = new PWBean(id, userId, pwName, pwValue);
        return result;
    }

    /**
     * 查询全部
     * @return
     */
    public List<PWBean> queryAll(){
        List<PWBean> result = query(new String[]{"*"}, null, null, null, null, null);
        return result;
    }

}
