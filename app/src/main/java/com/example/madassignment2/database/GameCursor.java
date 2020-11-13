package com.example.madassignment2.database;

import android.database.Cursor;
import android.database.CursorWrapper;
import android.widget.EditText;

import com.example.madassignment2.fragment.FragmentStatus;
import com.example.madassignment2.object.GameData;
import com.example.madassignment2.object.GameElement;
import com.example.madassignment2.object.Setting;
import com.example.madassignment2.object.Structure;

public class GameCursor extends CursorWrapper {

    public GameCursor(Cursor cursor) {
        super(cursor);
    }

    public void getSetting() {
        int width = getInt(getColumnIndex(GameSchema.SettingTable.Cols.MAP_WIDTH));
        int height = getInt(getColumnIndex(GameSchema.SettingTable.Cols.MAP_HEIGHT));
        int money = getInt(getColumnIndex(GameSchema.SettingTable.Cols.STARTING_MONEY));

        //set in getSetting.
        GameData.get().getSetting().setMapWidth(width);
        GameData.get().getSetting().setMapHeight(height);
        GameData.get().getSetting().setInitialMoney(money);
    }

    public void getStatusMenuData() {
        int gameTime = getInt(getColumnIndex(GameSchema.StatusScreenTable.Cols.GAME_TIME));
        int population = getInt(getColumnIndex(GameSchema.StatusScreenTable.Cols.POPULATION));
        int nRes = getInt(getColumnIndex(GameSchema.StatusScreenTable.Cols.NUM_RES));
        int nComm = getInt(getColumnIndex(GameSchema.StatusScreenTable.Cols.NUM_COMM));
        String cityName = getString(getColumnIndex(GameSchema.StatusScreenTable.Cols.CITY_NAME));
        int employmentRate = getInt(getColumnIndex(GameSchema.StatusScreenTable.Cols.EMPLOYMENT_RATE));
        int money = getInt(getColumnIndex(GameSchema.StatusScreenTable.Cols.CURR_MONEY));

        //set in getStatus
        GameData.get().setGameTime(gameTime);
        GameData.get().setPopulation(population);
        GameData.get().setnRes(nRes);
        GameData.get().setnComm(nComm);
        GameData.get().setCityName(cityName);
        GameData.get().setEmploymentRate(employmentRate);
        GameData.get().setMoney(money);
    }

    //evidential that data saves into database, howver cannot output back onto the map screen
    public void getMapData() {
        int position = getInt(getColumnIndex(GameSchema.MapTable.Cols.POSITION));
        String label = getString(getColumnIndex(GameSchema.MapTable.Cols.OWNER_NAME));
        int drawableId = getInt(getColumnIndex(GameSchema.MapTable.Cols.STRUCTURE));
        String type = getString(getColumnIndex(GameSchema.MapTable.Cols.TYPE));

        Structure newStructure = new Structure(type, drawableId, label);

        GameData.get().getGrid().get(position).setStructure(newStructure);
    }
}
