package com.example.hyperlocalecom;

import static com.example.hyperlocalecom.MainActivity.showCart;
import static com.example.hyperlocalecom.RegisterActivity.setSignUpFragment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductDetailsActivity extends AppCompatActivity {


    public static boolean running_wishlist_query = false;
    public static boolean running_cart_query = false;

    private ViewPager productImagesViewPager;
    private TabLayout viewpagerIndicator;

    private TextView productTitle;
    private TextView productPrice;
    private TextView cuttedPrice;
    private View codIndicator;
    private TextView tvCodIndicator;

    private ConstraintLayout productDetailsOnlyContainer;
    private ConstraintLayout productDetailsTabsContainer;
    private ViewPager productDetailsViewPager;
    private TabLayout productDetailsTablayout;
    private TextView productOnlyDescriptionBody;

    private Button buyNowBtn;
    private Dialog signInDialog;
    private Dialog loadingDialog;
    private LinearLayout addToCartBtn;
    private DocumentSnapshot documentSnapshot;

    private TextView badgeCount;

    private FirebaseUser currentUser;

    private String productDescription;
    private String productOtherDetails;
    public static String productId;
    private List<ProductSpecificationModel> productSpecificationModelList = new ArrayList<>();

    public static boolean ALREADY_ADDED_TO_WISHLIST = false;
    public static boolean ALREADY_ADDED_TO_CART = false;
    public static FloatingActionButton addToWishlistBtn;
    public static MenuItem cartItem;

    private FirebaseFirestore firebaseFirestore;

    //  @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        productImagesViewPager = (ViewPager) findViewById(R.id.product_images_viewpager);
        viewpagerIndicator = findViewById(R.id.viewpager_indicator);
        addToWishlistBtn = findViewById(R.id.add_to_wishlist_btn);
        productTitle = findViewById(R.id.product_title);
        productPrice = findViewById(R.id.product_price);
        cuttedPrice = findViewById(R.id.cutted_price);
        tvCodIndicator = findViewById(R.id.tv_cod_indicator);
        codIndicator = findViewById(R.id.cod_indicator_imageview);


        productDetailsViewPager = findViewById(R.id.product_details_viewpager);
        productDetailsTablayout = findViewById(R.id.product_details_tablayout);

        buyNowBtn = findViewById(R.id.buy_now_btn);
        productDetailsTabsContainer = findViewById(R.id.product_details_tabs_body);
        productDetailsOnlyContainer = findViewById(R.id.product_details_container);
        productOnlyDescriptionBody = findViewById(R.id.product_details_body);

        addToCartBtn = findViewById(R.id.add_to_cart_btn);

        ///////loading dialog
        loadingDialog = new Dialog((ProductDetailsActivity.this));
        loadingDialog.setContentView(R.layout.loading_progress_dialog);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.slider_background));
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        loadingDialog.show();
        //////loading dialog


        firebaseFirestore = FirebaseFirestore.getInstance();
        productId = getIntent().getStringExtra("PRODUCT_ID");
        List<String> productImages = new ArrayList<>();

        firebaseFirestore.collection("PRODUCTS").document(productId)
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    documentSnapshot = task.getResult();

                    for (long x = 1; x < (long) documentSnapshot.get("no_of_product_images") + 1; x++) {
                        productImages.add(documentSnapshot.get("product_image_" + x).toString());
                    }
                    ProductImagesAdapter productImagesAdapter = new ProductImagesAdapter(productImages);
                    productImagesViewPager.setAdapter(productImagesAdapter);

                    productTitle.setText(documentSnapshot.get("product_title").toString());
                    productPrice.setText("Rs." + documentSnapshot.get("product_price").toString() + "/-");
                    cuttedPrice.setText("Rs." + documentSnapshot.get("cutted_price").toString() + "/-");
                    if ((boolean) documentSnapshot.get("COD")) {
                        codIndicator.setVisibility(View.VISIBLE);
                        tvCodIndicator.setVisibility(View.VISIBLE);
                    } else {
                        codIndicator.setVisibility(View.INVISIBLE);
                        tvCodIndicator.setVisibility(View.INVISIBLE);
                    }

                    if ((boolean) documentSnapshot.get("use_tab_layout")) {
                        productDetailsTabsContainer.setVisibility(View.VISIBLE);
                        productDetailsOnlyContainer.setVisibility(View.GONE);
                        productDescription = documentSnapshot.get("product_description").toString();
                        productOtherDetails = documentSnapshot.get("product_other_details").toString();

                        for (long x = 1; x < (long) documentSnapshot.get("total_spec_titles") + 1; x++) {
                            productSpecificationModelList.add(new ProductSpecificationModel(0, documentSnapshot.get("spec_title_" + x).toString()));

                            for (long y = 1; y < (long) documentSnapshot.get("spec_title_" + x + "_total_fields") + 1; y++) {
                                productSpecificationModelList.add(new ProductSpecificationModel(1, documentSnapshot.get("spec_title_" + x + "_field_" + y + "_name").toString(), documentSnapshot.get("spec_title_" + x + "_field_" + y + "_value").toString()));
                            }
                        }

                    } else {
                        productDetailsTabsContainer.setVisibility(View.GONE);
                        productDetailsOnlyContainer.setVisibility(View.VISIBLE);
                        productOnlyDescriptionBody.setText(documentSnapshot.get("product_description").toString());
                    }
                    productDetailsViewPager.setAdapter(new ProductDetailsAdapter(getSupportFragmentManager(), productDetailsTablayout.getTabCount(), productDescription, productOtherDetails, productSpecificationModelList));

                    if (currentUser != null) {
                        if (DBqueries.cartList.size() == 0) {
                            DBqueries.loadCartList(ProductDetailsActivity.this, loadingDialog, false, badgeCount, new TextView(ProductDetailsActivity.this));
                        }
                        if (DBqueries.wishList.size() == 0) {
                            DBqueries.loadWishlist(ProductDetailsActivity.this, loadingDialog, false);
                        } else {
                            loadingDialog.dismiss();
                        }
                    } else {
                        loadingDialog.dismiss();
                    }
                    if (DBqueries.cartList.contains(productId)) {
                        ALREADY_ADDED_TO_CART = true;

                    } else {
                        ALREADY_ADDED_TO_CART = false;
                    }

                    if (DBqueries.wishList.contains(productId)) {
                        ALREADY_ADDED_TO_WISHLIST = true;
                        addToWishlistBtn.setSupportImageTintList(getResources().getColorStateList(R.color.colorPrimary));
                    } else {
                        addToWishlistBtn.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#9e9e9e")));
                        ALREADY_ADDED_TO_WISHLIST = false;
                    }
                    if ((boolean) documentSnapshot.get("in_stock")) {
                        addToCartBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (currentUser == null) {
                                    signInDialog.show();
                                } else {

                                    if (!running_cart_query) {
                                        running_cart_query = true;

                                        if (ALREADY_ADDED_TO_CART) {
                                            running_cart_query = false;
                                            Toast.makeText(ProductDetailsActivity.this, "ALready added to cart!", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Map<String, Object> addProduct = new HashMap<>();
                                            addProduct.put("product_ID_" + String.valueOf(DBqueries.cartList.size()), productId);
                                            addProduct.put("list_size", (long) (DBqueries.cartList.size() + 1));

                                            firebaseFirestore.collection("USERS").document(currentUser.getUid()).collection("USER_DATA").document("MY_CART")
                                                    .update(addProduct).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        if (DBqueries.cartItemModelList.size() != 0) {
                                                            DBqueries.cartItemModelList.add(0, new CartItemModel(CartItemModel.CART_ITEM, productId
                                                                    , documentSnapshot.get("product_image_1").toString()
                                                                    , documentSnapshot.get("product_title").toString()
                                                                    , documentSnapshot.get("product_price").toString()
                                                                    , documentSnapshot.get("cutted_price").toString()
                                                                    , (long) 0
                                                                    , (long) 1
                                                                    , (boolean) documentSnapshot.get("in_stock")
                                                                    , (long)documentSnapshot.get("max-quantity")));
                                                        }

                                                        ALREADY_ADDED_TO_CART = true;
                                                        DBqueries.cartList.add(productId);
                                                        Toast.makeText(ProductDetailsActivity.this, "Added to Wishlist successfully", Toast.LENGTH_SHORT).show();
                                                        invalidateOptionsMenu();

                                                        //addToWishlistBtn.setEnabled(true);
                                                        running_cart_query = false;

                                                    } else {
                                                        //addToWishlistBtn.setEnabled(true);
                                                        running_cart_query = false;
                                                        String error = task.getException().getMessage();
                                                        Toast.makeText(ProductDetailsActivity.this, error, Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });
                                        }
                                    }
                                }
                            }
                        });


                    } else {
                        buyNowBtn.setVisibility(View.GONE);
                        TextView outOfStock = (TextView) addToCartBtn.getChildAt(0);
                        outOfStock.setText("Out of Stock");
                        outOfStock.setTextColor(getResources().getColor(R.color.colorPrimary));
                        outOfStock.setCompoundDrawables(null, null, null, null);
                    }
                } else {
                    loadingDialog.dismiss();
                    String error = task.getException().getMessage();
                    Toast.makeText(ProductDetailsActivity.this, error, Toast.LENGTH_SHORT).show();
                }
            }
        });


        viewpagerIndicator.setupWithViewPager(productImagesViewPager, true);

        addToWishlistBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentUser == null) {
                    signInDialog.show();
                } else {
                    //addToWishlistBtn.setEnabled(false);
                    if (!running_wishlist_query) {
                        running_wishlist_query = true;

                        if (ALREADY_ADDED_TO_WISHLIST) {
                            int index = DBqueries.wishList.indexOf(productId);
                            DBqueries.removeFromWishlist(index, ProductDetailsActivity.this);
                            //ALREADY_ADDED_TO_WISHLIST = false;
                            addToWishlistBtn.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#9e9e9e")));
                        } else {
                            addToWishlistBtn.setSupportImageTintList(getResources().getColorStateList(R.color.colorPrimary));
                            Map<String, Object> addProduct = new HashMap<>();
                            addProduct.put("product_ID_" + String.valueOf(DBqueries.wishList.size()), productId);
                            addProduct.put("list_size", (long) (DBqueries.wishList.size() + 1));

                            firebaseFirestore.collection("USERS").document(currentUser.getUid()).collection("USER_DATA").document("MY_WISHLIST")
                                    .update(addProduct).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {

                                        if (DBqueries.wishlistModelList.size() != 0) {
                                            DBqueries.wishlistModelList.add(new WishlistModel(productId, documentSnapshot.get("product_image_1").toString()
                                                    , documentSnapshot.get("product_title").toString()
                                                    , documentSnapshot.get("product_price").toString()
                                                    , documentSnapshot.get("cutted_price").toString()
                                                    , (boolean) documentSnapshot.get("COD")));
                                        }

                                        ALREADY_ADDED_TO_WISHLIST = true;
                                        addToWishlistBtn.setSupportImageTintList(getResources().getColorStateList(R.color.colorPrimary));

                                        DBqueries.wishList.add(productId);
                                        Toast.makeText(ProductDetailsActivity.this, "Added to Wishlist successfully", Toast.LENGTH_SHORT).show();


                                    } else {
                                        addToWishlistBtn.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#9e9e9e")));
                                        //addToWishlistBtn.setEnabled(true);

                                        String error = task.getException().getMessage();
                                        Toast.makeText(ProductDetailsActivity.this, error, Toast.LENGTH_SHORT).show();
                                    }
                                    running_wishlist_query = false;
                                }
                            });
                        }
                    }
                }

            }
        });


        productDetailsViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(productDetailsTablayout));
        productDetailsTablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                productDetailsViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        buyNowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadingDialog.show();
                if (currentUser == null) {
                    signInDialog.show();
                } else {
                    DeliveryActivity.fromCart = false;
                    loadingDialog.show();
                    
                    DeliveryActivity.cartItemModelList = new ArrayList<>();

                    DeliveryActivity.cartItemModelList.add(new CartItemModel(CartItemModel.CART_ITEM, productId
                            , documentSnapshot.get("product_image_1").toString()
                            , documentSnapshot.get("product_title").toString()
                            , documentSnapshot.get("product_price").toString()
                            , documentSnapshot.get("cutted_price").toString()
                            , (long) 0
                            , (long) 1
                            , (boolean) documentSnapshot.get("in_stock")
                            , (long)documentSnapshot.get("max-quantity")));

                    DeliveryActivity.cartItemModelList.add(new CartItemModel(CartItemModel.TOTOAL_AMOUNT));
                    if (DBqueries.addressModelList.size() == 0) {
                        DBqueries.loadAddresses(ProductDetailsActivity.this, loadingDialog);
                    } else {
                        loadingDialog.dismiss();
                        Intent deliveryIntent = new Intent(ProductDetailsActivity.this, DeliveryActivity.class);
                        startActivity(deliveryIntent);
                    }
                }

            }
        });


        /// sign in dialog
        signInDialog = new Dialog((ProductDetailsActivity.this));
        signInDialog.setContentView(R.layout.sign_in_dialog);
        signInDialog.setCancelable(true);
        signInDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        Button dialogSignInBtn = signInDialog.findViewById(R.id.sign_in_btn);
        Button dialogSignUpBtn = signInDialog.findViewById(R.id.sign_up_btn);

        Intent registerIntent = new Intent(ProductDetailsActivity.this, RegisterActivity.class);

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

        //// Sign In dialog

    }

    @Override
    protected void onStart() {
        super.onStart();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if (currentUser != null) {
/*            if (DBqueries.cartList.size() == 0) {
                DBqueries.loadCartList(ProductDetailsActivity.this, loadingDialog, false);
            }
*/
            if (DBqueries.wishList.size() == 0) {
                DBqueries.loadWishlist(ProductDetailsActivity.this, loadingDialog, false);
            } else {
                loadingDialog.dismiss();
            }
        } else {
            loadingDialog.dismiss();
        }

        if (DBqueries.cartList.contains(productId)) {
            ALREADY_ADDED_TO_CART = true;

        } else {
            ALREADY_ADDED_TO_CART = false;
        }

        if (DBqueries.wishList.contains(productId)) {
            ALREADY_ADDED_TO_WISHLIST = true;
            addToWishlistBtn.setSupportImageTintList(getResources().getColorStateList(R.color.colorPrimary));
        } else {
            addToWishlistBtn.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#9e9e9e")));
            ALREADY_ADDED_TO_WISHLIST = false;
        }

        invalidateOptionsMenu();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search_and_cart_icon, menu);
        cartItem = menu.findItem(R.id.mainCartIcon);
        cartItem.setActionView(R.layout.badge_layout);
        ImageView badgeicon = cartItem.getActionView().findViewById(R.id.badge_icon);
        badgeicon.setImageResource(R.mipmap.cart_white);
        badgeCount = cartItem.getActionView().findViewById(R.id.badge_count);

        if (currentUser != null) {
            if (DBqueries.cartList.size() == 0) {
                DBqueries.loadCartList(ProductDetailsActivity.this, loadingDialog, false, badgeCount, new TextView(ProductDetailsActivity.this));
            } else {
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
                if (currentUser == null) {
                    signInDialog.show();
                } else {
                    Intent cartIntent = new Intent(ProductDetailsActivity.this, MainActivity.class);
                    showCart = true;
                    startActivity(cartIntent);
                }
            }
        });
        return true;
    }


    public boolean onOptionsItemsSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
            return true;
        } else if (id == R.id.mainSearchIcon) {
            return true;
        } else if (id == R.id.mainCartIcon) {
            if (currentUser == null) {
                signInDialog.show();
            } else {
                Intent cartIntent = new Intent(ProductDetailsActivity.this, MainActivity.class);
                showCart = true;
                startActivity(cartIntent);
                return true;
            }

        }

        return super.onOptionsItemSelected(item);
    }
}