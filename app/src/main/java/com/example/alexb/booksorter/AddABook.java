package com.example.alexb.booksorter;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;

public class AddABook extends AppCompatActivity {
    private EditText editTextBookName;
    private EditText editTextBookYear;
    private EditText editTextBookAuthor;
    private EditText editTextBookPages;
    private EditText editTextBookCurrentPage;
    private EditText editTextBookDesc;
    private Button btnSaveData;
    private DrawerLayout dLayout;
    private Button addAnImage;
    private final static int SELECT_PHOTO = 12345;
    private ImageView imgPictureFromGallery;
    private Uri imageURi;
    private InputStream inputStream;
    private Bitmap bitmapImgView;
    private byte[] byteArrayContainingImgData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_a_book);
        imgPictureFromGallery = (ImageView) findViewById(R.id.imgViewFromGallery);
        getSupportActionBar().hide();
        DataBaseHandler dataBaseHandler= new DataBaseHandler(this);




        addAnImage = (Button) findViewById(R.id.btnAddImage);
        btnSaveData = (Button) findViewById(R.id.btnSaveBookData);
        editTextBookName = (EditText) findViewById(R.id.txtBookName);
        editTextBookYear = (EditText) findViewById(R.id.txtBookYear);
        editTextBookAuthor = (EditText) findViewById(R.id.txtBookAuthor);
        editTextBookPages = (EditText) findViewById(R.id.txtBookPages);
        editTextBookCurrentPage = (EditText) findViewById(R.id.txtBookCurrentPage);
        editTextBookDesc = (EditText) findViewById(R.id.txtBookDescription);
        dLayout = (DrawerLayout) findViewById(R.id.navigationDrawerLayout);
        NavigationView navView = (NavigationView) findViewById(R.id.navigation);
        navView.setItemIconTintList(null);
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment=null;
                int itemID = item.getItemId();
                if(itemID==R.id.first){
                    Intent goToAddABook = new Intent(AddABook.this,AddABook.class);
                    startActivity(goToAddABook);
                }
                if(itemID==R.id.second){
                    Intent goToDeleteABook = new Intent(AddABook.this,DeleteABook.class);
                }
                if(fragment!=null){
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.frameStartPage,fragment);
                    fragmentTransaction.commit();
                    dLayout.closeDrawer(Gravity.RIGHT);
                    return true;
                }
                return false;
            }

        });


        addAnImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pickPhotoForBook = new Intent(Intent.ACTION_PICK);
                pickPhotoForBook.setType("image/*");
                startActivityForResult(pickPhotoForBook,SELECT_PHOTO);
                bitmapImgView = ((BitmapDrawable)imgPictureFromGallery.getDrawable()).getBitmap();
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                bitmapImgView.compress(Bitmap.CompressFormat.PNG,100,bos);
                byteArrayContainingImgData = bos.toByteArray();
            }
        });

        navView.setItemIconTintList(null);
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment=null;
                int itemID = item.getItemId();
                if(itemID==R.id.first){
                    Intent goToAddABook = new Intent(AddABook.this,AddABook.class);
                    startActivity(goToAddABook);
                }
                else if(itemID==R.id.second){
                    Intent goToAddABook = new Intent(AddABook.this,DeleteABook.class);
                    startActivity(goToAddABook);
                }
                else if (itemID==R.id.third){
                    Intent goToUpdateABook = new Intent(AddABook.this,UpdateABook.class);
                    startActivity(goToUpdateABook);
                }
                else if(itemID==R.id.homeOption){
                    Intent goToHomePage = new Intent(AddABook.this,MainActivity.class);
                    startActivity(goToHomePage);
                }
                return false;
            }
        });

        btnSaveData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String bookName = editTextBookName.getText().toString();
                int bookYear = Integer.parseInt(editTextBookYear.getText().toString());
                String bookAuthor = editTextBookAuthor.getText().toString();
                int bookNoOfPages = Integer.parseInt(editTextBookPages.getText().toString());
                int bookCurrentPage = Integer.parseInt(editTextBookCurrentPage.getText().toString());
                String bookDescription = editTextBookDesc.getText().toString();
                DataBaseHandler db = new DataBaseHandler(AddABook.this);
                db.addBook(new BookInformation(bookName,bookYear,bookAuthor,bookNoOfPages,bookCurrentPage,bookDescription,byteArrayContainingImgData));
                clearEditText(editTextBookName,editTextBookYear,editTextBookAuthor,editTextBookPages,editTextBookCurrentPage,editTextBookDesc);
                Toast.makeText(AddABook.this, "Book added", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode){
            case(SELECT_PHOTO):
                if(resultCode==RESULT_OK){
                    Uri selectedImage = data.getData();
                    InputStream imageStream = null;
                    try{
                        imageStream = getContentResolver().openInputStream(selectedImage);
                    }catch(FileNotFoundException e){
                        e.printStackTrace();
                    }
                    Bitmap yourSelectedImage = BitmapFactory.decodeStream(imageStream);
                    imgPictureFromGallery.setImageURI(selectedImage);
                }
        }
    }

    private void clearEditText(EditText bookName, EditText bookYear, EditText bookAuthor, EditText bookPages, EditText bookCurrentPage, EditText bookDescription){

        bookName.setText("");
        bookYear.setText("");
        bookAuthor.setText("");
        bookPages.setText("");
        bookCurrentPage.setText("");
        bookDescription.setText("");

    }

}
