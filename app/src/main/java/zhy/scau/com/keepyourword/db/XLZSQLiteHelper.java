package zhy.scau.com.keepyourword.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by 80234812 on 2017/8/15.
 * 数据库帮助类
 */
public class XLZSQLiteHelper extends SQLiteOpenHelper {

    public final static int DB_VERSION = 1;

    public final static String DB_NAME = "PW.db";

    public final static String PW_TABLE_NAME ="PW_TABLE_NAME";

    public final static String USER_TABLE_NAME ="USER_TABLE_NAME";


    public XLZSQLiteHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String strCreateSql = "create table if not exists "
                + USER_TABLE_NAME + " ("
                + UserDao.ID + " integer primary key AUTOINCREMENT, "
                + UserDao.USERNAME + " text(30), "
                + UserDao.PASSWORD +" text(30),"
                + UserDao.TRYTIMES + " integer)";
        sqLiteDatabase.execSQL(strCreateSql);

        strCreateSql = "create table if not exists "
            + PW_TABLE_NAME + " ("
            + PWDao.ID + " integer primary key AUTOINCREMENT, "
            + PWDao.USERID + " integer, "
            + PWDao.PWNAME +" text(30), "
            + PWDao.PWVALUE + " text(30))";
        sqLiteDatabase.execSQL(strCreateSql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String strUpgrade = "DROP TABLE IF EXISTS " + PW_TABLE_NAME;
        sqLiteDatabase.execSQL(strUpgrade);
        onCreate(sqLiteDatabase);
    }
}
