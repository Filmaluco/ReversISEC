package com.example.rmcsilva.reverisectest.ReversiLogic.StateMachine;

import com.example.rmcsilva.reverisectest.ReversiLogic.GameDataModel;

public class PlayerSelection extends StateAdapter {
    public PlayerSelection(GameDataModel g) {
        super(g);
    }

    @Override
    public IState nextPlayer() {
        game.swapSides();

        if(game.isOver()){
            return this;
        }

        if(game.getBlackPieces() + game.getWhitePieces() == 8*8){
            game.over();
            return this;
        }

        if(game.inLopp()){
            game.over();
            return this;
        }

        return new PlayerTurn(game);
    }

    @Override
    public IState gameOver() {
        if(game.getBlackPieces() + game.getWhitePieces() == 8*8){
            game.over();
            return new GameOver(game);
        }
        if(game.inLopp()){
            game.over();
            return new GameOver(game);
        }

        return this;
    }
}
