package com.example.hyperlocalecom;

public class MyOrderItemModel {

    private int productImage;
    private String productTitle;
    private String deiveryStatus;


    public MyOrderItemModel(int productImage, String productTitle, String deiveryStatus) {
        this.productImage = productImage;
        this.productTitle = productTitle;
        this.deiveryStatus = deiveryStatus;
    }


    public int getProductImage() {
        return productImage;
    }

    public void setProductImage(int productImage) {
        this.productImage = productImage;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public String getDeiveryStatus() {
        return deiveryStatus;
    }

    public void setDeiveryStatus(String deiveryStatus) {
        this.deiveryStatus = deiveryStatus;
    }
}
