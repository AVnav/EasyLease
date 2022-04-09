package com.example.easylease;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.easylease.fragments.MaintenanceFragment;
import com.example.easylease.fragments.MessagesFragment;
import com.example.easylease.fragments.ProfileFragment;
import com.example.easylease.fragments.ScheduleFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";
    private BottomNavigationView bottomNavigationView;
    final FragmentManager fragmentManager = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById((R.id.bottom_navigation));

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener(){

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = new MaintenanceFragment();

                switch (item.getItemId()) {
                    case R.id.action_maintenance:
                        fragment = new MaintenanceFragment();
                        //Toast.makeText(MainActivity.this, "Maintenance", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_schedule:
                        //Toast.makeText(MainActivity.this, "Schedule", Toast.LENGTH_SHORT).show();
                        fragment = new ScheduleFragment();
                        break;
                    case R.id.action_notification:
                        //Toast.makeText(MainActivity.this, "Notifications", Toast.LENGTH_SHORT).show();
                        fragment = new MessagesFragment();
                        break;
                    case R.id.action_profile:
                        //Toast.makeText(MainActivity.this, "Profile", Toast.LENGTH_SHORT).show();
                        fragment = new ProfileFragment();
                        break;
                    default:
                        //Toast.makeText(MainActivity.this, "Profile", Toast.LENGTH_SHORT).show();
                        fragment = new MaintenanceFragment();
                        //fragment = new ProfileFragment();
                        break;
                }
                fragmentManager.beginTransaction().replace(R.id.flContainer, fragment).commit();
                return true;
            }
        });

        bottomNavigationView.setSelectedItemId(R.id.action_maintenance);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //return super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.settings){
            //Go to settings activity
            Toast.makeText(this, "Settings page!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}