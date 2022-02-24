package com.example.hyperlocalecom;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class WishlistAdapter extends RecyclerView.Adapter<WishlistAdapter.ViewHolder> {

    private List <WishlistModel> wishlistModelList;
    private Boolean wishlist;

    public WishlistAdapter(List<WishlistModel> wishlistModelList,Boolean wishlist) {
        this.wishlistModelList = wishlistModelList;
        this.wishlist = wishlist;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.wishlist_item_layout,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String resource = wishlistModelList.get(position).getProductImage();
        String title = wishlistModelList.get(position).getProductTitle();
        String productPrice = wishlistModelList.get(position).getProductPrice();
        String cuttedPrice = wishlistModelList.get(position).getCuttedPrice();
        Boolean paymentMethod = wishlistModelList.get(position).isCOD();
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

        private void setData(String resource,String title,String price,String cuttedPriceValue,boolean COD){
            Glide.with(itemView.getContext()).load(resource).apply(new RequestOptions()).placeholder(R.mipmap.home_icon_final).into(productImage);
            productTitle.setText(title);
            productPrice.setText("Rs."+price+"/-");
            cuttedPrice.setText("Rs."+cuttedPriceValue+"/+");
            if (COD){
                paymentMethod.setVisibility(View.VISIBLE);
            }else {
                paymentMethod.setVisibility(View.INVISIBLE);
            }

            if(wishlist){
                deleteBtn.setVisibility(View.VISIBLE);

            }else{
                deleteBtn.setVisibility(View.GONE);
            }

            deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(itemView.getContext(), "delete",Toast.LENGTH_SHORT).show();
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent productDetailsIntent = new Intent(itemView.getContext(),ProductDetailsActivity.class);
                    itemView.getContext().startActivity(productDetailsIntent);

                }
            });

        }
    }
}
