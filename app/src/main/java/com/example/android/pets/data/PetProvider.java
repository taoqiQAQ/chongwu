package com.example.android.pets.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

/**
 * {@link ContentProvider}宠物应用程式。
 */
public class PetProvider extends ContentProvider {

    /** 标记日志消息*/
    public static final String LOG_TAG = PetProvider.class.getSimpleName();

    /*
    数据库助手对象
     */
    private PetDbHelper mDbHelper;
    /**
     * 初始化提供程序和数据库助手对象。
     */
    @Override
    public boolean onCreate() {
        mDbHelper = new PetDbHelper(getContext());
        return true;
    }

    /**
     * 对给定的URI执行查询。 使用给定的投影，选择，选择参数和排序顺序。
     */
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {
        return null;
    }

    /**
     * 将新数据插入具有给定ContentValues的提供程序。
     */
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        return null;
    }

    /**
     * 使用新的ContentValues更新给定选择和选择参数下的数据。
     */
    @Override
    public int update(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {
        return 0;
    }

    /**
     *
     删除给定选择和选择参数的数据。
     */
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    /**
     * 返回内容URI的数据的MIME类型。
     */
    @Override
    public String getType(Uri uri) {
        return null;
    }
}