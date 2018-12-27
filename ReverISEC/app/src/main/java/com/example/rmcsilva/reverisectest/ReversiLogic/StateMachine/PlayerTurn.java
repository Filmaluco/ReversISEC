package com.example.rmcsilva.reverisectest.ReversiLogic.StateMachine;

import com.example.rmcsilva.reverisectest.ReversiLogic.GameDataModel;

public class PlayerTurn extends StateAdapter {
    public PlayerTurn(GameDataModel g) {
        super(g);
    }

    @Override
    public IState canPlay() {
        if(!game.isAMovePossible()){
            game.updateLoopControl(false);
            return new PlayerSelection(game);
        }

        return new ActionPhase(game);
    }
}
