package de.szut.dqi12.texasholdem.guibackbone;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;

import de.szut.dqi12.texasholdem.Controller;

import static android.database.sqlite.SQLiteDatabase.openOrCreateDatabase;

/**
 * Created by Jascha on 09.10.2015.
 * This class is not doing anything so far please ignore
 */
public class DatabaseConnection {
    SQLiteDatabase db;
    DatabaseOpenHelper openHelper;

    public DatabaseConnection() {
        openHelper = new DatabaseOpenHelper(Controller.getInstance().getActiveActivity().getBaseContext(),"TexasHoldem");
        //db = openOrCreateDatabase("TexasHoldem", null);
        db = openHelper.getReadableDatabase();
        db.execSQL("CREATE TABLE IF NOT EXISTS OPTIONS(volume INT, username VARCHAR(20))");
    }

    public void setUsername(String username) {
        db.execSQL("UPDATE OPTIONS SET username=" + username);
    }

    public void setVolume(int volume) {
        db.execSQL("UPDATE OPTIONS SET volume=" + volume);
    }

    public String getUsername() {
        Cursor cursor= db.rawQuery("SELECT username FROM OPTIONS", null);
        if(cursor.moveToFirst()){
            return cursor.getString(0);
        }
        return null;
    }

    public int getVolume() {
         Cursor cursor = db.rawQuery("SELECT volume FROM OPTIONS", null);
         if(cursor.moveToFirst()) {
             return cursor.getInt(0);
         }
        return 50;
    }
}
