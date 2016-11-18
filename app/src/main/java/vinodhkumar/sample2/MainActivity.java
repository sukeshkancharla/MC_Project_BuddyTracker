package vinodhkumar.sample2;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.text.Layout;
import android.util.Log;
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
import android.widget.RelativeLayout;
import android.widget.Toast;
import static android.Manifest.permission.READ_CONTACTS;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    Button mSignUpButton;
    private static final int REQUEST_READ_CONTACTS = 1;
    //private static final int PERMISSIONS_REQUEST_READ_CONTACTS =  1;
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_READ_CONTACTS) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted , Access contacts here or do whatever you need.
            }
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startService(new Intent(this, RecieverService.class));


        //FragmentManager fragmentManager = getFragmentManager();
        //fragmentManager.beginTransaction().add(R.id.content_frame,new HomeFragment()).commit();
        //fragmentManager.beginTransaction().replace(R.id.content_main, new HomeFragment()).commit();
        SharedPreferences sharedPref=getSharedPreferences(getString(R.string.shared_pref_id),Context.MODE_PRIVATE);
        //mSignUpButton = (Button)findViewById(R.id.signUpButton);
       // if(sharedPref.getString(getString(R.string.phone_number),"").equals("")){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        Toast.makeText(this,sharedPref.getString(getString(R.string.phone_number),"nothing"),Toast.LENGTH_SHORT).show();
        if(!sharedPref.contains(getString(R.string.phone_number))) {
            Toast.makeText(this,"asjd",Toast.LENGTH_SHORT).show();
            findViewById(R.id.homeScreen).setVisibility(View.GONE);
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_main, new SignUpFragment()).commit();
        }
        /*mSignUpButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                findViewById(R.id.homeScreen).setVisibility(View.GONE);
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_main, new SignUpFragment()).commit();
            }
        });*/
        else{


        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        }); */


            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_main, new HomeFragment()).commit();
        }

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.d("MainActivity: ","onDestroy()");
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        Fragment fragment = null;
        int id = item.getItemId();

        if(id != R.id.nav_home){
            getSupportActionBar().setTitle(item.getTitle());
            findViewById(R.id.homeScreen).setVisibility(View.GONE);
        }

        if (id == R.id.req_location) {
            fragment = new RequestLocationFragment();

        } else if (id == R.id.notifications) {
            Intent i=new Intent(getApplicationContext(),MyLocation.class);
            startActivity(i);
        } else if(id == R.id.nav_home){
            //findViewById(R.id.content_main).setVisibility(View.VISIBLE);
            findViewById(R.id.homeScreen).setVisibility(View.VISIBLE);
            getSupportActionBar().setTitle(R.string.app_name);
            fragment = new HomeFragment();
        } else if(id == R.id.signUpMenuItem){
            fragment = new SignUpFragment();
        }

            if (fragment != null) {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_main, fragment).commit();
        } else {
            Log.e("MainActivity", "Error in creating fragment");
        }

//         else if (id == R.id.nav_slideshow) {
//
//        } else if (id == R.id.nav_manage) {
//
//        } else if (id == R.id.nav_share) {
//
//        } else if (id == R.id.nav_send) {
//
//        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
