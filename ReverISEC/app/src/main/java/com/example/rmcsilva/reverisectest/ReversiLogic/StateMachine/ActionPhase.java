package com.example.rmcsilva.reverisectest.ReversiLogic.StateMachine;

import android.util.Log;

import com.example.rmcsilva.reverisectest.ReversiLogic.GameDataModel;

public class ActionPhase extends StateAdapter {
    public ActionPhase(GameDataModel g) {
        super(g);
    }

    @Override
    public IState makeMove(int x, int y) throws IllegalAccessException {
        Log.v("GameBoardView", "User tried to move at " + x + ", " + y);


        // is the selected square a valid play? (unoccupied, would result in flips)
        int captured = game.move(x, y);
        if (captured == 0) {
            Log.e("GameBoardView", "User's move at " + x + ", " + y + " was not valid.");
            throw new IllegalAccessException("Can't move to ["+x+","+y+"]");
        }
        Log.i("GameBoardView", "User's move at " + x + "," + y + " was valid w/take of " + captured + " piece(s)");

        if(game.getPlayer() == GameDataModel.ReversiCell.WHITE){
            game.setBlackPieces(game.getBlackPieces()-captured);
            game.setWhitePieces(game.getWhitePieces()+captured+1);
        }else {
            game.setBlackPieces(game.getBlackPieces()+captured+1);
            game.setWhitePieces(game.getWhitePieces()-captured);
        }

        game.updateLoopControl(true);
        return new SelectionPhase(game);
    }

    @Override
    public IState switchPlayer(GameDataModel.GameMode mode) {
        game.setGameMode(mode);
        return this;
    }

    @Override
    public IState surrender() {
        game.over();
        return new GameOver(game);
    }
}
