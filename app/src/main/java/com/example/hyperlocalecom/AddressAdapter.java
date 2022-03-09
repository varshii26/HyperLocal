package com.example.hyperlocalecom;

import static com.example.hyperlocalecom.DeliveryActivity.SELECT_ADDRESS;
import static com.example.hyperlocalecom.MyAccountFragment.MANAGE_ADDRESS;
import static com.example.hyperlocalecom.MyAddressesActivity.refreshItem;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.List;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.Viewholder> {
    private List<AddressModel> addressModelList;
    private int MODE;
    private int preSelectedPosition;


    public AddressAdapter(List<AddressModel> addressModelList,int MODE) {
        this.addressModelList = addressModelList;
        this.MODE = MODE;
        preSelectedPosition = DBqueries.selectedAddress;
    }

    @NonNull
    @Override
    public AddressAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.addresses_item_layout,parent,false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddressAdapter.Viewholder holder, int position) {
        String name = addressModelList.get(position).getFullname();
        String address = addressModelList.get(position).getAddress();
        String pincode = addressModelList.get(position).getPincode();
        Boolean selected = addressModelList.get(position).getSelected();
        holder.setData(name,address,pincode,selected,position);

    }

    @Override
    public int getItemCount() {
        return addressModelList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        private TextView fullName;
        private TextView address;
        private TextView pincode;
        private ImageView icon;
        private LinearLayout optionsContainer;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            fullName = itemView.findViewById(R.id.name);
            address = itemView.findViewById(R.id.address);
            pincode = itemView.findViewById(R.id.pincode);
            icon = itemView.findViewById(R.id.icon_view);
            optionsContainer = itemView.findViewById(R.id.option_container);
        }

        private void setData(String userName,String userAddress,String userPincode,Boolean selected,final int position){
            fullName.setText(userName);
            address.setText(userAddress);
            pincode.setText(userPincode);

            if(MODE==SELECT_ADDRESS){
                icon.setImageResource(R.mipmap.check);
                if(selected){
                    icon.setVisibility(View.VISIBLE);
                    preSelectedPosition = position;
                }else{
                    icon.setVisibility(View.GONE);
                }
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(preSelectedPosition!=position){
                            addressModelList.get(position).setSelected(true);
                            addressModelList.get(preSelectedPosition).setSelected(false);
                            refreshItem(preSelectedPosition,position);
                            preSelectedPosition = position;
                            DBqueries.selectedAddress = position;
                        }
                    }
                });
            }else if(MODE == MANAGE_ADDRESS){

                optionsContainer.setVisibility(View.GONE);
                icon.setImageResource(R.mipmap.vertical_dots);
                icon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        optionsContainer.setVisibility(View.VISIBLE);
                        refreshItem(preSelectedPosition,preSelectedPosition);
                        preSelectedPosition = position;
                    }
                });

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        refreshItem(preSelectedPosition,preSelectedPosition);
                        preSelectedPosition=-1;
                    }
                });

            }
        }
    }
}
