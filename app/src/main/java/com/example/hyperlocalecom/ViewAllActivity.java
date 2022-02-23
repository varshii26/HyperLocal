package com.example.hyperlocalecom;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActionBar;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

public class ViewAllActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private GridView gridView;
    public static List<HorizontalProductScrollModel> horizontalProductScrollModelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle(getIntent().getStringExtra("title"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recyclerView = findViewById(R.id.recycler_view);
        gridView = findViewById(R.id.grid_view);

        int layout_code = getIntent().getIntExtra("layout_code", -1);
        if (layout_code == 0) {
            gridView.setVisibility(View.INVISIBLE);
        recyclerView.setVisibility(View.VISIBLE);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        List<WishlistModel> wishlistModelList = new ArrayList<>();
        wishlistModelList.add(new WishlistModel(R.drawable.assorted_pocket_diary_p4, "Assorted Diary", "Rs.259/-", "Rs.299/-", "Cash on Delivery Available"));
        wishlistModelList.add(new WishlistModel(R.drawable.assorted_pocket_diary_p4, "Assorted Diary", "Rs.259/-", "Rs.299/-", "Cash on Delivery not Available"));
        wishlistModelList.add(new WishlistModel(R.drawable.assorted_pocket_diary_1, "Assorted Diary", "Rs.359/-", "Rs.419/-", "Cash on Delivery not Available"));
        wishlistModelList.add(new WishlistModel(R.drawable.assorted_pocket_diary_2, "Assorted Diary", "Rs.259/-", "Rs.299/-", "Cash on Delivery Available"));
        wishlistModelList.add(new WishlistModel(R.drawable.spiral_pocket_diary_p5, "Assorted Diary", "Rs.459/-", "Rs.499/-", "Cash on Delivery Available"));
        wishlistModelList.add(new WishlistModel(R.drawable.assorted_pocket_diary_p4, "Assorted Diary", "Rs.259/-", "Rs.299/-", "Cash on Delivery Available"));
        wishlistModelList.add(new WishlistModel(R.drawable.assorted_pocket_diary_p4, "Assorted Diary", "Rs.379/-", "Rs.399/-", "Cash on Delivery not Available"));
        wishlistModelList.add(new WishlistModel(R.drawable.spiral_pocket_diary_p5, "Assorted Diary", "Rs.259/-", "Rs.299/-", "Cash on Delivery Available"));
        wishlistModelList.add(new WishlistModel(R.drawable.assorted_pocket_diary_1, "Assorted Diary", "Rs.359/-", "Rs.419/-", "Cash on Delivery not Available"));
        wishlistModelList.add(new WishlistModel(R.drawable.assorted_pocket_diary_2, "Assorted Diary", "Rs.259/-", "Rs.299/-", "Cash on Delivery Available"));
        wishlistModelList.add(new WishlistModel(R.drawable.spiral_pocket_diary_p5, "Assorted Diary", "Rs.459/-", "Rs.499/-", "Cash on Delivery Available"));
        wishlistModelList.add(new WishlistModel(R.drawable.assorted_pocket_diary_p4, "Assorted Diary", "Rs.259/-", "Rs.299/-", "Cash on Delivery Available"));
        wishlistModelList.add(new WishlistModel(R.drawable.assorted_pocket_diary_p4, "Assorted Diary", "Rs.379/-", "Rs.399/-", "Cash on Delivery not Available"));
        wishlistModelList.add(new WishlistModel(R.drawable.spiral_pocket_diary_p5, "Assorted Diary", "Rs.259/-", "Rs.299/-", "Cash on Delivery Available"));



            WishlistAdapter wishlistAdapter = new WishlistAdapter(wishlistModelList, false);
        recyclerView.setAdapter(wishlistAdapter);
        wishlistAdapter.notifyDataSetChanged();
    }else if(layout_code==1 ){
            gridView.setVisibility(View.VISIBLE);
//            horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.assorted_pocket_diary_p4,"Assorted Pocket Diary","Set of 4","RS.259"));
//            horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.assorted_pocket_diary_p4,"Assorted Pocket Diary","Set of 4","RS.259"));
//            horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.assorted_pocket_diary_p4,"Assorted Pocket Diary","Set of 4","RS.259"));
//            horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.assorted_pocket_diary_p4,"Assorted Pocket Diary","Set of 4","RS.259"));
//            horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.assorted_pocket_diary_p4,"Assorted Pocket Diary","Set of 4","RS.259"));
//            horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.assorted_pocket_diary_p4,"Assorted Pocket Diary","Set of 4","RS.259"));
//            horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.assorted_pocket_diary_p4,"Assorted Pocket Diary","Set of 4","RS.259"));
//            horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.assorted_pocket_diary_p4,"Assorted Pocket Diary","Set of 4","RS.259"));
//            horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.assorted_pocket_diary_p4,"Assorted Pocket Diary","Set of 4","RS.259"));
//            horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.assorted_pocket_diary_p4,"Assorted Pocket Diary","Set of 4","RS.259"));
//            horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.assorted_pocket_diary_p4,"Assorted Pocket Diary","Set of 4","RS.259"));
//            horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.assorted_pocket_diary_p4,"Assorted Pocket Diary","Set of 4","RS.259"));
//            horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.assorted_pocket_diary_p4,"Assorted Pocket Diary","Set of 4","RS.259"));
//            horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.assorted_pocket_diary_p4,"Assorted Pocket Diary","Set of 4","RS.259"));
//            horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.assorted_pocket_diary_p4,"Assorted Pocket Diary","Set of 4","RS.259"));
//            horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.assorted_pocket_diary_p4,"Assorted Pocket Diary","Set of 4","RS.259"));
//            horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.assorted_pocket_diary_p4,"Assorted Pocket Diary","Set of 4","RS.259"));
//            horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.assorted_pocket_diary_p4,"Assorted Pocket Diary","Set of 4","RS.259"));
//            horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.assorted_pocket_diary_p4,"Assorted Pocket Diary","Set of 4","RS.259"));
//            horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.assorted_pocket_diary_p4,"Assorted Pocket Diary","Set of 4","RS.259"));

            GridProductLayoutAdapter gridProductLayoutAdapter = new GridProductLayoutAdapter(horizontalProductScrollModelList);
            gridView.setAdapter(gridProductLayoutAdapter);

        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
            return true ;
        }
        return super.onOptionsItemSelected(item);
    }
}