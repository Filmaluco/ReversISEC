package com.example.rmcsilva.reverisectest.ReversiLogic;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.rmcsilva.reverisectest.ReversiLogic.Board.*;

import java.io.Serializable;

public class GameDataModel implements Serializable {

    public final static long serialVersionUID = 1L;

    //----------------------------------------------------------------------------------------------
    // Variables
    //----------------------------------------------------------------------------------------------
    //Public
    public enum GameMode { MULTIPLAYER, LOCAL_MULTIPLAYER, COMPUTER }
    public enum ReversiCell { EMPTY, WHITE, BLACK}
    private enum moveStatus { NOT_USED, USED, SELECTED}

    //Private
        //CONSTANTS
    private final static int SIZE = 8;
        //Logic
    private ReversiCell boardData[][];
    private ReversiCell currentPlayer;
    private int loopControl;
    private GameMode gameMode;
    private boolean isOver;
    private moveStatus skipMove[], extraMove[];
        //UI
    private int whitePieces = 0;
    private int blackPieces = 0;

    //----------------------------------------------------------------------------------------------
    // Constructors
    //----------------------------------------------------------------------------------------------

    /**
     * Create a new Game State for a 8x8 board.
     * @param gameMode creates the based on the given GameMode
     * @see GameMode
     */
    public GameDataModel(GameMode gameMode) {
        this.boardData = new ReversiCell[SIZE][SIZE];
        this.loopControl = 1;
        this.gameMode = gameMode;
        this.isOver = false;
        this.skipMove = new moveStatus[2]; skipMove[0] = moveStatus.NOT_USED; skipMove[1] = moveStatus.NOT_USED;
        this.extraMove = new moveStatus[2]; extraMove[0] = moveStatus.NOT_USED; extraMove[1] = moveStatus.NOT_USED;

        //this.debugBoard();
        this.clearBoard();
    }


    /**
     * Create a new Game State based on another.
     * @param copy (based on...)
     */
    public GameDataModel(GameDataModel copy) {
        this.currentPlayer = copy.currentPlayer;
        this.boardData = new ReversiCell[SIZE][SIZE];
        this.loopControl = copy.loopControl;
        this.gameMode = copy.gameMode;
        this.isOver = copy.isOver;
        this.skipMove = copy.skipMove;
        this.extraMove = copy.extraMove;
        this.whitePieces = copy.whitePieces;
        this.blackPieces = copy.blackPieces;
        for(int y=0; y<SIZE; y++){
            for(int x=0; x<SIZE; x++){
                this.boardData[y][x] = copy.boardData[y][x];
            }
        }
    }

    //----------------------------------------------------------------------------------------------
    // LOGIC Methods
    //----------------------------------------------------------------------------------------------

    /**
     * Clears the board
     */
    private void clearBoard() {

        // empty the board.
        for(int y=0; y<SIZE; y++){
            for(int x=0; x<SIZE; x++){
                boardData[y][x] = ReversiCell.EMPTY;
            }
        }

        // place the starting pieces.
        // remember that true arrays start at zero
        int i = (SIZE-1)/2;
        boardData[i][i]     = ReversiCell.WHITE;
        boardData[i+1][i+1] = ReversiCell.WHITE;
        whitePieces = 2;
        //-------
        boardData[i][i+1]   = ReversiCell.BLACK;
        boardData[i+1][i]   = ReversiCell.BLACK;
        blackPieces = 2;


        // white goes first (sorry Martin Luther King)
        currentPlayer = ReversiCell.WHITE;
    }

