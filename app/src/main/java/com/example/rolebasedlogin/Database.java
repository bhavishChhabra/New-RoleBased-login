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
    private static final String TABLE_NAME = "list";
    private static final String CHECHPOINT_ID = "checkpointid" ;
    private static final String DESCRIPTION = "description" ;
    private static final String VALUE = "value" ;
    private static final String TYPE_ID = "typeId" ;
    private static final String MANDATORY = "mandatory" ;
    private static final String EDITABLE = "editable" ;
    private static final String CORRECT = "correct" ;
    private static final String SIZE = "size" ;
    private static final String SCORE = "Score" ;
    private static final String LANGUAGE = "language" ;
    private static final String ACTIVE = "Active" ;
    private static final String IS_DEPT = "Is_Dept" ;
    private static final String LOGIC = "Logic" ;
    private static final String IS_GEOFENCE = "isGeofence" ;
    private static final String ACTION = "action";
    private static final String ANSWER = "answer" ;

    private static final int DATABASE_VERSION = 1;
    private static final String ID_KEY = "ID";
    private static final String DATABASE_NAME = "Json Offline";

    public Database(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super (context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String query = "CREATE TABLE " + TABLE_NAME + " ("
//                + CHECHPOINT_ID + " TEXT PRIMARY KEY"
//                +", "+ DESCRIPTION + " TEXT"
//                +", "+ VALUE + " TEXT"
//                +", "+ TYPE_ID + " TEXT"
                + CAPTION + " TEXT)";
//                +", "+ EDITABLE + " TEXT)";
//                +", "+ CORRECT + " TEXT"
//                +", "+ SIZE + " TEXT"
//                +", "+ SCORE + " TEXT"
//                +", "+ LANGUAGE + " TEXT"
//                +", "+ LOGIC + " TEXT"
//                +", "+ IS_GEOFENCE + " TEXT"
//                +", "+ ANSWER + " TEXT"
//                +", "+ ACTION + "TEXT"
//                +", "+ ACTIVE + " TEXT"
//                +", "+ IS_DEPT + " TEXT)";
                db.execSQL (query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        if (oldVersion != newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
//        }
    }
    public void setCaption(String CAPTION){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CAPTION, CAPTION);

        db.insert(TABLE_NAME, null, values);

        db.close();
    }
    public void insertData(String checkpt,String description,String typeId,String size,String editable){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(CHECHPOINT_ID, checkpt);
        values.put(DESCRIPTION, description);
        values.put(TYPE_ID, typeId);
        values.put(SIZE, size);
        values.put(EDITABLE, editable);

        db.insert(TABLE_NAME, null, values);

        db.close();
    }
    public ArrayList fetchData(int i)
    {
        ArrayList<String>stringArrayList=new ArrayList<String>();
        String fetchdata="select * from "+TABLE_NAME;
        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
        Cursor cursor=sqLiteDatabase.rawQuery(fetchdata, null);
        if(cursor.moveToFirst()){
            do
            {
                stringArrayList.add(cursor.getString(i));
            } while (cursor.moveToNext());
        }
        return stringArrayList;
    }
}
