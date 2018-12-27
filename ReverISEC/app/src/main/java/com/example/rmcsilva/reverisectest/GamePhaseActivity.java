package com.example.rmcsilva.reverisectest;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.rmcsilva.reverisectest.ReversiLogic.GameDataModel;

public class GamePhaseActivity extends AppCompatActivity implements GameActionFragment.OnFragmentInteractionListener {

    TextView tvWhiteScore,
             tvBlackScore;

    ImageButton btnExtra,
                btnSkip;

    GameDataModel.GameMode gameMode;
    GameActionFragment gameFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        gameMode = GameDataModel.GameMode.valueOf(getIntent().getExtras().getString("gameMode"));

        setContentView(R.layout.activity_game_phase);

        tvBlackScore = findViewById(R.id.tvPlayerOneScore);
        tvWhiteScore = findViewById(R.id.tvPlayerTwoScore);
        btnExtra = findViewById(R.id.btnExtraMove);
        btnSkip = findViewById(R.id.btnPassMove);

        gameFragment = (GameActionFragment) getSupportFragmentManager().findFragmentById(R.id.gameFragment);
    }

    public void onExtraMove(View view){
        Log.i("Reversi", "onExtraMove");
        if(!gameFragment.canExtra()) return;
        gameFragment.useExtra();
        btnExtra.setAlpha(0.5F);

    }

    public void onSkipMove(View view){
        Log.i("Reversi", "onSkipMove");
        if(!gameFragment.canSkip()) return;
        btnSkip.setAlpha(0.5F);
        gameFragment.useSkip();
    }

    @Override
    public void gameOver() {

    }

    public void updateScore(int black, int white){
        tvBlackScore.setText(Integer.toString(black));
        tvWhiteScore.setText(Integer.toString(white));
    }

    @Override
    public void addicionalMoves(GameDataModel game) {
        //TODO:
    }

    @Override
    public GameDataModel.GameMode getGameMode() {
        return gameMode;
    }

    @Override
    public void newTurn() {
        if(gameFragment.canSkip()){
            btnSkip.setAlpha(1F);
        }else{
            btnSkip.setAlpha(0.5F);
        }
        if(gameFragment.canExtra()){
            btnExtra.setAlpha(1F);
        }else {
            btnExtra.setAlpha(0.5F);
        }
    }

}
