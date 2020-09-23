package com.example.hex;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Display;

import com.example.hex.noodles.NoodlePanel;

public class GameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Display display = getWindowManager().getDefaultDisplay();
        setContentView(new NoodlePanel(this, display.getWidth(), display.getHeight()));
    }
}
