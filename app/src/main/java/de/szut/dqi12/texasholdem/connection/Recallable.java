package de.szut.dqi12.texasholdem.connection;

/**
 * Created by Jascha Beste on 16.10.2015.
 */
public interface Recallable {

    public abstract long getMaxWaitTIme();

    public abstract long getTimeStamp();

    public abstract void inform(String action, String params[]);

    public abstract String Action(); // this method should return the Action at which inform should be called.

    public abstract String[] Params(); // this method should return specific params if they are required for the inform function to be called. (return null if no params are necessary)
}
