package com.example.rmcsilva.reverisectest.ReversiLogic;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;

import com.example.rmcsilva.reverisectest.R;
import com.example.rmcsilva.reverisectest.ReversiLogic.GameDataModel.ReversiCell;


public class Board extends View{

    //Required for AI
    public static class BoardPosition {
        public int y = -1;
        public int x = -1;
    }

    // here were some hardcoded assumptions
    //TODO: protect with fragment WIDTH or HEIGHT (depending on the biggest)
    public int BOARD_SCREEN_SIZE = 500;

    public int BOARD_DIMS = 8;
    public int CELL_SIZE = BOARD_SCREEN_SIZE/BOARD_DIMS;
    public int PIECE_RADIUS = 4*CELL_SIZE /10;
    public int CELL_PADDING = (CELL_SIZE)/2;

    Paint paint = new Paint();
    GameDataModel game;
    Context context;

    @SuppressLint("ClickableViewAccessibility")
    public Board(Context context, GameDataModel game) {
        super(context);
        Log.e("GameBoardView", "Starting");
        this.context = context;
        this.game = game;
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
        Log.i("GameBoardView", "onMeasure called with " + widthMeasureSpec + "x" + heightMeasureSpec);

        // The square board should fully fill the smaller dimension
        int parentWidth = MeasureSpec.getSize(widthMeasureSpec);
        int parentHeight = MeasureSpec.getSize(heightMeasureSpec);
        if(parentHeight > parentWidth) {
            BOARD_SCREEN_SIZE = parentWidth;
        } else {
            BOARD_SCREEN_SIZE = parentHeight;
        }

        Log.i("GameBoardView", "board size of " + BOARD_SCREEN_SIZE);
        this.setMeasuredDimension(BOARD_SCREEN_SIZE, BOARD_SCREEN_SIZE);
        CELL_SIZE = BOARD_SCREEN_SIZE/BOARD_DIMS;
        PIECE_RADIUS = 4 * CELL_SIZE / 10;
        CELL_PADDING = CELL_SIZE / 2;
    }


    @Override
    public void onDraw(Canvas canvas) {
        int x, y;

        //Update line based on game mode
        if(game.getGameMode() == GameDataModel.GameMode.COMPUTER){
            paint.setColor(getResources().getColor(R.color.colorPrimaryLigh));
        }else {
            if(game.getPlayer() == ReversiCell.WHITE)  paint.setColor(Color.GRAY); //white is too agressive gray is nhee TODO: I leave up to you Ricardo
            else                                       paint.setColor(Color.BLACK);
        }

        paint.setStrokeWidth(2);
        for(y=0; y<BOARD_DIMS; y++) {
            canvas.drawLine(y*CELL_SIZE, 0, y*CELL_SIZE, BOARD_SCREEN_SIZE, paint);
        }
        canvas.drawLine(BOARD_SCREEN_SIZE, 0, BOARD_SCREEN_SIZE, BOARD_SCREEN_SIZE, paint);

        // draw horizontal board lines
        for(y=0; y<BOARD_DIMS; y++) {
            canvas.drawLine(0, y*CELL_SIZE, BOARD_SCREEN_SIZE, y*CELL_SIZE, paint);
        }
        canvas.drawLine(0, BOARD_SCREEN_SIZE, BOARD_SCREEN_SIZE, BOARD_SCREEN_SIZE, paint);


        // now draw the pieces on the board
        for(y=0; y<BOARD_DIMS; y++){
            for(x=0; x<BOARD_DIMS; x++){
                ReversiCell piece = game.getCell(x, y);
                if(piece == ReversiCell.WHITE || piece == ReversiCell.BLACK) {
                    if(piece == ReversiCell.WHITE) {
                        paint.setColor(Color.WHITE);
                    }
                    if(piece == ReversiCell.BLACK) {
                        paint.setColor(Color.BLACK);
                    }
                    canvas.drawCircle(
                            (x * CELL_SIZE) + CELL_PADDING,
                            (y * CELL_SIZE) + CELL_PADDING,
                            PIECE_RADIUS, // radius
                            paint);
                }
            }
        }
    }
}
