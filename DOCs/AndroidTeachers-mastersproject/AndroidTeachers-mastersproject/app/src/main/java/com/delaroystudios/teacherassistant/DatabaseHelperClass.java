package com.delaroystudios.teacherassistant;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Vishakh on 19-04-2018.
 */

public class DatabaseHelperClass extends SQLiteOpenHelper {

    private static final String DATABASE="admin_db";
    private static final String TABLE_NAME="adminlogin";
    private static final String COL1="ID";
    private static final String COL2="NAME";
    private static final String COL3="USERNAME";
    private static final String COL4="PASSWORD";
    private static final String COL5="CONFIRMPASSWORD";
    private static final String COL6="PHONENUMBER";

    public DatabaseHelperClass(Context context) {
        super(context, DATABASE, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String Admintable="CREATE TABLE "+ TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT,NAME TEXT,USERNAME TEXT,PASSWORD TEXT,CONFIRMPASSWORD TEXT,PHONENUMBER TEXT)";
        db.execSQL(Admintable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP IF TABLE EXISTS"+TABLE_NAME);
        onCreate(db);
    }
    public boolean addData(String name,String username,String password,String confirmpass,String phone)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(COL2,name);
        contentValues.put(COL3,username);
        contentValues.put(COL4,password);
        contentValues.put(COL5,confirmpass);
        contentValues.put(COL6,phone);
        long result=db.insert(TABLE_NAME,null,contentValues);

        if(result==-1)
            return false;

        else {
            return true;
        }

    }
    Loginretrieve getContact(String id) {
        try {

            SQLiteDatabase db = this.getReadableDatabase();

            Cursor cursor = db.query(TABLE_NAME, new String[]{COL1, COL2, COL3,
                            COL4,COL5,COL6}, COL3+ "=?",
                    new String[]{String.valueOf(id)}, null, null, null, null);
            if (cursor != null)
                cursor.moveToFirst();

            Loginretrieve contact = new Loginretrieve(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2),
                    cursor.getString(3), cursor.getString(4), cursor.getString(5));
               db.close();
            cursor.close();
            return contact;
        } catch (NullPointerException e) {

        } catch (IndexOutOfBoundsException e) {

        }
        return null;
    }
    public int updateContact(String abc, String password) {

        ContentValues values = new ContentValues();
        SQLiteDatabase db = this.getWritableDatabase();

        values.put(COL3, abc);
        values.put(COL4, password);

        return db.update(TABLE_NAME, values, COL3 + "=?",
                new String[] { String.valueOf(abc) });
    }

}