    /**
     * Simulates an impossible scneario
     */
    private void debugBoard() {
        //1 - linha
        boardData[0][0] = ReversiCell.EMPTY;
        boardData[0][1] = ReversiCell.EMPTY;
        boardData[0][2] = ReversiCell.EMPTY;
        boardData[0][3] = ReversiCell.WHITE;
        boardData[0][4] = ReversiCell.WHITE;
        boardData[0][5] = ReversiCell.WHITE;
        boardData[0][6] = ReversiCell.WHITE;
        boardData[0][7] = ReversiCell.BLACK;
//----------------------------------
        boardData[1][0] = ReversiCell.EMPTY;
        boardData[1][1] = ReversiCell.EMPTY;
        boardData[1][2] = ReversiCell.WHITE;
        boardData[1][3] = ReversiCell.EMPTY;
        boardData[1][4] = ReversiCell.BLACK;
        boardData[1][5] = ReversiCell.BLACK;
        boardData[1][6] = ReversiCell.BLACK;
        boardData[1][7] = ReversiCell.BLACK;
//----------------------------------
        boardData[2][0] = ReversiCell.EMPTY;
        boardData[2][1] = ReversiCell.EMPTY;
        boardData[2][2] = ReversiCell.EMPTY;
        boardData[2][3] = ReversiCell.WHITE;
        boardData[2][4] = ReversiCell.BLACK;
        boardData[2][5] = ReversiCell.BLACK;
        boardData[2][6] = ReversiCell.BLACK;
        boardData[2][7] = ReversiCell.BLACK;
//----------------------------------
        boardData[3][0] = ReversiCell.EMPTY;
        boardData[3][1] = ReversiCell.EMPTY;
        boardData[3][2] = ReversiCell.EMPTY;
        boardData[3][3] = ReversiCell.WHITE;
        boardData[3][4] = ReversiCell.BLACK;
        boardData[3][5] = ReversiCell.WHITE;
        boardData[3][6] = ReversiCell.BLACK;
        boardData[3][7] = ReversiCell.BLACK;
//----------------------------------
        boardData[4][0] = ReversiCell.EMPTY;
        boardData[4][1] = ReversiCell.WHITE;
        boardData[4][2] = ReversiCell.WHITE;
        boardData[4][3] = ReversiCell.WHITE;
        boardData[4][4] = ReversiCell.BLACK;
        boardData[4][5] = ReversiCell.WHITE;
        boardData[4][6] = ReversiCell.WHITE;
        boardData[4][7] = ReversiCell.BLACK;
//----------------------------------
        boardData[5][0] = ReversiCell.EMPTY;
        boardData[5][1] = ReversiCell.EMPTY;
        boardData[5][2] = ReversiCell.WHITE;
        boardData[5][3] = ReversiCell.WHITE;
        boardData[5][4] = ReversiCell.BLACK;
        boardData[5][5] = ReversiCell.WHITE;
        boardData[5][6] = ReversiCell.WHITE;
        boardData[5][7] = ReversiCell.BLACK;
//----------------------------------
        boardData[6][0] = ReversiCell.EMPTY;
        boardData[6][1] = ReversiCell.EMPTY;
        boardData[6][2] = ReversiCell.EMPTY;
        boardData[6][3] = ReversiCell.WHITE;
        boardData[6][4] = ReversiCell.BLACK;
        boardData[6][5] = ReversiCell.WHITE;
        boardData[6][6] = ReversiCell.WHITE;
        boardData[6][7] = ReversiCell.BLACK;
//----------------------------------
        boardData[7][0] = ReversiCell.EMPTY;
        boardData[7][1] = ReversiCell.EMPTY;
        boardData[7][2] = ReversiCell.EMPTY;
        boardData[7][3] = ReversiCell.WHITE;
        boardData[7][4] = ReversiCell.BLACK;
        boardData[7][5] = ReversiCell.WHITE;
        boardData[7][6] = ReversiCell.WHITE;
        boardData[7][7] = ReversiCell.WHITE;
//----------------------------------

        whitePieces = 24;
        blackPieces = 19;
        currentPlayer = ReversiCell.WHITE;

    }

    /** Swaps current player */
    public void swapSides() {
        currentPlayer = (currentPlayer == ReversiCell.WHITE)? ReversiCell.BLACK : ReversiCell.WHITE;
    }

    /**
     * Updates the control of loops
     * @param moveMade true means a player successfully moved
     */
   public void updateLoopControl(boolean moveMade){
        if(moveMade) loopControl = loopControl < 2 ? loopControl+1 : loopControl;
        else         loopControl = loopControl > 0 ? loopControl-1 : loopControl;
   }

    /**
     * Checks if the game entered a loop ( turns of non movement)
     * @return true if 2 turns of non movement were made in succession
     */
   public boolean inLopp(){
       return loopControl < 1;
   }

