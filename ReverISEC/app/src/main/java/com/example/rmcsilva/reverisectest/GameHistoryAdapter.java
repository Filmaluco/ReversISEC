package com.example.rmcsilva.reverisectest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import com.example.rmcsilva.reverisectest.ReversiLogic.ReversIsecScoreManager.ReversIsecScore;

import java.util.ArrayList;
import java.util.List;

public class GameHistoryAdapter extends ArrayAdapter<ReversIsecScore> {

    private ArrayList<ReversIsecScore> dataSet;
    Context mContext;
    int layoutID;

    private int lastPosition = -1;

    public GameHistoryAdapter(ArrayList<ReversIsecScore> data, Context context) {
        super(context, R.layout.listview_game_history, data);
        this.dataSet = data;
        this.mContext=context;
    }

    // View lookup cache
    private static class ViewHolder {
        TextView playerOneName;
        TextView playerOneScore;
        TextView playerTwoName;
        TextView playerTwoScore;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        ReversIsecScore dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.listview_game_history, parent, false);
            viewHolder.playerOneName = (TextView) convertView.findViewById(R.id.tvPlayerOneNameHistory);
            viewHolder.playerOneScore = (TextView) convertView.findViewById(R.id.tvPlayerOneScoreHistory);
            viewHolder.playerTwoName = (TextView) convertView.findViewById(R.id.tvPlayerTwoNameHistory);
            viewHolder.playerTwoScore = (TextView) convertView.findViewById(R.id.tvPlayerTwoScoreHistory);

            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();

            result=convertView;
        }

        lastPosition = position;

        viewHolder.playerOneName.setText(dataModel.getPlayer1());
        viewHolder.playerOneScore.setText(Integer.toString(dataModel.getWhiteScore()));
        viewHolder.playerTwoName.setText(dataModel.getPlayer2());
        viewHolder.playerTwoScore.setText(Integer.toString(dataModel.getBlackScore()));

        return convertView;
    }

    public void setLayout(int layoutID){
        this.layoutID = layoutID;
    }
}
