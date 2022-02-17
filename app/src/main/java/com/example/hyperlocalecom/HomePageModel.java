package com.example.hyperlocalecom;

import java.util.List;

public class HomePageModel  {
    public static final int HORIZONTAL_PRODUCT_VIEW = 0;
    public static final int GRID_PRODUCT_VIEW = 1;

    private int type;
    ////////////Horizontal product layout
    private String title;
    private List <HorizontalProductScrollModel> horizontalProductScrollModelList;

    public HomePageModel(int type, String title, List<HorizontalProductScrollModel> horizontalProductScrollModelList) {
        this.type = type;
        this.title = title;
        this.horizontalProductScrollModelList = horizontalProductScrollModelList;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public List<HorizontalProductScrollModel> getHorizontalProductScrollModelList() {
        return horizontalProductScrollModelList;
    }
    public void setHorizontalProductScrollModelList(List<HorizontalProductScrollModel> horizontalProductScrollModelList) {
        this.horizontalProductScrollModelList = horizontalProductScrollModelList;
    }

    public int getType() {
        return type;
    }

    ////////////Horizontal product layout
    
}
