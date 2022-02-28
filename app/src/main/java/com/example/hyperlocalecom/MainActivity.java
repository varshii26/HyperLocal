package com.example.hyperlocalecom;

import static com.example.hyperlocalecom.RegisterActivity.setSignUpFragment;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
// import android.widget.Toolbar;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.hyperlocalecom.ui.home.HomeFragment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hyperlocalecom.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import javax.annotation.Nullable;

public class MainActivity extends AppCompatActivity {

    private static final int HOME_FRAGMENT = 0;
    private static final int CART_FRAGMENT = 1;
    private static final int ORDERS_FRAGMENT = 2;
    private static final int WISHLIST_FRAGMENT = 3;
    private static final int ACCOUNT_FRAGMENT = 4;

    public static Boolean showCart = false;

    ActionBarDrawerToggle actionBarDrawerToggle;
    private int currentFragment = -1;
    private NavigationView navigationView;

    private TextView actionBarLogo;

    private ImageView noInternetConnection;
    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    private FrameLayout frameLayout;
    MenuItem menuItem;
    private Dialog signInDialog;
    public static DrawerLayout drawer;

    private FirebaseUser currentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.appBarMain.toolbar);
        actionBarLogo = findViewById(R.id.action_bar_logo);

         drawer = binding.drawerLayout;
        //drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
      /*  actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawer,toolbar,R.string.open,R.string.close);
        drawer.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

     */

        //  navigationView = binding.navView;
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.bringToFront();
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        navigationView.getMenu().getItem(0).setChecked(true);
        frameLayout = findViewById(R.id.main_framelayout);


        if (showCart) {
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            goToFragment("My Cart", new MyCartFragment(), -2);
        } else {

            ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.open, R.string.close);
            drawer.addDrawerListener(actionBarDrawerToggle);
            actionBarDrawerToggle.syncState();

            setFragment(new HomeFragment(), HOME_FRAGMENT);
        }


        signInDialog = new Dialog((MainActivity.this));
        signInDialog.setContentView(R.layout.sign_in_dialog);
        signInDialog.setCancelable(true);
        signInDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        Button dialogSignInBtn = signInDialog.findViewById(R.id.sign_in_btn);
        Button dialogSignUpBtn = signInDialog.findViewById(R.id.sign_up_btn);

        Intent registerIntent = new Intent(MainActivity.this, RegisterActivity.class);

        dialogSignInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SignInFragment.disableCloseBtn = true;
                SignUpFragment.disableCloseBtn = true;
                signInDialog.dismiss();
                setSignUpFragment = false;
                startActivity(registerIntent);
            }
        });

        dialogSignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SignInFragment.disableCloseBtn = true;
                SignUpFragment.disableCloseBtn = true;
                signInDialog.dismiss();
                setSignUpFragment = true;
                startActivity(registerIntent);
            }
        });


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                if (currentUser != null) {

                    int id = item.getItemId();

                    // if (currentuser != null) {
                    // drawer.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
                    //  @Override
                    //  public void onDrawerClosed(View drawerView) {
                    //    super.onDrawerClosed(drawerView);

                    if (id == R.id.App) {

                        actionBarLogo.setVisibility(View.VISIBLE);
                        invalidateOptionsMenu();
                        setFragment(new HomeFragment(), HOME_FRAGMENT);

                    } else if (id == R.id.Orders) {
                        goToFragment("My Order", new MyOrdersFragment(), ORDERS_FRAGMENT);
                    } else if (id == R.id.Favourites) {
                        goToFragment("My Favorites", new MyWishlistFragment(), WISHLIST_FRAGMENT);
                    } else if (id == R.id.Cart) {
                        goToFragment("My Cart", new MyCartFragment(), CART_FRAGMENT);
                    } else if (id == R.id.Account) {
                        goToFragment("My Account", new MyAccountFragment(), ACCOUNT_FRAGMENT);
                    } else if (id == R.id.SignOut) {
                        FirebaseAuth.getInstance().signOut();
                        Intent registerIntent = new Intent(MainActivity.this, RegisterActivity.class);
                        startActivity(registerIntent);
                        finish();

                    }
                    drawer.closeDrawer(GravityCompat.START);
                    return true;
                } else {
                    drawer.closeDrawer(GravityCompat.START);
                    signInDialog.show();
                    return false;
                }

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if (currentUser == null) {
            navigationView.getMenu().getItem(navigationView.getMenu().size() - 1).setEnabled(false);
        } else {
            navigationView.getMenu().getItem(navigationView.getMenu().size() - 1).setEnabled(true);
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (currentFragment == HOME_FRAGMENT) {
                super.onBackPressed();
                currentFragment = -1;
            } else {
                if (showCart) {
                    showCart = false;
                    finish();
                } else {
                    actionBarLogo.setVisibility(View.VISIBLE);
                    invalidateOptionsMenu();
                    setFragment(new HomeFragment(), HOME_FRAGMENT);
                    navigationView.getMenu().getItem(0).setChecked(true);
                }

            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        if (currentFragment == HOME_FRAGMENT) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getMenuInflater().inflate(R.menu.main, menu);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.mainSearchIcon) {
            return true;
        } else if (id == R.id.mainNotificationIcon) {
            return true;
        } else if (id == R.id.mainCartIcon) {
            if (currentUser == null)
                signInDialog.show();
            else {
                goToFragment("My Cart", new MyCartFragment(), CART_FRAGMENT);
            }
            return true;
        } else if (id == android.R.id.home) {
            if (showCart) {
                showCart = false;
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void goToFragment(String title, Fragment fragment, int fragmentNo) {
        actionBarLogo.setVisibility(View.GONE);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle(title);
        invalidateOptionsMenu();
        setFragment(fragment, fragmentNo);
        if (fragmentNo == CART_FRAGMENT) {
            navigationView.getMenu().getItem(2).setChecked(true);
        }

    }
///changed abhi
  /*  @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    } */


    private void setFragment(Fragment fragment, int fragmentNo) {
        if (fragmentNo != currentFragment) {
            currentFragment = fragmentNo;
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
            fragmentTransaction.replace(frameLayout.getId(), fragment);
            fragmentTransaction.commit();

        }
    }


}