    /**
     * Checks if there's any available move left
     * @return true if a move is possible
     */
    public boolean isAMovePossible() {
    // TODO: return an array of permissible moves instead.
        for(int y=0;y<SIZE;y++){
            for(int x=0;x<SIZE;x++){
                int numCaptured = this.move(y, x, false);
                if(numCaptured > 0) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Computes whether a given move is valid, and, possibly, do it.
     * @param y board Y coordinate
     * @param x board X coordinate
     * @param doMove if true means the move is executed, false just calculates the number of captured cells
     * @throws IllegalArgumentException if coordinates do not fit on the board
     * @return number of captured cells
     */
    public int move(int x, int y, Boolean doMove) throws IllegalArgumentException{
        if( !(y >= 0) || !(y < SIZE) || !(x >= 0) || !(x < SIZE)) throw new IllegalArgumentException("Given coordinates are not possible");

        // if the proposed space is already occupied, bail.
        if(boardData[y][x] != ReversiCell.EMPTY){
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
                for(int steps = 1; steps < SIZE; steps++){
                    int ray_i = y + (dx*steps);
                    int ray_j = x + (dy*steps);

                    // if the ray has gone out of bounds, give up
                    if(ray_i < 0 || ray_i >= SIZE || ray_j < 0 || ray_j >= SIZE){ break; }

                    ReversiCell ray_cell = boardData[ray_i][ray_j];

                    // if we hit a blank cell before terminating a sequence, give up
                    if(ray_cell == ReversiCell.EMPTY){ break; }

                    // if we hit a piece that's our own, let's capture the sequence
                    if(ray_cell == currentPlayer){
                        if(steps > 1){
                            // we've gone at least one step, capture the ray.
                            totalCaptured += steps - 1;
                            if(doMove) { // okay, let's actually execute on this
                                while(steps-- > 0){
                                    boardData[y + (dx*steps)][x + (dy*steps)] = currentPlayer;
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

    /**
     * Makes a move on the given coordinates
     * @param y board Y coordinate
     * @param x board X coordinate
     * @return number of captured cells
     * @throws IllegalArgumentException if coordinates do not fit on the board
     */
    public int move(int x, int y) throws IllegalArgumentException{
        return this.move(x, y, true);
    }

    /**
     * Gets game current mode
     * @return GameMode
     */
    public GameMode getGameMode() { return gameMode; }

    public void setWhitePieces(int whitePieces) {
        this.whitePieces = whitePieces;
    }

    public void setBlackPieces(int blackPieces) {
        this.blackPieces = blackPieces;
    }

    public void over() { this.isOver = true;}
    public boolean isOver() {return this.isOver;}

    public void setGameMode(GameMode mode) {
        this.gameMode = mode;
    }



    //----------------------------------------------------------------------------------------------
    // Special Moves
    //----------------------------------------------------------------------------------------------

    public void useSkip(){
        if( currentPlayer == ReversiCell.WHITE ) skipMove[0] = moveStatus.SELECTED;
        else                                     skipMove[1] = moveStatus.SELECTED;
    }

    public void useExtra(){
        if( currentPlayer == ReversiCell.WHITE ) extraMove[0] = moveStatus.SELECTED;
        else                                     extraMove[1] = moveStatus.SELECTED;
    }

    public void useMove(){
        skipMove[0] = skipMove[0] == moveStatus.SELECTED ?  moveStatus.USED : skipMove[0];
        skipMove[1] = skipMove[1] == moveStatus.SELECTED ?  moveStatus.USED : skipMove[1];
        extraMove[0] = extraMove[0] == moveStatus.SELECTED ?  moveStatus.USED : extraMove[0];
        extraMove[1] = extraMove[1] == moveStatus.SELECTED ?  moveStatus.USED : extraMove[1];
    }

    public boolean selectedSkip(){
        if( currentPlayer == ReversiCell.WHITE )  return skipMove[0] == moveStatus.SELECTED;
        else                                      return skipMove[1] == moveStatus.SELECTED;
    }

    public boolean selectedExtra(){
        if( currentPlayer == ReversiCell.WHITE )  return extraMove[0] == moveStatus.SELECTED;
        else                                      return extraMove[1] == moveStatus.SELECTED;
    }

    //----------------------------------------------------------------------------------------------
    // UI Methods
    //----------------------------------------------------------------------------------------------


    /** @return (int) number white pieces */
    public int getWhitePieces() { return whitePieces; }
    /** @return (int) number black pieces */
    public int getBlackPieces() { return blackPieces; }

    /**
     * White or Black
     * @return ReversiCell
     * @see ReversiCell
     */
    public ReversiCell getPlayer(){return currentPlayer;}

    /**
     * @param x coordinate on the board
     * @param y coordinate on the board
     * @return the type of cell on the given coordinates
     * @throws IllegalArgumentException if coordinates do not fit on the board
     */
    public ReversiCell getCell(int x, int y){
        if( !(y >= 0) || !(y < SIZE) || !(x >= 0) || !(x < SIZE)) throw new IllegalArgumentException("Given coordinates are not possible");
        return boardData[y][x];
    }

    public boolean canSkip(){
        if( currentPlayer == ReversiCell.WHITE )  return skipMove[0] == moveStatus.NOT_USED;
        else                                      return skipMove[1] == moveStatus.NOT_USED;
    }

    public boolean canExtra(){
        if( currentPlayer == ReversiCell.WHITE )  return extraMove[0] == moveStatus.NOT_USED;
        else                                      return extraMove[1] == moveStatus.NOT_USED;
    }

}
