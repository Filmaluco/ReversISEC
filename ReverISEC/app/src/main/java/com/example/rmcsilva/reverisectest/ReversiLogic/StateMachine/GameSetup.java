package com.example.rmcsilva.reverisectest.ReversiLogic.StateMachine;

import com.example.rmcsilva.reverisectest.ReversiLogic.GameDataModel;
import com.example.rmcsilva.reverisectest.ReversiLogic.GameDataModel.*;

public class GameSetup extends StateAdapter {
    public GameSetup(GameDataModel g) {
        super(g);
    }

    @Override
    public IState start(GameMode mode) {
        return new PlayerTurn(new GameDataModel(mode));
    }
}
