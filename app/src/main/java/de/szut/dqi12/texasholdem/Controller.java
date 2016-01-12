package de.szut.dqi12.texasholdem;

import de.szut.dqi12.texasholdem.connection.Connection;
import de.szut.dqi12.texasholdem.connection.Decryption;
import de.szut.dqi12.texasholdem.connection.Receive;
import de.szut.dqi12.texasholdem.connection.Send;
import de.szut.dqi12.texasholdem.connection.session.Session;

/**
 * Created by Alex on 28.09.2015.
 */
public class Controller {

    private Connection connection;
    private Send send;
    private Receive receive;
    private Decryption decryption;
    private long ping;
    private Session session;

    public static Controller instance;


    private Controller(){}

    public static Controller getInstance() {
        if (instance == null) {
            instance = new Controller();
            instance.start();
        }
        return instance;
    }

    public void start() {
        decryption = new Decryption();
        connection = Connection.getInstance();
        send = new Send();
        receive = new Receive();
        session = new Session();
        receive.execute();
        //decryption.startDecryption();

    }
    public Session getCurrentSession(){
        return this.session;
    }

    public boolean sendAction(String action, String[] params) {
        return send.sendAction(action, params);
    }
    public Connection getConnection() {
        return connection;
    }

    public Receive getReceive() {
        return receive;
    }

    public Decryption getDecryption() {return decryption;}

    public Send getSend() {
        return send;
    }


    public void setPing(long ping) {
        this.ping = ping;
    }

    public long getPing(){
        return ping;
    }
}
