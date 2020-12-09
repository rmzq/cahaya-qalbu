package com.example.e_pertanian;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.e_pertanian.fragments.ScheduleFragment;
import com.example.e_pertanian.schedule.SchedulingActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private GoogleSignInClient mGoogle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //authentication

        mAuth = FirebaseAuth.getInstance();

        FirebaseUser userSekarang = mAuth.getCurrentUser();

        if (userSekarang == null) {
            startActivity(new Intent(this, SignInActivity.class));
            finish();
            return;
        }

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogle = GoogleSignIn.getClient(this,gso);

        //buat button navigation bar

        //getSupportFragmentManager().beginTransaction().replace(R.id.pager,null).commit();
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.tapBar);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;
                switch (item.getItemId()) {
                    case R.id.navHome:
                        Toast.makeText(MainActivity.this, "Home", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.navSchedule:
                        Toast.makeText(MainActivity.this, "schedule", Toast.LENGTH_SHORT).show();
//                        Intent intent = new Intent(MainActivity.this, SchedulingActivity.class);
//                        startActivity(intent);
                        selectedFragment = new ScheduleFragment();
                        break;
                    case R.id.navChat:
                        Toast.makeText(MainActivity.this, "chat", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.navProfile:
                        Toast.makeText(MainActivity.this, "profile", Toast.LENGTH_SHORT).show();
                    default: selectedFragment = null;
                }

                getSupportFragmentManager().beginTransaction().replace(R.id.pager,selectedFragment).commit();
                return true;
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuLogOut:
                mAuth.signOut();
                mGoogle.signOut();
                startActivity(new Intent(this, SignInActivity.class));
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
}