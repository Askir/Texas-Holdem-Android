package de.szut.dqi12.texasholdem.gui;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import de.szut.dqi12.texasholdem.R;
import de.szut.dqi12.texasholdem.guibackbone.*;

/**
 * Created by Jascha on 09.01.2016.
 */
public class GameListAdaptertemp extends BaseAdapter {
    private final LayoutInflater mLayoutInflator;

    public GameListAdaptertemp(Context context){
        super();
        mLayoutInflator = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return GameList.getInstance().getGames().size();
    }

    @Override
    public Object getItem(int position) {
        return GameList.getInstance().getGames().get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = mLayoutInflator.inflate(R.layout.game_list_item, parent, false);
        }

        TextView gameName = (TextView) view.findViewById(R.id.gameListItemName);
        TextView hostName = (TextView) view.findViewById(R.id.gameListItemHostName);
        TextView password = (TextView) view.findViewById(R.id.gameListItemPassword);
        TextView Slots = (TextView) view.findViewById(R.id.gameListItemSlots);

        ArrayList<de.szut.dqi12.texasholdem.guibackbone.Game> games = GameList.getInstance().getGames();
        de.szut.dqi12.texasholdem.guibackbone.Game game = games.get(position);

        gameName.setText(game.name);
        hostName.setText(game.host);
        password.setText(Boolean.toString(game.password));
        Slots.setText(game.currentPlayers + "/" + game.maxPlayers);
        if(game.maxPlayers==game.currentPlayers){
            Slots.setTextColor(Color.parseColor("#ff0000"));
        }

        return view;
    }
}
