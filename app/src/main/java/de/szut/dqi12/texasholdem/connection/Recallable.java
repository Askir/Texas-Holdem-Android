package de.szut.dqi12.texasholdem.connection;

/**
 * Created by Jascha Beste on 16.10.2015.
 */
public interface Recallable {
    /**
     * This function returns the longest time the implementing class is willing to wait
     * @return The maximum wait time in milliseconds (I suggest 5000)
     */
    public abstract long getMaxWaitTIme();

    /**
     * This function returns the timestamp shortly before addExpectation is called
     * @return Timestamp before addExpetation is called
     */
    public abstract long getTimeStamp();

    /**
     * This function is called once the action the class is waiting for has occured
     * @param action The occured action
     * @param params The parameters that this action has been called with
     */
    public abstract void inform(String action, String params[]);

    /**
     * @return The Action this class is waiting for
     */
    public abstract String Action(); // this method should return the Action at which inform should be called.

    /**
     * @return The parameters for the specified action
     */
    public abstract String Params(); // this method should return specific params if they are required for the inform function to be called. (return null if no params are necessary)
}
