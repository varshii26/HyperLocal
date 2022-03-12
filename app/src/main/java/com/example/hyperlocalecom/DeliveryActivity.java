package com.example.hyperlocalecom;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class DeliveryActivity extends AppCompatActivity {
    public static List<CartItemModel> cartItemModelList;
    private RecyclerView deliveryRecyclerview;
    private Button changeORaddNewAddressBtn;
    public static final int SELECT_ADDRESS = 0;
    private TextView totalAmount;
    private TextView fullname;
    private String name,mobileNo;
    private TextView fullAddress;
    private TextView pincode;
    private Button continueBtn;
    private Dialog loadingDialog;
    private Dialog paymentMethodDialog;
    private ImageButton paytm,cod;
    public static boolean fromCart;
    private FirebaseFirestore firebaseFirestore;
    private boolean allProductsAvailable = true;
    public static  boolean getQtyIDs = true ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery);

        Toolbar toolbar = (Toolbar) findViewById((R.id.toolbar));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Delivery");

        deliveryRecyclerview = findViewById(R.id.delivery_recyclerview);
        changeORaddNewAddressBtn = findViewById(R.id.change_or_add_address_btn);
        totalAmount = findViewById(R.id.total_cart_amount);
        fullname = findViewById(R.id.fullname);
        fullAddress = findViewById(R.id.address);
        pincode = findViewById(R.id.pincode);
        continueBtn = findViewById(R.id.cart_continue_btn);

        ///////loading dialog
        loadingDialog = new Dialog(DeliveryActivity.this);
        loadingDialog.setContentView(R.layout.loading_progress_dialog);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.slider_background));
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        //////loading dialog

        ///////paymentMethodDialog
        paymentMethodDialog = new Dialog(DeliveryActivity.this);
        paymentMethodDialog.setContentView(R.layout.payment_method);
        paymentMethodDialog.setCancelable(true);
        paymentMethodDialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.slider_background));
        paymentMethodDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        cod = paymentMethodDialog.findViewById(R.id.cod_btn);
        //////paymentMethodDialog

        firebaseFirestore = FirebaseFirestore.getInstance();


        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        deliveryRecyclerview.setLayoutManager(layoutManager);

//        List<CartItemModel> cartItemModelList = new ArrayList<>();

        CartAdapter cartAdapter = new CartAdapter(cartItemModelList, totalAmount, false);
        deliveryRecyclerview.setAdapter(cartAdapter);
        cartAdapter.notifyDataSetChanged();

        changeORaddNewAddressBtn.setVisibility(View.VISIBLE);
        changeORaddNewAddressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getQtyIDs = false;
                Intent myAddressIntent = new Intent(DeliveryActivity.this, MyAddressesActivity.class);
                myAddressIntent.putExtra("MODE", SELECT_ADDRESS);
                startActivity(myAddressIntent);
            }
        });

        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(allProductsAvailable){
                    paymentMethodDialog.show();
                    paytm = paymentMethodDialog.findViewById(R.id.paytm);
                }else{

                }

            }
        });

        cod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getQtyIDs = false ;
                Intent optIntent = new Intent(DeliveryActivity.this,OTPverificationActivity.class);
                optIntent.putExtra("mobileNo",mobileNo.substring(0,10));
                startActivity(optIntent);
            }
        });

        paytm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getQtyIDs = false ;
                paymentMethodDialog.dismiss();
                loadingDialog.show();
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();

        if(getQtyIDs) {

            for (int x = 0; x < cartItemModelList.size() - 1; x++) {
                final int finalX = x;
                firebaseFirestore.collection("PRODUCTS").document(cartItemModelList.get(x).getProductId()).collection("QUANTITY").orderBy("available", Query.Direction.DESCENDING).limit(cartItemModelList.get(x).getProductQuantity()).get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (final QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()) {
                                        if ((boolean) queryDocumentSnapshot.get("available")) {


                                            firebaseFirestore.collection("PRODUCTS").document(cartItemModelList.get(finalX).getProductId()).collection("QUANTITY").document(queryDocumentSnapshot.getId()).update("available", false)
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if (task.isSuccessful()) {
                                                                cartItemModelList.get(finalX).getQtyIDs().add(queryDocumentSnapshot.getId());
                                                            } else {
                                                                String error = task.getException().getMessage();
                                                                Toast.makeText(DeliveryActivity.this, error, Toast.LENGTH_SHORT).show();
                                                            }
                                                        }
                                                    });
                                        } else {
                                            allProductsAvailable = false;
                                            Toast.makeText(DeliveryActivity.this, "All products may not be available at required quantity!", Toast.LENGTH_SHORT).show();
                                            break;
                                        }
                                    }
                                } else {
                                    String error = task.getException().getMessage();
                                    Toast.makeText(DeliveryActivity.this, error, Toast.LENGTH_SHORT).show();

                                }
                            }
                        });

            }
        }else{
            getQtyIDs = true ;
        }

        name = DBqueries.addressModelList.get(DBqueries.selectedAddress).getFullname();
        mobileNo = DBqueries.addressModelList.get(DBqueries.selectedAddress).getMobileNo();
        fullname.setText(name + " - " + mobileNo);
        fullAddress.setText(DBqueries.addressModelList.get(DBqueries.selectedAddress).getAddress());
        pincode.setText(DBqueries.addressModelList.get(DBqueries.selectedAddress).getPincode());
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
        loadingDialog.dismiss();

        if(getQtyIDs) {
            for (int x = 0; x < cartItemModelList.size() - 1; x++) {

                for (String qtyID : cartItemModelList.get(x).getQtyIDs()) {
                    firebaseFirestore.collection("PRODUCTS").document(cartItemModelList.get(x).getProductId()).collection("QUANTITY").document(qtyID).update("available", true);


                }

            }
        }
    }


}

