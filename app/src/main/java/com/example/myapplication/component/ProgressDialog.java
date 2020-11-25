package com.example.myapplication.component;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import com.example.myapplication.EditActivity;
import com.example.myapplication.R;
import com.example.myapplication.model.Item;
import com.example.myapplication.model.ItemDatabase;
import com.example.myapplication.service.ActivityShowList;
import com.example.myapplication.service.DataManager;

import java.time.LocalDateTime;
import java.util.List;

public class ProgressDialog extends DialogFragment {
    private LinearLayout itemLayout;
    private TextView doneBtn, backBtn, editBtn, nameText, timeText;
    private ImageView iconImg;
    private RadioGroup radioGroup;
    private EditText commentText;
    private int itemId;
    private Item item;
    private ActivityShowList activity;

    private ItemDatabase appDB;

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }
    public void setActivity(ActivityShowList activity) {
        this.activity = activity;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.progress_dialog, null);

        appDB = ItemDatabase.getInstance(view.getContext());

        itemLayout = view.findViewById(R.id.itemLayout);
        doneBtn = view.findViewById(R.id.doneBtnText);
        backBtn = view.findViewById(R.id.backBtnText);
        editBtn = view.findViewById(R.id.editBtnText);
        nameText = view.findViewById(R.id.nameText);
        timeText = view.findViewById(R.id.timeText);
        iconImg = view.findViewById(R.id.iconImg);
        radioGroup = view.findViewById(R.id.radioGroup);
        commentText = view.findViewById(R.id.commentText);
        commentText.setEnabled(false);
        commentText.setAlpha((float) 0.43);

        item = appDB.itemDao().getItemById(itemId);
        if (!item.isAvailable()) {
            TextView headText = view.findViewById(R.id.headText);
            headText.setText("Result");
            doneBtn.setText("Delete");
            doneBtn.setTextColor(Color.RED);

            editBtn.setAlpha(0);
            ((RadioButton)radioGroup.getChildAt(0)).setEnabled(false);
            ((RadioButton)radioGroup.getChildAt(1)).setEnabled(false);
            if (item.isSuccess()) {
                ((RadioButton)radioGroup.getChildAt(0)).setChecked(true);
            } else {
                ((RadioButton)radioGroup.getChildAt(1)).setChecked(true);
                commentText.setEnabled(false);
                commentText.setAlpha(1);
                commentText.setText(item.getComment());

            }
        }
        nameText.setText(item.getName());
        timeText.setText(DataManager.getDisplayDateFormat(DataManager.stringToLocalDateTime(item.getDate()), false) + " " + item.getTime());
        iconImg.setImageResource(item.getIcon());
        iconImg.setColorFilter(ContextCompat.getColor(view.getContext(), item.getColor()), android.graphics.PorterDuff.Mode.MULTIPLY);

        backBtn.setOnClickListener(v -> dismiss());
        if (item.isAvailable()) {
            itemLayout.setOnClickListener(v -> {
                Intent intent = (new Intent(view.getContext(), EditActivity.class));
                intent.putExtra("ITEM_ID", itemId);
                getActivity().startActivityForResult(intent, DataManager.LAUNCH_EDIT);
                dismiss();
            });
        }
        doneBtn.setOnClickListener(v -> {
            if (!item.isAvailable()) {
                AlertDialog dialog = new AlertDialog.Builder(view.getContext())
                        .setTitle("Do you want to delete this task ?")
                        .setMessage("This will remove it from history, you can't get it back later.")
                        .setPositiveButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setNegativeButton("YES", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Log.i("ii", "onClick: " + "delete item");
                                appDB.itemDao().deleteItem(item);
                                activity.setItemList(activity.getItemList());
                                dismiss();
                            }
                        })
                        .show();
            }
            else {
                int selectedId = radioGroup.getCheckedRadioButtonId();
                if (selectedId == -1) {
                    dismiss();return;
                }
                else if (selectedId == R.id.sucBtn) {
                    item.setSuccess(true);
                }
                else if (selectedId == R.id.unsucBtn) {
                    item.setSuccess(false);
                    item.setComment(commentText.getText().toString());
                }
                item.setAvailable(false);
                appDB.itemDao().updateItem(item);
                activity.setItemList(activity.getItemList());
                dismiss();
            }
        });
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            int selectedId = radioGroup.getCheckedRadioButtonId();
            if (selectedId == R.id.sucBtn) {
                commentText.setEnabled(false);
                commentText.setAlpha((float) 0.43);
            }
            if (selectedId == R.id.unsucBtn) {
                commentText.setEnabled(true);
                commentText.setAlpha(1);
            }
        });

        return view;
    }

}
