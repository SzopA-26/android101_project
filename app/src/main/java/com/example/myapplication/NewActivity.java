package com.example.myapplication;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.myapplication.component.ColorDialog;
import com.example.myapplication.component.IconDialog;
import com.example.myapplication.model.Item;
import com.example.myapplication.model.ItemDatabase;
import com.example.myapplication.service.DataManager;
import com.example.myapplication.service.RangeTimePickerDialog;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

@RequiresApi(api = Build.VERSION_CODES.O)
public class NewActivity extends AppCompatActivity {
    private TextView doneBtnText, backBtnText;

    private LocalDateTime selectedDate = LocalDate.now().atTime(0,0);  //today

    private LinearLayout dateItem, startTimer, endTimer;
    private EditText nameTaskText;
    private TextView dateItemText, startTimeText, endTimeText;
    private int stHour, stMin, etHour, etMin;
    private boolean stIsSet = false, etIsSet = false;

    public ImageView iconImg, colorImg;
    private TextView iconText, colorText;
    private int selectedColor, selectedIcon;
    public void setSelectedColor(int color) {
        this.selectedColor = color;
    }
    public void setSelectedIcon(int selectedIcon) {
        this.selectedIcon = selectedIcon;
    }

    private final int LAUNCH_CALENDAR = 1;

    private ItemDatabase appDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);

        appDB = ItemDatabase.getInstance(this);

        nameTaskText = findViewById(R.id.nameTaskText);
        iconImg = findViewById(R.id.iconImg);
        iconText = findViewById(R.id.iconText);
        colorImg = findViewById(R.id.colorImg);
        colorText = findViewById(R.id.colorText);

        // initial default icon and color
        selectedColor = R.color.color6;
        selectedIcon = R.drawable.i01;

        setNavBtnOnClick();
        setIconOnClick();
        setTimer();
        dateItemText.setText(DataManager.getDisplayDateFormat(selectedDate, true));
    }

    private void setIconOnClick() {
        iconImg.setOnClickListener(v -> {
            IconDialog dialog = new IconDialog();
            dialog.show(getSupportFragmentManager(), "IconDialog");
        });
        iconText.setOnClickListener(v -> {
            IconDialog dialog = new IconDialog();
            dialog.show(getSupportFragmentManager(), "IconDialog");
        });
        colorImg.setOnClickListener(v -> {
            ColorDialog dialog = new ColorDialog();
            dialog.show(getSupportFragmentManager(), "ColorDialog");
        });
        colorText.setOnClickListener(v -> {
            ColorDialog dialog = new ColorDialog();
            dialog.show(getSupportFragmentManager(), "ColorDialog");
        });
    }

    private void setNavBtnOnClick() {
        backBtnText = findViewById(R.id.backBtnText);
        doneBtnText = findViewById(R.id.doneBtnText);

        backBtnText.setOnClickListener(v -> {
            Intent returnIntent = new Intent();
            setResult(Activity.RESULT_CANCELED, returnIntent);
            finish();
        });
        doneBtnText.setOnClickListener(v -> {
            if (nameTaskText.getText().toString().equals("")) {
                AlertDialog dialog = new AlertDialog.Builder(this)
                        .setTitle("Please enter your task name")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Continue with delete operation
                            }
                        })
                        .show();
            } else {
                String date = DataManager.dateTimeToString(LocalDate.of(selectedDate.getYear(), selectedDate.getMonth(), selectedDate.getDayOfMonth()).atTime(0,0,0));
                Item item = new Item(nameTaskText.getText().toString(), date, selectedIcon, selectedColor);
                if (stIsSet) item.setStart(DataManager.getStringTime(stHour,stMin));
                if (etIsSet) item.setEnd(DataManager.getStringTime(etHour,etMin));

                appDB.itemDao().insertItem(item);

                Log.i("ii", "created: " + item);
                Intent returnIntent = new Intent();
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            }
        });

        dateItemText = findViewById(R.id.dateItemText);
        dateItem = findViewById(R.id.dateItem);
        dateItem.setOnClickListener(v -> startActivityForResult(new Intent(NewActivity.this, CalActivity.class), LAUNCH_CALENDAR));
    }

    private void setTimer() {
        startTimer = findViewById(R.id.startTimer);
        startTimeText = findViewById(R.id.startTimeText);
        endTimer = findViewById(R.id.endTimer);
        endTimeText = findViewById(R.id.endTimeText);

        startTimer.setOnClickListener(v -> {
            TimePickerDialog dialog = new TimePickerDialog(NewActivity.this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    stHour = hourOfDay;
                    stMin = minute;

                    Calendar calendar = Calendar.getInstance();
                    calendar.set(0, 0, 0, stHour, stMin);
                    startTimeText.setText(DateFormat.format("HH:mm", calendar));
                    stIsSet = true;
                    endTimer.setEnabled(true);
                    endTimer.setAlpha(1);
                }
            }, 24, 0, true);
            dialog.updateTime(stHour, stMin);
            dialog.show();
        });
        endTimer.setEnabled(false);
        endTimer.setOnClickListener(v -> {
            RangeTimePickerDialog dialog = new RangeTimePickerDialog(NewActivity.this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    etHour = hourOfDay;
                    etMin = minute;

                    Calendar calendar = Calendar.getInstance();
                    calendar.set(0, 0, 0, etHour, etMin);
                    endTimeText.setText(DateFormat.format("HH:mm", calendar));
                    etIsSet = true;
                }
            }, 24, 0, true);
            dialog.setMin(stHour, stMin);
            dialog.updateTime(etHour, etMin);
            dialog.show();
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == LAUNCH_CALENDAR) {
            if(resultCode == Activity.RESULT_OK){
                String result = data.getStringExtra("result");
                Log.i("result", "onActivityResult: " + result);

                String pattern = "yyyy-MM-dd HH:mm:ss";
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
                selectedDate = LocalDateTime.parse(result + " 00:00:00", formatter);

                String dateStr = DataManager.getDisplayDateFormat(selectedDate, true);
                dateItemText.setText(dateStr);
                Log.i("ii", "dateSelected: " + dateStr);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }
}