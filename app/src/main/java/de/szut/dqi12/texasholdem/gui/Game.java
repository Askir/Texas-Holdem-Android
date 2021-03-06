package de.szut.dqi12.texasholdem.gui;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import de.szut.dqi12.texasholdem.Controller;
import de.szut.dqi12.texasholdem.R;


/**
 * Created by Marcel on 09.11.2015.
 */
public class Game  extends Activity {

    ImageView ivP1C1, ivP1C2, ivP2C1, ivP2C2, ivP3C1, ivP3C2, ivP4C1, ivP4C2, ivP5C1, ivP5C2,
            tc1, tc2, tc3, tc4, tc5, ivPC1, ivPC2, ivBR1, ivBR2, ivBR3, ivBR4, ivBR5, ivBR6, ivBP;
    Button btnFold, btnAllIn, btnCheckCall, btnBetRaise, btnExit, btnRestart;
    TextView tvPot, tvBudget, tvMinBet;
    EditText etBet;

    private ViewGroup queryContainer, endQuery;


    // nor = number of rivals, just a default value.
    int nor = 1;

    private Boolean myTurn = false;
    Boolean firstRound = false;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Controller.getInstance().setActiveActivity(this);
        setContentView(R.layout.game);

        initializeViews();

    }

    /**
     * This method is just to promote readability. It initializes all views of the activity Game.
     */
    private void initializeViews(){
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

        // contains all views in the middle of game field
        queryContainer = (ViewGroup) findViewById(R.id.queryContainer);

        // load table cards
        tc1 = (ImageView)findViewById(R.id.imageViewGameTC1);
        tc2 = (ImageView)findViewById(R.id.imageViewGameTC2);
        tc3 = (ImageView)findViewById(R.id.imageViewGameTC3);
        tc4 = (ImageView)findViewById(R.id.imageViewGameTC4);
        tc5 = (ImageView)findViewById(R.id.imageViewGameTC5);

        // load player cards
        ivPC1 = (ImageView)findViewById(R.id.imageViewGamePC1);
        ivPC2 = (ImageView)findViewById(R.id.imageViewGamePC2);

        // set the available rivals
        setRivalsReady();

        btnAllIn = (Button)findViewById(R.id.buttonGameAllIn);
        btnAllIn.setOnClickListener(view);
        btnFold = (Button)findViewById(R.id.buttonGameFold);
        btnFold.setOnClickListener(view);

        btnBetRaise = (Button)findViewById(R.id.buttonGameBetRaise);
        btnBetRaise.setOnClickListener(view);
        btnCheckCall = (Button)findViewById(R.id.buttonGameCheckCall);
        btnCheckCall.setOnClickListener(view);
        etBet = (EditText)findViewById(R.id.editTextGameBet);
        setBtnStatus();

        tvPot = (TextView)findViewById(R.id.textViewGamePot);
        // 0 is the default value for the beginning
        displayPot(0);

        tvBudget = (TextView)findViewById(R.id.textViewGameBudget);
        tvMinBet = (TextView)findViewById(R.id.textViewGameBet);

        // blindviews
        ivBP = (ImageView)findViewById(R.id.ivGameBlindP);
        ivBR1 = (ImageView)findViewById(R.id.ivGameBlindR1);
        ivBR2 = (ImageView)findViewById(R.id.ivGameBlindR2);
        ivBR3 = (ImageView)findViewById(R.id.ivGameBlindR3);
        ivBR4 = (ImageView)findViewById(R.id.ivGameBlindR4);
        ivBR5 = (ImageView)findViewById(R.id.ivGameBlindR5);

        // this container will be exchanged
        queryContainer = (ViewGroup) findViewById(R.id.queryContainer);
        endQuery = (ViewGroup) findViewById(R.id.endQuery);
    }

    /**
     * Promotes readability. Searches for the clicked view via view-id and runs specified
     * function for that action.
     */
    private View.OnClickListener view = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case (R.id.buttonGameBetRaise):
                    if(getMyTurn()) {
                        Toast.makeText(getBaseContext(), "Input your new bid.", Toast.LENGTH_SHORT).show();
                        // open number keyboard and let the player input a set
                        etBet.setFocusableInTouchMode(true);
                        etBet.setFocusable(true);
                        etBet.requestFocus();
                        InputMethodManager m = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        if (m != null) {
                            m.toggleSoftInput(0, InputMethodManager.SHOW_IMPLICIT);
                        }
                        // TODO: 07.01.2016 send value input. Is it enough? Yes -> change
                    }else{
                        Toast.makeText(getBaseContext(), "It's not your turn.", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case (R.id.buttonGameCheckCall):
                    if(getMyTurn()) {
                        Toast.makeText(getBaseContext(), "Check/Call", Toast.LENGTH_SHORT).show();
                        // TODO: 07.01.2016 send interaction
                    }else{
                        Toast.makeText(getBaseContext(), "It's not your turn.", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case (R.id.buttonGameFold):
                    if(getMyTurn()){
                        Toast.makeText(getBaseContext(), "Fold...", Toast.LENGTH_SHORT).show();
                        // TODO: 07.01.2016 send interaction
                    }else{
                        Toast.makeText(getBaseContext(), "It's not your turn.", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case (R.id.buttonGameAllIn):
                    if(getMyTurn()){
                        Toast.makeText(getBaseContext(), "All-In!", Toast.LENGTH_SHORT).show();
                        // TODO: 07.01.2016 send interaction
                    }else{
                        Toast.makeText(getBaseContext(), "It's not your turn.", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case (R.id.exit):
                    // TODO: 22.02.2016 go back to main menue
                    Toast.makeText(getBaseContext(), "Exit Game", Toast.LENGTH_SHORT).show();
                    break;
                case (R.id.restart):
                    setContentView(R.layout.game);
                    // TODO: 22.02.2016 restart game
                    Toast.makeText(getBaseContext(), "Restart Game", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };


    /**
     * Sets the variable nor with the number of playing rivals.
     *
     * @param rivals number of rivals playing in the game.
     */
    public void setNor(int rivals){
        nor = rivals;
    } // TODO: 22.02.2016 setNor required in this class?

    /**
     * Sets all cards, of whose player is not there, in gray color, to show only from player
     * played cards.
     */
    private void setRivalsReady(){

        if(nor < 5){

            ivP5C1.setImageResource(R.color.gray);
            ivP5C2.setImageResource(R.color.gray);

            if(nor < 4){

                ivP4C1.setImageResource(R.color.gray);
                ivP4C2.setImageResource(R.color.gray);

                if(nor < 3){

                    ivP3C1.setImageResource(R.color.gray);
                    ivP3C2.setImageResource(R.color.gray);

                    if(nor < 2){

                        ivP2C1.setImageResource(R.color.gray);
                        ivP2C2.setImageResource(R.color.gray);

                    }
                }
            }
        }
    }

    /**
     * Changes the status of the Boolean 'firstRound'.
     */
    public void setFirstRoundStatus(){
        firstRound = !firstRound;
    }

    /**
     * Changes the labes on the buttons on the right side of the cards of the player.
     */
    public void setBtnStatus(){

        if(firstRound){
            btnCheckCall.setText("Check");
            btnBetRaise.setText("Bet");
            setFirstRoundStatus();
        }else{
            btnCheckCall.setText("Call");
            btnBetRaise.setText("Raise");
        }
    }

    /**
     *
     * @param firstCard whether it is players 1st or 2nd card.
     * @param color which color meant card has.
     * @param number which number meant card has.
     */
    public void setPlayerCards(Boolean firstCard, String color, int number){
        if(firstCard)
            changeCardStatus(ivPC1, color, number);
        else
            changeCardStatus(ivPC2, color, number);
    }

    /**
     *
     * @param rival number of rival that is meant.
     * @param firstCard whether it is his first or his second card.
     * @param color which color his card has.
     * @param number which number the card has.
     */
    public void setRivalCards(int rival, Boolean firstCard, String color, int number){

        if(rival == 0){
            if(firstCard)
                changeCardStatus(ivP1C1, color, number);
            else
                changeCardStatus(ivP1C2, color, number);
        }
        else if(rival == 1){
            if(firstCard)
                changeCardStatus(ivP1C1, color, number);
            else
                changeCardStatus(ivP1C2, color, number);
        }
        else if(rival == 2){
            if(firstCard)
                changeCardStatus(ivP1C1, color, number);
            else
                changeCardStatus(ivP1C2, color, number);
        }
        else if(rival == 3){
            if(firstCard)
                changeCardStatus(ivP1C1, color, number);
            else
            changeCardStatus(ivP1C2, color, number);
        }
        else if(rival == 4){
            if(firstCard)
                changeCardStatus(ivP1C1, color, number);
            else
                changeCardStatus(ivP1C2, color, number);
        }
        else if(rival == 5){
            if(firstCard)
                changeCardStatus(ivP1C1, color, number);
            else
                changeCardStatus(ivP1C2, color, number);
        }
    }

    /**
     * changes the card status of the boardcards.
     * @param boardCard which card shall be changed. 1st-5th.
     * @param color which color the boardcard shall become. ('c' = cross, 's' = spades, 'h' = hearts, 'd' = diamonds)
     * @param number the number or picture ('2' = 2; '3' = 3 ... '11' = Jack,
     *                  '12' = Queen, '13' = King, '14' = Ace).
     */
    public void setBoardCard(int boardCard, String color, int number){
        if(boardCard == 1)
            changeCardStatus(tc1, color, number);
        else if(boardCard == 2)
            changeCardStatus(tc2, color, number);
        else if(boardCard == 3)
            changeCardStatus(tc3, color, number);
        else if(boardCard == 4)
            changeCardStatus(tc4, color, number);
        else if(boardCard == 5)
            changeCardStatus(tc5, color, number);
    }

    /**
     * Changes the Status of any card.
     *
     * @param iv        delivered Imageview that should become changed
     * @param color     the "color" that the ImageView should become. letter is for the color
     *                  ('c' = cross, 's' = spades, 'h' = hearts, 'd' = diamonds)
     * @param number    the number or picture ('2' = 2; '3' = 3 ... '11' = Jack,
     *                  '12' = Queen, '13' = King, '14' = Ace).
     */
    // TODO: 02.02.2016 maybe rewrite?
    private void changeCardStatus(ImageView iv, String color, int number) {

        switch (color) {
            case "c":
                switch (number) {
                    case 2:
                        iv.setImageResource(R.mipmap.ic_c2);
                        break;
                    case 3:
                        iv.setImageResource(R.mipmap.ic_c3);
                        break;
                    case 4:
                        iv.setImageResource(R.mipmap.ic_c4);
                        break;
                    case 5:
                        iv.setImageResource(R.mipmap.ic_c5);
                        break;
                    case 6:
                        iv.setImageResource(R.mipmap.ic_c6);
                        break;
                    case 7:
                        iv.setImageResource(R.mipmap.ic_c7);
                        break;
                    case 8:
                        iv.setImageResource(R.mipmap.ic_c8);
                        break;
                    case 9:
                        iv.setImageResource(R.mipmap.ic_c9);
                        break;
                    case 10:
                        iv.setImageResource(R.mipmap.ic_c10);
                        break;
                    case 11:
                        iv.setImageResource(R.mipmap.ic_c11);
                        break;
                    case 12:
                        iv.setImageResource(R.mipmap.ic_c12);
                        break;
                    case 13:
                        iv.setImageResource(R.mipmap.ic_c13);
                        break;
                    case 14:
                        iv.setImageResource(R.mipmap.ic_c14);
                        break;
                }
                break;
            case "s":
                switch (number) {
                    case 2:
                        iv.setImageResource(R.mipmap.ic_s2);
                        break;
                    case 3:
                        iv.setImageResource(R.mipmap.ic_s3);
                        break;
                    case 4:
                        iv.setImageResource(R.mipmap.ic_s4);
                        break;
                    case 5:
                        iv.setImageResource(R.mipmap.ic_s5);
                        break;
                    case 6:
                        iv.setImageResource(R.mipmap.ic_s6);
                        break;
                    case 7:
                        iv.setImageResource(R.mipmap.ic_s7);
                        break;
                    case 8:
                        iv.setImageResource(R.mipmap.ic_s8);
                        break;
                    case 9:
                        iv.setImageResource(R.mipmap.ic_s9);
                        break;
                    case 10:
                        iv.setImageResource(R.mipmap.ic_s10);
                        break;
                    case 11:
                        iv.setImageResource(R.mipmap.ic_s11);
                        break;
                    case 12:
                        iv.setImageResource(R.mipmap.ic_s12);
                        break;
                    case 13:
                        iv.setImageResource(R.mipmap.ic_s13);
                        break;
                    case 14:
                        iv.setImageResource(R.mipmap.ic_s14);
                        break;
                }
            case "h":
                switch (number) {
                    case 2:
                        iv.setImageResource(R.mipmap.ic_h2);
                        break;
                    case 3:
                        iv.setImageResource(R.mipmap.ic_h3);
                        break;
                    case 4:
                        iv.setImageResource(R.mipmap.ic_h4);
                        break;
                    case 5:
                        iv.setImageResource(R.mipmap.ic_h5);
                        break;
                    case 6:
                        iv.setImageResource(R.mipmap.ic_h6);
                        break;
                    case 7:
                        iv.setImageResource(R.mipmap.ic_h7);
                        break;
                    case 8:
                        iv.setImageResource(R.mipmap.ic_h8);
                        break;
                    case 9:
                        iv.setImageResource(R.mipmap.ic_h9);
                        break;
                    case 10:
                        iv.setImageResource(R.mipmap.ic_h10);
                        break;
                    case 11:
                        iv.setImageResource(R.mipmap.ic_h11);
                        break;
                    case 12:
                        iv.setImageResource(R.mipmap.ic_h12);
                        break;
                    case 13:
                        iv.setImageResource(R.mipmap.ic_h13);
                        break;
                    case 14:
                        iv.setImageResource(R.mipmap.ic_h14);
                        break;
                }
            case "d":
                switch (number) {
                    case 2:
                        iv.setImageResource(R.mipmap.ic_d2);
                        break;
                    case 3:
                        iv.setImageResource(R.mipmap.ic_d3);
                        break;
                    case 4:
                        iv.setImageResource(R.mipmap.ic_d4);
                        break;
                    case 5:
                        iv.setImageResource(R.mipmap.ic_d5);
                        break;
                    case 6:
                        iv.setImageResource(R.mipmap.ic_d6);
                        break;
                    case 7:
                        iv.setImageResource(R.mipmap.ic_d7);
                        break;
                    case 8:
                        iv.setImageResource(R.mipmap.ic_d8);
                        break;
                    case 9:
                        iv.setImageResource(R.mipmap.ic_d9);
                        break;
                    case 10:
                        iv.setImageResource(R.mipmap.ic_d10);
                        break;
                    case 11:
                        iv.setImageResource(R.mipmap.ic_d11);
                        break;
                    case 12:
                        iv.setImageResource(R.mipmap.ic_d12);
                        break;
                    case 13:
                        iv.setImageResource(R.mipmap.ic_d13);
                        break;
                    case 14:
                        iv.setImageResource(R.mipmap.ic_d4);
                        break;
                }
        }
    }
    public void setLabelText(int nr,String text){
        TextView tw1 = (TextView)findViewById(R.id.textViewMoney1);
        TextView tw2 = (TextView)findViewById(R.id.textViewMoney2);
        TextView tw3 = (TextView)findViewById(R.id.textViewMoney3);
        TextView tw4 = (TextView)findViewById(R.id.textViewMoney4);
        TextView tw5 = (TextView)findViewById(R.id.textViewMoney5);
        switch(nr){
            case 1:
                tw1.setText(text);
                break;
            case 2:
                tw2.setText(text);
                break;
            case 3:
                tw3.setText(text);
                break;
            case 4:
                tw4.setText(text);
                break;
            case 5:
                tw5.setText(text);
                break;
            default:
                Toast.makeText(getBaseContext(),"FICK DICH MARCEL",Toast.LENGTH_LONG);

        }

    }

    /**
     * Displays the actual pot size on the gameboard.
     * @param newPot    the new value of the pot
     */
    public void displayPot(int newPot){
        String pot = "" + newPot + "";
        tvPot.setText(pot);
    }

    /**
     * Displays the actual budget of the player on the gameboard.
     * @param budget the new budget of the player.
     */
    public void displayTvBudget(int budget){
        tvBudget.setText("Your Cupy's:\\n" + budget);
    }

    /**
     * Displays on the gameboard the minimum bet the player can do.
     * @param minBet the new minimum bet the player can bid.
     */
    public void displayTvMinBet(int minBet){
        tvMinBet.setText("Minimum bet:\\n" + minBet);
    }

    /**
     *
     * @param isItMyTurn Controller can set, whether it is clients turn or not
     */
    public void setIsMyTurn(Boolean isItMyTurn){
        myTurn = isItMyTurn;
    }

    /**
     * @return true if asking players turn, false when not.
     */
    public Boolean getMyTurn() {return myTurn;}

    /**
     * Sets the blinds. 'R' is for rival and the following number is for the explicit rival.
     * 'P' is for player.
     *
     * @param sb Small blind. E.g. sb = 'R2' -> Rival 2 gets small blind and Rival 1 gets big blind.
     */
    public void setBlinds(String sb){
        switch(sb){
            case "P":
                ivBP.setImageResource(R.mipmap.ic_sb);
                ivBR5.setImageResource(R.mipmap.ic_bb);
                break;
            case "R1":
                ivBR1.setImageResource(R.mipmap.ic_bb);
                ivBP.setImageResource(R.mipmap.ic_sb);
                break;
            case "R2":
                ivBR2.setImageResource(R.mipmap.ic_bb);
                ivBR1.setImageResource(R.mipmap.ic_sb);
                break;
            case "R3":
                ivBR3.setImageResource(R.mipmap.ic_bb);
                ivBR2.setImageResource(R.mipmap.ic_sb);
                break;
            case "R4":
                ivBR4.setImageResource(R.mipmap.ic_bb);
                ivBR3.setImageResource(R.mipmap.ic_sb);
                break;
            case "R5":
                ivBR5.setImageResource(R.mipmap.ic_bb);
                ivBR4.setImageResource(R.mipmap.ic_sb);
                break;
        }
    }

    /**
     * announces which users turn it is.
     *
     * @param user the user whose turn it is.
     */
    public void announcePlayingUser(String user){
        Toast.makeText(getBaseContext(), user + "'s turn.", Toast.LENGTH_SHORT).show();
    }

    public  void rivalLeft(String username, int rivalPos){
        Toast.makeText(getBaseContext(), username + " left", Toast.LENGTH_SHORT).show();
        if (rivalPos == 1)
        {
            ivP1C1.setImageResource(R.color.gray);
            ivP1C2.setImageResource(R.color.gray);
        }
        else if(rivalPos == 2)
        {
            ivP2C1.setImageResource(R.color.gray);
            ivP2C2.setImageResource(R.color.gray);
        }
        else if(rivalPos == 3)
        {
            ivP3C1.setImageResource(R.color.gray);
            ivP3C2.setImageResource(R.color.gray);
        }
        else if(rivalPos == 4)
        {
            ivP4C1.setImageResource(R.color.gray);
            ivP4C2.setImageResource(R.color.gray);
        }
        else if(rivalPos == 5)
        {
            ivP5C1.setImageResource(R.color.gray);
            ivP5C2.setImageResource(R.color.gray);
        }

    }

    /**
     * Displays a new field in the middle of the game field. the new field queries whether the user
     * want to exit or to restart the game. The new field is integrated as fragment and will disappear,
     */
    public void endGameQuery(){

        // shows query whether to restart or to exit the game
        queryContainer.removeAllViews();
        // TODO: 22.02.2016 maybe get exception, not sure wether delivering the fragment xml is right.
        View showEQ = getLayoutInflater().inflate(R.layout.fragment_end_query, null);
        queryContainer.addView(showEQ);

        btnExit = (Button) showEQ.findViewById(R.id.exit);
        btnExit.setOnClickListener(view);

        btnRestart = (Button) showEQ.findViewById(R.id.restart);
        btnRestart.setOnClickListener(view);
    }


    /**
     * Do nothing on pressing the physical back button to not quit the game.
     */
    @Override
    public void onBackPressed() {
    }

}