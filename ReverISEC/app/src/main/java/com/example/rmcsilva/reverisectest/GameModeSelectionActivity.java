package com.example.rmcsilva.reverisectest;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.rmcsilva.reverisectest.ReversiLogic.GameDataModel;

public class GameModeSelectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_mode_selection);

        setupAnimations();
    }

    void setupAnimations(){
        ConstraintLayout constraintLayout = (ConstraintLayout) findViewById(R.id.rootLayout);
        AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(4000);
        animationDrawable.start();
    }

    public void playVsComputer(View view) {
        Intent playVsComputer = new Intent(GameModeSelectionActivity.this, GamePhaseActivity.class);
        playVsComputer.putExtra("gameMode", GameDataModel.GameMode.COMPUTER.name());
        GameModeSelectionActivity.this.startActivity(playVsComputer);
    }

    public void playVsUser(View view) {
        Intent playVsUser = new Intent(GameModeSelectionActivity.this, GamePhaseActivity.class);
        playVsUser.putExtra("gameMode", GameDataModel.GameMode.LOCAL_MULTIPLAYER.name());
        GameModeSelectionActivity.this.startActivity(playVsUser);
    }


    public void playOnline(View view) {
        Intent playOnline = new Intent(GameModeSelectionActivity.this, OnlineGameSelectionActivity.class);
        playOnline.putExtra("gameMode", GameDataModel.GameMode.MULTIPLAYER.name());
        throw new UnsupportedOperationException("Not yet implemented");
        //GameModeSelectionActivity.this.startActivity(playOnline);
    }
}
