package com.example.sagrocery.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.sagrocery.Database.DBHelper;
import com.example.sagrocery.MainActivity;
import com.example.sagrocery.Model.AddStock;
import com.example.sagrocery.R;
import com.example.sagrocery.Utils.ListStockAdapter;
import com.example.sagrocery.Utils.UserManager;
import com.example.sagrocery.databinding.FragmentAddStockBinding;
import com.example.sagrocery.databinding.FragmentListStockBinding;

import java.util.List;

public class ListStockFragment extends Fragment  implements View.OnClickListener  {
    private RecyclerView recyclerView;
    private ListStockAdapter adapter;
    DBHelper dbHelper;
    FragmentListStockBinding binding;

    public UserManager userManager;
    public ListStockFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentListStockBinding.inflate(inflater, container, false);
        RecyclerView recyclerView = binding.recyclerView;

        // Set up RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new ListStockAdapter(getActivity(), fetchItemsFromDB());
        recyclerView.setAdapter(adapter);
        dbHelper = new DBHelper(getActivity());

        return binding.getRoot();
    }

    @Override
    public void onViewCreated( View view,  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.btnCancel.setOnClickListener(this);
        dbHelper = new DBHelper(getActivity());

    }


    private List<AddStock> fetchItemsFromDB() {
        // Fetch items from the database using your DBHelper
        DBHelper dbHelper = new DBHelper(getActivity());
        return dbHelper.getAllItemsFromStock();
    }

    private void navigateToHome() {

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnCancel) {
            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
        }


    }


}