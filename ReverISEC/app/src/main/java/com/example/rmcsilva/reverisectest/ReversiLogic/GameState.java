package com.example.rmcsilva.reverisectest.ReversiLogic;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;

import com.example.rmcsilva.reverisectest.ReversiLogic.AI.AI;
import com.example.rmcsilva.reverisectest.ReversiLogic.AI.NegamaxAI;

public class GameState {
    public CellType board[][];
    public int dims;
    public CellType currentPlayer;
    public AI ai;
    public Board view;

    // Create a new Game State for a NxN board.
    public GameState(int dims, Board view) {
        this.dims = dims;
        this.view = view;
        board = new CellType[dims][dims];
        ai = new NegamaxAI();
        clearBoard();
    }

    // Create a new Game State based on another.
    public GameState(GameState copy) {
        this.dims = copy.dims;
        this.currentPlayer = copy.currentPlayer;
        this.ai = copy.ai;
        this.board = new CellType[dims][dims];
        int i, j;
        for(i=0; i<dims; i++){
            for(j=0; j<dims; j++){
                this.board[i][j] = copy.board[i][j];
            }
        }
    }

    // Initialize the board to a starting state.
    public void clearBoard() {

        // empty the board.
        int i, j;
        for(i=0; i<dims; i++){
            for(j=0; j<dims; j++){
                board[i][j] = CellType.EMPTY;
            }
        }

        // place the starting pieces.
        i = (int)((dims-1)/2);
        board[i][i] = CellType.WHITE;
        board[i+1][i+1] = CellType.WHITE;
        board[i][i+1] = CellType.BLACK;
        board[i+1][i] = CellType.BLACK;

        // white goes first
        currentPlayer = CellType.WHITE;

        // redraw the board.
        view.invalidate();
    }

    // The game is over! Calculate winner & display / record.
    public void gameOver() {
        int whitePieces = 0;
        int blackPieces = 0;
        int i, j;
        Log.i("GameState", "Game over, man!");

        // tally the counts
        for(i=0;i<dims;i++){
            for(j=0;j<dims;j++){
                if(board[i][j] == CellType.WHITE){
                    whitePieces++;
                }
                if(board[i][j] == CellType.BLACK){
                    blackPieces++;
                }
            }
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        /*
        if(whitePieces > blackPieces) {
            builder.setTitle(R.string.alert_gameover_title_white_wins);
        }
        if(whitePieces < blackPieces) {
            builder.setTitle(R.string.alert_gameover_title_black_wins);
        }
        if(whitePieces == blackPieces) {
            builder.setTitle(R.string.alert_gameover_title_tie);
        }
        */

        builder.setMessage("GameOver {implement system to load from string values the condition of the game}");

        builder.setPositiveButton("New Game", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                clearBoard();
            }
        });
        builder.create().show();
        this.currentPlayer = CellType.EMPTY;
    }


    // background task to do computer move computation OFF of the UI thread.
    private class AITask extends AsyncTask<GameState, Integer, BoardPosition> {

        @Override
        protected BoardPosition doInBackground(GameState... states) {

            Log.i("GameState", "Computation thread started...");

            // sleep for a little bit to give the human some time to see their move
            // before the computer makes their move!
            try {
                Thread.sleep(300);
            } catch(InterruptedException e) {
                e.printStackTrace();
            }

            // okay, now do the difficult work.
            return (ai.computerMove(states[0]));
        }


        @Override
        protected void onPostExecute(BoardPosition result) {
            // done computing our move, now back on the main thread.
            Log.i("GameState", "Computation thread finished.");
            super.onPostExecute(result);

            Log.i("GameState", "Computer moving at " + result.i + ", " + result.j);
            int numCaptured = move(result.i, result.j, true);
            Log.i("GameState", "  Computer captured " + numCaptured);

            view.invalidate(); // redraw the board now that the computer's moved!

            nextTurn(false); // back to the human!
        }
    }


    public void swapSides() {
        assert(currentPlayer != CellType.EMPTY);
        currentPlayer = (currentPlayer == CellType.WHITE)? CellType.BLACK : CellType.WHITE;
    }


    // The current side has ended their turn.
    // Switch sides and see if the game is over.
    public void nextTurn(boolean lastMoveWasForfeit) {

        swapSides();

        Log.i("GameState", "Now it's " + currentPlayer + "'s turn");

        if(!this.isAMovePossible()) {
            if(lastMoveWasForfeit) {
                Log.i("GameState", "No moves left, ending game");
                this.gameOver();
            } else {
                Log.i("GameState", "Couldn't move! Checking other side.");
                this.nextTurn(true);
            }
        } else {
            // if a move is possible and it's the computer's turn,
            // fire up the AI task to make a move!
            if(currentPlayer == CellType.BLACK) {
                new AITask().execute(this);
            }
        }
    }


    // checks to see if the current player can make any move at all.
    // TODO: return an array of permissible moves instead.
    public boolean isAMovePossible() {
        int i, j;
        for(i=0;i<dims;i++){
            for(j=0;j<dims;j++){
                int numCaptured = this.move(i, j, false);
                if(numCaptured > 0) {
                    return true;
                }
            }
        }
        return false;
    }


    // compute whether a given move is valid, and, possibly, do it.
    public int move(int i, int j, Boolean doMove) {
        assert(i >= 0);
        assert(i < dims);
        assert(j >= 0);
        assert(j < dims);

        // if the proposed space is already occupied, bail.
        if(board[i][j] != CellType.EMPTY){
            return 0;
        }

        // explore whether any of the eight 'rays' extending from the current piece
        // have a line of at least one opponent piece terminating in one of our own pieces.
        int dx, dy;
        int totalCaptured = 0;
        for(dx = -1; dx <= 1; dx++){
            for(dy = -1; dy <= 1; dy++){
                // (skip the null movement case)
                if(dx == 0 && dy == 0){ continue; }

                // explore the ray for potential captures.
                for(int steps = 1; steps < dims; steps++){
                    int ray_i = i + (dx*steps);
                    int ray_j = j + (dy*steps);

                    // if the ray has gone out of bounds, give up
                    if(ray_i < 0 || ray_i >= dims || ray_j < 0 || ray_j >= dims){ break; }

                    CellType ray_cell = board[ray_i][ray_j];

                    // if we hit a blank cell before terminating a sequence, give up
                    if(ray_cell == CellType.EMPTY){ break; }

                    // if we hit a piece that's our own, let's capture the sequence
                    if(ray_cell == currentPlayer){
                        if(steps > 1){
                            // we've gone at least one step, capture the ray.
                            totalCaptured += steps - 1;
                            if(doMove) { // okay, let's actually execute on this
                                while(steps-- > 0){
                                    board[i + (dx*steps)][j + (dy*steps)] = currentPlayer;
                                }
                            }
                        }
                        break;
                    }
                }
            }
        }
        return totalCaptured;
    }
}
