package com.example.rmcsilva.reverisectest;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.LayerDrawable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupAnimations();
    }

    void setupAnimations(){
        ConstraintLayout constraintLayout = (ConstraintLayout) findViewById(R.id.rootLayout);
        AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(4000);
        animationDrawable.start();

        ImageView ivLogo = (ImageView) findViewById(R.id.logoCircle);
        AnimationDrawable logoAnimationDrawable = (AnimationDrawable) ivLogo.getDrawable();
        logoAnimationDrawable.setEnterFadeDuration(2000);
        logoAnimationDrawable.setExitFadeDuration(4000);
        logoAnimationDrawable.start();
    }

    public void playGame(View view) {
        Intent playGame = new Intent(MainActivity.this, GameModeSelectionActivity.class);
        MainActivity.this.startActivity(playGame);
    }

    public void gameSettings(View view) {
        Intent gameSettings = new Intent(MainActivity.this, GameSettingsActivity.class);
        MainActivity.this.startActivity(gameSettings);
    }

    public void gameInfo(View view) {
        Intent gameInfo = new Intent(MainActivity.this, GameInfoActivity.class);
        MainActivity.this.startActivity(gameInfo);
    }
}
