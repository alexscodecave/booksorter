package com.example.alexb.booksorter;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.IntegerRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by alexb on 27/06/2017.
 */

public class DatabaseCursorAdapter extends android.widget.CursorAdapter {

    public DatabaseCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.custom_book_layout,parent,false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView customBookName = (TextView) view.findViewById(R.id.customBookName);
        TextView customBookYear = (TextView) view.findViewById(R.id.customBookYear);
        TextView customBookAuthor = (TextView) view.findViewById(R.id.customBookAuthor);
        TextView customBookNoOfPages = (TextView) view.findViewById(R.id.customBookPages);
        TextView customBookCurrentPage = (TextView) view.findViewById(R.id.customBookCurrentPage);
        TextView customBookDescription = (TextView) view.findViewById(R.id.customBookDescription);
        // Extract properties from cursor

        String getBookName= cursor.getString(cursor.getColumnIndexOrThrow("colBookName"));
        int getBookYear= cursor.getInt(cursor.getColumnIndexOrThrow("colBookYear"));
        String getBookAuthor = cursor.getString(cursor.getColumnIndexOrThrow("colBookAuthor"));
        int getBookNoOfPages= cursor.getInt(cursor.getColumnIndexOrThrow("colBookNoOfPages"));
        int getBookCurrentPage= cursor.getInt(cursor.getColumnIndexOrThrow("colCurrentPage"));
        String getBookDescription = cursor.getString(cursor.getColumnIndexOrThrow("colBookDesc"));


        customBookName.setText("Book Name: "+getBookName);
        customBookYear.setText("Published: "+(Integer.toString(getBookYear)));
        customBookAuthor.setText("Book Author: "+(getBookAuthor.toString()));
        customBookNoOfPages.setText("Number of pages in book: "+(Integer.toString(getBookNoOfPages)));
        customBookCurrentPage.setText("Current page: "+(Integer.toString(getBookCurrentPage)));
        customBookDescription.setText("Book Description: " +getBookDescription);
        // Populate fields with extracted properties

    }
}
