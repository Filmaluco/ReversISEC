package com.example.rmcsilva.reverisectest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

public class OnlineGameSelectionActivity extends AppCompatActivity {

    ListView availableGames;
    TextView emptyText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_game_selection);

        availableGames = (ListView)findViewById(R.id.lvGamesAvailable);
        emptyText = (TextView)findViewById(android.R.id.empty);
        availableGames.setEmptyView(emptyText);
    }
}
