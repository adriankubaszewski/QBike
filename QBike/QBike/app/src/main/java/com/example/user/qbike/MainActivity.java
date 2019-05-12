package com.example.user.qbike;

import android.os.Bundle;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.content.*;

import java.lang.*;
import android.content.Context;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private TextView Informacja;
    int czestotl=3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Button button = (Button) findViewById(R.id.buttonStart);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Context context = getApplicationContext();
                Toast.makeText(context, "Rozpoczynam trening...", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(context, GPSPrzesuwne.class);
                startActivity(intent);

            }
        });

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        Informacja = (TextView) findViewById(R.id.Komunikat);
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {

            Context context = getApplicationContext();
            Toast.makeText(context, "Wczytuję instrukcję...", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(context, InstrukcjaObslugiActivity.class);
            startActivity(intent);
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {
            Context context = getApplicationContext();
            Toast.makeText(context, "Rozpoczynam trening...", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(context, GPSPrzesuwne.class);
            startActivity(intent);

        } else if (id == R.id.nav_slideshow) {
            Context context = getApplicationContext();
            Toast.makeText(context, "Wczytuję mapę...", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(context, MapsActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_manage) {
            Context context = getApplicationContext();
            Toast.makeText(context, "Wczytuję historię tras...", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(context, PrzejechanetrasyActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_share) {
            Context context = getApplicationContext();
            Toast.makeText(context, "Wczytuję ustawienia...", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(context, ActivityUstawienia.class);
            startActivity(intent);

        } else if (id == R.id.nav_send) {
            Context context = getApplicationContext();
            Toast.makeText(context, "Wczytuję osiągnięcia...", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(context, OsiagnieciaActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_informacje) {
            Context context = getApplicationContext();
            Toast.makeText(context, "Wczytuję informacje...", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(context, InformacjeActivity.class);
            startActivity(intent);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
