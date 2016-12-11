package com.example.android.pets.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Mac on 2016/12/6.
 */

public class PetContract {
        //为了防止有人意外地实例化合同类，
        //给它一个空的构造函数。
    private PetContract() {
    }

    /**
          * The "Content authority" is a name for the entire content provider, similar to the
          * relationship between a domain name and its website.  A convenient string to use for the
          * content authority is the package name for the app, which is guaranteed to be unique on the
          * device.
          */
        public static final String CONTENT_AUTHORITY = "com.example.android.pets";

                /**
          * Use CONTENT_AUTHORITY to create the base of all URI's which apps will use to contact
          * the content provider.
          */
                public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

                /**
          * Possible path (appended to base content URI for possible URI's)
          * For instance, content://com.example.android.pets/pets/ is a valid path for
          * looking at pet data. content://com.example.android.pets/staff/ will fail,
          * as the ContentProvider hasn't been given any information on what to do with "staff".
          */
                public static final String PATH_PETS = "pets";
        /*
        * 为pets数据库表定义常量值的内部类。
        * 表中的每个条目代表一只宠物。
        */
    public static final class PetEntry implements BaseColumns {
            /** The content URI to access the pet data in the provider */
                    public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_PETS);
        /**
         * 宠物数据库表名
         */
        public final static String TABLE_NAME = "pets";
        /**
         * 宠物的唯一ID号（仅用于数据库表）
         *
         * 类型：INTEGER
         */
        public final static String _ID = BaseColumns._ID;
        /*宠物名称。
        *：
        *类型：TEXT */
        public final static String COLUMN_PET_NAME = "name";
        /*宠物的品种。
        *：
        *类型：TEXT  */
        public final static String COLUMN_PET_BREED = "breed";
        /*
        宠物的性别
        唯一可能的值是{@link #GENDER_UNKNOWN}, {@link #GENDER_MALE},
        +         * or {@link #GENDER_FEMALE}.
        类型：INTEGER
         */
        public final static String COLUMN_PET_GENDER = "gender";
        /*
        宠物的重量。
        *：
        *类型：INTEGER
         */
        public final static String COLUMN_PET_WEIGHT = "weight";
        /*
        宠物的性别的可能值。

         */
        public static final int GENDER_UNKNOWN = 0;
        public static final int GENDER_MALE = 1;
        public static final int GENDER_FEMALE = 2;
    }
}
