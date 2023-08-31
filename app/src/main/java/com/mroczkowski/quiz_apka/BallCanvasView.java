package com.mroczkowski.quiz_apka;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BallCanvasView extends SurfaceView implements SurfaceHolder.Callback, SensorEventListener {

    private SurfaceHolder surfaceHolder;
    private List<Ball> balls;
    private SensorManager sensorManager;
    private Sensor accelerometer;

    private class Ball {
        float x, y;
        float xSpeed, ySpeed;
        Paint paint;
        float radius;

        Ball(float x, float y, float radius, Paint paint, float xSpeed, float ySpeed) {
            this.x = x;
            this.y = y;
            this.radius = radius;
            this.paint = paint;
            this.xSpeed = xSpeed;
            this.ySpeed = ySpeed;
        }
    }

    public BallCanvasView(Context context, AttributeSet attrs) {
        super(context, attrs);

        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
        setWillNotDraw(false);

        balls = new ArrayList<>();

        Resources resources = getResources();
        int screenWidth = resources.getDisplayMetrics().widthPixels;
        int screenHeight = resources.getDisplayMetrics().heightPixels;

        int numBalls = 50;

        Random random = new Random();

        for (int i = 0; i < numBalls; i++) {
            float x = random.nextFloat() * screenWidth;
            float y = random.nextFloat() * screenHeight;
            float radius = 50;
            int color = Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256));
            float xSpeed = ((random.nextFloat() * 90 + 10) / 100)*6; // 10% - 100% of screenWidth
            float ySpeed = ((random.nextFloat() * 90 + 10) / 100)*6; // 10% - 100% of screenHeight
            Paint paint = new Paint();
            paint.setColor(color);
            balls.add(new Ball(x, y, radius, paint, xSpeed, ySpeed));
        }

        // Initialize the SensorManager and accelerometer
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        // Register the accelerometer listener
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        // Not used in this example
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // Unregister the accelerometer listener
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float accelerationX = -event.values[0]; // Reverse the direction for X axis
            float accelerationY = event.values[1];

            for (Ball ball : balls) {
                ball.x += ball.xSpeed * accelerationX;
                ball.y += ball.ySpeed * accelerationY;

                int screenWidth = getWidth();
                int screenHeight = getHeight();

                if (ball.x < ball.radius) {
                    ball.x = ball.radius;
                } else if (ball.x > screenWidth - ball.radius) {
                    ball.x = screenWidth - ball.radius;
                }
                if (ball.y < ball.radius) {
                    ball.y = ball.radius;
                } else if (ball.y > screenHeight - ball.radius) {
                    ball.y = screenHeight - ball.radius;
                }
            }

            drawCanvas();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    private void drawCanvas() {
        if (surfaceHolder.getSurface().isValid()) {
            Canvas canvas = surfaceHolder.lockCanvas();

            canvas.drawColor(Color.BLACK);

            for (Ball ball : balls) {
                // Draw ball
                canvas.drawCircle(ball.x, ball.y, ball.radius, ball.paint);
            }

            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }
}
