package com.example.rmcsilva.reverisectest.ReversiLogic;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public final class ReversIsecScoreManager {

    public static class ReversIsecScore implements Serializable {
        public final static long serialVersionUID = 1L;
        //Score
        int whiteScore, blackScore;
        //Info
        String player1, player2, gameMode;

        public ReversIsecScore(int whiteScore, int blackScore, String player1, String player2, String gameMode) {
            this.whiteScore = whiteScore;
            this.blackScore = blackScore;
            this.player1 = player1;
            this.player2 = player2;
            this.gameMode = gameMode;
        }
        //Getters
        public int getWhiteScore() { return whiteScore; }
        public int getBlackScore() { return blackScore; }
        public String getPlayer1() { return player1; }
        public String getPlayer2() { return player2; }
        public String getGameMode() { return gameMode; }

        @Override
        public String toString() {
            return "ReversIsecScore{" +
                    "whiteScore=" + whiteScore +
                    ", blackScore=" + blackScore +
                    ", player1='" + player1 + '\'' +
                    ", player2='" + player2 + '\'' +
                    ", gameMode='" + gameMode + '\'' +
                    '}';
        }
    }

    //Variables
    //----------------------------------------------------------------------------------------------
    //Static Variables
    // Control variables
    private static final String TAG = "[HistoryManager]";
    // Assets Files
    private static final String GAME_HISTORY_FILE = "reversISECScores.bin";
    //Protected Variables
    Context context;

    //----------------------------------------------------------------------------------------------
    //     SAVE METHOD
    //----------------------------------------------------------------------------------------------

    public static void saveScore(Context context, ReversIsecScore newScore){
        Log.i(TAG, "Saving new Score");
        //if first time just save new object
        if(!context.getFileStreamPath(GAME_HISTORY_FILE).exists()){
            Log.i(TAG, "Student data found");
            firstSave(context, newScore);
            return;
        }
        save(context, newScore);
    }

    private static void firstSave(Context context, ReversIsecScore newScore){
        ArrayList<ReversIsecScore> scoreList = new ArrayList<>(1);
        scoreList.add(newScore);
        try {
            FileOutputStream fos = context.openFileOutput(GAME_HISTORY_FILE, Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(scoreList);
            oos.flush();
            oos.close();
            fos.close();
        } catch(IOException e){
            Log.e(TAG, "Failed to save new score", e);
        }
    }

    private static void save(Context context, ReversIsecScore newScore){
        //linkedList would optimized this, no time to study it sorry professor
        ArrayList<ReversIsecScore> scoreList = loadScore(context);
        scoreList.add(0, newScore);
        try {
            FileOutputStream fos = context.openFileOutput(GAME_HISTORY_FILE, Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(scoreList);
            oos.flush();
            oos.close();
            fos.close();
        } catch(IOException e){
            Log.e(TAG, "Failed to save new score", e);
        }
    }

    //----------------------------------------------------------------------------------------------
    //     LOAD METHOD
    //----------------------------------------------------------------------------------------------
    public static ArrayList<ReversIsecScore> loadScore(Context context){
        ArrayList<ReversIsecScore> toReturn = new ArrayList<>();

        try{
            FileInputStream fis = context.openFileInput(GAME_HISTORY_FILE);
            ObjectInputStream ois = new ObjectInputStream(fis);
            toReturn = (ArrayList<ReversIsecScore>) ois.readObject();
            ois.close();
        }catch (IOException | ClassNotFoundException e){
            Log.e(TAG, "Failed to load scores", e);
        }

        return toReturn;
    }


}
