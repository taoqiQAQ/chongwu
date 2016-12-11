/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.pets;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.android.pets.data.PetContract;
import com.example.android.pets.data.PetDbHelper;

/**
 * 显示输入并存储在应用程序中的宠物列表。
 */
public class CatalogActivity extends AppCompatActivity {
    /*
    将为我们提供对数据库的访问的数据库助手
    */
    private PetDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);

        // 设置FAB以打开EditorActivity
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CatalogActivity.this, EditorActivity.class);
                startActivity(intent);
            }
        });
        //要访问我们的数据库，我们实例化我们的SQLiteOpenHelper的子类
        //并传递上下文，这是当前活动
        mDbHelper = new PetDbHelper(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        displayDatabaseInfo();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // 从res / menu / menu_catalog.xml文件中扩充菜单选项。
        //这会将菜单项添加到应用栏。
        getMenuInflater().inflate(R.menu.menu_catalog, menu);
        return true;
    }

    /*
    帮助方法将硬编码的宠物数据插入数据库。
    仅用于调试目的。
     */
    private void insertPet() {
        //以写入模式获取数据库
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        //创建ContentValues对象，其中列名称是键，
        //和Toto的pet属性是值。
        ContentValues values = new ContentValues();
        values.put(PetContract.PetEntry.COLUMN_PET_NAME, "Toto");
        values.put(PetContract.PetEntry.COLUMN_PET_BREED, "Terrier");
        values.put(PetContract.PetEntry.COLUMN_PET_GENDER, PetContract.PetEntry.GENDER_MALE);
        values.put(PetContract.PetEntry.COLUMN_PET_WEIGHT, 7);
        //在数据库中插入Toto的新行，返回该新行的ID。
        // db.insert（）的第一个参数是pets表名。
        //第二个参数提供了框架所在的列的名称
        //可以在ContentValues为空的情况下插入NULL（if
        //这被设置为“null”，那么框架将不会插入一行
        //没有值）。
        //第三个参数是包含Toto的info的ContentValues对象。
        long newRowId = db.insert(PetContract.PetEntry.TABLE_NAME, null, values);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //用户点击了应用栏溢出菜单中的菜单选项
        switch (item.getItemId()) {
            // 响应对“插入虚拟数据”菜单选项的点击
            case R.id.action_insert_dummy_data:
                insertPet();
                displayDatabaseInfo();
                return true;
            // 响应对“删除所有条目”菜单选项的点击
            case R.id.action_delete_all_entries:
                // 现在什么也不做
                return true;
        }
        return super.onOptionsItemSelected(item);

    }

    /**
     * 临时帮助方法在屏幕TextView上显示有关状态的信息
     * 宠物数据库。
     */
    private void displayDatabaseInfo() {
        // 创建和/或打开数据库以从中读取数据
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        // 定义一个投影，指定数据库中的哪些列
        //你实际上会在这个查询之后使用。
        String[] projection = {
                PetContract.PetEntry._ID,
                PetContract.PetEntry.COLUMN_PET_NAME,
                PetContract.PetEntry.COLUMN_PET_BREED,
                PetContract.PetEntry.COLUMN_PET_GENDER,
                PetContract.PetEntry.COLUMN_PET_WEIGHT };

        // 对pets表执行查询
        Cursor cursor = db.query(
                PetContract.PetEntry.TABLE_NAME,   // 要查询的表
                projection,            // 要返回的列
                null,                  // WHERE子句的列
                null,                  // WHERE子句的值
                null,                  // 不要对行进行分组
                null,                  // 不要按行组过滤
                null);                   // 排序顺序

        TextView displayView = (TextView) findViewById(R.id.text_view_pet);

        try {
            // 在文本视图中创建如下所示的标题：
            //
            //宠物表包含<游标>宠物中的行数。
            // _id - name - breed - gender - weight
            //
            //在下面的while循环中，遍历游标和显示的行
            //此列中每个列的信息。
            displayView.setText("The pets table contains " + cursor.getCount() + " pets.\n\n");
            displayView.append(PetContract.PetEntry._ID + " - " +
                    PetContract.PetEntry.COLUMN_PET_NAME + " - " +
                    PetContract.PetEntry.COLUMN_PET_BREED + " - " +
                    PetContract.PetEntry.COLUMN_PET_GENDER + " - " +
                    PetContract.PetEntry.COLUMN_PET_WEIGHT + "\n");

            // 找出每列的索引
            int idColumnIndex = cursor.getColumnIndex(PetContract.PetEntry._ID);
            int nameColumnIndex = cursor.getColumnIndex(PetContract.PetEntry.COLUMN_PET_NAME);
            int breedColumnIndex = cursor.getColumnIndex(PetContract.PetEntry.COLUMN_PET_BREED);
            int genderColumnIndex = cursor.getColumnIndex(PetContract.PetEntry.COLUMN_PET_GENDER);
            int weightColumnIndex = cursor.getColumnIndex(PetContract.PetEntry.COLUMN_PET_WEIGHT);

            // 迭代游标中所有返回的行
            while (cursor.moveToNext()) {
                // 使用该索引提取单词的String或Int值
                //在当前行光标处于打开状态。
                int currentID = cursor.getInt(idColumnIndex);
                String currentName = cursor.getString(nameColumnIndex);
                String currentBreed = cursor.getString(breedColumnIndex);
                int currentGender = cursor.getInt(genderColumnIndex);
                int currentWeight = cursor.getInt(weightColumnIndex);
                // 显示TextView中光标中当前行的每一列的值
                displayView.append(("\n" + currentID + " - " +
                        currentName + " - " +
                        currentBreed + " - " +
                        currentGender + " - " +
                        currentWeight));
            }
        } finally {
            // 当你完成阅读后，总是关闭光标。 这释放其所有
            //资源并使其无效。
            cursor.close();
        }
    }



}
