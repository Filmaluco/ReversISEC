package com.example.rmcsilva.reverisectest.ReversiLogic.StateMachine;

import com.example.rmcsilva.reverisectest.ReversiLogic.AI.AI;
import com.example.rmcsilva.reverisectest.ReversiLogic.Board;
import com.example.rmcsilva.reverisectest.ReversiLogic.Board.*;

public class StateAdapter implements IState {

    private ReversiCell board[][];
    private int dims;
    private ReversiCell currentPlayer;
    private AI ai;
    private Board view;


}
