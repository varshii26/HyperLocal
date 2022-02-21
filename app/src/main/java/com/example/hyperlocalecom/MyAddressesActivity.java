package com.example.hyperlocalecom;

import static com.example.hyperlocalecom.DeliveryActivity.SELECT_ADDRESS;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;


public class MyAddressesActivity extends AppCompatActivity {
    private RecyclerView myAddressesRecyclerView;
    private Button deliverHereBtn;
    private static AddressAdapter addressAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_addresses);

        Toolbar toolbar= (Toolbar) findViewById((R.id.toolbar));
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("My Addresses");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        myAddressesRecyclerView = findViewById(R.id.addresses_recyclerview);
        deliverHereBtn = findViewById(R.id.deliver_here_btn);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        myAddressesRecyclerView.setLayoutManager(layoutManager);

        List<AddressModel> addressModelList = new ArrayList<>();
        addressModelList.add(new AddressModel("Varshita Jain","Pramukh Hills,Vapi","396191",true));
        addressModelList.add(new AddressModel("Vaidehi Gajjar","Surat","396100",false));
        addressModelList.add(new AddressModel("Varshita Jain","Pramukh Hills,Vapi","396100",false));
        addressModelList.add(new AddressModel("Vaidehi Gajjar","Surat","396191",false));
        addressModelList.add(new AddressModel("Varshita Jain","Pramukh Hills,Vapi","396191",false));
        addressModelList.add(new AddressModel("Vaidehi Gajjar","Surat","396100",false));
        addressModelList.add(new AddressModel("Varshita Jain","Pramukh Hills,Vapi","396100",false));
        addressModelList.add(new AddressModel("Vaidehi Gajjar","Surat","396191",false));

        int mode = getIntent().getIntExtra("MODE",-1);
        if(mode == SELECT_ADDRESS){
            deliverHereBtn.setVisibility(View.VISIBLE);
        }else{
            deliverHereBtn.setVisibility(View.GONE);
        }
         addressAdapter  = new AddressAdapter(addressModelList,mode);
        myAddressesRecyclerView.setAdapter(addressAdapter);
        ((SimpleItemAnimator)myAddressesRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        addressAdapter.notifyDataSetChanged();


    }

    public static void refreshItem(int deselect,int select){
        addressAdapter.notifyItemChanged(deselect);
        addressAdapter.notifyItemChanged(select);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}