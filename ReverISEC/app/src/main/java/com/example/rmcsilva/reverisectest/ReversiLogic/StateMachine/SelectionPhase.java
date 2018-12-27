package com.example.rmcsilva.reverisectest.ReversiLogic.StateMachine;

import com.example.rmcsilva.reverisectest.ReversiLogic.GameDataModel;

public class SelectionPhase extends StateAdapter {
    public SelectionPhase(GameDataModel g) {
        super(g);
    }

    @Override
    public IState applyRules() {
        if(game.selectedExtra()){
            game.useMove();
            return new PlayerTurn(game);
        }


        return new PlayerSelection(game);
    }
}
