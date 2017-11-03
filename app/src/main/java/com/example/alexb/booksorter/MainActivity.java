package com.example.alexb.booksorter;

import android.content.Intent;
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

public class MainActivity extends AppCompatActivity {
    Button btnMainActivity;
    private DrawerLayout dLayoutMainActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnMainActivity = (Button) findViewById(R.id.btnMainActivity);
        getSupportActionBar().hide();
        dLayoutMainActivity = (DrawerLayout) findViewById(R.id.navigationDrawerLayoutStartPage);
        NavigationView navViewMainActivity = (NavigationView) findViewById(R.id.navigationStartPage);
        navViewMainActivity.setItemIconTintList(null);
        navViewMainActivity.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment=null;
                int itemID = item.getItemId();
                if(itemID==R.id.first){
                    Intent goToAddABook = new Intent(MainActivity.this,AddABook.class);
                    startActivity(goToAddABook);
                }
                else if(itemID==R.id.second){
                    Intent goToDeleteABook = new Intent(MainActivity.this,DeleteABook.class);
                    startActivity(goToDeleteABook);
                }
                else if(itemID==R.id.third){
                    Intent goToUpdateABook = new Intent(MainActivity.this,UpdateABook.class);
                    startActivity(goToUpdateABook);
                }
                else if(itemID==R.id.homeOption){
                    Intent goToHomePage = new Intent(MainActivity.this,MainActivity.class);
                    startActivity(goToHomePage);
                }
                else if(itemID==R.id.similiarbooksearch){
                    Intent goToSearchPage = new Intent(MainActivity.this,SearchSimiliarBooks.class);
                    startActivity(goToSearchPage);
                }
                if(fragment!=null){
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.frameStartPage,fragment);
                    fragmentTransaction.commit();
                    dLayoutMainActivity.closeDrawer(Gravity.LEFT);
                    return true;
                }
                return false;
            }
        });
        btnMainActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToMyBooks = new Intent(MainActivity.this,MyBooks.class);
                startActivity(goToMyBooks);
            }
        });
    }
}
