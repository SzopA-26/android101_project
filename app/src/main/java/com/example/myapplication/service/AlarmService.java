package com.example.myapplication.service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Process;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.example.myapplication.TodayActivity;
import com.example.myapplication.model.Item;
import com.example.myapplication.model.ItemDatabase;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class AlarmService extends Service {

    ItemDatabase db;

    // This method run only one time. At the first time of service created and running
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onCreate() {
        HandlerThread thread = new HandlerThread("ServiceStartArguments",
                Process.THREAD_PRIORITY_BACKGROUND);
        thread.start();
        Log.i("ii", "After service created");

        db = ItemDatabase.getInstance(this);
        List<Item> tomorrowList = db.itemDao().getItemListByDate(DataManager.dateTimeToString(LocalDateTime.now().plus(1, ChronoUnit.DAYS)), true);
        for (Item item : tomorrowList) {
            if (item.getStart() == null) {
                setNotification(item, "Your tomorrow task", item.getName());
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        List<Item> todayList = db.itemDao().getItemListByDate(DataManager.dateTimeToString(LocalDateTime.now()), true);
        LocalDateTime now = LocalDateTime.now();
        for (Item item : todayList) {
            if (item.getStart() == null) {
                setNotification(item, "This is your task for TODAY!",item.getName());
                item.setNoti(false);
            }
            if (now.getHour() == DataManager.stringToLocalDateTime(item.getStart(), item.getDate()).getHour()
                    && Math.abs(now.getMinute() - DataManager.stringToLocalDateTime(item.getStart(), item.getDate()).getMinute()) <= 5
                    && item.isNoti()) {
                setNotification(item, "It's time for your task!",item.getName() + " " + item.getStart());
                item.setNoti(false);
                db.itemDao().updateItem(item);
                Log.i("ii", "onStartCommand: " + item.toString());
            }
        }

        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void setNotification(Item item, String title, String mes) {
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("TASK_ALARM",
                    "Task Alarm",
                    NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription("YOUR_NOTIFICATION_CHANNEL_DESCRIPTION");
            mNotificationManager.createNotificationChannel(channel);
        }
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext(), "TASK_ALARM")
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setSmallIcon(item.getIcon()) // notification icon
                .setContentTitle(title) // title for notification
                .setContentText(mes)// message for notification
                .setAutoCancel(true); // clear notification after click
        Intent intent = new Intent(getApplicationContext(), TodayActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this, 1234, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(pi);
        mNotificationManager.notify(0, mBuilder.build());
    }

}