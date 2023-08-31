package com.mroczkowski.quiz_apka;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class ChatActivity extends AppCompatActivity {

    TextView chat;
    EditText message;
    Button send;
    Button disconnect;

    String address;
    private BluetoothAdapter BA;
    BluetoothDevice device;
    BluetoothServerSocket serverSocket;
    BluetoothSocket socket;

    Thread listening_thread;

    UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    Handler mainHandler = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        chat = findViewById(R.id.message_text_large);
        message = findViewById(R.id.message_text_small);
        send = findViewById(R.id.send_button);
        disconnect = findViewById(R.id.disconnect_button);

        BA = BluetoothAdapter.getDefaultAdapter();
        mainHandler = new Handler(this.getMainLooper());

        address = getIntent().getStringExtra("address");
        if (address != null) {
            Toast.makeText(this, address, Toast.LENGTH_SHORT).show();
            connect();
            listen();
            sendMessageConfiguration();
        } else {
            accept();
        }
    }

    private void sendMessageConfiguration() {
        send.setOnClickListener(view -> {
            sendMessage();
        });
        disconnect.setOnClickListener(view -> {
            disconnectDevice();
        });
    }

    private void connect() {
        device = BA.getRemoteDevice(address);
        try {
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            socket = device.createInsecureRfcommSocketToServiceRecord(MY_UUID);
            socket.connect();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void accept() {
        new Thread(() -> {
            try {
                if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                serverSocket = BA.listenUsingInsecureRfcommWithServiceRecord("BT_old", MY_UUID);
                socket = serverSocket.accept();
                listen();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).start();
        sendMessageConfiguration();
    }

    private void listen(){
        listening_thread = new Thread(() -> {
            mainHandler.postDelayed(() -> {
                Toast.makeText(this, socket.toString(), Toast.LENGTH_SHORT).show();
            }, 100);
            while (true){
                try {
                    byte[] inputData = new byte[1024];
                    socket.getInputStream().read(inputData);
                    String input_message = new String(inputData, StandardCharsets.UTF_8);
                    mainHandler.postDelayed(() -> {
                        chat.setText(chat.getText() + "\n Device 2: " + input_message);
                    }, 100);
                } catch (Exception e) {
                    break;
                }
            }
        });
        listening_thread.start();
    }

    private void sendMessage(){
        try {
            String outputMessage = message.getText().toString();
            byte[] outputData = outputMessage.getBytes(StandardCharsets.UTF_8);
            socket.getOutputStream().write(outputData);
            chat.setText(chat.getText() + "\n Me : " + outputMessage);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void disconnectDevice(){
        listening_thread.stop();
        finish();
    }
}