package com.example.hyperlocalecom.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

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
    private List<CategoryModel> categoryModelList;
    private FirebaseFirestore firebaseFirestore;


    ///Horizontal Product Layout


    ///Horizontal Product Layout


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        categoryRecyclerView = view.findViewById(R.id.category_recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        categoryRecyclerView.setLayoutManager(layoutManager);
        categoryModelList = new ArrayList<CategoryModel>();
        categoryAdapter = new CategoryAdapter(categoryModelList);
        categoryRecyclerView.setAdapter(categoryAdapter);



        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("CATEGORIES").orderBy("index").get().
                addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                          @Override
                                          public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                              if (task.isSuccessful()) {
                                                  for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                                      categoryModelList.add(new CategoryModel(documentSnapshot.get("icon").toString(), documentSnapshot.get("categoryName").toString()));
                                                  }
                                                  categoryAdapter.notifyDataSetChanged();

                                              } else {
                                                  String error = task.getException().getMessage();
                                                  Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
                                              }

                                          }
                                      }

                );



        //final TextView textView = binding.textHome;
        //homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

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


        ///Grid Product Layout

        ///Grid Product Layout

        ////////////////////////
        homePageRecyclerView = view.findViewById(R.id.home_page_recyclerview);
        LinearLayoutManager homePageRecyclerViewLayoutManager = new LinearLayoutManager(getContext());
        homePageRecyclerViewLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        homePageRecyclerView.setLayoutManager(homePageRecyclerViewLayoutManager);
        List<HomePageModel> homePageModelList = new ArrayList<>();
//        homePageModelList.add(new HomePageModel(0, "Deals of the Day", horizontalProductScrollModelList));
//        homePageModelList.add(new HomePageModel(1, "Trending", horizontalProductScrollModelList));
//        homePageModelList.add(new HomePageModel(0, "Deals of the Day", horizontalProductScrollModelList));
//        homePageModelList.add(new HomePageModel(1, "Trending", horizontalProductScrollModelList));
        HomePageAdapter adapter = new HomePageAdapter(homePageModelList);
        homePageRecyclerView.setAdapter(adapter);

        firebaseFirestore.collection("CATEGORIES")
                .document("HOME")
                .collection("TOP_DEALS").orderBy("index").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {

                                if(((long)documentSnapshot.get("view_type"))==0){
                                    List<HorizontalProductScrollModel> horizontalProductScrollModelList = new ArrayList<>();
                                    long no_of_products = (long)documentSnapshot.get("no_of_products");
                                    for(long x=1;x < no_of_products+1;x++){
                                        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(documentSnapshot.get("product_ID_"+x).toString()
                                                ,documentSnapshot.get("product_image_"+x).toString()
                                                ,documentSnapshot.get("product_title_"+x).toString()
                                                ,documentSnapshot.get("product_subtitle_"+x).toString()
                                                ,documentSnapshot.get("product_price_"+x).toString()));
                                    }
                                     homePageModelList.add(new HomePageModel(0,documentSnapshot.get("layout_title").toString(),documentSnapshot.get("layout_background").toString(),horizontalProductScrollModelList));

                                }else if(((long)documentSnapshot.get("view_type"))==1){
                                    List<HorizontalProductScrollModel> gridLayoutModelList = new ArrayList<>();
                                    long no_of_products = (long)documentSnapshot.get("no_of_products");
                                    for(long x=1;x < no_of_products+1;x++){
                                        gridLayoutModelList.add(new HorizontalProductScrollModel(documentSnapshot.get("product_ID_"+x).toString()
                                                ,documentSnapshot.get("product_image_"+x).toString()
                                                ,documentSnapshot.get("product_title_"+x).toString()
                                                ,documentSnapshot.get("product_subtitle_"+x).toString()
                                                ,documentSnapshot.get("product_price_"+x).toString()));
                                    }
                                    homePageModelList.add(new HomePageModel(1,documentSnapshot.get("layout_title").toString(),documentSnapshot.get("layout_background").toString(),gridLayoutModelList));

                                }
                            }
                            adapter.notifyDataSetChanged();


                        } else {
                            String error = task.getException().getMessage();
                            Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
                        }

                    }
                });

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