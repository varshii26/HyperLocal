package com.example.hyperlocalecom;

import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.gridlayout.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HomePageAdapter extends RecyclerView.Adapter {
    private  int type;

    private List <HomePageModel> homePageModelList;
    private RecyclerView.RecycledViewPool recycledViewPool;

    public HomePageAdapter(List<HomePageModel> homePageModelList) {
        this.homePageModelList = homePageModelList;
        recycledViewPool = new RecyclerView.RecycledViewPool();
    }

    @Override
    public int getItemViewType(int position) {
        switch (homePageModelList.get(position).getType()){
            case 0:
                return HomePageModel.HORIZONTAL_PRODUCT_VIEW;
            case 1:
                return HomePageModel.GRID_PRODUCT_VIEW;
            default:
                return -1;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        switch(viewType){
            case HomePageModel.HORIZONTAL_PRODUCT_VIEW:
                View horizontalProductView= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.horizontal_item_layout,viewGroup,false);
                return new HorizontalProductViewHolder(horizontalProductView);
            case HomePageModel.GRID_PRODUCT_VIEW:
                View gridProductView= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.grid_product_layout,viewGroup,false);
                return new GridProductViewHolder(gridProductView);


            default:
                return null;

        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {

        switch(homePageModelList.get(position).getType()){
            case HomePageModel.HORIZONTAL_PRODUCT_VIEW:
                String horizontalLayoutTitle= homePageModelList.get(position).getTitle();
                List <HorizontalProductScrollModel> horizontalProductScrollModelList = homePageModelList.get(position).getHorizontalProductScrollModelList();
                ((HorizontalProductViewHolder)viewHolder).setHorizontalProductLayout(horizontalProductScrollModelList,horizontalLayoutTitle);
                break;
            case HomePageModel.GRID_PRODUCT_VIEW:
                String gridLayoutTitle= homePageModelList.get(position).getTitle();
                List <HorizontalProductScrollModel> gridProductScrollModelList = homePageModelList.get(position).getHorizontalProductScrollModelList();
                ((GridProductViewHolder)viewHolder).setGridProductLayout(gridProductScrollModelList,gridLayoutTitle);
                break;
            default:
                return;



        }



    }

    @Override
    public int getItemCount() {
        return homePageModelList.size();
    }


    ////////////Horizontal product layout

    public class HorizontalProductViewHolder extends RecyclerView.ViewHolder{

        private TextView horizontalLayoutTitle;
        private Button horizontalViewAllBtn;
        private RecyclerView horizontalRecyclerView;
        public HorizontalProductViewHolder(@NonNull View itemView) {
            super(itemView);

            horizontalLayoutTitle= itemView.findViewById(R.id.horizontal_scroll_layout_title);
            horizontalViewAllBtn=itemView.findViewById(R.id.horizontal_scroll_view_btn);
            horizontalRecyclerView=itemView.findViewById(R.id.horizontal_scroll_layout_recyclerview);
            horizontalRecyclerView.setRecycledViewPool(recycledViewPool);
        }
        private void setHorizontalProductLayout(List <HorizontalProductScrollModel> horizontalProductScrollModelList,String title){

            horizontalLayoutTitle.setText(title);
            if(horizontalProductScrollModelList.size()>8){
                horizontalViewAllBtn.setVisibility(View.VISIBLE);
                horizontalViewAllBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent viewAllIntent = new Intent(itemView.getContext(),ViewAllActivity.class);
                        viewAllIntent.putExtra("layout_code",0);
                        itemView.getContext().startActivity(viewAllIntent);
                    }
                });
            }else{
                horizontalViewAllBtn.setVisibility(View.INVISIBLE);
            }
            HorizontalProductScrollAdapter horizontalProductScrollAdapter = new HorizontalProductScrollAdapter(horizontalProductScrollModelList);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(itemView.getContext());
            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            horizontalRecyclerView.setLayoutManager(linearLayoutManager);

            horizontalRecyclerView.setAdapter(horizontalProductScrollAdapter);
            horizontalProductScrollAdapter.notifyDataSetChanged();
        }
    }

    ////////////Horizontal product layout


    //////////////Grid Product Layout

    public class GridProductViewHolder extends RecyclerView.ViewHolder{

        private TextView gridLayoutTitle;
        private Button gridLayoutBtn;
        private GridLayout gridProductLayout;

        public GridProductViewHolder(@NonNull View itemView) {
            super(itemView);
            gridLayoutTitle = itemView.findViewById(R.id.grid_product_layout_title);
            gridLayoutBtn = itemView.findViewById(R.id.grid_product_layout_viewAllBtn);
            gridProductLayout = itemView.findViewById(R.id.grid_layout);
        }

        private void setGridProductLayout(List <HorizontalProductScrollModel> horizontalProductScrollModelList,String title){
            gridLayoutTitle.setText(title);

            for (int x = 0; x < 4; x++){
                ImageView productImage = gridProductLayout.getChildAt(x).findViewById(R.id.h_s_product_image);
                TextView productTitle = gridProductLayout.getChildAt(x).findViewById(R.id.h_s_product_title);
                TextView productDiscription = gridProductLayout.getChildAt(x).findViewById(R.id.h_s_product_description);
                TextView productPrice = gridProductLayout.getChildAt(x).findViewById(R.id.h_s_product_price);

                productImage.setImageResource(horizontalProductScrollModelList.get(x).getProductImage());
                productTitle.setText(horizontalProductScrollModelList.get(x).getProductTitle());
                productDiscription.setText(horizontalProductScrollModelList.get(x).getProductDescription());
                productPrice.setText(horizontalProductScrollModelList.get(x).getProductPrice());

                gridProductLayout.getChildAt(x).setBackgroundColor(Color.parseColor("#ffffff"));

                gridProductLayout.getChildAt(x).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent productDetailsIntent = new Intent(itemView.getContext(),ProductDetailsActivity.class);
                        itemView.getContext().startActivity(productDetailsIntent);
                    }
                });
            }

            gridLayoutBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent viewAllIntent = new Intent(itemView.getContext(),ViewAllActivity.class);
                    viewAllIntent.putExtra("layout_code",1);
                    itemView.getContext().startActivity(viewAllIntent);

                }
            });
        }
    }

    //////////////Grid Product Layout

}
