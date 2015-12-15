package de.szut.dqi12.texasholdem.gui;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;

import de.szut.dqi12.texasholdem.R;

/**
 * Created by Marcel on 09.11.2015.
 */
public class Game  extends Activity {

    ImageView ivP1C1, ivP1C2, ivP2C1, ivP2C2, ivP3C1, ivP3C2, ivP4C1, ivP4C2, ivP5C1, ivP5C2,  ivP6C1, ivP6C2;

    // numberOfRivals cant be less
    int numberOfRivals = 1;
    // just a default value
    int FieldSize = 90;

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

        setFieldSize();
        fillTableRowTop(FieldSize);
    }

    // 6x9
    public void fillTableRowTop(int fieldSize){

        // existing ImageViews
        ImageView[] existingIVs = {ivP1C1, ivP1C2, ivP2C1, ivP2C2, ivP3C1, ivP3C2, ivP4C1, ivP4C2, ivP5C1, ivP5C2, ivP6C2};

        // TODO: 01.12.2015 get Number of rival Player
        // just a default value
        numberOfRivals = 6;

        int pair = 0;

        for(int addRival = 0; addRival<numberOfRivals; addRival++){
            //get rivals from list
            ImageView tempRival = existingIVs[addRival];

            // TODO: 01.12.2015 anywhy rivals can not be changed
            // set width of the tempRival
            tempRival.getLayoutParams().width = (fieldSize / 3);
            // set height of the tempRival
            tempRival.getLayoutParams().height = ((fieldSize/3)/6*9);

            // set left margin of tempRival
            if(pair%2 != 0){
                // when the second card of the rival gets processed..
                // add a margin of ..., because a pair of cards is shown
                setSecondMargin(tempRival);
            }else{
                // when beginning at the first card of the pair
                setPrimeMargin(tempRival);
            }

            //set due size and margin of the element
        }
        //default
    }

    public void setFieldSize(){
        DisplayMetrics dm = new DisplayMetrics();

        getWindowManager().getDefaultDisplay().getMetrics(dm);


        int dWidth = dm.widthPixels;

        FieldSize = dWidth/numberOfRivals;

    }

    public void setPrimeMargin(ImageView iv){

        int leftMargin = (FieldSize/2) - (FieldSize/3) - ((FieldSize/2) - (FieldSize/3))/6;

        if (iv.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) iv.getLayoutParams();
            p.setMargins(leftMargin, 0, 0, 0);
            iv.requestLayout();
        }

//        LinearLayout.LayoutParams primeMargin = (LinearLayout.LayoutParams)iv.getLayoutParams();
//        primeMargin.setMargins(leftMargin, 0, 0, 0);
    }

    public void setSecondMargin(ImageView iv){

        int leftMargin = ((FieldSize/2) - (FieldSize/3) - ((FieldSize/2) - (FieldSize/3))/6)/numberOfRivals;

        if (iv.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) iv.getLayoutParams();
            p.setMargins(leftMargin, 0, 0, 0);
            iv.requestLayout();
        }
//
//        LinearLayout.LayoutParams secondMargin = (LinearLayout.LayoutParams)iv.getLayoutParams();
//        secondMargin.setMargins(leftMargin, 0, 0, 0);
    }

    @Override
    public void onBackPressed() {
    }

}
