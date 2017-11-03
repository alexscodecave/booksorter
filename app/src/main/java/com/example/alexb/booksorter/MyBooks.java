package com.example.alexb.booksorter;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import java.util.List;

public class MyBooks extends AppCompatActivity {

    ListView listView;
    DataBaseHandler dbHandler;
    SQLiteDatabase sqLiteDatabase;
    Cursor toDoHandler;
    Cursor selectBookID;
    DatabaseCursorAdapter todoAdapter;
    EditText getUserText;
    private ImageView imgView;
//    private byte[] imageBytes = getBlob(cursor,)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_books2);
        dbHandler = new DataBaseHandler(this);
        sqLiteDatabase  = dbHandler.getWritableDatabase();

        imgView = (ImageView) findViewById(R.id.imgViewCustomBookLayout);
        toDoHandler = sqLiteDatabase.rawQuery("SELECT * FROM tblBookInformation",null);
        //byte[] getImageByte = toDoHandler.getBlob(3);
        //Bitmap bm = BitmapFactory.decodeByteArray(getImageByte,0,getImageByte.length);
        byte[] imageBytes = getBlob(toDoHandler,"colBookImage",null);
        if(imageBytes!=null){
            Bitmap bmp = convertByteArrayToBitmap(imageBytes);
            imgView.setImageBitmap(bmp);
        }
        getSupportActionBar().hide();
        if(toDoHandler!=null){
            Log.d("mybook","This blob is not empty and is not null");
        }
        listView = (ListView) findViewById(R.id.listViewMyBooks);
// Setup cursor adapter using cursor from last step
        todoAdapter = new DatabaseCursorAdapter(this, toDoHandler,0);
// Attach cursor adapter to the ListView
        listView.setAdapter(todoAdapter);
        //imgView.setImageBitmap(bm);

    }


    private byte[] getBlob(Cursor cursor, String columnName,byte[] defaultValue){
        try{
            int colIndex;
            if(cursor!=null && (colIndex=cursor.getColumnIndex(columnName))>-1 && !cursor.isNull(colIndex))
                    return cursor.getBlob(colIndex);
            return defaultValue;
        }catch(Exception e){
            e.printStackTrace();
            return defaultValue;
        }
    }

    private Bitmap convertByteArrayToBitmap(byte[] bytes){
        return BitmapFactory.decodeByteArray(bytes,0,bytes.length);
    }
}
