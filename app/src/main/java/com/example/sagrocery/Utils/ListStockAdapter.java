package com.example.sagrocery.Utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sagrocery.Model.AddStock;
import com.example.sagrocery.R;

import java.util.List;

public class ListStockAdapter extends RecyclerView.Adapter<ListStockAdapter.ItemViewHolder> {

    private Context context;
    private List<AddStock> itemList;

    public ListStockAdapter(Context context, List<AddStock> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        // Access the listEmptyTextView from the item layout and handle its visibility based on list size.
        TextView listEmptyTextView = holder.itemView.findViewById(R.id.listEmpty);

        if(itemList.size()==0){
            listEmptyTextView.setText("Stock list is Empty!!!");
        }else{
            listEmptyTextView.setText("");
        }
        // Get the AddStock Model item at the current position and bind its data to the ViewHolder.
        AddStock item = itemList.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView itemName, itemQuantity, itemPrice,taxable,itemCode;

        public ItemViewHolder( View itemView) {
            super(itemView);
            // Find and assign views to respective TextViews within each item.
            itemName = itemView.findViewById(R.id.textValueItemName);
            itemQuantity = itemView.findViewById(R.id.textValueQuantity);
            itemPrice = itemView.findViewById(R.id.textValuePrice);
            taxable=itemView.findViewById(R.id.textValueTaxable);
            itemCode=itemView.findViewById(R.id.textValueItemCode);

        }

        // bind method sets AddStock item's data to respective TextViews within the ViewHolder.
        public void bind(AddStock item) {
            itemName.setText(item.getItemName());
            itemQuantity.setText(String.valueOf(item.getQuantity()));
            itemPrice.setText(String.valueOf(item.getPrice()));
            taxable.setText(String.valueOf(item.getIsTaxable()));
            itemCode.setText(String.valueOf(item.getItemCode()));

        }
    }
}
