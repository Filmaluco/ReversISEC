package com.example.rmcsilva.reverisectest.ReversiLogic.AI;

import com.example.rmcsilva.reverisectest.ReversiLogic.Board.BoardPosition;
import com.example.rmcsilva.reverisectest.ReversiLogic.GameDataModel;

public interface AI {
    BoardPosition computerMove(GameDataModel game);
}
