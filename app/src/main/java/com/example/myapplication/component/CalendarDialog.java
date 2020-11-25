package com.example.myapplication.component;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;

import com.example.myapplication.R;
import com.example.myapplication.service.ActivityDetail;
import com.example.myapplication.service.DataManager;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@RequiresApi(api = Build.VERSION_CODES.O)
public class CalendarDialog extends DialogFragment {
    private CalendarView calendar;
    private String selectedDate = LocalDate.now().toString();
    private ActivityDetail activityDetail;

    public void setActivityDetail(ActivityDetail activityDetail) {
        this.activityDetail = activityDetail;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.cal_dialog, null);

        calendar = view.findViewById(R.id.calendar);
        Date now = new Date();
        calendar.setMinDate(now.getTime());
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                selectedDate = year + "-";
                if (month+1 < 10) {
                    selectedDate += 0;
                } selectedDate += (month+1) + "-";
                if (dayOfMonth < 10) {
                    selectedDate += "0";
                } selectedDate += dayOfMonth;
                Log.i("ii", "onSelectedDayChange: " + selectedDate);

                activityDetail.setActivityDate(selectedDate);
                dismiss();
            }
        });

        return view;
    }
}
