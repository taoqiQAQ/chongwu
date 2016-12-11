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
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.android.pets.data.PetContract;
import com.example.android.pets.data.PetDbHelper;

/**
 * 允许用户创建新宠物或编辑现有宠物。
 */
public class EditorActivity extends AppCompatActivity {

    /** EditText字段输入宠物的名字 */
    private EditText mNameEditText;

    /** EditText字段输入宠物的品种 */
    private EditText mBreedEditText;

    /** EditText字段输入宠物的体重 */
    private EditText mWeightEditText;

    /** EditText字段输入宠物的性别 */
    private Spinner mGenderSpinner;

    /**
     * 宠物的性别。 可能的值为：
     * 0表示未知性别，1表示男性，2表示女性。
     */
    private int mGender = PetContract.PetEntry.GENDER_UNKNOWN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        // 查找我们需要从中读取用户输入的所有相关视图
        mNameEditText = (EditText) findViewById(R.id.edit_pet_name);
        mBreedEditText = (EditText) findViewById(R.id.edit_pet_breed);
        mWeightEditText = (EditText) findViewById(R.id.edit_pet_weight);
        mGenderSpinner = (Spinner) findViewById(R.id.spinner_gender);

        setupSpinner();
    }

    /**
     * 设置下拉微调器允许用户选择宠物的性别。
     */
    private void setupSpinner() {
        // 创建微调适配器。 列表选项来自它将使用的String数组
        //微调器将使用默认布局
        ArrayAdapter genderSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_gender_options, android.R.layout.simple_spinner_item);

        // 指定下拉布局样式 - 简单列表视图，每行1个项目
        genderSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        // 将适配器应用于旋转器
        mGenderSpinner.setAdapter(genderSpinnerAdapter);

        // 将整数mSelected设置为常量值
        mGenderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.gender_male))) {
                        mGender = PetContract.PetEntry.GENDER_MALE; // Male
                    } else if (selection.equals(getString(R.string.gender_female))) {
                        mGender = PetContract.PetEntry.GENDER_FEMALE; // Female
                    } else {
                        mGender = PetContract.PetEntry.GENDER_UNKNOWN; // Unknown
                    }
                }
            }

            // 因为AdapterView是一个抽象类，所以必须定义onNothingSelected
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mGender = 0; // Unknown
            }
        });
    }
        /*
        从编辑器获取用户输入并将新宠物保存到数据库中。
        */
    private void insertPet(){
        //从输入字段读取
        //使用trim来消除前导或尾随的空白
        String nameString = mNameEditText.getText().toString().trim();
        String breedString = mBreedEditText.getText().toString().trim();
        String weightString = mWeightEditText.getText().toString().trim();
        int weight = Integer.parseInt(weightString);
        //创建数据库助手
        PetDbHelper mDbHelper = new PetDbHelper(this);
        //以写入模式获取数据库
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        //创建ContentValues对象，其中列名称是键，
        //和编辑器的pet属性是值。
        ContentValues values = new ContentValues();
        values.put(PetContract.PetEntry.COLUMN_PET_NAME,nameString);
        values.put(PetContract.PetEntry.COLUMN_PET_BREED,breedString);
        values.put(PetContract.PetEntry.COLUMN_PET_GENDER,mGender);
        values.put(PetContract.PetEntry.COLUMN_PET_WEIGHT,weight);

        //在数据库中插入pet的新行，返回该新行的ID。
        long newRowId = db.insert(PetContract.PetEntry.TABLE_NAME,null,values);
        //根据插入是否成功显示toast消息
        if (newRowId == -1) {
            //如果行ID为-1，则说明插入时出错。
            Toast.makeText(this,"Error with saving pet", Toast.LENGTH_SHORT).show();
        } else {
            //否则，插入成功，我们可以显示带有行ID的
            Toast.makeText(this,"pet saved with roe id : " + newRowId, Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // 从res / menu / menu_editor.xml文件中扩充菜单选项。
        //这会将菜单项添加到应用栏。
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // 用户点击了应用栏溢出菜单中的菜单选项
        switch (item.getItemId()) {
            // 响应对“保存”菜单选项的点击
            case R.id.action_save:
                // 保存宠物信息到数据库
                insertPet();
                //退出活动
                finish();
                return true;
            // 响应对“删除”菜单选项的点击
            case R.id.action_delete:
                // 现在什么也不做
                return true;
            // 响应对应用栏中的“向上”箭头按钮的点击
            case android.R.id.home:
                // 导航回父活动（CatalogActivity）
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}