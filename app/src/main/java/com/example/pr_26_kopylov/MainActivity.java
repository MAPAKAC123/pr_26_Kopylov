package com.example.pr_26_kopylov;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;


public class MainActivity extends AppCompatActivity {
    // Идентификатор уведомления
    private static final int NOTIFY_ID = 101;

    private static String CHANNEL_ID = "Cat channel";

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.button);
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        String id = "my_channel_01";
        CharSequence name = getString(R.string.channel_name);

        String description = getString(R.string.channel_description);

        int importance = NotificationManager.IMPORTANCE_LOW;

        NotificationChannel mChannel = new NotificationChannel(id, name,importance);

        mChannel.setDescription(description);

        mChannel.enableLights(true);

        mChannel.setLightColor(Color.RED);

        mChannel.enableVibration(true);
        mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});

        mNotificationManager.createNotificationChannel(mChannel);
        button.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("MissingPermission")
            @Override
            public void onClick(View v) {
                Intent notificationIntent = new Intent(MainActivity.this, MainActivity.class);
                PendingIntent contentIntent = PendingIntent.getActivity(MainActivity.this,
                        0, notificationIntent,
                        PendingIntent.FLAG_CANCEL_CURRENT | PendingIntent.FLAG_IMMUTABLE);
                notificationIntent = new Intent(MainActivity.this, MainActivity2.class);
                PendingIntent contentIntent2 = PendingIntent.getActivity(MainActivity.this,
                        0, notificationIntent,
                        PendingIntent.FLAG_CANCEL_CURRENT | PendingIntent.FLAG_IMMUTABLE);
                BitmapFactory BitmapFactory = null;
                NotificationCompat.Builder builder =
                        new NotificationCompat.Builder(MainActivity.this, CHANNEL_ID)
                                .setSmallIcon(R.drawable.baseline_notifications_active_24)
                                .setContentTitle("Напоминание")
                                .setContentText("Пора покормить кота")
                                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                                .setChannelId("my_channel_01")
                                .setContentIntent(contentIntent)
                                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.cat))
                                .addAction(R.drawable.ic_launcher_foreground, "Открыть", contentIntent)
                                .addAction(R.drawable.ic_launcher_foreground, "Отказаться", contentIntent2)
                                .addAction(R.drawable.ic_launcher_foreground, "Другой вариант", contentIntent)
                                .setStyle(new NotificationCompat.InboxStyle()
                                        .addLine("Нужно покрмить кота!").addLine("Он умрет")
                                        .setSummaryText("+1 more"))
                                .setAutoCancel(false); // автоматически закрыть уведомление после нажатия;

                NotificationManagerCompat notificationManager =
                        NotificationManagerCompat.from(MainActivity.this);
                notificationManager.notify(NOTIFY_ID, builder.build());
            }
        });

    }
}