package com.example.rmcsilva.reverisectest;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.rmcsilva.reverisectest.ReversiLogic.Board;
import com.example.rmcsilva.reverisectest.ReversiLogic.GameDataModel;
import com.example.rmcsilva.reverisectest.ReversiLogic.StateMachine.GameSetup;
import com.example.rmcsilva.reverisectest.ReversiLogic.StateMachine.IState;

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
    public interface OnFragmentInteractionListener { }

    //----------------------------------------------------------------------------------------------
    // Variables
    //----------------------------------------------------------------------------------------------
    //Required
    private OnFragmentInteractionListener mListener;

    //View
    View board;

    //GameLogic
    IState state;


    //----------------------------------------------------------------------------------------------
    // Constructors
    //----------------------------------------------------------------------------------------------

    public GameActionFragment() {
        // Required empty public constructor
    }


    //----------------------------------------------------------------------------------------------
    // Fragment life cycle
    //----------------------------------------------------------------------------------------------

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public void onStart(){
        super.onStart();

        //Make sure the view that calls this fragment has a listener to interact with this fragment
        try {
            mListener = (OnFragmentInteractionListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        state = new GameSetup(null);
        state = state.start(GameDataModel.GameMode.localMultiplayer);
        state = state.canPlay();
        updateViewScore();

        final Board board = new Board(getContext(), state.getGame());
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
                    state = state.makeMove(x, y);
                    state = state.applyRules();
                    state = state.nextPlayer();
                    state = state.canPlay();

                    try {
                        ((GamePhaseActivity) getActivity()).updateScore(state.getGame().getBlackPieces(),
                                state.getGame().getWhitePieces());
                    }catch (Exception e){

                        Log.w("Reversi", "Failed to updated UI" + e.getMessage());
                        e.printStackTrace();
                    }

                    //Check if its gameOver


                    v.invalidate();
                    return true;
                }
                return false;
            }
        });
        return board;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    //----------------------------------------------------------------------------------------------
    // "View"
    //----------------------------------------------------------------------------------------------

    public void updateViewScore(){
        try {
            ((GamePhaseActivity) getActivity()).updateScore(state.getGame().getBlackPieces(),
                                                            state.getGame().getWhitePieces());
        }catch (Exception ignored){
            Log.w("Reversi", "Failed to updated UI");
        }
    }

}
