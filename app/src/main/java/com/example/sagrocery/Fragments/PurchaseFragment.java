package com.example.sagrocery.Fragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.Toast;

import com.example.sagrocery.Database.DBHelper;
import com.example.sagrocery.MainActivity;
import com.example.sagrocery.Model.Purchase;
import com.example.sagrocery.Model.SalesItem;
import com.example.sagrocery.R;
import com.example.sagrocery.Utils.CustomAlert;
import com.example.sagrocery.Utils.UserManager;
import com.example.sagrocery.databinding.FragmentPurchaseBinding;
import com.example.sagrocery.databinding.FragmentSalesBinding;

import java.util.Calendar;


public class PurchaseFragment extends Fragment implements View.OnClickListener  {
    FragmentPurchaseBinding binding; // Replace with your generated binding class name
    DBHelper dbHelper;
    boolean status;
    public UserManager userManager;

    Calendar cal = Calendar.getInstance();
    int currentYear = cal.get(Calendar.YEAR);
    int currentMonth = cal.get(Calendar.MONTH);
    int currentDay = cal.get(Calendar.DAY_OF_MONTH);
    boolean dataIsValid = false;
    DatePickerDialog datePicker;

    public PurchaseFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentPurchaseBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.edtDate.setOnClickListener(this);
        binding.btnSubmit.setOnClickListener(this);
        binding.btnCancel.setOnClickListener(this);
        dbHelper = new DBHelper(getActivity());

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.edtDate) {

            // Open DatePickerDialog when the date EditText is clicked
            Calendar cal = Calendar.getInstance();
            int dayOfSales = cal.get(Calendar.DAY_OF_MONTH);
            int monthOfSales = cal.get(Calendar.MONTH);
            int yearOfSales = cal.get(Calendar.YEAR);
            datePicker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    if (year < currentYear ||
                            (year == currentYear && month < currentMonth) ||
                            (year == currentYear && month == currentMonth && dayOfMonth <= currentDay)) {
                        // The selected date is less than or equal to the current date
                        String selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year; // Note: month is 0-based
                        binding.edtDate.setText(selectedDate);
                        dataIsValid = true;
                    } else {
                        // showing error if date is in future
                        dataIsValid = false;
                        Toast.makeText(getActivity(), "Purchase date cannot be in the future", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    binding.edtDate.setText(dayOfMonth + "/" + month + "/" + year);
                }
            }, yearOfSales, monthOfSales, dayOfSales);
            datePicker.getDatePicker().setMaxDate(System.currentTimeMillis());
            datePicker.show();
        } else if (binding.txtLayItemCode.getEditText().getText().toString().trim().length() == 0) {
            binding.edtItemCode.setError("This field is required");
        } else if (binding.txtLayQty.getEditText().getText().toString().trim().length() == 0) {
            binding.edtQty.setError("This field is required");
        } else if (dataIsValid == false) {
            Toast.makeText(getActivity(), "Sales date cannot be in the future", Toast.LENGTH_SHORT).show();
        } else if (v.getId() == R.id.btnSubmit) {
            Purchase addPurchaseObj = addPurchase();

            if (addPurchaseObj != null) {

                status = dbHelper.insertPurchase(
                        getActivity(),
                        addPurchaseObj.getItemCode(),
                        addPurchaseObj.getQuantityPurchased(),
                        addPurchaseObj.getDateOfPurchase()
                );
                if (status) {
                    clearFields();
                    hideKeyboard(v.getContext());
                    AlertDialog customDialog = CustomAlert.createCustomDialog(
                            getActivity(),
                            "Successful",
                            "Purchase Added Successfully",
                            R.drawable.tick
                    );
                    customDialog.show();
                } else {
                    hideKeyboard(v.getContext());
                }
            }
        }
        if (v.getId() == R.id.btnCancel) {
            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
        }


    }

    public Purchase addPurchase() {
        Purchase objPurchaseItem = new Purchase();

        try {
            int itemCode = Integer.parseInt(binding.edtItemCode.getText().toString().trim());
            objPurchaseItem.setItemCode(itemCode);
        } catch (NumberFormatException e) {
            Toast.makeText(getContext(), "Invalid ItemCode", Toast.LENGTH_SHORT).show();
        }

        try {
            int qty = Integer.parseInt(binding.edtQty.getText().toString().trim());
            objPurchaseItem.setQuantityPurchased(qty);
        } catch (NumberFormatException e) {
            Toast.makeText(getContext(), "Invalid ItemCode", Toast.LENGTH_SHORT).show();
        }

        objPurchaseItem.setDateOfPurchase(binding.edtDate.getText().toString().trim());

        return objPurchaseItem;
    }

    private void hideKeyboard(Context context) {
        InputMethodManager inputMethodManager = (InputMethodManager) requireContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        View view = requireActivity().getCurrentFocus();
        if (view != null) {
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void clearFields() {
        binding.edtItemCode.setText("");
        binding.edtQty.setText("");
        binding.edtDate.setText("");

    }



}