package com.bignerdranch.android.remindernote;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Fragment Manager
        FragmentManager FM=getSupportFragmentManager();
        Fragment fragment=FM.findFragmentById(R.id.fragment_container);
        if (fragment == null){
            fragment=new ListFragment();
            FM.beginTransaction()
                    .add(R.id.fragment_container,fragment)
                    .commit();
        }
    }

    //Option Menu of Add Notes
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //Add Notes if User Clicks on The Add Option Menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add:
                startActivity(new Intent(this, NoteActivity.class));
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

