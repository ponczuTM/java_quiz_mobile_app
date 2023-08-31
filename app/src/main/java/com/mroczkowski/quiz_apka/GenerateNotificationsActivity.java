package com.mroczkowski.quiz_apka;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class GenerateNotificationsActivity extends AppCompatActivity {
    private EditText numberInput;
    private Button sendButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generatenotifications);

        numberInput = findViewById(R.id.number_input);
        sendButton = findViewById(R.id.send_button);

        sendButton.setOnClickListener(v -> {
            String input = numberInput.getText().toString();
            int notificationCount = 10; // Domyślna liczba powiadomień

            if (!input.isEmpty()) {
                notificationCount = Integer.parseInt(input);
            }

            generateNotifications(notificationCount);
            Toast.makeText(GenerateNotificationsActivity.this, "ok", Toast.LENGTH_SHORT).show();
        });
    }

    private void generateNotifications(int count) {
        final NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        final String notificationMessage = "I po co Ci tyle powiadomień?";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence channelName = "My Notifications";
            String channelId = "test";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }

        final Handler handler = new Handler();
        final int delay = 2000; // 2 sekundy

        for (int i = 0; i < count; i++) {
            final int notificationId = i;

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    String channelId = "test"; // Identyfikator kanału powiadomień

                    Notification notification = new Notification.Builder(GenerateNotificationsActivity.this, channelId)
                            .setContentTitle("Powiadomienie " + (notificationId + 1))
                            .setContentText(notificationMessage)
                            .setSmallIcon(R.drawable.notification_icon)
                            .build();

                    if (notificationManager != null) {
                        notificationManager.notify(notificationId, notification);
                    }
                }
            }, i * delay); //i * delay);
        }
    }

}
