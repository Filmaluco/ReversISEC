package com.example.rmcsilva.reverisectest.ReversiLogic.StateMachine;

import com.example.rmcsilva.reverisectest.ReversiLogic.GameDataModel;
import  com.example.rmcsilva.reverisectest.ReversiLogic.GameDataModel.*;

public interface IState {

    IState start(GameMode mode);
    IState surrender();
    IState gameOver();

    IState applyRules();
    IState switchPlayer();
    IState makeMove(int x, int y);
    IState nextPlayer();
    IState canPlay();


    GameDataModel getGame();
    IState getState();
}
