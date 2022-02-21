package com.example.hyperlocalecom;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class WishlistAdapter extends RecyclerView.Adapter<WishlistAdapter.ViewHolder> {

    private List <WishlistModel> wishlistModelList;

    public WishlistAdapter(List<WishlistModel> wishlistModelList) {
        this.wishlistModelList = wishlistModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.wishlist_item_layout,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int resource = wishlistModelList.get(position).getProductImage();
        String title = wishlistModelList.get(position).getProductTitle();
        String productPrice = wishlistModelList.get(position).getProductPrice();
        String cuttedPrice = wishlistModelList.get(position).getCuttedPrice();
        String paymentMethod = wishlistModelList.get(position).getPaymentMethod();
        holder.setData(resource,title,productPrice,cuttedPrice,paymentMethod);

    }

    @Override
    public int getItemCount() {
        return wishlistModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView productImage;
        private TextView productTitle;
        private View priceCut;
        private TextView productPrice;
        private TextView cuttedPrice;
        private TextView paymentMethod;
        private ImageButton deleteBtn;

        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            productImage = itemView.findViewById(R.id.wishlist_product_image);
            productTitle = itemView.findViewById(R.id.wishlist_product_title);
            priceCut = itemView.findViewById(R.id.price_cut);
            productPrice = itemView.findViewById(R.id.wishlist_product_price);
            paymentMethod = itemView.findViewById(R.id.wishlist_payment_method);
            cuttedPrice = itemView.findViewById(R.id.wishlist_cutted_price);
            deleteBtn = itemView.findViewById(R.id.wishlist_delete_btn);
        }

        private void setData(int resource,String title,String price,String cuttedPriceValue,String payMethod){
            productImage.setImageResource(resource);
            productTitle.setText(title);
            productPrice.setText(price);
            cuttedPrice.setText(cuttedPriceValue);
            paymentMethod.setText(payMethod);

            deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(itemView.getContext(), "delete",Toast.LENGTH_SHORT).show();
                }
            });

        }
    }
}
