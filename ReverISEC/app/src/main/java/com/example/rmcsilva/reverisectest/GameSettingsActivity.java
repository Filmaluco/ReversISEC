package com.example.rmcsilva.reverisectest;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

import static com.example.rmcsilva.reverisectest.CameraActivity.IMAGE;

public class GameSettingsActivity extends AppCompatActivity {

    public static final String PREFERENCES = "UserInfo";

    TextView playerName;
    ListView gameHistory;
    ImageView playerImage;
    TextView emptyText;

    private static final int REQUEST_CODE = 1;
    private Bitmap bitmap;

    final File file = new File(Environment.getExternalStorageDirectory() + IMAGE);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_settings);

        playerName = findViewById(R.id.tvPlayerName);
        gameHistory = findViewById(R.id.lvGameHistory);
        playerImage = findViewById(R.id.ivPlayerImage);

        emptyText = (TextView)findViewById(android.R.id.empty);
        gameHistory.setEmptyView(emptyText);


        if(file.exists()){
            playerImage.setBackground(null);
            Bitmap bitmap = BitmapFactory.decodeFile(file.getPath());
            playerImage.setImageBitmap(BitmapFactory.decodeFile(file.getPath()));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(file.exists()){
            Bitmap bitmap = BitmapFactory.decodeFile(file.getPath());
            playerImage.setImageBitmap(BitmapFactory.decodeFile(file.getPath()));
        }
    }

    public void onChangePhoto(View view) {
            Intent openCamera = new Intent(GameSettingsActivity.this, CameraActivity.class);
            GameSettingsActivity.this.startActivity(openCamera);
        }





}
