package com.example.sagrocery.Fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sagrocery.Database.DBHelper;
import com.example.sagrocery.Login.LoginActivity;
import com.example.sagrocery.MainActivity;
import com.example.sagrocery.Model.AddStock;
import com.example.sagrocery.Model.Register;
import com.example.sagrocery.R;
import com.example.sagrocery.Register.RegisterActivity;
import com.example.sagrocery.Utils.CustomAlert;
import com.example.sagrocery.Utils.CustomToast;
import com.example.sagrocery.Utils.UserManager;
import com.example.sagrocery.databinding.ActivityLoginBinding;
import com.example.sagrocery.databinding.FragmentAddStockBinding;
import androidx.navigation.Navigation;


public class AddStockFragment extends Fragment  implements View.OnClickListener {
     FragmentAddStockBinding binding; // Replace with your generated binding class name
    DBHelper dbHelper;
    boolean status;
    public UserManager userManager;

    public AddStockFragment() {
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
        binding = FragmentAddStockBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated( View view,  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.btnSubmit.setOnClickListener(this);
        binding.btnCancel.setOnClickListener(this);
        dbHelper = new DBHelper(getActivity());

    }

    @Override
    public void onClick(View v) {
        RadioGroup radioGroup = binding.rgTypeTax;

        if (binding.txtLayItemName.getEditText().getText().toString().trim().length() == 0) {
            binding.edtItemName.setError("This field is required");
        }
        else if (binding.txtLayQty.getEditText().getText().toString().trim().length() == 0) {
            binding.edtQty.setError("This field is required");
        }

        else if (binding.txtLayPrice.getEditText().getText().toString().trim().length() == 0) {
            binding.edtPrice.setError("This field is required");
        }
        if (radioGroup.getCheckedRadioButtonId() == -1) {
            Toast.makeText(getContext(), "Select tax preferences", Toast.LENGTH_SHORT).show();

        }

        else   if (v.getId() == R.id.btnSubmit) {
            AddStock addStockObj = addStock();

            if (addStockObj != null) {

                status = dbHelper.insertStock(
                        addStockObj.getItemName(),
                        addStockObj.getQuantity(),
                        addStockObj.getPrice(),
                        addStockObj.getIsTaxable()
                );
                if (status) {
                    clearFields();
                    hideKeyboard(v.getContext());
                    AlertDialog customDialog = CustomAlert.createCustomDialog(
                            getActivity(),
                            "Successful",
                            "Stock Added Successfully",
                            R.drawable.tick
                    );
                    customDialog.show();
                } else {
                    clearFields();
                    hideKeyboard(v.getContext());
                    AlertDialog customDialog = CustomAlert.createCustomDialog(
                            getActivity(),
                            "Error",
                            "Stock add failed",
                            R.drawable.tick
                    );
                    customDialog.show();
                }
            }
        }
        if (v.getId() == R.id.btnCancel) {
            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
        }



    }

    public AddStock addStock(){
        AddStock objAddStock = new AddStock();
        objAddStock.setItemName(binding.edtItemName.getText().toString().trim());

        try {
            int quantity = Integer.parseInt(binding.edtQty.getText().toString().trim());
            objAddStock.setQuantity(quantity);
        } catch (NumberFormatException e) {
            Toast.makeText(getContext(), "Invalid quantity", Toast.LENGTH_SHORT).show();
        }

        // Parsing price from EditText to double
        try {
            double price = Double.parseDouble(binding.edtPrice.getText().toString().trim());
            objAddStock.setPrice(price);
        } catch (NumberFormatException e) {
            Toast.makeText(getContext(), "Invalid price", Toast.LENGTH_SHORT).show();
        }

        RadioGroup rgTypeTax = binding.rgTypeTax;
        int selectedRadioButtonId = rgTypeTax.getCheckedRadioButtonId();

        boolean isTaxable = false; // default value if no selection is made

        if (selectedRadioButtonId != -1) {
            RadioButton selectedRadioButton = rgTypeTax.findViewById(selectedRadioButtonId);
            if (selectedRadioButton.getId() == R.id.rdTypeTaxable) {
                isTaxable = true;
            }
            else{
                isTaxable = false;
            }
        } else {
            Toast.makeText(getContext(), "Please select taxable or non-taxable", Toast.LENGTH_SHORT).show();
        }

        objAddStock.setTaxable(isTaxable);

        return objAddStock;
    }

    private void hideKeyboard(Context context) {
        InputMethodManager inputMethodManager = (InputMethodManager) requireContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        View view = requireActivity().getCurrentFocus();
        if (view != null) {
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void clearFields() {
        binding.edtItemName.setText("");
        binding.edtQty.setText("");
        binding.edtPrice.setText("");
        binding.rdTypeTaxable.setChecked(false);
        binding.rdTypeNonTaxable.setChecked(false);
    }

    private void showSuccessDialog() {

    }

}