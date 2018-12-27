package com.example.rmcsilva.reverisectest.ReversiLogic.AI;

import com.example.rmcsilva.reverisectest.ReversiLogic.GameDataModel;

/**
 * @author dweekly
 * This AI was not made by the creators of the Game, it was developed and ajusted from dweekly code
 * @see <a href="https://github.com/dweekly">github.com/dweekly</a>
 */
public class NegamaxAI implements AI {

    public static class BoardPosition {
        public int y = -1;
        public int x = -1;
    }

    private static int maxPly = 3;
    private BoardPosition best;

    // See http://www.generation5.org/content/2002/game02.asp
    private static int weighting[][] = {
            {50, -1,5,2,2,5, -1,50},
            {-1,-10,1,1,1,1,-10,-1},
            { 5,  1,1,1,1,1,  1, 5},
            { 2,  1,1,0,0,1,  1, 2},
            { 2,  1,1,0,0,1,  1, 2},
            { 5,  1,1,1,1,1,  1, 5},
            {-1,-10,1,1,1,1,-10,-1},
            {50, -1,5,2,2,5, -1,50}
    };


    // evaluate a given board state for desirability
    private int eval(GameDataModel dataModel) {

        // TODO: We're currently hardcoded for 8x8, though could explore a wider
        // range of weightings based on the above table....

        // pretty highly value having the ability to move!
        int mobilityValue = 10;

        int boardScore = 0;
        int bestCapture = 0;

        for(int i=0; i<8; i++){
            for(int j=0; j<8; j++){
                GameDataModel.ReversiCell piece = dataModel.getCell(i, j);
                if(piece == GameDataModel.ReversiCell.EMPTY){
                    int capture = dataModel.move(i, j, false);
                    if(capture > 0) boardScore += mobilityValue;
                    if(capture > bestCapture) bestCapture = capture;
                } else {
                    int mul = (piece == dataModel.getPlayer())? 1 : -1;
                    boardScore += weighting[i][j] * mul;
                }
            }
        }

        boardScore += bestCapture;

        return boardScore;
    }


    // perform a negamax N-ply search for the best move.
    private int negamax(GameDataModel state, int ply) {
        assert(ply >= 0);
        if(ply == 0) { // we've gone as deep as we can, so just return the score for this node.
            return eval(state);
        }

        // TODO: order moves by desirability first to become negascout
        int alpha = Integer.MIN_VALUE;
        for(int i=0; i<8; i++){
            for(int j=0; j<8; j++){
                if(state.move(i, j, false) > 0) {
                    GameDataModel gs = new GameDataModel(state); // copy current game state
                    gs.move(i, j, true);
                    gs.swapSides();
                    int childWeight = -negamax(gs, ply-1);
                    if(childWeight > alpha) {
                        alpha = childWeight;
                        if(ply == maxPly) {
                            best.x = i;
                            best.y = j;
                        }
                    }
                }
            }
        }

        // if we just couldn't move, just try other side?
        if(alpha == Integer.MIN_VALUE){
            GameDataModel gs = new GameDataModel(state);
            gs.swapSides();
            return -negamax(gs, ply-1);
        }

        return alpha;
    }

    @Override
    public BoardPosition computerMove(GameDataModel state) {
        assert(state.getPlayer() == GameDataModel.ReversiCell.BLACK);
        best = new BoardPosition();
        negamax(state, maxPly);
        assert(best.y < 8 && best.x < 8);
        // note that best.y & x MAY be -1 if there's no legal move we could
        // make from the current position! Trap it anyhow b/c it's interesting
        assert(best.y >= 0 && best.x >= 0);
        return best;
    }
}
