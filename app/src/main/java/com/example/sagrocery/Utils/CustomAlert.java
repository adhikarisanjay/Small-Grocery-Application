package com.example.sagrocery.Utils;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sagrocery.R;

public class CustomAlert {
// This class, CustomAlert, generates a custom AlertDialog with a specified title, message, and image.
    public static AlertDialog createCustomDialog(Context context, String title, String message, int imageResource) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.custom_alert_dialog, null);
        builder.setView(dialogView);

        TextView titleTextView = dialogView.findViewById(R.id.dialog_title);
        TextView messageTextView = dialogView.findViewById(R.id.dialog_message);
        ImageView imageView = dialogView.findViewById(R.id.dialog_Image);
        Button okButton = dialogView.findViewById(R.id.dialog_button);

        titleTextView.setText(title);
        messageTextView.setText(message);
        imageView.setImageResource(imageResource);

        AlertDialog dialog = builder.create();
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        return dialog;
    }
}
