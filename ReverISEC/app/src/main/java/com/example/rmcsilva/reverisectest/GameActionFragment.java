package com.example.rmcsilva.reverisectest;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.rmcsilva.reverisectest.ReversiLogic.Board;
import com.example.rmcsilva.reverisectest.ReversiLogic.GameDataModel;
import com.example.rmcsilva.reverisectest.ReversiLogic.StateMachine.GameSetup;
import com.example.rmcsilva.reverisectest.ReversiLogic.StateMachine.IState;
import com.example.rmcsilva.reverisectest.ReversiLogic.StateMachine.PlayerSelection;
import com.example.rmcsilva.reverisectest.ReversiLogic.StateMachine.SelectionPhase;

public class GameActionFragment extends Fragment {

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href="http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void gameOver();
        void updateScore(int black, int white);
        GameDataModel.GameMode getGameMode();
        void newTurn();
    }

    //----------------------------------------------------------------------------------------------
    // Variables
    //----------------------------------------------------------------------------------------------
    //Required
    private OnFragmentInteractionListener mListener;
    Activity mActivity;

    //View
    Board board;

    //GameLogic
    IState state;
    GameDataModel.GameMode gameMode;


    //----------------------------------------------------------------------------------------------
    // Constructors
    //----------------------------------------------------------------------------------------------

    public GameActionFragment() {
        // Required empty public constructor
    }


    //----------------------------------------------------------------------------------------------
    // Fragment life cycle
    //----------------------------------------------------------------------------------------------

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        try {
            mListener = (OnFragmentInteractionListener) getActivity();
            mActivity = getActivity();
            gameMode = mListener.getGameMode();
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString()
                    + " must implement OnFragmentInteractionListener");
        }

        if (savedInstanceState != null)
            state = new GameSetup((GameDataModel) savedInstanceState.getSerializable("game"));

        if(state == null)
            state = new GameSetup(new GameDataModel(gameMode));

        state = state.start();

        //Start game
        try{
            state = state.canPlay();
        } catch (Exception ignored){/*Debug mode*/}

        board = new Board(getContext(), state.getGame());
        board.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        board.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // on touch down, calculate what square the user tried to touch.
                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    int x = (int)(event.getX() * board.BOARD_DIMS / board.BOARD_SCREEN_SIZE);
                    int y = (int)(event.getY() * board.BOARD_DIMS / board.BOARD_SCREEN_SIZE);
                    if (x >= board.BOARD_DIMS || y >= board.BOARD_DIMS || x<0 || y<0) {
                        return false;
                    }
                    // pass the board touch on
                    try { state = state.makeMove(x, y);
                    } catch (IllegalAccessException e) {
                        vibrate();
                        Toast.makeText(getContext(), R.string.cantPlayThere, Toast.LENGTH_SHORT).show();
                    }

                    //GAME CONTROLLER --------------------------------------------------------------
                    //game controller logic its mostly here since a single touch is responsible for 90% of the interactions

                    // State Machine full rotation
                    state = state.applyRules();
                    state = state.gameOver();
                    state = state.nextPlayer();
                    //Check if it's an impossible scenario -----------------------------------------
                    try {
                        state = state.canPlay();
                    }catch (Exception e){
                        state = new PlayerSelection(state.getGame());
                        state = state.nextPlayer();
                        try{ state = state.canPlay(); } catch (Exception ignored){/*game is over anyway*/}
                    } //----------------------------------------------------------------------------
                    // UI interaction
                    updateScore();
                    newTurn();
                    board.invalidate();
                    // Check if game is over
                    if(state.getGame().isOver()) {
                        state = state.gameOver();
                        gameOver();
                    }
                    return true;
                }
                return false;
            }


        });
        return board;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mActivity = null;
        mListener = null;
    }

    //----------------------------------------------------------------------------------------------
    // Helper Methods
    //----------------------------------------------------------------------------------------------

    public void vibrate(){
        try {
            Vibrator v = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE));
            } else {
                v.vibrate(100);
            }
        }catch (Exception ignored){}
    }

    //----------------------------------------------------------------------------------------------
    // View Interaction (frag -> view)
    //----------------------------------------------------------------------------------------------
    public void updateScore() {

        mListener.updateScore(state.getGame().getBlackPieces(),
                              state.getGame().getWhitePieces());
    }

    private void gameOver(){
        mListener.gameOver();
    }

    private void newTurn(){
        mListener.newTurn();
    }


    //----------------------------------------------------------------------------------------------
    // View Interaction (view -> frag)
    //----------------------------------------------------------------------------------------------
    public boolean canExtra() {
        return state.getGame().canExtra();
    }

    public void useExtra() {
        state.getGame().useExtra();
    }

    public boolean canSkip() {
        return state.getGame().canSkip();
    }

    public void useSkip(){
        state.getGame().useSkip();
        state.getGame().useMove();
        state = new SelectionPhase(state.getGame());
        state = state.applyRules();
        if(state.getGame().isOver()){ state = state.gameOver(); return;}
        state = state.nextPlayer();
        state = state.canPlay();
        newTurn();
        board.invalidate();
    }

    public void reset() {
        //ResetDATA
        state = new GameSetup(new GameDataModel(gameMode));
        state = state.start();
        //Reset Board
        board.reset(state.getGame());
        board.invalidate();
        //UpdateUI
        updateScore();
        newTurn();
    }

    public void switchGameMode(GameDataModel.GameMode newMode){
        gameMode = newMode;
        if(state.getGame().getPlayer() == GameDataModel.ReversiCell.BLACK){
            state.getGame().swapSides();
        }
        state = state.switchPlayer(newMode);
        updateScore();
        newTurn();
        board.invalidate();
    }

    //----------------------------------------------------------------------------------------------
    // Screen rotations....
    //----------------------------------------------------------------------------------------------

    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putSerializable("game", state.getGame());
    }

}

