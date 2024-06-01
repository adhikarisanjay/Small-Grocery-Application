package com.example.sagrocery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sagrocery.Fragments.AddStockFragment;
import com.example.sagrocery.Fragments.HomeFragment;
import com.example.sagrocery.Fragments.ListStockFragment;
import com.example.sagrocery.Fragments.PurchaseFragment;
import com.example.sagrocery.Fragments.SalesFragment;
import com.example.sagrocery.Fragments.SearchStockFragment;
import com.example.sagrocery.Login.LoginActivity;
import com.example.sagrocery.Utils.CustomToast;
import com.example.sagrocery.Utils.UserManager;
import com.example.sagrocery.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    public UserManager userManager;
    ActivityMainBinding binding;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        userManager = new UserManager(this); // Initialize the AuthenticatorHandler or UserManager
        if (!userManager.isLoggedIn()) {
            logout();
        }


        Toolbar toolbar = findViewById(R.id.toolbar); //Ignore red line errors
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav,
                R.string.close_nav);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }
        View headerView = navigationView.getHeaderView(0);
        TextView userNameTextView = headerView.findViewById(R.id.userName);
        if(userManager.userName()!=null){
            userNameTextView.setText(userManager.userName());
        }
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
                break;
            case R.id.nav_addStock:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AddStockFragment()).commit();
                break;
            case R.id.nav_sales:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SalesFragment()).commit();
                break;
            case R.id.nav_purchase:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new PurchaseFragment()).commit();
                break;

            case R.id.nav_searchStock:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SearchStockFragment()).commit();
                break;

            case R.id.nav_listStock:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ListStockFragment()).commit();
                break;

            case R.id.nav_logout:
                userManager.logoutUser();
                logout();
                CustomToast.showToast(this, "User Logout Successfully", getResources().getColor(R.color.white), R.color.green, R.drawable.tick);
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void logout(){
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

}