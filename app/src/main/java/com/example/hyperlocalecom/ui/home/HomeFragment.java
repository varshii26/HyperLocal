package com.example.hyperlocalecom.ui.home;

import static com.example.hyperlocalecom.DBqueries.categoryModelList;
import static com.example.hyperlocalecom.DBqueries.firebaseFirestore;

import static com.example.hyperlocalecom.DBqueries.lists;
import static com.example.hyperlocalecom.DBqueries.loadCategories;
import static com.example.hyperlocalecom.DBqueries.loadFragmentData;
import static com.example.hyperlocalecom.DBqueries.loadedCategoriesNames;

import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.hyperlocalecom.CategoryAdapter;
import com.example.hyperlocalecom.CategoryModel;
import com.example.hyperlocalecom.GridProductLayoutAdapter;
import com.example.hyperlocalecom.HomePageAdapter;
import com.example.hyperlocalecom.HomePageModel;
import com.example.hyperlocalecom.HorizontalProductScrollAdapter;
import com.example.hyperlocalecom.HorizontalProductScrollModel;
import com.example.hyperlocalecom.R;
import com.example.hyperlocalecom.databinding.FragmentHomeBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private RecyclerView categoryRecyclerView;
    private CategoryAdapter categoryAdapter;
    private RecyclerView homePageRecyclerView;
    private ImageView noInternetConnection;
    private HomePageAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        noInternetConnection = view.findViewById(R.id.no_internet_connection);

        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if(networkInfo!=null && networkInfo.isConnected() == true){
            noInternetConnection.setVisibility(View.GONE);

            categoryRecyclerView = view.findViewById(R.id.category_recyclerview);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            categoryRecyclerView.setLayoutManager(layoutManager);
            categoryAdapter = new CategoryAdapter(categoryModelList);
            categoryRecyclerView.setAdapter(categoryAdapter);

            if(categoryModelList.size()==0){
                loadCategories(categoryAdapter,getContext());
            }else{
                categoryAdapter.notifyDataSetChanged();
            }

            homePageRecyclerView = view.findViewById(R.id.home_page_recyclerview);
            LinearLayoutManager homePageRecyclerViewLayoutManager = new LinearLayoutManager(getContext());
            homePageRecyclerViewLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            homePageRecyclerView.setLayoutManager(homePageRecyclerViewLayoutManager);



            if(lists.size()==0){
                loadedCategoriesNames.add("HOME");
                lists.add(new ArrayList<HomePageModel>());
                 adapter = new HomePageAdapter(lists.get(0));

                loadFragmentData(adapter,getContext(),0,"Home");
            }else{
                 adapter = new HomePageAdapter(lists.get(0));
                categoryAdapter.notifyDataSetChanged();
            }

            homePageRecyclerView.setAdapter(adapter);

        }else{
            Glide.with(this).load(R.drawable.no_internet_connection).into(noInternetConnection);
            noInternetConnection.setVisibility(View.VISIBLE);

        }

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        ///Horizontal Product Layout

//        List<HorizontalProductScrollModel> horizontalProductScrollModelList = new ArrayList<>();
//        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.assorted_pocket_diary_p4, "Assorted Pocket Diary", "Set of 4", "RS.259"));
//        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.assorted_pocket_diary_p4, "Assorted Pocket Diary", "Set of 4", "RS.259"));
//        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.assorted_pocket_diary_p4, "Assorted Pocket Diary", "Set of 4", "RS.259"));
//        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.assorted_pocket_diary_p4, "Assorted Pocket Diary", "Set of 4", "RS.259"));
//        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.assorted_pocket_diary_p4, "Assorted Pocket Diary", "Set of 4", "RS.259"));
//        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.assorted_pocket_diary_p4, "Assorted Pocket Diary", "Set of 4", "RS.259"));
//        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.assorted_pocket_diary_p4, "Assorted Pocket Diary", "Set of 4", "RS.259"));
//        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.assorted_pocket_diary_p4, "Assorted Pocket Diary", "Set of 4", "RS.259"));
//        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.assorted_pocket_diary_p4, "Assorted Pocket Diary", "Set of 4", "RS.259"));

        ///Horizontal Product Layout

        //        homePageModelList.add(new HomePageModel(0, "Deals of the Day", horizontalProductScrollModelList));
//        homePageModelList.add(new HomePageModel(1, "Trending", horizontalProductScrollModelList));
//        homePageModelList.add(new HomePageModel(0, "Deals of the Day", horizontalProductScrollModelList));
//        homePageModelList.add(new HomePageModel(1, "Trending", horizontalProductScrollModelList));

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}