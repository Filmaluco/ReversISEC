package com.example.rmcsilva.reverisectest.ReversiLogic.AI;

import com.example.rmcsilva.reverisectest.ReversiLogic.Board;
import com.example.rmcsilva.reverisectest.ReversiLogic.GameState;

public interface AI {
    public Board.BoardPosition computerMove(GameState state);
}
