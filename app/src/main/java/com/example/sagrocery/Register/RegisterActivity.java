package com.example.sagrocery.Register;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sagrocery.Database.DBHelper;
import com.example.sagrocery.Login.LoginActivity;
import com.example.sagrocery.Model.Register;
import com.example.sagrocery.R;
import com.example.sagrocery.Utils.CustomToast;
import com.example.sagrocery.databinding.ActivityLoginBinding;
import com.example.sagrocery.databinding.ActivityRegisterBinding;
import com.google.android.material.snackbar.Snackbar;
public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    ActivityRegisterBinding binding;
    DBHelper dbHelper;
    boolean insertStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        dbHelper = new DBHelper(this);

        binding.btnSubmit.setOnClickListener(this);
        binding.alreadyLoginText.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.alreadyLoginText){
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
        }
        if (binding.txtLayUserName.getEditText().getText().toString().trim().length() == 0) {
            binding.edtUserName.setError("This field is required");
        }
        else if (binding.txtLayEmail.getEditText().getText().toString().trim().length() == 0) {
            binding.edtEmail.setError("This field is required");
        }

        else  if(!Patterns.EMAIL_ADDRESS.matcher(binding.txtLayEmail.getEditText().getText()).matches()){
            binding.edtEmail.setError("Invalid email address");
        }
        else if (binding.txtLayPassword.getEditText().getText().toString().trim().length() == 0) {
            binding.edtPassword.setError("This field is required");
        }
        else if (binding.txtLayConfirmPassword.getEditText().getText().toString().trim().length() == 0) {
            binding.edtConfirmPassword.setError("This field is required");
        }
        else if (!binding.edtPassword.getText().toString().trim().equals(binding.edtConfirmPassword.getText().toString().trim()) ) {
            binding.edtConfirmPassword.setError("Password and confirm password did not match");
        }
       else if (v.getId() == R.id.btnSubmit) {
            Register registerObj = createUser();
            if (registerObj != null) {

                insertStatus = dbHelper.registerUser(
                        registerObj.getUsername(),
                        registerObj.getEmail(),
                        registerObj.getPassword()
                );
                if (insertStatus) {
                    hideKeyboard(v.getContext());
                    CustomToast.showToast(this, "User Register Successfully", getResources().getColor(R.color.white), R.color.green, R.drawable.tick);
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);

                } else {
                    // Registration failed
                    hideKeyboard(v.getContext());
                    CustomToast.showToast(this, "Registration failed", getResources().getColor(R.color.white), R.color.red, R.drawable.ic_baseline_warning_24);


                }
            } else {
                // Handle invalid input or null object creation
            }
        }
    }

    private Register createUser() {
        Register objRegister = new Register();
        objRegister.setUsername(binding.edtUserName.getText().toString().trim());
        objRegister.setEmail(binding.edtEmail.getText().toString().trim());
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

}
