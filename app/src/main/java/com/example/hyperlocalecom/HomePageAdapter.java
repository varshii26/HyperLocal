package com.example.hyperlocalecom;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.gridlayout.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

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
                String color = homePageModelList.get(position).getBackgroundColor();
                String horizontalLayoutTitle= homePageModelList.get(position).getTitle();
                List<WishlistModel> viewAllProductList = homePageModelList.get(position).getViewAllProductList();
                List <HorizontalProductScrollModel> horizontalProductScrollModelList = homePageModelList.get(position).getHorizontalProductScrollModelList();
                ((HorizontalProductViewHolder)viewHolder).setHorizontalProductLayout(horizontalProductScrollModelList,horizontalLayoutTitle,color,viewAllProductList);
                break;
            case HomePageModel.GRID_PRODUCT_VIEW:
                String gridcolor = homePageModelList.get(position).getBackgroundColor();
                String gridLayoutTitle= homePageModelList.get(position).getTitle();
                List <HorizontalProductScrollModel> gridProductScrollModelList = homePageModelList.get(position).getHorizontalProductScrollModelList();
                ((GridProductViewHolder)viewHolder).setGridProductLayout(gridProductScrollModelList,gridLayoutTitle,gridcolor);
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
        private ConstraintLayout container;
        public HorizontalProductViewHolder(@NonNull View itemView) {
            super(itemView);
            container = itemView.findViewById(R.id.container);
            horizontalLayoutTitle= itemView.findViewById(R.id.horizontal_scroll_layout_title);
            horizontalViewAllBtn=itemView.findViewById(R.id.horizontal_scroll_view_btn);
            horizontalRecyclerView=itemView.findViewById(R.id.horizontal_scroll_layout_recyclerview);
            horizontalRecyclerView.setRecycledViewPool(recycledViewPool);
        }
        private void setHorizontalProductLayout(List <HorizontalProductScrollModel> horizontalProductScrollModelList,String title,String color,List<WishlistModel> viewAllProductList){
            container.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(color)));

            horizontalLayoutTitle.setText(title);
            if(horizontalProductScrollModelList.size()>8){
                horizontalViewAllBtn.setVisibility(View.VISIBLE);
                horizontalViewAllBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ViewAllActivity.wishlistModelList = viewAllProductList;
                        Intent viewAllIntent = new Intent(itemView.getContext(),ViewAllActivity.class);
                        viewAllIntent.putExtra("layout_code",0);
                        viewAllIntent.putExtra("title",title);
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
        private ConstraintLayout container_grid;
        private TextView gridLayoutTitle;
        private Button gridLayoutBtn;
        private GridLayout gridProductLayout;

        public GridProductViewHolder(@NonNull View itemView) {
            super(itemView);
            container_grid = itemView.findViewById(R.id.container_grid);
            gridLayoutTitle = itemView.findViewById(R.id.grid_product_layout_title);
            gridLayoutBtn = itemView.findViewById(R.id.grid_product_layout_viewAllBtn);
            gridProductLayout = itemView.findViewById(R.id.grid_layout);
        }

        private void setGridProductLayout(List <HorizontalProductScrollModel> horizontalProductScrollModelList,String title,String color){
            container_grid.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(color)));
            gridLayoutTitle.setText(title);

            for (int x = 0; x < 4; x++){
                ImageView productImage = gridProductLayout.getChildAt(x).findViewById(R.id.h_s_product_image);
                TextView productTitle = gridProductLayout.getChildAt(x).findViewById(R.id.h_s_product_title);
                TextView productDiscription = gridProductLayout.getChildAt(x).findViewById(R.id.h_s_product_description);
                TextView productPrice = gridProductLayout.getChildAt(x).findViewById(R.id.h_s_product_price);

               Glide.with(itemView.getContext()).load(horizontalProductScrollModelList.get(x).getProductImage()).apply(new RequestOptions().placeholder(R.mipmap.home_icon_final)).into(productImage);
                productTitle.setText(horizontalProductScrollModelList.get(x).getProductTitle());
                productDiscription.setText(horizontalProductScrollModelList.get(x).getProductDescription());
                productPrice.setText("Rs."+horizontalProductScrollModelList.get(x).getProductPrice()+"/-");

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
                    ViewAllActivity.horizontalProductScrollModelList = horizontalProductScrollModelList;
                    Intent viewAllIntent = new Intent(itemView.getContext(),ViewAllActivity.class);
                    viewAllIntent.putExtra("layout_code",1);
                    viewAllIntent.putExtra("title",title);
                    itemView.getContext().startActivity(viewAllIntent);

                }
            });
        }
    }

    //////////////Grid Product Layout

}
