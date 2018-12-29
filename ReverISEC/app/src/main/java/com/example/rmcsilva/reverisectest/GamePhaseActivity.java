package com.example.rmcsilva.reverisectest;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rmcsilva.reverisectest.ReversiLogic.GameDataModel;

import java.io.File;

import static com.example.rmcsilva.reverisectest.CameraActivity.IMAGE;
import static com.example.rmcsilva.reverisectest.GameSettingsActivity.PREFERENCES;

public class GamePhaseActivity extends AppCompatActivity implements GameActionFragment.OnFragmentInteractionListener {

    TextView tvWhiteScore,
             tvBlackScore,
             tvBlackIndicator,
             tvWhiteIndicator,
             tvPlayerOneName;

    ImageView ivPlayerImage;

    ImageButton btnExtra,
                btnSkip;

    final File file = new File(Environment.getExternalStorageDirectory() + IMAGE);

    GameDataModel.GameMode gameMode;
    GameActionFragment gameFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        gameMode = GameDataModel.GameMode.valueOf(getIntent().getExtras().getString("gameMode"));

        setContentView(R.layout.activity_game_phase);

        tvBlackScore = findViewById(R.id.tvPlayerOneScore);
        tvWhiteScore = findViewById(R.id.tvPlayerTwoScore);
        tvBlackIndicator = findViewById(R.id.tvPlayerOneIndicator);
        tvWhiteIndicator = findViewById(R.id.tvPlayerTwoIndicator);
        btnExtra = findViewById(R.id.btnExtraMove);
        btnSkip = findViewById(R.id.btnPassMove);
        tvPlayerOneName = findViewById(R.id.tvPlayerOneName);

        ivPlayerImage = findViewById(R.id.ivPlayerOneImage);

        if(file.exists()){
            ivPlayerImage.setBackground(null);
            Bitmap bitmap = BitmapFactory.decodeFile(file.getPath());
            ivPlayerImage.setImageBitmap(BitmapFactory.decodeFile(file.getPath()));
        }

        SharedPreferences prefs = getSharedPreferences(PREFERENCES, MODE_PRIVATE);
        String name = prefs.getString("name", null);
        if (name != null) tvPlayerOneName.setText(name);

        gameFragment = (GameActionFragment) getSupportFragmentManager().findFragmentById(R.id.gameFragment);

        //Updated UI
        gameFragment.updateScore();
        this.newTurn();
    }
    //----------------------------------------------------------------------------------------------
    // UI Buttons
    //----------------------------------------------------------------------------------------------

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

    public void onOptions(final View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //TODO: convert in R.String
        builder.setTitle(R.string.gameModeMenu);
        builder.setItems(R.array.gameModes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case 0: {
                        if (gameMode == GameDataModel.GameMode.COMPUTER) break;
                        gameMode = GameDataModel.GameMode.COMPUTER;
                        gameFragment.switchGameMode(gameMode);
                    }break;
                    case 1: {
                        if (gameMode == GameDataModel.GameMode.LOCAL_MULTIPLAYER) break;
                        gameMode = GameDataModel.GameMode.LOCAL_MULTIPLAYER;
                        gameFragment.switchGameMode(gameMode);
                    }break;
                }
            }
        });
        builder.show();
    }

    //----------------------------------------------------------------------------------------------
    // Fragment Call Backs
    //----------------------------------------------------------------------------------------------

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

        builder.setPositiveButton(getString(R.string.newGame), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                gameFragment.reset();
            }
        });
        builder.create().show();
    }

    @Override
    public GameDataModel.GameMode getGameMode() {
        return gameMode;
    }


    @Override
    public void newTurn() {
        //TODO: change image focus based on player
        if (gameFragment.state.getGame().getPlayer()==GameDataModel.ReversiCell.WHITE){
            tvBlackIndicator.setVisibility(View.INVISIBLE);
            tvWhiteIndicator.setVisibility(View.VISIBLE);
        } else {
            tvBlackIndicator.setVisibility(View.VISIBLE);
            tvWhiteIndicator.setVisibility(View.INVISIBLE);
        }

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

    @Override
    public void updateScore(int black, int white){
        tvBlackScore.setText(Integer.toString(black));
        tvWhiteScore.setText(Integer.toString(white));
    }

}
