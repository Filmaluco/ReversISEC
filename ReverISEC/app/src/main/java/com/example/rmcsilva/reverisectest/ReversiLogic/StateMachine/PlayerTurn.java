package com.example.rmcsilva.reverisectest.ReversiLogic.StateMachine;

import com.example.rmcsilva.reverisectest.ReversiLogic.AI.AI;
import com.example.rmcsilva.reverisectest.ReversiLogic.AI.NegamaxAI;
import com.example.rmcsilva.reverisectest.ReversiLogic.GameDataModel;

public class PlayerTurn extends StateAdapter {
    public PlayerTurn(GameDataModel g) {
        super(g);
    }

    @Override
    public IState canPlay() throws IllegalStateException{
        if(!game.isAMovePossible()){
            game.updateLoopControl(false);
            if(game.inLopp()){
                game.over();
                return new PlayerSelection(game);
            }
            throw new IllegalStateException("Player can't play");
        }

        //If its playing against an AI make the AI move --------------------------------------------
        if(game.getGameMode() == GameDataModel.GameMode.COMPUTER && game.getPlayer() == GameDataModel.ReversiCell.BLACK){
            NegamaxAI.BoardPosition aiMove = (new NegamaxAI()).computerMove(game);
            int captured = game.move(aiMove.x, aiMove.y);
            game.swapSides();
            if(captured == 0){
                game.updateLoopControl(false);
                return new ActionPhase(game);
            }
            game.updateLoopControl(true);
            game.setBlackPieces(game.getBlackPieces()+captured+1);
            game.setWhitePieces(game.getWhitePieces()-captured);

            if(game.getBlackPieces() + game.getWhitePieces() == 8*8){
                game.over();
                return new GameOver(game);
            }
            if(game.inLopp()){
                game.over();
                return new GameOver(game);
            }
            this.canPlay();
        }
        //------------------------------------------------------------------------------------------
        //TODO: internetMode (yeah right no time XD)

        return new ActionPhase(game);
    }
}
