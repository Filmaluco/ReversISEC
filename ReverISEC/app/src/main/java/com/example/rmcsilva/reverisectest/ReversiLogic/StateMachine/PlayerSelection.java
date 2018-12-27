package com.example.rmcsilva.reverisectest.ReversiLogic.StateMachine;

import com.example.rmcsilva.reverisectest.ReversiLogic.GameDataModel;

public class PlayerSelection extends StateAdapter {
    public PlayerSelection(GameDataModel g) {
        super(g);
    }

    @Override
    public IState nextPlayer() {
        game.swapSides();
        return new PlayerTurn(game);
    }
}
