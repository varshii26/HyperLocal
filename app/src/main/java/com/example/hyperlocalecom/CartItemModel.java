package com.example.hyperlocalecom;

public class CartItemModel {

    //CART ITEMS

    public static final int CART_ITEM = 0;
    public static final int TOTOAL_AMOUNT =1;

    private int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }



    ///////// cart item
    private String productId;
    private String productImage;
    private String productTitle;
    private String productPrice;
    private String cuttedPrice;
    private long offersApplied;
    private long productQuantity;

    public CartItemModel(int type,String productId, String productImage, String productTitle, String productPrice, String cuttedPrice, long offersApplied, long productQuantity) {
        this.type = type;
        this.productId = productId;
        this.productImage = productImage;
        this.productTitle = productTitle;
        this.productPrice = productPrice;
        this.cuttedPrice = cuttedPrice;
        this.offersApplied = offersApplied;
        this.productQuantity = productQuantity;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getCuttedPrice() {
        return cuttedPrice;
    }

    public void setCuttedPrice(String cuttedPrice) {
        this.cuttedPrice = cuttedPrice;
    }

    public long getOffersApplied() {
        return offersApplied;
    }

    public void setOffersApplied(long offersApplied) {
        this.offersApplied = offersApplied;
    }

    public long getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(long productQuantity) {
        this.productQuantity = productQuantity;
    }

    ///////// cart item

    ///////// cart total

    public CartItemModel(int type) {
        this.type = type;
    }


    ///////// cart total
}




//////////////////////////////////////////////////////
/*package com.example.hyperlocalecom;

public class CartItemModel {

    //CART ITEMS

    public static final int CART_ITEM = 0;
    public static final int TOTOAL_AMOUNT =1;

    public CartItemModel(int i, String s, String s1, String s2, String s3, String s4) {
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    private int type;

    ///////// cart item
    private int productImage;
    private String productTitle;
    private String productPrice;
    private String cuttedPrice;
    private int offersApplied;
    private int productQuantity;

    public CartItemModel(int type, int productImage, String productTitle, String productPrice, String cuttedPrice, int offersApplied, int productQuantity) {
        this.type = type;
        this.productImage = productImage;
        this.productTitle = productTitle;
        this.productPrice = productPrice;
        this.cuttedPrice = cuttedPrice;
        this.offersApplied = offersApplied;
        this.productQuantity = productQuantity;
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

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getCuttedPrice() {
        return cuttedPrice;
    }

    public void setCuttedPrice(String cuttedPrice) {
        this.cuttedPrice = cuttedPrice;
    }

    public int getOffersApplied() {
        return offersApplied;
    }

    public void setOffersApplied(int offersApplied) {
        this.offersApplied = offersApplied;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }

    ///////// cart item

    ///////// cart total

    private int totalItems;
    private String totalAmount;
    private String deliveryPrice;
    private String savedAmount;

    public CartItemModel(int type, int totalItems, String totalAmount, String deliveryPrice, String savedAmount) {
        this.type = type;
        this.totalItems = totalItems;
        this.totalAmount = totalAmount;
        this.deliveryPrice = deliveryPrice;
        this.savedAmount = savedAmount;
    }

    public int getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getDeliveryPrice() {
        return deliveryPrice;
    }

    public void setDeliveryPrice(String deliveryPrice) {
        this.deliveryPrice = deliveryPrice;
    }

    public String getSavedAmount() {
        return savedAmount;
    }

    public void setSavedAmount(String savedAmount) {
        this.savedAmount = savedAmount;
    }

    ///////// cart total
}
*/