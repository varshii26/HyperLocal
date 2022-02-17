package com.example.hyperlocalecom;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
// import android.widget.Toolbar;

import com.example.hyperlocalecom.databinding.ActivityCategoryBinding;
import com.example.hyperlocalecom.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class CategoryActivity extends AppCompatActivity {

    private ActivityCategoryBinding binding;
    private RecyclerView categoryRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        String title = getIntent().getStringExtra("CategoryName");
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        categoryRecyclerView = findViewById(R.id.category_recyclerview);




        //final TextView textView = binding.textHome;
        //homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        ///Horizontal Product Layout

        List<HorizontalProductScrollModel> horizontalProductScrollModelList= new ArrayList<>();
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.assorted_pocket_diary_p4,"Assorted Pocket Diary","Set of 4","RS.259"));
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.assorted_pocket_diary_p4,"Assorted Pocket Diary","Set of 4","RS.259"));
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.assorted_pocket_diary_p4,"Assorted Pocket Diary","Set of 4","RS.259"));
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.assorted_pocket_diary_p4,"Assorted Pocket Diary","Set of 4","RS.259"));
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.assorted_pocket_diary_p4,"Assorted Pocket Diary","Set of 4","RS.259"));
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.assorted_pocket_diary_p4,"Assorted Pocket Diary","Set of 4","RS.259"));
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.assorted_pocket_diary_p4,"Assorted Pocket Diary","Set of 4","RS.259"));
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.assorted_pocket_diary_p4,"Assorted Pocket Diary","Set of 4","RS.259"));



        ///Horizontal Product Layout


        ///Grid Product Layout

        ///Grid Product Layout

        ////////////////////////

        LinearLayoutManager testingLayoutManager = new LinearLayoutManager(this);
        testingLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        categoryRecyclerView.setLayoutManager(testingLayoutManager);

        List <HomePageModel> homePageModelList = new ArrayList<>();
        homePageModelList.add(new HomePageModel(0,"Deals of the Day",horizontalProductScrollModelList));
        homePageModelList.add(new HomePageModel(1,"Deals of the Day",horizontalProductScrollModelList));
        homePageModelList.add(new HomePageModel(1,"Deals of the Day",horizontalProductScrollModelList));
        homePageModelList.add(new HomePageModel(0,"Deals of the Day",horizontalProductScrollModelList));


        HomePageAdapter adapter = new HomePageAdapter(homePageModelList);
        categoryRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search_icon, menu);
        return true;
    }

    public boolean onOptionsItemsSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.mainSearchIcon) {
            return true;
        }else if(id== android.R.id.home){
            finish();
            return true;
        }



        return super.onOptionsItemSelected(item);
    }
}