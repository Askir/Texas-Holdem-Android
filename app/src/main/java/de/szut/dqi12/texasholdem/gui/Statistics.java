package de.szut.dqi12.texasholdem.gui;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import de.szut.dqi12.texasholdem.R;

/**
 * Created by Marcel on 06.11.2015.
 */
public class Statistics extends Activity {
    private de.szut.dqi12.texasholdem.guibackbone.Statistics stats;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statistics);
        stats = new de.szut.dqi12.texasholdem.guibackbone.Statistics(this);
        stats.updateStatistics();


    }

    public void writeStats(){
        TextView gamesPlayed = (TextView) findViewById(R.id.textViewStatisticsGamesPlayed);
        TextView gamesWon = (TextView) findViewById(R.id.textViewStatisticsGemsWon);
        TextView gainedCupys = (TextView) findViewById(R.id.textViewStatisticsGainedCupys);
        TextView highestProfit = (TextView) findViewById(R.id.textViewStatisticsHighestProfit);
        TextView highestLoss = (TextView) findViewById(R.id.textViewStatisticsHighestLoss);
        TextView playtime = (TextView) findViewById(R.id.textViewStatisticsPlaytime);
        TextView dateOfRegistration = (TextView) findViewById(R.id.textViewStatisticsDateOfReg);

        String[] statistics = stats.getStatistics();
        gamesPlayed.setText(statistics[0]);
        gamesWon.setText(statistics[1]);
        gainedCupys.setText(statistics[2]);
        highestProfit.setText(statistics[3]);
        highestLoss.setText(statistics[4]);
        playtime.setText(statistics[5]);
        dateOfRegistration.setText(statistics[6]);
    }
}


