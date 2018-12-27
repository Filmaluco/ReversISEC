package com.example.rmcsilva.reverisectest.ReversiLogic.StateMachine;

import com.example.rmcsilva.reverisectest.ReversiLogic.GameDataModel;
import  com.example.rmcsilva.reverisectest.ReversiLogic.GameDataModel.*;

public interface IState {

    IState start(GameMode mode);
    IState surrender();
    IState gameOver();

    IState applyRules();
    IState switchPlayer(GameMode mode);
    IState makeMove(int x, int y) throws IllegalAccessException;
    IState nextPlayer();
    IState canPlay() throws IllegalStateException;


    GameDataModel getGame();
    IState getState();
}
