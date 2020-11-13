package com.example.madassignment2.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.madassignment2.object.GameData;
import com.example.madassignment2.R;

public class MainActivity extends AppCompatActivity {

    Context context;
    private Button startBtn, settingBtn, resetBtn, loadBtn;
    private TextView width, height, money;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialises the database.
        GameData.get().load(getApplicationContext());

        startBtn = (Button) findViewById(R.id.button_start);
        resetBtn = (Button) findViewById(R.id.button_reset);
        settingBtn = (Button) findViewById(R.id.button_settings);
        loadBtn = (Button) findViewById(R.id.load_button);

        width = (TextView) findViewById(R.id.show_width);
        height = (TextView) findViewById(R.id.show_height);
        money = (TextView) findViewById(R.id.show_money);

        width.setText(Integer.toString(GameData.get().getSetting().getMapWidth()));
        height.setText(Integer.toString(GameData.get().getSetting().getMapHeight()));
        money.setText(Integer.toString(GameData.get().getMoney()));

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //remove database input from previous load user wishes to start a new game.
                GameData.get().resetGame();
                Intent intent = new Intent(MainActivity.this, StartActivity.class);
                startActivity(intent);
            }
        });

        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //make GameData instance null and then use get(), will generate a new grid
                GameData.get().resetGame();
                GameData.get().setInstance();
                GameData.get();

                //refresh the main activity
                finish();
                startActivity(getIntent());
            }
        });

        //open settings activity
        settingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SettingsMenu.class);
                startActivity(intent);
            }
        });

        loadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //load all data from database and output back into the game
                if (GameData.get().loadGameData() && GameData.get().loadSettings() == true) {
                    GameData.get().loadMapData();
                    Intent intent = new Intent(MainActivity.this, StartActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}
