package com.example.lab2.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.lab2.R;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper";

    private static final String TABLE_NAME = "storage";
    private static final String COL1 = "ID";
    private static final String COL2 = "title";
    private static final String COL3 = "description";
    private static final String COL4 = "drawable";
    private SQLiteDatabase defaultWritableDatabase = null;


    public DatabaseHelper(Context context) {
        super(context, TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.defaultWritableDatabase = db;
        String createTable = "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, description TEXT, drawable INTEGER)";
        defaultWritableDatabase.execSQL(createTable);
        addData("Chrome", "https://www.google.com/chrome", R.drawable.chrome);
        addData("Firefox", "https://www.mozilla.org/en-US/", R.drawable.firefox);
        addData("Safari", "https://www.apple.com/safari/", R.drawable.safari);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        this.defaultWritableDatabase = db;
        defaultWritableDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean addData(String title, String description, int drawable) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, title);
        contentValues.put(COL3, description);
        contentValues.put(COL4, drawable);

        Log.d(TAG, "addData: Adding " + title + ", " + description + ", " + drawable + " to " + TABLE_NAME);

        long result = db.insert(TABLE_NAME, null, contentValues);

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }
    /**
     * Returns all the data from database
     * @return
     */
    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        return db.rawQuery(query, null);
    }


    @Override
    public SQLiteDatabase getWritableDatabase() {
        final SQLiteDatabase db;
        if(defaultWritableDatabase != null){
            db = defaultWritableDatabase;
        } else {
            db = super.getWritableDatabase();
        }
        return db;
    }

}
