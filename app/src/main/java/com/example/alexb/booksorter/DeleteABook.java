package com.example.alexb.booksorter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class DeleteABook extends AppCompatActivity {
    Cursor getBookInfoForDelete;
    EditText getBookIDFromUser;
    ListView listViewShowingBookInfo;
    DatabaseCursorAdapter todoAdapter;
    private Button btnDeleteRecord;
    private Spinner ddlDeleteValues;
    private String getDDLValue;
    private DrawerLayout deleteBookDrawerLayout;
    private DataBaseHandler dbHandler;
    SQLiteDatabase sqLiteDatabase;
    private String dropDownValue;
    private Context context;
    private ImageView imgView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_abook);
        dbHandler = new DataBaseHandler(this);
        sqLiteDatabase = dbHandler.getWritableDatabase();
        getSupportActionBar().hide();
        Log.d("errortag","This runs when a error occurs");
        Cursor mCursor = sqLiteDatabase.rawQuery("SELECT * FROM tblBookInformation WHERE colBookName= " + getDDLValue, null);
        ArrayList<String> holdDBData = new ArrayList<String>();
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, holdDBData);
        final TextView bookYear = (TextView) findViewById(R.id.txtViewDeleteBookYear);
        final TextView bookAuthor = (TextView) findViewById(R.id.txtViewDeleteBookAuthor);
        final TextView bookNoOfPages = (TextView) findViewById(R.id.txtViewDeleteNoOfPages);
        final TextView bookCurrentPage = (TextView) findViewById(R.id.txtViewDeleteCurrentPage);
        final TextView bookDescription = (TextView) findViewById(R.id.txtViewDeleteBookDescription);
        imgView = (ImageView) findViewById(R.id.imgViewDeleteBookImage);

        ddlDeleteValues = (Spinner) findViewById(R.id.deleteSpinner);
        btnDeleteRecord = (Button) findViewById(R.id.btnSaveBookDeleteData);
        deleteBookDrawerLayout = (DrawerLayout) findViewById(R.id.deleteNavigationDrawerLayout);
        NavigationView navViewDeleteBookActivity = (NavigationView) findViewById(R.id.navigation);

        btnDeleteRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dropDownValue = ddlDeleteValues.getSelectedItem().toString();
                DataBaseHandler db = new DataBaseHandler(DeleteABook.this);
                db.deleteBook(ddlDeleteValues.getSelectedItem().toString());
                clearTextView(bookYear,bookAuthor,bookNoOfPages,bookCurrentPage,bookDescription);

                Toast.makeText(DeleteABook.this, "Book deleted", Toast.LENGTH_SHORT).show();
            }
        });
        navViewDeleteBookActivity.setItemIconTintList(null);
        navViewDeleteBookActivity.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                int itemID = item.getItemId();
                if (itemID == R.id.first) {
                    Intent goToAddABook = new Intent(DeleteABook.this, AddABook.class);
                    startActivity(goToAddABook);
                } else if (itemID == R.id.second) {
                    Intent goToDeleteBook = new Intent(DeleteABook.this, DeleteABook.class);
                    startActivity(goToDeleteBook);
                } else if (itemID == R.id.third) {
                    Intent goToUpdateBook = new Intent(DeleteABook.this, UpdateABook.class);
                    startActivity(goToUpdateBook);
                } else if (itemID == R.id.homeOption) {
                    Intent goToHomePage = new Intent(DeleteABook.this, MainActivity.class);
                    startActivity(goToHomePage);
                }
                if (fragment != null) {
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.deleteFrame, fragment);
                    fragmentTransaction.commit();
                    deleteBookDrawerLayout.closeDrawer(Gravity.LEFT);
                    return true;
                }
                return false;
            }
        });
        loadDatabaseValuesInSpinners();
        ddlDeleteValues.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                getDBInformation(ddlDeleteValues.getSelectedItem().toString());
                Log.d("dropdowntag","This runs when drop down value is selected");
//                Toast.makeText(context, "An item selected", Toast.LENGTH_SHORT).show();
//                if(position==0){
//                    Toast.makeText(context, "At position 0", Toast.LENGTH_SHORT).show();
//                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(context, "No item selected", Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void getDBInformation(String columnName) {
        DataBaseHandler db = new DataBaseHandler(this);
        List<String> getEverything = db.alternativeMethodToGainDBInfo(columnName);
        DataBaseHandler dataHelper = new DataBaseHandler(this);


        ((TextView) findViewById(R.id.txtViewDeleteBookYear)).setText("Author: " + getEverything.get(0));
        ((TextView) findViewById(R.id.txtViewDeleteBookAuthor)).setText("Book published: " + getEverything.get(1));
        ((TextView) findViewById(R.id.txtViewDeleteNoOfPages)).setText("Book pagination: " + getEverything.get(2));
        ((TextView) findViewById(R.id.txtViewDeleteCurrentPage)).setText("Your current page: " + getEverything.get(3));
        ((TextView) findViewById(R.id.txtViewDeleteBookDescription)).setText("Book Description: " + getEverything.get(4));

    }


    private void clearTextView(TextView bookYear, TextView bookAuthor, TextView bookPages, TextView bookCurrentPage, TextView bookDescription) {
        bookYear.setText("");
        bookAuthor.setText("");
        bookPages.setText("");
        bookCurrentPage.setText("");
        bookDescription.setText("");
    }

    private void loadDatabaseValuesInSpinners() {
        DataBaseHandler db = new DataBaseHandler(this);
        List<String> bookNames = db.getBookName();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, bookNames);
        ddlDeleteValues.setAdapter(dataAdapter);
    }


}
