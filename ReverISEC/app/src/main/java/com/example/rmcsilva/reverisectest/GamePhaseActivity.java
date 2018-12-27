package com.example.rmcsilva.reverisectest;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class GamePhaseActivity extends AppCompatActivity implements GameActionFragment.OnFragmentInteractionListener {

    TextView whiteScore;
    TextView blackScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_phase);

        blackScore = findViewById(R.id.tvPlayerOneScore);
        whiteScore = findViewById(R.id.tvPlayerTwoScore);

    }

    public void onExtraMove(View view){
        Log.i("Reversi", "onExtraMove");
    }

    public void onSkipMove(View view){
        Log.i("Reversi", "onSkipMove");
    }

    public void updateScore(int black, int white){
        blackScore.setText(Integer.toString(black));
        whiteScore.setText(Integer.toString(white));
    }
}
