package com.example.rmcsilva.reverisectest.ReversiLogic.AI;

import com.example.rmcsilva.reverisectest.ReversiLogic.GameDataModel;

public interface AI {
    NegamaxAI.BoardPosition computerMove(GameDataModel game);
}
