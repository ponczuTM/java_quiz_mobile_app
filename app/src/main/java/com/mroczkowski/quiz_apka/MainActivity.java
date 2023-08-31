package com.mroczkowski.quiz_apka;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    ImageView ivShowImage;
    ImageView ivMedals;
    ArrayList<Question> questions = new ArrayList<>();
    int index = 0;
    Button btn1, btn2, btn3, btn4;
    TextView tvPoints;
    int points = 0;

    boolean showRewards = false;
    LinearLayout rewardLayout;
    Button btnBluetoothChat, btnFlyingBall, btnTakePhoto, btnGenerateNotification, btnSuprise;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ivShowImage = findViewById(R.id.ivShowImage);
        ivMedals = findViewById(R.id.ivMedals);
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        btn4 = findViewById(R.id.btn4);
        tvPoints = findViewById(R.id.tvPoints);
        rewardLayout = findViewById(R.id.rewardLayout);
        btnBluetoothChat = findViewById(R.id.btnBluetoothChat);
        btnFlyingBall = findViewById(R.id.btnFlyingBall);
        btnTakePhoto = findViewById(R.id.btnTakePhoto);
        btnGenerateNotification = findViewById(R.id.btnGenerateNotification);
        btnSuprise = findViewById(R.id.btnSuprise);
        addQuestions();
        generateQuestion(index);
    }

    private void addQuestions() {
        questions.add(new Question(
                "Python",
                "https://scontent-fra5-2.xx.fbcdn.net/v/t1.15752-9/348358100_260280949863579_4460032287572418440_n.png?_nc_cat=107&ccb=1-7&_nc_sid=ae9488&_nc_ohc=pAdGjNW3W24AX_g18E7&_nc_ht=scontent-fra5-2.xx&oh=03_AdRQZFsPH-RIlyIHehG-1hUmSipq7PB7cQ5_TnECDRGFMg&oe=6493D11E",
                "Python",
                "JAVA",
                "C++",
                "C"
        ));
        questions.add(new Question(
                "HTML",
                "https://scontent-fra3-1.xx.fbcdn.net/v/t1.15752-9/348361319_629248609098860_8399086638230996846_n.png?_nc_cat=101&ccb=1-7&_nc_sid=ae9488&_nc_ohc=O8jMUnOwXhkAX-kVSxO&_nc_ht=scontent-fra3-1.xx&oh=03_AdRi_I1FrZkecw0OOML4curGbC2J138jNU77zmZKMJ4gNg&oe=6493CD13",
                "Python",
                "HTML",
                "LUA",
                "CSS"
        ));
        questions.add(new Question(
                "C++",
                "https://scontent-fra5-2.xx.fbcdn.net/v/t1.15752-9/348360401_779989630419129_3495015984971681202_n.png?_nc_cat=109&ccb=1-7&_nc_sid=ae9488&_nc_ohc=-2wHb78yexsAX8iECXh&_nc_ht=scontent-fra5-2.xx&oh=03_AdRNva83HLC-ItWy8GNqKa4JDNoDloZm8a9b45Zi2HBlOA&oe=6493D68B",
                "LUA",
                "C",
                "C++",
                "C#"
        ));
        questions.add(new Question(
                "CSS",
                "https://scontent-fra3-1.xx.fbcdn.net/v/t1.15752-9/348356466_1653910998382350_6862795027077919965_n.png?_nc_cat=105&ccb=1-7&_nc_sid=ae9488&_nc_ohc=hUceDNi-Ou4AX-TtEf7&_nc_ht=scontent-fra3-1.xx&oh=03_AdR0b5eGs5sIAkqJlANYwiGpJUp1NIrssqAf6ztWKsvw5w&oe=6493C77E",
                "Python",
                "C",
                "C++",
                "CSS"
        ));
        questions.add(new Question(
                "LUA",
                "https://scontent-fra3-1.xx.fbcdn.net/v/t1.15752-9/348360225_778860633883209_5557615072800353373_n.png?_nc_cat=104&ccb=1-7&_nc_sid=ae9488&_nc_ohc=PxPnENFsEnwAX9M9rDK&_nc_ht=scontent-fra3-1.xx&oh=03_AdSjuq7kGpiwP7qy4dVgNOr_VXCWxKmQIIEGFZa4CkBb2A&oe=6493E476",
                "LUA",
                "C#",
                "C++",
                "CSS"
        ));
        questions.add(new Question(
                "C",
                "https://scontent-fra5-2.xx.fbcdn.net/v/t1.15752-9/348357000_976708310433335_613815040660568111_n.png?_nc_cat=106&ccb=1-7&_nc_sid=ae9488&_nc_ohc=hbJEaN-uKX4AX_w0bl8&_nc_ht=scontent-fra5-2.xx&oh=03_AdTdg2rUi9I_goFgVr6ZmF1JsamiLuIjUiVZ8gC6_lBbBQ&oe=6493DFB2",
                "LUA",
                "C",
                "C++",
                "C#"
        ));
        questions.add(new Question(
                "C#",
                "https://scontent-fra5-1.xx.fbcdn.net/v/t1.15752-9/348367125_6783795648300548_4160475150071110744_n.png?_nc_cat=102&ccb=1-7&_nc_sid=ae9488&_nc_ohc=ZrBnBa7kLLAAX9vgfYV&_nc_ht=scontent-fra5-1.xx&oh=03_AdTMW55PN0pVryR0bjCSs9PkRPNwpIJAyRQ7yxcISQU-Ew&oe=6493DA88",
                "C",
                "C++",
                "C#",
                "JAVA"
        ));
        questions.add(new Question(
                "JAVA",
                "https://scontent-fra3-1.xx.fbcdn.net/v/t1.15752-9/348384310_952562259287016_1444699634592033821_n.png?_nc_cat=105&ccb=1-7&_nc_sid=ae9488&_nc_ohc=IY6z7RQmxJkAX-UEIZ0&_nc_ht=scontent-fra3-1.xx&oh=03_AdTjkKQsrFVlpK9bWrQPEtTtCGTd6zTMndquyOyExYMSDw&oe=6493D9BD",
                "C",
                "C++",
                "C#",
                "JAVA"
        ));
        Collections.shuffle(questions);
    }

    private void generateQuestion(int index) {
        Question currentQuestion = questions.get(index);

        Glide.with(this)
                .asBitmap()
                .load(currentQuestion.getImageUrl())
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        ivShowImage.setImageBitmap(resource);
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {
                    }
                });

        ArrayList<String> answers = currentQuestion.getAnswers();
        Collections.shuffle(answers);
        btn1.setText(answers.get(0));
        btn2.setText(answers.get(1));
        btn3.setText(answers.get(2));
        btn4.setText(answers.get(3));
    }

    public void answerSelected(View view) {
        String selectedAnswer = ((Button) view).getText().toString();
        Question currentQuestion = questions.get(index);

        if (selectedAnswer.equals(currentQuestion.getCorrectAnswer())) {
            points++;
            tvPoints.setText(points + " na " + questions.size() + " możliwych");
        }

        index++;

        if (index >= questions.size()) {
            showResult();
        } else {
            generateQuestion(index);
        }
    }

    private void showResult() {
        ivShowImage.setVisibility(View.GONE);
        btn1.setVisibility(View.GONE);
        btn2.setVisibility(View.GONE);
        btn3.setVisibility(View.GONE);
        btn4.setVisibility(View.GONE);

        // Wyświetl medal w zależności od wyniku
        if (points == questions.size()) {
            ivMedals.setImageResource(R.drawable.medals_gold);
        } else if (points >= (int) (questions.size() * 0.7)) {
            ivMedals.setImageResource(R.drawable.medals_silver);
        } else if (points >= (int) (questions.size() * 0.5)) {
            ivMedals.setImageResource(R.drawable.medals_bronze);
        }
        if (points >= 0) {
            showRewards = true;
            rewardLayout.setVisibility(View.VISIBLE);
            btnBluetoothChat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Przeniesienie do MainActivity dla czatu Bluetooth
                    Intent intent = new Intent(MainActivity.this, BluetoothActivity.class);
                    startActivity(intent);
                }
            });

            btnFlyingBall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Przeniesienie do FlyingBallActivity dla kulki
                    Intent intent = new Intent(MainActivity.this, FlyingBallActivity.class);
                    startActivity(intent);
                }
            });


            btnTakePhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Przeniesienie do TakePhoto
                    Intent intent = new Intent(MainActivity.this, TakePhotoActivity.class);
                    startActivity(intent);
                }
            });

            btnGenerateNotification.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Przeniesienie do TakePhoto
                    Intent intent = new Intent(MainActivity.this, GenerateNotificationsActivity.class);
                    startActivity(intent);
                }
            });

            btnSuprise.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Tworzenie obiektu Vibrator
                    Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);

                    // Wibracja przez 5 sekund
                    if (vibrator != null && vibrator.hasVibrator()) {
                        vibrator.vibrate(5000);
                    }

                    // Odtwarzanie dźwięku
                    MediaPlayer mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.sound);

                    if (mediaPlayer != null) {
                        mediaPlayer.start();

                        // Zatrzymanie odtwarzania dźwięku po 5 sekundach
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mediaPlayer.release();
                            }
                        }, 5000);
                    }
                }
            });
        }
    }

    class Question {
        private String correctAnswer;
        private String imageUrl;
        private ArrayList<String> answers;

        public Question(String correctAnswer, String imageUrl, String... answers) {
            this.correctAnswer = correctAnswer;
            this.imageUrl = imageUrl;

            this.answers = new ArrayList<>();
            Collections.addAll(this.answers, answers);
        }

        public String getCorrectAnswer() {
            return correctAnswer;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public ArrayList<String> getAnswers() {
            return answers;
        }
    }
}