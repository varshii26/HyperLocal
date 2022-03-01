package com.example.hyperlocalecom;

import android.app.Dialog;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.widget.Toast;

import android.graphics.Color;
import android.content.res.ColorStateList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.hyperlocalecom.ui.home.HomeFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBqueries {


    public static FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    public static List<CategoryModel> categoryModelList = new ArrayList<>();

    public static List<List<HomePageModel>> lists = new ArrayList<>();
    public static List<String> loadedCategoriesNames = new ArrayList<>();

    public static List<String> wishList = new ArrayList<>();
    public static List<WishlistModel> wishlistModelList = new ArrayList<>();


    public static void loadCategories(RecyclerView categoryRecyclerView, final Context context) {

        firebaseFirestore.collection("CATEGORIES").orderBy("index").get().
                addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                          @Override
                                          public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                              if (task.isSuccessful()) {
                                                  for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                                      categoryModelList.add(new CategoryModel(documentSnapshot.get("icon").toString(), documentSnapshot.get("categoryName").toString()));
                                                  }
                                                  CategoryAdapter categoryAdapter = new CategoryAdapter(categoryModelList);
                                                  categoryRecyclerView.setAdapter(categoryAdapter);
                                                  categoryAdapter.notifyDataSetChanged();
                                              } else {
                                                  String error = task.getException().getMessage();
                                                  Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                                              }
                                          }
                                      }
                );
    }

    public static void loadFragmentData(final RecyclerView homePageRecyclerView, final Context context, final int index, String categoryName) {
        firebaseFirestore.collection("CATEGORIES")
                .document(categoryName.toUpperCase())
                .collection("TOP_DEALS").orderBy("index").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {

                                if (((long) documentSnapshot.get("view_type")) == 0) {


                                    List<WishlistModel> viewAllProductList = new ArrayList<>();
                                    List<HorizontalProductScrollModel> horizontalProductScrollModelList = new ArrayList<>();
                                    long no_of_products = (long) documentSnapshot.get("no_of_products");
                                    for (long x = 1; x < no_of_products + 1; x++) {
                                        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(documentSnapshot.get("product_ID_" + x).toString()
                                                , documentSnapshot.get("product_image_" + x).toString()
                                                , documentSnapshot.get("product_title_" + x).toString()
                                                , documentSnapshot.get("product_subtitle_" + x).toString()
                                                , documentSnapshot.get("product_price_" + x).toString()));

                                        viewAllProductList.add(new WishlistModel(documentSnapshot.get("product_image_" + x).toString()
                                                , documentSnapshot.get("product_full_title_" + x).toString()
                                                , documentSnapshot.get("product_price_" + x).toString()
                                                , documentSnapshot.get("cutted_price_" + x).toString()
                                                , (boolean) documentSnapshot.get("COD_" + x)));
                                    }
                                    lists.get(index).add(new HomePageModel(0, documentSnapshot.get("layout_title").toString(), documentSnapshot.get("layout_background").toString(), horizontalProductScrollModelList, viewAllProductList));

                                } else if (((long) documentSnapshot.get("view_type")) == 1) {
                                    List<HorizontalProductScrollModel> gridLayoutModelList = new ArrayList<>();
                                    long no_of_products = (long) documentSnapshot.get("no_of_products");
                                    for (long x = 1; x < no_of_products + 1; x++) {
                                        gridLayoutModelList.add(new HorizontalProductScrollModel(documentSnapshot.get("product_ID_" + x).toString()
                                                , documentSnapshot.get("product_image_" + x).toString()
                                                , documentSnapshot.get("product_title_" + x).toString()
                                                , documentSnapshot.get("product_subtitle_" + x).toString()
                                                , documentSnapshot.get("product_price_" + x).toString()));
                                    }
                                    lists.get(index).add(new HomePageModel(1, documentSnapshot.get("layout_title").toString(), documentSnapshot.get("layout_background").toString(), gridLayoutModelList));

                                }
                            }
                            HomePageAdapter homePageAdapter = new HomePageAdapter(lists.get(index));
                            homePageRecyclerView.setAdapter(homePageAdapter);
                            homePageAdapter.notifyDataSetChanged();
                            HomeFragment.swipeRefreshLayout.setRefreshing(false);
                        } else {
                            String error = task.getException().getMessage();
                            Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                        }

                    }
                });


    }

    public static void loadWishlist(final Context context, Dialog dialog,final boolean loadProductData  ) {
        firebaseFirestore.collection("USERS").document(FirebaseAuth.getInstance().getUid()).collection("USER_DATA").document("MY_WISHLIST")
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    for (long x = 0; x < (long) task.getResult().get("list_size"); x++) {
                        wishList.add(task.getResult().get("product_ID" + x).toString());

                        if (DBqueries.wishList.contains(ProductDetailsActivity.productId)) {
                            ProductDetailsActivity.ALREADY_ADDED_TO_WISHLIST = true;
                            if(ProductDetailsActivity.addToWishlistBtn != null) {
                                ProductDetailsActivity.addToWishlistBtn.setSupportImageTintList(context.getResources().getColorStateList(R.color.colorPrimary));
                            }
                        } else {
                            if(ProductDetailsActivity.addToWishlistBtn != null) {
                                ProductDetailsActivity.addToWishlistBtn.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#9e9e9e")));
                            }
                            ProductDetailsActivity.ALREADY_ADDED_TO_WISHLIST = false;
                        }
                        if(loadProductData) {
                            firebaseFirestore.collection("PRODUCTS").document(task.getResult().get("product_ID_" + x).toString())
                                    .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        wishlistModelList.add(new WishlistModel(task.getResult().get("product_image_1").toString()
                                                , task.getResult().get("product_title").toString()
                                                , task.getResult().get("product_price").toString()
                                                , task.getResult().get("cutted_price").toString()
                                                , (boolean) task.getResult().get("COD")));

                                        MyWishlistFragment.wishlistAdapter.notifyDataSetChanged();

                                    } else {
                                        String error = task.getException().getMessage();
                                        Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });
                        }

                    }

                } else {
                    String error = task.getException().getMessage();
                    Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                }

                dialog.dismiss();
            }
        });
    }

    public static void removeFromWishlist(int index, final Context context) {
        wishList.remove(index);
        Map<String,Object> updateWishlist = new HashMap<>();

        for (int x = 0; x < wishList.size(); x++) {
            updateWishlist.put("product_ID_"+x,wishList.get(x));
        }
        updateWishlist.put("list_size",(long)wishList.size());

        firebaseFirestore.collection("USERS").document(FirebaseAuth.getInstance().getUid()).collection("USER_DATA").document("MY_WISHLIST")
                .set(updateWishlist).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    if (wishlistModelList.size() != 0){
                        wishlistModelList.remove(index);
                        MyWishlistFragment.wishlistAdapter.notifyDataSetChanged();
                    }
                    ProductDetailsActivity.ALREADY_ADDED_TO_WISHLIST = false;
                    Toast.makeText(context,"Removed successfully! ",Toast.LENGTH_SHORT).show();
                }else {
                    if(ProductDetailsActivity.addToWishlistBtn != null) {
                        ProductDetailsActivity.addToWishlistBtn.setSupportImageTintList(context.getResources().getColorStateList(R.color.colorPrimary));
                    }
                    String error = task.getException().getMessage();
                    Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                }
                if(ProductDetailsActivity.addToWishlistBtn != null) {
                    ProductDetailsActivity.addToWishlistBtn.setEnabled(true);
                }
            }
        });

    }

    public static void clearData() {
        categoryModelList.clear();
        lists.clear();
        loadedCategoriesNames.clear();
        wishList.clear();
        wishlistModelList.clear();
    }
}
