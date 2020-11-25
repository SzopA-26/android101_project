package com.example.myapplication.component;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;

import com.example.myapplication.NewActivity;
import com.example.myapplication.R;
import com.example.myapplication.service.ActivityDetail;

import java.util.ArrayList;
import java.util.List;

public class ColorDialog extends DialogFragment {
    private List<ImageView> colorsView = new ArrayList<>();
    private ActivityDetail activity;

    public void setActivity(ActivityDetail activity) {
        this.activity = activity;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.color_dialog, null);
        findAllView(view);

        colorsView.get(0).setOnClickListener(v -> setColorOnClick(R.color.color1));
        colorsView.get(1).setOnClickListener(v -> setColorOnClick(R.color.color2));
        colorsView.get(2).setOnClickListener(v -> setColorOnClick(R.color.color3));
        colorsView.get(3).setOnClickListener(v -> setColorOnClick(R.color.color4));
        colorsView.get(4).setOnClickListener(v -> setColorOnClick(R.color.color5));
        colorsView.get(5).setOnClickListener(v -> setColorOnClick(R.color.color6));
        colorsView.get(6).setOnClickListener(v -> setColorOnClick(R.color.color7));
        colorsView.get(7).setOnClickListener(v -> setColorOnClick(R.color.color8));
        colorsView.get(8).setOnClickListener(v -> setColorOnClick(R.color.color9));

        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setColorOnClick(int color) {
        activity.setActivityColor(color);
        dismiss();
    }

    private void findAllView(View view) {
        colorsView.add(view.findViewById(R.id.color1));
        colorsView.add(view.findViewById(R.id.color2));
        colorsView.add(view.findViewById(R.id.color3));
        colorsView.add(view.findViewById(R.id.color4));
        colorsView.add(view.findViewById(R.id.color5));
        colorsView.add(view.findViewById(R.id.color6));
        colorsView.add(view.findViewById(R.id.color7));
        colorsView.add(view.findViewById(R.id.color8));
        colorsView.add(view.findViewById(R.id.color9));
    }

}
