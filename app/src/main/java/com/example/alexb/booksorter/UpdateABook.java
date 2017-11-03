package com.example.alexb.booksorter;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import java.io.ByteArrayOutputStream;
import java.util.List;

public class UpdateABook extends AppCompatActivity {

    private EditText editTextBookYear;
    private EditText editTextBookAuthor;
    private EditText editTextBookPages;
    private EditText editTextBookCurrentPage;
    private EditText editTextBookDesc;
    private ImageView imgViewBook;
    private Button btnSaveData;
    private DrawerLayout dLayout;
    private Bitmap bitmapImageView;
    private Spinner dropDownBooks;
    private byte[] byteArrayContainingImgData;

    DataBaseHandler dbHandler;
    SQLiteDatabase sqLiteDatabase;
    Cursor toDoHandler;
    DatabaseCursorAdapter todoAdapter;
    String dropDownValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_abook);
        dbHandler = new DataBaseHandler(this);
        sqLiteDatabase  = dbHandler.getWritableDatabase();
//        toDoHandler = sqLiteDatabase.rawQuery("SELECT * FROM tblBookInformation",null);
//        toDoHandler.moveToFirst();
        getSupportActionBar().hide();

        imgViewBook = (ImageView) findViewById(R.id.imgViewUpdateBook);
        dropDownBooks = (Spinner) findViewById(R.id.dropDownBookID);
        btnSaveData = (Button) findViewById(R.id.btnSaveBookUpdateData);

        editTextBookYear = (EditText) findViewById(R.id.txtUpdateBookYear);
        editTextBookAuthor = (EditText) findViewById(R.id.txtUpdateBookAuthor);
        editTextBookPages = (EditText) findViewById(R.id.txtUpdateBookPages);
        editTextBookCurrentPage = (EditText) findViewById(R.id.txtBookUpdateCurrentPage);
        editTextBookDesc = (EditText) findViewById(R.id.txtUpdateBookDescription);
        dLayout = (DrawerLayout) findViewById(R.id.navigationDrawerLayout);
        NavigationView navView = (NavigationView) findViewById(R.id.navigation);
        loadSpinners();
        bitmapImageView =  ((BitmapDrawable)imgViewBook.getDrawable()).getBitmap();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmapImageView.compress(Bitmap.CompressFormat.PNG,100,bos);
        byteArrayContainingImgData = bos.toByteArray();
        dbHandler.getImageIntoImageView(dropDownBooks.getSelectedItem().toString(),imgViewBook);

// Setup cursor adapter using cursor from last step
        todoAdapter = new DatabaseCursorAdapter(this, toDoHandler,0);

        btnSaveData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                int anID = Integer.parseInt(editTextBookID.getText().toString());
//                String bookName = editTextBookName.getText().toString();
                dropDownValue = dropDownBooks.getSelectedItem().toString();
                String getBookYear = editTextBookYear.getText().toString();
                String getBookAuthor = editTextBookAuthor.getText().toString();
                String getBookPagination = editTextBookPages.getText().toString();
                String getBookCurrentPage = editTextBookCurrentPage.getText().toString();
                String getBookDescription = editTextBookDesc.getText().toString();
                int parseBookYear = Integer.parseInt(getBookYear);
                int parseBookPagination = Integer.parseInt(getBookPagination);
                int parseBookCurrentPage = Integer.parseInt(getBookCurrentPage);
                DataBaseHandler db = new DataBaseHandler(UpdateABook.this);

                db.updateBook(dropDownBooks.getSelectedItem().toString(),1990,getBookAuthor,parseBookPagination,parseBookCurrentPage,getBookDescription,byteArrayContainingImgData);
                Toast.makeText(UpdateABook.this, "Book updated", Toast.LENGTH_SHORT).show();
            }
        });

        navView.setItemIconTintList(null);
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment=null;
                int itemID = item.getItemId();
                if(itemID==R.id.first){
                    Intent goToAddABook = new Intent(UpdateABook.this,AddABook.class);
                    startActivity(goToAddABook);
                }
                else if(itemID==R.id.second){
                    Intent goToAddABook = new Intent(UpdateABook.this,DeleteABook.class);
                    startActivity(goToAddABook);
                }
                else if(itemID==R.id.third){
                    Intent goToUpdateABook = new Intent(UpdateABook.this,UpdateABook.class);
                    startActivity(goToUpdateABook);
                }
                else if(itemID==R.id.homeOption){
                    Intent goToHomePage = new Intent(UpdateABook.this,MainActivity.class);
                }
                if(fragment!=null){
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.updateFrame,fragment);
                    fragmentTransaction.commit();
                    dLayout.closeDrawer(Gravity.LEFT);
                    return true;
                }
                return false;
            }
        });
        dropDownBooks.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                DataBaseHandler db = new DataBaseHandler(UpdateABook.this);
                getDBInformation(dropDownBooks.getSelectedItem().toString());


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    /**
     * getDBInformation retrieves specific information from database
     * @param columnName
     */
    public void getDBInformation(String columnName) {
        DataBaseHandler db = new DataBaseHandler(this);
        List<String> getEverything = db.getEverythingToUpdateBook(columnName);
        DataBaseHandler dataHelper = new DataBaseHandler(this);
        ((EditText) findViewById(R.id.txtUpdateBookName)).setText(getEverything.get(0));
        ((EditText) findViewById(R.id.txtUpdateBookAuthor)).setText(getEverything.get(1));
        ((EditText) findViewById(R.id.txtUpdateBookYear)).setText(getEverything.get(2));

        ((EditText) findViewById(R.id.txtUpdateBookPages)).setText(getEverything.get(3));
        ((EditText) findViewById(R.id.txtBookUpdateCurrentPage)).setText(getEverything.get(4));
        ((EditText) findViewById(R.id.txtUpdateBookDescription)).setText(getEverything.get(5));
    }


    private void loadSpinners(){
        DataBaseHandler db = new DataBaseHandler(this);
        List<String> bookNames = db.getBookName();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,bookNames);
        dropDownBooks.setAdapter(dataAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Toast.makeText(this, item + " is selected", Toast.LENGTH_SHORT).show();
        return true;
    }

}
