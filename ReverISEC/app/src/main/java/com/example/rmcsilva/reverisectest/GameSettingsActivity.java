package com.example.rmcsilva.reverisectest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class GameSettingsActivity extends AppCompatActivity {

    TextView playerName;
    ListView gameHistory;
    ImageView playerImage;
    TextView emptyText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_settings);

        playerName = findViewById(R.id.tvPlayerName);
        gameHistory = findViewById(R.id.lvGameHistory);
        playerImage = findViewById(R.id.ivPlayerImage);

        emptyText = (TextView)findViewById(android.R.id.empty);
        gameHistory.setEmptyView(emptyText);
    }
}
