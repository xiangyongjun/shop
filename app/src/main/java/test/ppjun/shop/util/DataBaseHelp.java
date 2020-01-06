package test.ppjun.shop.util;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelp extends SQLiteOpenHelper{

      public DataBaseHelp( Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
      }

      @Override
      public void onCreate(SQLiteDatabase db) {
            //������
            db.execSQL("create table demo(id integer primary key autoincrement ,product_id int(10), title varchar(50),price varchar(20),image varchar(10) , num int(10) )");
      }

      @Override
      public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {


      }
}
