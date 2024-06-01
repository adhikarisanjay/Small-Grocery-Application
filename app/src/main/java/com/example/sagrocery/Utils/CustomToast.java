package com.example.sagrocery.Utils;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sagrocery.R;

public class CustomToast {
    public static void showToast(Context context, String message, int textColor, int backgroundColor, int imageResource) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View layout = inflater.inflate(R.layout.custom_toast, null);

        TextView textView = layout.findViewById(R.id.textView);
        textView.setText(message);
        textView.setTextColor(textColor);

        ImageView imageView = layout.findViewById(R.id.imageView);
        imageView.setImageResource(imageResource);

        Toast toast = new Toast(context);
        toast.setGravity(Gravity.BOTTOM, 0, 100);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }
}
