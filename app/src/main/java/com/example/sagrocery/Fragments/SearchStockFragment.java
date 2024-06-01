package com.example.sagrocery.Fragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sagrocery.Database.DBHelper;
import com.example.sagrocery.MainActivity;
import com.example.sagrocery.Model.AddStock;
import com.example.sagrocery.Model.SalesItem;
import com.example.sagrocery.R;
import com.example.sagrocery.Utils.CustomAlert;
import com.example.sagrocery.Utils.UserManager;
import com.example.sagrocery.databinding.FragmentSalesBinding;
import com.example.sagrocery.databinding.FragmentSearchStockBinding;

import java.util.Calendar;

public class SearchStockFragment extends Fragment  implements View.OnClickListener {

    FragmentSearchStockBinding binding; // Replace with your generated binding class name
    DBHelper dbHelper;
    boolean status;
    public UserManager userManager;
    public SearchStockFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSearchStockBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated( View view,  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.btnSearch.setOnClickListener(this);
        binding.btnCancel.setOnClickListener(this);
        dbHelper = new DBHelper(getActivity());
    }

    @Override
    public void onClick(View v) {
        if (binding.txtLayItemCode.getEditText().getText().toString().trim().length() == 0) {
            binding.edtItemCode.setError("This field is required");
        }
        else if (v.getId() == R.id.btnSearch) {
            int itemCode = Integer.parseInt(binding.edtItemCode.getText().toString().trim());
            System.out.println("itemCode"+itemCode);
            AddStock foundItem = dbHelper.searchItemByCode(itemCode);

            if (foundItem.getItemName()!=null) {
              showItemDetailsDialog (foundItem);
            }else{
                AlertDialog customDialog = CustomAlert.createCustomDialog(
                        getActivity(),
                        "No item found",
                        "No stock item found for item code "+  binding.edtItemCode.getText().toString(),
                        R.drawable.error
                );
                customDialog.show();
            }
        }
        if (v.getId() == R.id.btnCancel) {
            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
        }

    }


    private void showItemDetailsDialog(AddStock item) {
            // Create a dialog instance
            Dialog dialog = new Dialog(requireContext());
            dialog.setContentView(R.layout.show_item_dialog);

            // Initialize dialog views
            TextView textViewItemName = dialog.findViewById(R.id.textViewItemName);
            TextView textViewItemCode = dialog.findViewById(R.id.textViewItemCode);
            TextView textViewQuantity = dialog.findViewById(R.id.textViewItemQty);
            TextView textViewPrice = dialog.findViewById(R.id.textViewItemPrice);
            TextView textViewTax = dialog.findViewById(R.id.textViewItemTaxable);

         Button buttonClose = dialog.findViewById(R.id.buttonClose);
         String qty  = String.valueOf(item.getQuantity());
            // Set data to dialog views
            textViewItemCode.setText(String.valueOf("Item Code: "+item.getItemCode()));
            textViewItemName.setText("Item Name: "+item.getItemName());
            textViewQuantity.setText("Item Quantity: " +qty);
            textViewPrice.setText("Item Price: " + String.valueOf(item.getPrice()));
            textViewTax.setText("Item Taxable: " + String.valueOf(item.getIsTaxable()));

            buttonClose.setOnClickListener(v -> dialog.dismiss());

            // Show the dialog
            dialog.show();


    }
}