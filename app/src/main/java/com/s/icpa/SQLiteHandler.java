package com.s.icpa;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.HashMap;

public class SQLiteHandler extends SQLiteOpenHelper {

    private static final String TAG = SQLiteHandler.class.getSimpleName();

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "android_api";

    // Login table name
    private static final String TABLE_USER = "user";

    // Login Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_UID = "customer_id";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_MOBILE = "mobile";
    private static final String KEY_NAME = "name";
    private static final String KEY_BATCH_NO = "batch_no";
    private static final String KEY_DOB = "dob";
        private static final String KEY_CURRENT_FLEET = "current_fleet";
    private static final String KEY_SAP_NO = "sap_no";
    private static final String KEY_WORK_EMAIL = "work_email";
    private static final String KEY_ADDRESS = "address";
    private static final String KEY_MARITAL_STATUS = "marital_status";
    private static final String KEY_WEDDING_DATE = "wedding_date";
    private static final String KEY_MEMBER = "member";
    private static final String KEY_DESIGNATION = "Designation";
    private static final String KEY_REGION = "region";
    private static final String KEY_CREATED_AT = "created_at";


    public SQLiteHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_LOGIN_TABLE = "CREATE TABLE " + TABLE_USER + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_UID + " TEXT," + KEY_BATCH_NO + " TEXT,"
                + KEY_EMAIL + " TEXT UNIQUE," + KEY_MOBILE + " TEXT," + KEY_SAP_NO + " " + "TEXT,"
                + KEY_MEMBER + " TEXT," + KEY_DESIGNATION + " " + "TEXT," + KEY_REGION + " " + "TEXT,"
                + KEY_WORK_EMAIL + " TEXT," + KEY_ADDRESS + " TEXT," + KEY_MARITAL_STATUS + " TEXT," + KEY_WEDDING_DATE + " TEXT,"
                + KEY_NAME + " TEXT," + KEY_CREATED_AT + " TEXT," + KEY_DOB + " TEXT," + KEY_CURRENT_FLEET + " TEXT" + ")";


        db.execSQL(CREATE_LOGIN_TABLE);

        Log.d(TAG, "Database tables created");
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);

        // Create tables again
        onCreate(db);
    }

    /**
     * Storing user details in database
     */
    public void addUser(String uid, String batch_no, String email, String mobile, String name, String created_at, String dob
            , String currentfleet, String sap_no, String workemail,
                        String address, String merital_status, String wedding_date, String member, String designation, String region) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_UID, uid); // USER ID
        values.put(KEY_BATCH_NO, batch_no); // batch_no
        values.put(KEY_EMAIL, email); // Email
        values.put(KEY_MOBILE, mobile); // MOBILE
        values.put(KEY_NAME, name); // name
        values.put(KEY_CREATED_AT, created_at); // Created At
        values.put(KEY_DOB, dob); // dob
        values.put(KEY_CURRENT_FLEET, currentfleet); // currentfleet
        values.put(KEY_SAP_NO, sap_no); // sap_no
        values.put(KEY_WORK_EMAIL, workemail); // workemail
        values.put(KEY_ADDRESS, address); // address
        values.put(KEY_MARITAL_STATUS, merital_status); // merital_status
        values.put(KEY_WEDDING_DATE, wedding_date);
        values.put(KEY_MEMBER, member); // merital_status
        values.put(KEY_DESIGNATION, designation); // wedding_date
        values.put(KEY_REGION, region); // wedding_date

        // Inserting Row
        long id = db.insert(TABLE_USER, null, values);
        db.close(); // Closing database connection

        Log.d(TAG, "New user inserted into sqlite: " + id);
    }


    /**
     * Getting user data from database
     */
    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<String, String>();
        String selectQuery = "SELECT  * FROM " + TABLE_USER;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            user.put(cursor.getColumnName(1), cursor.getString(1));
            user.put(cursor.getColumnName(2), cursor.getString(2));
            user.put(cursor.getColumnName(3), cursor.getString(3));
            user.put(cursor.getColumnName(4), cursor.getString(4));
            user.put(cursor.getColumnName(5), cursor.getString(5));
            user.put(cursor.getColumnName(6), cursor.getString(6));
            user.put(cursor.getColumnName(7), cursor.getString(7));
            user.put(cursor.getColumnName(8), cursor.getString(8));
            user.put(cursor.getColumnName(9), cursor.getString(9));
            user.put(cursor.getColumnName(10), cursor.getString(10));
            user.put(cursor.getColumnName(11), cursor.getString(11));
            user.put(cursor.getColumnName(12), cursor.getString(12));
            user.put(cursor.getColumnName(13), cursor.getString(13));
            user.put(cursor.getColumnName(14), cursor.getString(14));
            user.put(cursor.getColumnName(15), cursor.getString(15));
            user.put(cursor.getColumnName(16), cursor.getString(16));
        }
        cursor.close();
        db.close();
        // return user
        Log.d(TAG, "Fetching user from Sqlite: " + user.toString());

        return user;
    }


    /**
     * Re crate database Delete all tables and create them again
     */
    public void deleteUsers() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_USER, null, null);
        db.close();

        Log.d(TAG, "Deleted all user info from sqlite");
    }


}