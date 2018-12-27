package com.example.rmcsilva.reverisectest;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.rmcsilva.reverisectest.ReversiLogic.GameDataModel;

public class GamePhaseActivity extends AppCompatActivity implements GameActionFragment.OnFragmentInteractionListener {

    TextView whiteScore;
    TextView blackScore;
    GameDataModel.GameMode gameMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        gameMode = GameDataModel.GameMode.valueOf(getIntent().getExtras().getString("gameMode"));

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

    @Override
    public void gameOver() {

    }

    public void updateScore(int black, int white){
        blackScore.setText(Integer.toString(black));
        whiteScore.setText(Integer.toString(white));
    }

    @Override
    public void addicionalMoves(GameDataModel game) {
        //TODO:
    }

    @Override
    public GameDataModel.GameMode getGameMode() {
        return gameMode;
    }


}
