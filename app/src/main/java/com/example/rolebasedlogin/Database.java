package com.example.rolebasedlogin;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;


public class Database extends SQLiteOpenHelper {
    private static final String CAPTION = "Caption";
    private static final int DATABASE_VERSION = 1;
    private static final String ID_KEY = "ID";
    private static final String DATABASE_NAME = "Json Offline";
    private static final String TABLE_NAME = "Checklist";

    public Database(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super (context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + ID_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + CAPTION + " TEXT)";
                db.execSQL (query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
    public void addCaption(String caption){
        SQLiteDatabase db = this.getWritableDatabase();

        // on below line we are creating a
        // variable for content values.
        ContentValues values = new ContentValues();

        // on below line we are passing all values
        // along with its key and value pair.
        values.put(CAPTION, caption);

        // after adding all values we are passing
        // content values to our table.
        db.insert(TABLE_NAME, null, values);

        // at last we are closing our
        // database after adding database.
        db.close();
    }
    public ArrayList fetchData()
    {
        ArrayList<String>stringArrayList=new ArrayList<String>();
        String fetchdata="select * from "+TABLE_NAME;
        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
        Cursor cursor=sqLiteDatabase.rawQuery(fetchdata, null);
        if(cursor.moveToFirst()){
            do
            {
                stringArrayList.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }
        return stringArrayList;
    }
}
