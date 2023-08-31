package com.mroczkowski.quiz_apka;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Surface;
import android.widget.TextView;
public class FlyingBallActivity extends AppCompatActivity{
    private BallCanvasView ballCanvasView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flyingball);

        ballCanvasView = findViewById(R.id.ballCanvasView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ballCanvasView.surfaceCreated(ballCanvasView.getHolder());
    }

    @Override
    protected void onPause() {
        super.onPause();
        ballCanvasView.surfaceDestroyed(ballCanvasView.getHolder());
    }
}