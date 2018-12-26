package com.example.rmcsilva.reverisectest;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class GamePhaseActivity extends AppCompatActivity implements GameActionFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_phase);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        Log.i("Reversi", "onFragmentInteraction");
    }

    public void onExtraMove(View view){
        Log.i("Reversi", "onExtraMove");
    }

    public void onSkipMove(View view){
        Log.i("Reversi", "onSkipMove");
    }
}
