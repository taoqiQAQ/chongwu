package com.example.android.pets.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Mac on 2016/12/6.
 */
/*
宠物应用程序的数据库助手。
管理数据库创建和版本管理。
 */
public class PetDbHelper extends SQLiteOpenHelper {

    public static final String LOG_TAG = PetDbHelper.class.getSimpleName();

    /**
     * 数据库文件的名称
     */
    private static final String DATABASE_NAME = "shelter.db";

    /**
     * 数据库版本。
     * 如果更改数据库模式，则必须增加数据库版本。
     */
    private static final int DATABASE_VERSION = 1;

    /**
     * 构造{@link PetDbHelper}的新实例。
     *
     * @param context 的应用程序
     */
    public PetDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * 这是在首次创建数据库时调用。
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        // 创建一个包含SQL语句的字符串来创建pets表
        String SQL_CREATE_PETS_TABLE = "CREATE TABLE " + PetContract.PetEntry.TABLE_NAME + " ("
                + PetContract.PetEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + PetContract.PetEntry.COLUMN_PET_NAME + " TEXT NOT NULL, "
                + PetContract.PetEntry.COLUMN_PET_BREED + " TEXT, "
                + PetContract.PetEntry.COLUMN_PET_GENDER + " INTEGER NOT NULL, "
                + PetContract.PetEntry.COLUMN_PET_WEIGHT + " INTEGER NOT NULL DEFAULT 0);";

        // 执行SQL语句
        db.execSQL(SQL_CREATE_PETS_TABLE);
    }

    /**
     * 这在数据库需要升级时调用。
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // 数据库仍然是版本1，因此在这里没有做。
    }
}