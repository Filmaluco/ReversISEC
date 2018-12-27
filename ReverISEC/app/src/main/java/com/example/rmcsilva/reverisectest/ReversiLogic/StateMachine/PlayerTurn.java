package com.example.rmcsilva.reverisectest.ReversiLogic.StateMachine;

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

        return new ActionPhase(game);
    }
}
