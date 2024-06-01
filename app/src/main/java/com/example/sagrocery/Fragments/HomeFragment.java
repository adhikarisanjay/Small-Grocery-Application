package com.example.sagrocery.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sagrocery.Database.DBHelper;
import com.example.sagrocery.MainActivity;
import com.example.sagrocery.Model.AddStock;
import com.example.sagrocery.R;
import com.example.sagrocery.Utils.ListStockAdapter;
import com.example.sagrocery.Utils.UserManager;
import com.example.sagrocery.databinding.FragmentHomeBinding;
import com.example.sagrocery.databinding.FragmentListStockBinding;
import com.google.android.material.navigation.NavigationView;

import java.util.List;


public class HomeFragment extends Fragment {

    DBHelper dbHelper;
    FragmentHomeBinding binding; // Replace with your generated binding class name
    public UserManager userManager;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userManager = new UserManager(getActivity()); // Initialize the AuthenticatorHandler or UserManager
        dbHelper = new DBHelper(getActivity());

        if(userManager.userName()!=null){
            binding.welcomeTextId.setText( "Welcome to SA Grocery "+userManager.userName());
        }
        int totalStockCount=fetchStockCountFromDb();
        binding.stockCount.setText(String.valueOf(totalStockCount));
        int totalSaleCount=fetchSaleCountFromDb();
        binding.saleCount.setText(String.valueOf(totalSaleCount));
        int totalPurchaseCount=fetchPurchaseCountFromDb();
        binding.purchaseCount.setText(String.valueOf(totalPurchaseCount));
    }



    private int fetchStockCountFromDb() {
        DBHelper dbHelper = new DBHelper(getActivity());
        return dbHelper.getStockTableRowCount();
    }

    private int fetchSaleCountFromDb() {
        DBHelper dbHelper = new DBHelper(getActivity());
        return dbHelper.getSaleTableRowCount();
    }

    private int fetchPurchaseCountFromDb() {
        DBHelper dbHelper = new DBHelper(getActivity());
        return dbHelper.getPurchaseTableRowCount();
    }

}