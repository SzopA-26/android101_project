package com.example.myapplication.component;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;

import com.example.myapplication.NewActivity;
import com.example.myapplication.R;
import com.example.myapplication.service.ActivityDetail;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class IconDialog extends DialogFragment {
    private ActivityDetail activity;

    public void setActivity(ActivityDetail activity) {
        this.activity = activity;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.icon_dialog, null);


        LinearLayout layout = view.findViewById(R.id.iconLayout);
        LinearLayout row1 = (LinearLayout) layout.getChildAt(0);
        LinearLayout row2 = (LinearLayout) layout.getChildAt(1);
        LinearLayout row3 = (LinearLayout) layout.getChildAt(2);
        LinearLayout row4 = (LinearLayout) layout.getChildAt(3);
        LinearLayout row5 = (LinearLayout) layout.getChildAt(4);

        row1.getChildAt(0).setOnClickListener(v -> setIconOnClick(R.drawable.i01));
        row1.getChildAt(1).setOnClickListener(v -> setIconOnClick(R.drawable.i02));
        row1.getChildAt(2).setOnClickListener(v -> setIconOnClick(R.drawable.i03));
        row1.getChildAt(3).setOnClickListener(v -> setIconOnClick(R.drawable.i04));
        row1.getChildAt(4).setOnClickListener(v -> setIconOnClick(R.drawable.i05));

        row2.getChildAt(0).setOnClickListener(v -> setIconOnClick(R.drawable.i06));
        row2.getChildAt(1).setOnClickListener(v -> setIconOnClick(R.drawable.i07));
        row2.getChildAt(2).setOnClickListener(v -> setIconOnClick(R.drawable.i08));
        row2.getChildAt(3).setOnClickListener(v -> setIconOnClick(R.drawable.i09));
        row2.getChildAt(4).setOnClickListener(v -> setIconOnClick(R.drawable.i10));

        row3.getChildAt(0).setOnClickListener(v -> setIconOnClick(R.drawable.i11));
        row3.getChildAt(1).setOnClickListener(v -> setIconOnClick(R.drawable.i12));
        row3.getChildAt(2).setOnClickListener(v -> setIconOnClick(R.drawable.i13));
        row3.getChildAt(3).setOnClickListener(v -> setIconOnClick(R.drawable.i14));
        row3.getChildAt(4).setOnClickListener(v -> setIconOnClick(R.drawable.i15));

        row4.getChildAt(0).setOnClickListener(v -> setIconOnClick(R.drawable.i16));
        row4.getChildAt(1).setOnClickListener(v -> setIconOnClick(R.drawable.i17));
        row4.getChildAt(2).setOnClickListener(v -> setIconOnClick(R.drawable.i18));
        row4.getChildAt(3).setOnClickListener(v -> setIconOnClick(R.drawable.i19));
        row4.getChildAt(4).setOnClickListener(v -> setIconOnClick(R.drawable.i20));

        row5.getChildAt(0).setOnClickListener(v -> setIconOnClick(R.drawable.i21));
        row5.getChildAt(1).setOnClickListener(v -> setIconOnClick(R.drawable.i22));
        row5.getChildAt(2).setOnClickListener(v -> setIconOnClick(R.drawable.i23));
        row5.getChildAt(3).setOnClickListener(v -> setIconOnClick(R.drawable.i24));
        row5.getChildAt(4).setOnClickListener(v -> setIconOnClick(R.drawable.i25));

        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setIconOnClick(int icon) {
        activity.setActivityIcon(icon);
        dismiss();
    }
}
