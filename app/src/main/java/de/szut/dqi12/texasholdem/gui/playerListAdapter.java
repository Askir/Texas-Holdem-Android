package de.szut.dqi12.texasholdem.gui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import de.szut.dqi12.texasholdem.R;
import de.szut.dqi12.texasholdem.guibackbone.Lobby;

/**
 * Created by Jascha on 08.01.2016.
 */


public class playerListAdapter extends BaseAdapter {
    private final LayoutInflater mLayoutInflator;

    public playerListAdapter(Context context){
        super();
        mLayoutInflator = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return Lobby.getInstance().getMaxPlayers();
    }

    @Override
    public Object getItem(int position) {
        return Lobby.getInstance().getPlayernames()[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = mLayoutInflator.inflate(R.layout.player_list_item, parent, false);
        }

        TextView playerName = (TextView) view.findViewById(R.id.playerListPlayerName);
        TextView playerStatus = (TextView) view.findViewById(R.id.playerListPlayerReady);

        String[] playerNames = Lobby.getInstance().getPlayernames();
        playerName.setText(playerNames[position]);
        boolean[] playerStates = Lobby.getInstance().getStates();
        playerStatus.setText(Boolean.toString(playerStates[position]));


        return view;
    }
}
