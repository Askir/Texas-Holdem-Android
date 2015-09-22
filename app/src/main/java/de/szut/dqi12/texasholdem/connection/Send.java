package de.szut.dqi12.texasholdem.connection;

/**
 * Created by Jascha on 22.09.2015.
 */
public class Send {

    public static boolean sendGameAction(GameAction action, String params[]){
        switch(action) {
            case FOLD:
                return sendFoldAction();

            case CALL:
                return sendCallAction();

            case CHECK:
                return sendCheckAction();

            case ALLIN:
                return sendAllinAction();

            case BET:
                return sendBetAction();

            case RAISE:
                return sendRaiseAction();

            case CHAT:
                return sendChatAction(params);

        }
        return false;
    }



    public static boolean sendClientAction(ClientAction action, String params[]){
        return false;
    }

    public static boolean sendGameAction(GameAction action){
        return false;
    }

    public static boolean sendClientAction(ClientAction action){
        return false;
    }



    //Just add the params[] parameter whereever it is needed I don't quite remember which action was what
    private static boolean sendFoldAction(){
        //// TODO: 22.09.2015  function needs to be filled
        return false;
    }

    private static boolean sendCheckAction() {
        //// TODO: 22.09.2015  function needs to be filled
        return false;
    }

    private static boolean sendAllinAction() {
        //// TODO: 22.09.2015  function needs to be filled
        return false;
    }

    private static boolean sendCallAction() {
        //// TODO: 22.09.2015  function needs to be filled
        return false;
    }

    private static boolean sendBetAction() {
        //// TODO: 22.09.2015  function needs to be filled
        return false;
    }

    private static boolean sendRaiseAction() {
        //// TODO: 22.09.2015  function needs to be filled
        return false;
    }

    private static boolean sendChatAction(String params[]) {
        //// TODO: 22.09.2015  function needs to be filled
        return false;
    }
}
