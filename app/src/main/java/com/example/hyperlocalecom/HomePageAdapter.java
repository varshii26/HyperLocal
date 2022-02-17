package com.example.hyperlocalecom;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HomePageAdapter extends RecyclerView.Adapter {
    private  int type;
///cmnt
    private List <HomePageModel> homePageModelList;

    public HomePageAdapter(List<HomePageModel> homePageModelList) {
        this.homePageModelList = homePageModelList;
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
        }
        private void setHorizontalProductLayout(List <HorizontalProductScrollModel> horizontalProductScrollModelList,String title){

            horizontalLayoutTitle.setText(title);
            if(horizontalProductScrollModelList.size()>8){
                horizontalViewAllBtn.setVisibility(View.VISIBLE);
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
        private GridView gridView;
        public GridProductViewHolder(@NonNull View itemView) {
            super(itemView);
            gridLayoutTitle = itemView.findViewById(R.id.grid_product_layout_title);
             gridLayoutBtn = itemView.findViewById(R.id.grid_product_layout_viewAllBtn);
             gridView = itemView.findViewById(R.id.grid_product_layout_gridView);
        }

        private void setGridProductLayout(List <HorizontalProductScrollModel> horizontalProductScrollModelList,String title){
            gridLayoutTitle.setText(title);
            gridView.setAdapter(new GridProductLayoutAdapter(horizontalProductScrollModelList));
        }
    }

    //////////////Grid Product Layout

}
