package com.example.hyperlocalecom.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hyperlocalecom.CategoryAdapter;
import com.example.hyperlocalecom.CategoryModel;
import com.example.hyperlocalecom.GridProductLayoutAdapter;
import com.example.hyperlocalecom.HomePageAdapter;
import com.example.hyperlocalecom.HomePageModel;
import com.example.hyperlocalecom.HorizontalProductScrollAdapter;
import com.example.hyperlocalecom.HorizontalProductScrollModel;
import com.example.hyperlocalecom.R;
import com.example.hyperlocalecom.databinding.FragmentHomeBinding;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private RecyclerView categoryRecyclerView;
    private CategoryAdapter categoryAdapter;
    private  RecyclerView testing;


    ///Horizontal Product Layout


    ///Horizontal Product Layout


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View view = inflater.inflate(R.layout.fragment_home,container,false);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        categoryRecyclerView = view.findViewById(R.id.category_recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        categoryRecyclerView.setLayoutManager(layoutManager);

        final List<CategoryModel> categoryModelList = new ArrayList<CategoryModel>();
        categoryModelList.add(new CategoryModel("link","Home"));
        categoryModelList.add(new CategoryModel("link","Appliances"));
        categoryModelList.add(new CategoryModel("link","Furniture"));
        categoryModelList.add(new CategoryModel("link","Toys"));
        categoryModelList.add(new CategoryModel("link","Sports"));
        categoryModelList.add(new CategoryModel("link","Wall Art"));
        categoryModelList.add(new CategoryModel("link","Stationery"));
        categoryModelList.add(new CategoryModel("link","Grocery"));
        categoryModelList.add(new CategoryModel("link","Pharmaceuticals"));
        categoryModelList.add(new CategoryModel("link","Dairy"));

        categoryAdapter = new CategoryAdapter(categoryModelList);
        categoryRecyclerView.setAdapter(categoryAdapter);
        categoryAdapter.notifyDataSetChanged();


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
         testing = view.findViewById(R.id.home_page_recyclerview);
        LinearLayoutManager testingLayoutManager = new LinearLayoutManager(getContext());
        testingLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        testing.setLayoutManager(testingLayoutManager);

        List <HomePageModel> homePageModelList = new ArrayList<>();
        homePageModelList.add(new HomePageModel(0,"Deals of the Day",horizontalProductScrollModelList));
        homePageModelList.add(new HomePageModel(1,"Trending",horizontalProductScrollModelList));
        homePageModelList.add(new HomePageModel(1,"Deals of the Day",horizontalProductScrollModelList));
        homePageModelList.add(new HomePageModel(0,"Trending",horizontalProductScrollModelList));


        HomePageAdapter adapter = new HomePageAdapter(homePageModelList);
        testing.setAdapter(adapter);
        adapter.notifyDataSetChanged();




        ///////////////////////
        return view;
        //
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}