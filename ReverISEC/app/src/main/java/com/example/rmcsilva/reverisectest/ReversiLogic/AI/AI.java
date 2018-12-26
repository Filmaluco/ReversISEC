package com.example.rmcsilva.reverisectest.ReversiLogic.AI;

import com.example.rmcsilva.reverisectest.ReversiLogic.BoardPosition;
import com.example.rmcsilva.reverisectest.ReversiLogic.GameState;

public interface AI {
    public BoardPosition computerMove(GameState state);
}
