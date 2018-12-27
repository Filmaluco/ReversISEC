package com.example.rmcsilva.reverisectest;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
        //TODO: make better
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        int whitePieces = Integer.parseInt((String) tvWhiteScore.getText());
        int blackPieces  = Integer.parseInt((String) tvBlackScore.getText());

        if(whitePieces > blackPieces) {
            builder.setTitle(R.string.gameover_white_wins);
        }
        if(whitePieces < blackPieces) {
            builder.setTitle(R.string.gameover_black_wins);
        }
        if(whitePieces == blackPieces) {
            builder.setTitle(R.string.gameover_tie);
        }

        builder.setMessage("["+whitePieces+"] white pieces \n VS\n ["+blackPieces+"] black pieces");

        builder.setPositiveButton("New Game", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                //TODO start a new game
            }
        });
        builder.create().show();
    }

    public void updateScore(int black, int white){
        tvBlackScore.setText(Integer.toString(black));
        tvWhiteScore.setText(Integer.toString(white));
    }

    @Override
    public GameDataModel.GameMode getGameMode() {
        return gameMode;
    }

    @Override
    public void newTurn() {
        //TODO: change image focus based on player
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
