package de.szut.dqi12.texasholdem.gui;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;

import de.szut.dqi12.texasholdem.R;

/**
 * Created by Marcel on 09.11.2015.
 */
public class Game  extends Activity {

    ImageView ivP1C1, ivP1C2, ivP2C1, ivP2C2, ivP3C1, ivP3C2, ivP4C1, ivP4C2, ivP5C1, ivP5C2,  ivP6C1, ivP6C2;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);

        // contains ImageViews
        TableRow tl = (TableRow)findViewById(R.id.TableRowTop);

        // rival Player 1 Cards
        ivP1C1 = (ImageView)findViewById(R.id.imageViewGameP1C1);
        ivP1C2 = (ImageView)findViewById(R.id.imageViewGameP1C2);

        // rival Player 2 Cards
        ivP2C1 = (ImageView)findViewById(R.id.imageViewGameP2C1);
        ivP2C2 = (ImageView)findViewById(R.id.imageViewGameP2C2);

        // rival Player 3 Cards
        ivP3C1 = (ImageView)findViewById(R.id.imageViewGameP3C1);
        ivP3C2 = (ImageView)findViewById(R.id.imageViewGameP3C2);

        // rival Player 4 Cards
        ivP4C1 = (ImageView)findViewById(R.id.imageViewGameP4C1);
        ivP4C2 = (ImageView)findViewById(R.id.imageViewGameP4C2);

        // rival Player 5 Cards
        ivP5C1 = (ImageView)findViewById(R.id.imageViewGameP5C1);
        ivP5C2 = (ImageView)findViewById(R.id.imageViewGameP5C2);

        // rival Player 6 Cards
        ivP6C1 = (ImageView)findViewById(R.id.imageViewGameP6C1);
        ivP6C2 = (ImageView)findViewById(R.id.imageViewGameP6C2);
    }

    // 6x9
    public void fillTableRowTop(){

        // existing ImageViews
        ImageView[] existingIVs = {ivP1C1, ivP1C2, ivP2C1, ivP2C2, ivP3C1, ivP3C2, ivP4C1, ivP4C2, ivP5C1, ivP5C2, ivP6C2};

        // TODO: 01.12.2015 get Number of rival Player
        // just a default value
        int numberOfRivals = 6;

        for(int addRival = 0; addRival<numberOfRivals; addRival++){
            //get rivals from list
            ImageView tempRival = existingIVs[addRival];
            //set due size and margin of the element
        }
        //default
    }
}
