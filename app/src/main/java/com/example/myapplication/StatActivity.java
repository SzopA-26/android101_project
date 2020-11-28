package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myapplication.component.MyRectangle;
import com.example.myapplication.model.ItemDatabase;

public class StatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stat);

        setMenuBtnOnClick();
        setGraph();
    }

    private void setGraph() {
        ItemDatabase db = ItemDatabase.getInstance(this);
        float amountItem = db.itemDao().getItemAllList(false).size();
        float amountSuccess = db.itemDao().getItemBySuccess(true).size();
        float amountFail = amountItem - amountSuccess;

        Log.i("ii", "setGraph: " + amountItem + " " + amountSuccess + " " + amountFail);

        float sPercent = amountSuccess/amountItem * 100;
        float fPercent = amountFail/amountItem * 100;
        float[] graph = {sPercent, fPercent};
        MyRectangle myRectangle = new MyRectangle(this, graph);

        ConstraintLayout layout = findViewById(R.id.layout);
        layout.addView(myRectangle);

        TextView sucPercent = findViewById(R.id.sucPercent);
        TextView failPercent = findViewById(R.id.failPercent);

        String s = String.format("%.2f", sPercent);
        String f = String.format("%.2f", fPercent);

        if (sPercent - (int)sPercent == 0) {
            s = String.valueOf((int)sPercent);
        }
        if (fPercent - (int)fPercent == 0) {
            f = String.valueOf((int)fPercent);
        }
        sucPercent.setText(s + "%");
        failPercent.setText(f + "%");
    }




    private void setMenuBtnOnClick() {
        ImageView todayBtnImg = findViewById(R.id.todayBtnImg);
        ImageView allBtnImg = findViewById(R.id.allBtnImg);
        ImageView newBtnImg = findViewById(R.id.newBtnImg);
        ImageView hisBtnImg = findViewById(R.id.hisBtnImg);

        TextView todayBtnText = findViewById(R.id.todayBtnText);
        TextView allBtnText = findViewById(R.id.allBtnText);
        TextView newBtnText = findViewById(R.id.newBtnText);
        TextView hisBtnText = findViewById(R.id.hisBtnText);

        allBtnImg.setOnClickListener(v -> startActivity(new Intent(StatActivity.this, AllActivity.class)));
        allBtnText.setOnClickListener(v -> startActivity(new Intent(StatActivity.this, AllActivity.class)));
        newBtnImg.setOnClickListener(v -> startActivity(new Intent(StatActivity.this, NewActivity.class)));
        newBtnText.setOnClickListener(v -> startActivity(new Intent(StatActivity.this, NewActivity.class)));
        todayBtnImg.setOnClickListener(v -> startActivity(new Intent(StatActivity.this, TodayActivity.class)));
        todayBtnText.setOnClickListener(v -> startActivity(new Intent(StatActivity.this, TodayActivity.class)));
        hisBtnImg.setOnClickListener(v -> startActivity(new Intent(StatActivity.this, HistoryActivity.class)));
        hisBtnText.setOnClickListener(v -> startActivity(new Intent(StatActivity.this, HistoryActivity.class)));
    }
}