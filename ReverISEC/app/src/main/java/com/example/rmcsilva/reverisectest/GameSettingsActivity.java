package com.example.rmcsilva.reverisectest;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.rmcsilva.reverisectest.ReversiLogic.ReversIsecScoreManager;
import com.example.rmcsilva.reverisectest.ReversiLogic.ReversIsecScoreManager.ReversIsecScore;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.example.rmcsilva.reverisectest.CameraActivity.IMAGE;

public class GameSettingsActivity extends AppCompatActivity {

    public static final String PREFERENCES = "UserInfo";

    TextView playerName;
    ListView gameHistory;
    ImageView playerImage;
    TextView emptyText;

    public static GameHistoryAdapter adapter;

    private static final int REQUEST_CODE = 1;
    private Bitmap bitmap;

    final File file = new File(Environment.getExternalStorageDirectory() + IMAGE);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_settings);

        playerName = findViewById(R.id.tvPlayerName);
        gameHistory = findViewById(R.id.lvGameHistory);
        playerImage = findViewById(R.id.ivPlayerImage);

        emptyText = (TextView)findViewById(android.R.id.empty);
        gameHistory.setEmptyView(emptyText);

        if(file.exists()){
            playerImage.setBackground(null);
            Bitmap bitmap = BitmapFactory.decodeFile(file.getPath());
            playerImage.setImageBitmap(BitmapFactory.decodeFile(file.getPath()));
        }

        ArrayList<ReversIsecScore> scoreList = ReversIsecScoreManager.loadScore(this);

        adapter = new GameHistoryAdapter(scoreList, getApplicationContext());
        adapter.setLayout(R.layout.listview_game_history);

        gameHistory.setAdapter(adapter);

        Log.i("score","Scores in memory: ");
        for (int i = 0; i < scoreList.size(); i++){
            Log.i("score", scoreList.get(i).toString());
        }

        SharedPreferences prefs = getSharedPreferences(PREFERENCES, MODE_PRIVATE);
        String name = prefs.getString("name", null);
        if (name != null) playerName.setText(name);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(file.exists()){
            playerImage.setBackground(null);
            Bitmap bitmap = BitmapFactory.decodeFile(file.getPath());
            playerImage.setImageBitmap(BitmapFactory.decodeFile(file.getPath()));
        }

        SharedPreferences prefs = getSharedPreferences(PREFERENCES, MODE_PRIVATE);
        String name = prefs.getString("name", null);
        if (name != null) playerName.setText(name);

    }

    public void onChangePhoto(View view) {
        Intent openCamera = new Intent(GameSettingsActivity.this, CameraActivity.class);
        GameSettingsActivity.this.startActivity(openCamera);
    }

    public void onChangeName(View view) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        final EditText edittext = new EditText(this);
        alert.setMessage(getString(R.string.alertEnterName));
        alert.setTitle(getString(R.string.alertNameTitle));

        alert.setView(edittext);

        alert.setPositiveButton(getString(R.string.altertNameConfirm), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //TODO: Verify length
                String newName = edittext.getText().toString();
                playerName.setText(newName);
                SharedPreferences.Editor editor = getSharedPreferences(PREFERENCES, MODE_PRIVATE).edit();
                editor.putString("name", newName);
                editor.apply();
            }
        });

        alert.setNegativeButton(getString(R.string.alertNameBack), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // what ever you want to do with No option.
            }
        });
        alert.show();

    }
}
