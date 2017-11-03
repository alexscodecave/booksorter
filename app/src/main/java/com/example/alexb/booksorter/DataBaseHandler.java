package com.example.alexb.booksorter;

import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.content;
import static android.R.attr.version;

/**
 * Created by alexb on 24/06/2017.
 */

public class DataBaseHandler extends SQLiteOpenHelper {
    public DataBaseHandler(Context context) {
        super(context, "testDB", null, 1);
    }

    private DataBaseHandler dbHandler;
    private String columnID = "colID";
    private String columnBookName="colBookName";
    private String columnBookAuthor="colBookAuthor";
    private String columnBookYear="colBookYear";
    private String columnBookNoOfPages = "colBookNoOfPages";
    private String columnCurrentPage = "colCurrentPage";
    private String columnBookDescription = "colBookDesc";
    private String columnBookImage = "colBookImage";

    private String tableName = "tblBookInformation";


    String[] dbColumns = new String[]{columnBookName,columnBookYear,columnBookAuthor,columnBookNoOfPages,columnCurrentPage,columnBookDescription,columnBookImage};
    String[] columnIDArray = new String[]{columnID};

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE tblBookInformation( _id INTEGER PRIMARY KEY AUTOINCREMENT, colBookName, colBookYear,colBookAuthor, colBookNoOfPages, colCurrentPage, colBookDesc,colBookImage)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + tableName);
        onCreate(db);
    }



    public void addBook(BookInformation bookInformation){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("colBookName",bookInformation.getBookName());
        contentValues.put("colBookYear",bookInformation.getBookYear());
        contentValues.put("colBookAuthor",bookInformation.getBookAuthor());
        contentValues.put("colBookNoOfPages",bookInformation.getPages());
        contentValues.put("colCurrentPage",bookInformation.getCurrentPage());
        contentValues.put("colBookDesc",bookInformation.getBookDescription());
        contentValues.put("colBookImage",bookInformation.getBookImage());
        //contentValues.put("colBookImage",bookInformation.getArray());
        long result = sqLiteDatabase.insert("tblBookInformation",null,contentValues);

        sqLiteDatabase.close();
    }

    public void getImageIntoImageView(String columnName,ImageView imgView){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor c = sqLiteDatabase.rawQuery("SELECT colBookImage FROM tblBookInformation WHERE colBookName='" + columnName + "'", null);
        if(c.getCount() >0){
            c.moveToFirst();
            do{
                byte[] blob = c.getBlob(0);
                Bitmap b = BitmapFactory.decodeByteArray(blob , 0, blob.length);
                imgView.setImageBitmap(BitmapFactory.decodeByteArray(blob, 0,blob.length));

            }while(c.moveToNext());
        }
    }

    public boolean updateBook(String editColumnBookName,int editColumnBookYear,String editColumnBookAuthor,int editColumnPages,int editColumnCurrentPage,String editColumnBookDescription,byte[] editColumnBookImage){
        SQLiteDatabase updateDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("colBookName",editColumnBookName);
        contentValues.put("colBookYear",editColumnBookYear);
        contentValues.put("colBookAuthor",editColumnBookAuthor);
        contentValues.put("colBookNoOfPages",editColumnPages);
        contentValues.put("colCurrentPage",editColumnCurrentPage);
        contentValues.put("colBookDesc",editColumnBookDescription);
        contentValues.put("colBookImage",editColumnBookImage);
        updateDatabase.update("tblBookInformation",contentValues,"colBookName = ?",new String[]{editColumnBookName});
        return true;
        //long updatedResult = updateDatabase.update("tblBookInformation",contentValues,"colBookName=?",null);
    }

    public void deleteBook(String columnBookName){
        SQLiteDatabase deleteDatabaseValue = this.getWritableDatabase();
        deleteDatabaseValue.delete("tblBookInformation","colBookName = ?",new String[]{columnBookName.toString()});
        deleteDatabaseValue.close();
    }


    public List<String> getBookName(){
        List<String> bookNames = new ArrayList<String>();
        String selectBookQuery = "SELECT colBookName from tblBookInformation";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectBookQuery,null);
        if(cursor.moveToFirst()){
            do{
                bookNames.add(cursor.getString(0));
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return bookNames;
    }
    public List<String> getBookYear(String colBookName){
        List<String> bookYear = new ArrayList<String>();
        String selectBookYear = "SELECT colBookYear from tblBookInformation WHERE colBookName = " + colBookName;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectBookYear,null);
        if(cursor.moveToFirst()){
            do{
                bookYear.add(cursor.getString(0));
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return bookYear;
    }

    public List<String> getAllData(){
        List<String> containSQLData = new ArrayList<String>();
        final String tblName = "tblBookInformation";
        String selectQuery = "SELECT * FROM " + tblName;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,null);
        List<String> data = null;
        if (cursor.moveToFirst()) {
            do{
                containSQLData.add(cursor.getString(0));
            }while(cursor.moveToNext());
        }

        cursor.close();
        return data;
    }

    public List<String> getIndividualDBInfo(String columnToPull){
        List<String> bookNames = new ArrayList<String>();
        String selectBookQuery = "SELECT " + columnToPull+" from tblBookInformation";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectBookQuery,null);
        if(cursor.moveToFirst()){
            do{
                bookNames.add(cursor.getString(0));
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return bookNames;
    }
    public List<String> alternativeMethodToGainDBInfo(String column){
        List<String> bookColumns = new ArrayList<String>();
        String selectAll = "SELECT colBookAuthor,colBookYear,colBookNoOfPages,colCurrentPage,colBookDesc FROM " + tableName+ " WHERE colBookName= '" + column+ "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectAll,null);
        if(cursor.moveToFirst()){
            do{
                bookColumns.add(cursor.getString(cursor.getColumnIndex("colBookAuthor")));
                bookColumns.add(cursor.getString(cursor.getColumnIndex("colBookYear")));
                bookColumns.add(cursor.getString(cursor.getColumnIndex("colBookNoOfPages")));
                bookColumns.add(cursor.getString(cursor.getColumnIndex("colCurrentPage")));
                bookColumns.add(cursor.getString(cursor.getColumnIndex("colBookDesc")));
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return bookColumns;
    }
    public List<String> getEverythingToUpdateBook(String column){
        List<String> bookColumns = new ArrayList<String>();
        String selectAll = "SELECT colBookName,colBookAuthor,colBookYear,colBookNoOfPages,colCurrentPage,colBookDesc FROM " + tableName+ " WHERE colBookName= '" + column+ "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectAll,null);
        if(cursor.moveToFirst()){
            do{
                bookColumns.add(cursor.getString(cursor.getColumnIndex("colBookName")));
                bookColumns.add(cursor.getString(cursor.getColumnIndex("colBookAuthor")));
                bookColumns.add(cursor.getString(cursor.getColumnIndex("colBookYear")));
                bookColumns.add(cursor.getString(cursor.getColumnIndex("colBookNoOfPages")));
                bookColumns.add(cursor.getString(cursor.getColumnIndex("colCurrentPage")));
                bookColumns.add(cursor.getString(cursor.getColumnIndex("colBookDesc")));
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return bookColumns;
    }

    private void getImage(ImageView imgView){
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT colBookImage from" + tableName,null);
        if(c.moveToNext()){
            byte[] image = c.getBlob(0);
            Bitmap bmp = BitmapFactory.decodeByteArray(image,0,image.length);
            imgView.setImageBitmap(bmp);
        }
    }

//    public Cursor queryID(){
//        Cursor cursorToQueryID = sqLiteDatabase.query("tblBookInformation",columnIDArray,null,null,null,null,null);
//        return cursorToQueryID;
//    }
}
