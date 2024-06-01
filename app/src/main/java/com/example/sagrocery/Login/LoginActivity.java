package com.example.sagrocery.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.sagrocery.Database.DBHelper;
import com.example.sagrocery.MainActivity;
import com.example.sagrocery.Model.Register;
import com.example.sagrocery.R;
import com.example.sagrocery.Register.RegisterActivity;
import com.example.sagrocery.Utils.CustomToast;
import com.example.sagrocery.Utils.UserManager;
import com.example.sagrocery.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity  implements View.OnClickListener {
    ActivityLoginBinding binding;
    DBHelper dbHelper;
    boolean status;
    public UserManager userManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dbHelper = new DBHelper(this);
        userManager = new UserManager(this); // Initialize the AuthenticatorHandler or UserManager

        // Initialize the layout using ViewBinding
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        // Set click listeners for signup text and submit button
        binding.signupText.setOnClickListener(this);
        binding.btnSubmit.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        // Check which view is clicked
        if(v.getId() == R.id.signupText){
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        }
        if (binding.txtLayUserName.getEditText().getText().toString().trim().length() == 0) {
            binding.edtUserName.setError("This field is required");
        }
        else if (binding.txtLayPassword.getEditText().getText().toString().trim().length() == 0) {
            binding.edtPassword.setError("This field is required");
        }
        else if (v.getId() == R.id.btnSubmit) {
            // Attempt login when the submit button is clicked
            Register loginObj = getUser();
            if (loginObj != null) {
                status = dbHelper.loginUser(
                        loginObj.getUsername(),
                        loginObj.getPassword()
                );
                if (status) {
                    userManager.loginUser();
                    userManager.storeLoginUser(binding.edtUserName.getText().toString().trim(),"");
                    hideKeyboard(v.getContext());
                    CustomToast.showToast(this, "User Login Successfully", getResources().getColor(R.color.white), R.color.green, R.drawable.tick);
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);

                } else {
                    // Login failed
                    hideKeyboard(v.getContext());
                    showInvalidCredentialsDialog();
                }
            }
        }
    }

    // Retrieve user credentials from EditText fields
    private Register getUser() {
        Register objRegister = new Register();
        objRegister.setUsername(binding.edtUserName.getText().toString().trim());
        objRegister.setPassword(binding.edtPassword.getText().toString().trim());
        return objRegister;
    }

    private void hideKeyboard(Context context) {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void showInvalidCredentialsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.custom_alert_dialog, null);
        builder.setView(dialogView);

        TextView titleTextView = dialogView.findViewById(R.id.dialog_title);
        TextView messageTextView = dialogView.findViewById(R.id.dialog_message);
        Button okButton = dialogView.findViewById(R.id.dialog_button);

        titleTextView.setText("Invalid Credentials");
        messageTextView.setText("Please check your username and password.");

        AlertDialog dialog = builder.create();
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
    private Register createUser() {
        Register objRegister = new Register();
        objRegister.setUsername("test");
        objRegister.setEmail("test@test.com");
        objRegister.setPassword("test");
        return objRegister;
    }

}
