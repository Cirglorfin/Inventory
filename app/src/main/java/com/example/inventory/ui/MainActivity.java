package com.example.inventory.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.inventory.R;
import com.example.inventory.ui.login.LoginFragment;
import com.example.inventory.ui.prefs.AccountFragment;
import com.example.inventory.ui.prefs.SettingsFragment;
import com.example.inventory.ui.prefs.UserPrefManager;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Gravity;
import android.view.View;

import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.inventory.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;

import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
    private NavController navController;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(binding.drawerLayout.isDrawerOpen(GravityCompat.START)){
            binding.drawerLayout.closeDrawer(GravityCompat.START);

        }else{
            super.onBackPressed();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.contentMain.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_acton_menu_foreground);


         navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        Set<Integer> topLevelDestination=new HashSet<>();
        topLevelDestination.add(R.id.dashBoardFragment);
        topLevelDestination.add(R.id.sectionListFragment);
        topLevelDestination.add(R.id.dependencyAddEditFragment);
        //appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        appBarConfiguration = new AppBarConfiguration.Builder(topLevelDestination).setOpenableLayout(binding.drawerLayout).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
//A continuación se configura las opciones del componente NavigationVIew
        setUpNavigationView();

    }

    private void setUpNavigationView() {
        binding.navigationview.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.dependencyListFragment:
                        navController.navigate(R.id.dependencyListFragment);
                        break;
                    case R.id.settingsFragment:
                        navController.navigate(R.id.settingsFragment);
                        break;
                    case R.id.sectionListFragment:
                        navController.navigate(R.id.sectionListFragment);
                        break;
                    case R.id.action_logout:
                        UserPrefManager userPrefManager=new UserPrefManager(getApplicationContext());
                        userPrefManager.logout();
                        navController.navigate(R.id.SplashFragment);

                        //Al añadir el menu y no ser parte de Splash, ni Login, ni Singup. Estos fragment no deben ser fragment
                        //de MainActivity. Son Ativity TA independientes.
                       // startActivity(MainActivity.this, LoginFragment.class);
                        break;
                }
                binding.drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}