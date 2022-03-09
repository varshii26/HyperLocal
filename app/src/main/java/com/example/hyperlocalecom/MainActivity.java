package com.example.hyperlocalecom;

import static com.example.hyperlocalecom.RegisterActivity.setSignUpFragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
// import android.widget.Toolbar;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;


import com.example.hyperlocalecom.databinding.AppBarMainBinding;
import com.example.hyperlocalecom.ui.home.HomeFragment;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
//import android.support.v4.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.hyperlocalecom.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

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

    private TextView badgeCount;

    private int scrollFlags;
    private AppBarLayout.LayoutParams params;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        binding = ActivityMainBinding.inflate(getLayoutInflater());
//
//        //setContentView(R.layout.activity_main); no
//        setContentView(binding.getRoot());
//        setSupportActionBar(binding.appBarMain.toolbar);
//
//      //  setSupportActionBar(binding.appBarMain.toolbar);
//          drawer = binding.drawerLayout;
//
////        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
////        binding = AppBarMainBinding.inflate(getLayoutInflater());
////        setContentView(binding.getRoot());
////        setSupportActionBar(toolbar);
////        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//
//        actionBarLogo = findViewById(R.id.action_bar_logo);
//
//
//
//      /*  actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawer,toolbar,R.string.open,R.string.close);
//        drawer.addDrawerListener(actionBarDrawerToggle);
//        actionBarDrawerToggle.syncState();
//
//     */
//
//        // navigationView = binding.navView;
//        navigationView = (NavigationView) findViewById(R.id.nav_view);
//        navigationView.bringToFront();
//        // Passing each menu ID as a set of Ids because each
//        // menu should be considered as top level destinations.
//        mAppBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
//                .setOpenableLayout(drawer)
//                .build();
////////////////added abhi
//        NavHostFragment navHostFragment =
//                (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
//        NavController navController = navHostFragment.getNavController();
//        navigationView = (NavigationView) findViewById(R.id.nav_view);
//        /////////////////////////added abhi
//
//
//        /////commented abhi
//      //  NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
//       NavigationUI.setupWithNavController(navigationView, navController);
//        navigationView.getMenu().getItem(0).setChecked(true);
//        frameLayout = findViewById(R.id.main_framelayout);
//


        //////////////NEW CODE
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setOpenableLayout(drawer)
                .build();

//      window = getWindow;
//      window.addFlag(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        // NavHostFragment navHostFragment =
        //         (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        // NavController navController = navHostFragment.getNavController();

        params = (AppBarLayout.LayoutParams)toolbar.getLayoutParams();
        scrollFlags = params.getScrollFlags();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(navigationView, navController);

        navigationView.getMenu().getItem(0).setChecked(true);
        frameLayout = findViewById(R.id.main_framelayout);

        actionBarLogo = findViewById(R.id.action_bar_logo);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.open, R.string.close);
        drawer.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();


        /////////////NEW CODE


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

//                     if (currentUser != null) {
//                     drawer.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
//                     @Override
//                      public void onDrawerClosed(View drawerView) {
//                        super.onDrawerClosed(drawerView);

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
                        DBqueries.clearData();
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

        invalidateOptionsMenu();

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

            MenuItem cartItem = menu.findItem(R.id.mainCartIcon);
            cartItem.setActionView(R.layout.badge_layout);
            ImageView badgeicon = cartItem.getActionView().findViewById(R.id.badge_icon);
            badgeicon.setImageResource(R.mipmap.cart_white);
            badgeCount = cartItem.getActionView().findViewById(R.id.badge_count);
            if (currentUser != null){
                if (DBqueries.cartList.size() == 0) {
                    DBqueries.loadCartList(MainActivity.this, new Dialog(MainActivity.this), false,badgeCount);
                }else{
                        badgeCount.setVisibility(View.VISIBLE);
                    if (DBqueries.cartList.size() < 99) {
                        badgeCount.setText(String.valueOf(DBqueries.cartList.size()));
                    } else {
                        badgeCount.setText("99");
                    }
                }
            }

            cartItem.getActionView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (currentUser == null)
                        signInDialog.show();
                    else {
                        goToFragment("My Cart", new MyCartFragment(), CART_FRAGMENT);
                    }
                }
            });
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
            params.setScrollFlags(0);
        }else{
            params.setScrollFlags(scrollFlags);
        }

    }

    ///changed abhi
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


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
