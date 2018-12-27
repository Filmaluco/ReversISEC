package com.example.rmcsilva.reverisectest.ReversiLogic.StateMachine;


import com.example.rmcsilva.reverisectest.ReversiLogic.GameDataModel;

public class StateAdapter implements IState {

    protected GameDataModel game;

    public StateAdapter(GameDataModel g) { this.game = g; }

    @Override
    public IState start() { return this; }

    @Override
    public IState surrender() {
        return this;
    }

    @Override
    public IState gameOver() {
        return this;
    }

    @Override
    public IState applyRules() {
        return this;
    }

    @Override
    public IState switchPlayer(GameDataModel.GameMode mode) { return this; }

    @Override
    public IState makeMove(int x, int y) throws IllegalAccessException {
        return this;
    }

    @Override
    public IState nextPlayer() {
        return this;
    }

    @Override
    public IState canPlay() {
        return this;
    }

    @Override
    public GameDataModel getGame() { return game; }

    @Override
    public IState getState() {
        return this;
    }
}
