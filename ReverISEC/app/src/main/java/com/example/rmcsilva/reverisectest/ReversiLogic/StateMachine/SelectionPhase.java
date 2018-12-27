package com.example.rmcsilva.reverisectest.ReversiLogic.StateMachine;

import com.example.rmcsilva.reverisectest.ReversiLogic.GameDataModel;

public class SelectionPhase extends StateAdapter {
    public SelectionPhase(GameDataModel g) {
        super(g);
    }

    @Override
    public IState applyRules() {
        //TODO: special card1
        //TODO: special card2


        return new PlayerSelection(game);
    }
}
