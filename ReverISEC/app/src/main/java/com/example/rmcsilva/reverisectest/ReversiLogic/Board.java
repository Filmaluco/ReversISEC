package com.example.rmcsilva.reverisectest.ReversiLogic;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.example.rmcsilva.reverisectest.R;


public class Board extends View{

    public enum ReversiCell {
        EMPTY,
        WHITE,
        BLACK
    }

    public static class BoardPosition {
        public int y = -1;
        public int x = -1;
    }

    // here were some hardcoded assumptions
    //TODO: protect with fragment WIDTH or HEIGHT (depending on the biggest)
    int BOARD_SCREEN_SIZE = 500;

    int BOARD_DIMS = 8;
    int CELL_SIZE = BOARD_SCREEN_SIZE/BOARD_DIMS;
    int PIECE_RADIUS = 4*CELL_SIZE /10;
    int CELL_PADDING = (CELL_SIZE)/2;


    Paint paint = new Paint();
    GameState state;
    Context context;

    public Board(Context context) {
        super(context);
        Log.e("GameBoardView", "Starting");
        this.context = context;
        state = new GameState(BOARD_DIMS, this);

        // listen to touch events so we can handle the user's move.
        this.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // on touch down, calculate what square the user tried to touch.
                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    int x = (int)(event.getX() * BOARD_DIMS / BOARD_SCREEN_SIZE);
                    int y = (int)(event.getY() * BOARD_DIMS / BOARD_SCREEN_SIZE);
                    if (x >= BOARD_DIMS || y >= BOARD_DIMS || x<0 || y<0) {
                        return false;
                    }
                    // pass the board touch on
                    handleUserMove(x, y);
                    return true;
                }
                return false;
            }
        });
    }

    // handle an attempted user move.
    public void handleUserMove(int x, int y) {
        Log.v("GameBoardView", "User tried to move at " + x + ", " + y);

        // is it the user's turn?
        if(state.currentPlayer != ReversiCell.WHITE) {
            Log.e("GameBoardView", "It wasn't the user's turn! Reprimanding ;)");
            Toast.makeText(this.context, "Not your turn", Toast.LENGTH_SHORT).show();
            return;
        }

        // is the selected square a valid play? (unoccupied, would result in flips)
        int captured = state.move(x, y, true);
        if (captured == 0) {
            Log.e("GameBoardView", "User's move at " + x + ", " + y + " was not valid.");
            Toast.makeText(this.context, "Can't move there", Toast.LENGTH_SHORT).show();
            return;
        }
        Log.i("GameBoardView", "User's move at " + x + "," + y + " was valid w/take of " + captured + " piece(s)");

        // TODO: just invalidate the screen area around the flipped/new pieces?
        this.invalidate();

        state.nextTurn(false);
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

        // draw vertical board lines
        paint.setColor(getResources().getColor(R.color.colorPrimaryLigh));
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
                ReversiCell piece = state.board[y][x];
                if(piece == ReversiCell.WHITE || piece == ReversiCell.BLACK) {
                    if(piece == ReversiCell.WHITE) {
                        paint.setColor(Color.WHITE);
                    }
                    if(piece == ReversiCell.BLACK) {
                        paint.setColor(Color.BLACK);
                    }
                    canvas.drawCircle(
                            (y * CELL_SIZE) + CELL_PADDING,
                            (x * CELL_SIZE) + CELL_PADDING,
                            PIECE_RADIUS, // radius
                            paint);
                }
            }
        }
    }
}
