package com.mroczkowski.quiz_apka;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;
import java.util.Set;

public class BluetoothActivity extends AppCompatActivity {

    CheckBox enable_bt, visible_bt;
    ImageView search_bt;
    TextView name_bt;
    ListView list_view;
    Button accept_bt;

    private BluetoothAdapter BA;
    private Set<BluetoothDevice> pairedDevices;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth);

        enable_bt = findViewById(R.id.enable_bt);
        visible_bt = findViewById(R.id.visible_bt);
        search_bt = findViewById(R.id.search_bt);
        name_bt = findViewById(R.id.name_bt);
        list_view = findViewById(R.id.list_view);
        accept_bt = findViewById(R.id.accept_button);

        name_bt.setText(getLocalBluetoothName());

        BA = BluetoothAdapter.getDefaultAdapter();

        if (BA == null) {
            Toast.makeText(this, "Coś poszło nie tak", Toast.LENGTH_SHORT).show();
            finish();
        }

        if (BA.isEnabled()) {
            enable_bt.setChecked(true);
        }

        enable_bt.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            if (!isChecked) {
                if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                BA.disable();
                Toast.makeText(BluetoothActivity.this, "Wyłączono bluetooth", Toast.LENGTH_SHORT).show();
            } else {
                Intent intentOn = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(intentOn, 0);
                Toast.makeText(BluetoothActivity.this, "Włączono bluetooth", Toast.LENGTH_SHORT).show();
            }
        });

        visible_bt.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            if (isChecked) {
                Intent getVisible = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                startActivityForResult(getVisible, 0);
                Toast.makeText(BluetoothActivity.this, "Widoczny na 2 minuty", Toast.LENGTH_SHORT).show();
            }
        });

        search_bt.setOnClickListener(v -> list());
        list_view.setOnItemClickListener((adapterView, view, i, l) -> {
            connectTo(((MaterialTextView) view).getText().toString());
        });

        accept_bt.setOnClickListener(v -> acceptTo());
    }

    private void acceptTo() {
        Intent intent = new Intent(this, ChatActivity.class);
        startActivity(intent);
    }

    private void connectTo(String deviceName) {
        BluetoothDevice device = null;
        for (BluetoothDevice bt : pairedDevices) {
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            if (bt.getName().equals(deviceName)) {
                device = bt;
                break;
            }
        }
        if (device == null) {
            return;
        }
        Intent intent = new Intent(this, ChatActivity.class);
        intent.putExtra("address", device.getAddress());
        startActivity(intent);
    }

    private void list() {

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        pairedDevices = BA.getBondedDevices();

        ArrayList<String> list = new ArrayList<>();

        for (BluetoothDevice bt : pairedDevices) {
            list.add(bt.getName());
        }

        Toast.makeText(this, "Urządzenia", Toast.LENGTH_SHORT).show();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        list_view.setAdapter(adapter);
    }

    public String getLocalBluetoothName() {
        if (BA == null) {
            BA = BluetoothAdapter.getDefaultAdapter();
        }
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
            return null;
        }
        String name = BA.getName();
        if(name==null){
            name = BA.getAddress();
        }

        return name;
    }
}