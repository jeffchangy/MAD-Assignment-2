package com.example.madassignment2.object;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Chronometer;

import com.example.madassignment2.R;
import com.example.madassignment2.activities.MainActivity;
import com.example.madassignment2.database.GameCursor;
import com.example.madassignment2.database.GameDbhelper;
import com.example.madassignment2.database.GameSchema;

import java.util.ArrayList;
import java.util.List;

//STORES GAME DATA, everything to do this the game specifically
public class GameData {

    //DECLARATIONS
    private Setting set;
    private SQLiteDatabase db;
    private Structure structure;
    private static GameData instance = null;
    private List<GameElement> grid;
    private GameElement selectedElement;
    int money, row, col;
    int gameTime, population, nRes, nComm;
    String cityName;
    double employmentRate;
    Context context;


    private GameData() {
        set = new Setting(50,10,1000);
        money = set.getInitialMoney();
        generateGrid();
    }

    public GameData(int gameTime, int population, int nRes, int nComm, String cityName, double employmentRate) {
        this.gameTime = gameTime;
        this.population = population;
        //this.nextDay = nextDay;
        this.nRes = nRes;
        this.nComm = nComm;
        this.cityName = cityName;
        this.employmentRate = employmentRate;
    }

    public static GameData get() {
        if (instance == null) {
            instance = new GameData();
        }
        return instance;
}

    //not in use anymore lol
    /*    public void generateGrid() {
        //create drawable gameElements to allow image input
        grid = new GameElement[set.getMapWidth()][set.getMapHeight()];
        for (int i = 0; i < set.getMapWidth(); i++) {
            for (int j = 0; j < set.getMapHeight(); j++) {
                grid[i][j] = new GameElement(null, null, null);
            }
        }
    }*/

    //stores GameElement into an ArrayList
    public void generateGrid() {
        grid = new ArrayList<GameElement>();
        for (int i = 0; i < set.getMapHeight() * set.getMapWidth(); i++) {
            grid.add(new GameElement(null, null, null));
        }
    }

    public List<GameElement> getGrid() { return grid; }
    public GameElement get(int i) {
        return grid.get(i);
    }

    public Setting getSetting() {
        return set;
    }
    public void setInstance() { this.instance = null; }

    public void setMoney(int money) { this.money = money; }
    public int getMoney() { return money; }

    //add to database in cursor
    public int getCurrMoney() { return money; }
    public int getGameTime() { return gameTime; }
    public String getCityName() { return cityName; }
    public int getPopulation() { return population; }
    public double getEmploymentRate() { return employmentRate; }
    public int getnRes() { return nRes; }
    public int getnComm() { return nComm; }

    public void setCurrMoney(int money) { this.money = money; }
    public void setGameTime(int gameTime) { this.gameTime = gameTime; }
    public void setCityName(String cityName) { this.cityName = cityName; }
    public void setPopulation(int pop) { this.population = pop; }
    public void setEmploymentRate(int eRate) { this.employmentRate = eRate; }
    public void setnRes(int nRes) { this.nRes = nRes; }
    public void setnComm(int nComm) { this.nComm = nComm; }

    //so i can actually transfer data into the details menu
    public GameElement getSelectedElement() { return selectedElement; }
    public void setSelectedElement(GameElement selectedElement) { this.selectedElement = selectedElement; }

    //public Structure getSelectedStructure() { return structure; }
    public void setSelectedStructure(Structure structure) { this.structure = structure; }

    public int getRow() { return row; }
    public void setRow(int row) { this.row = row; }

    public int getCol() { return col; }
    public void setCol(int col) { this.col = col; }


    //Database methods
    public void load(Context context) {
        this.db = new GameDbhelper(context.getApplicationContext()).getWritableDatabase();
    }
    public void loadGameData() {
        //List<GameData> list = new ArrayList<>();
        GameCursor cursor = new GameCursor(
                db.query(GameSchema.StatusScreenTable.NAME,
                        null, null,null, null,null,null)
        );
        try {
            cursor.moveToFirst();
            if (instance != null) {
                while (!cursor.isAfterLast()) {
                    cursor.getStatusMenuData();
                    cursor.moveToNext();
                }
            }
        }
        finally {
            cursor.close();
        }
    }
    public void loadSettings() {
        //List<GameData> list = new ArrayList<>();
        GameCursor cursor = new GameCursor(
                db.query(GameSchema.SettingTable.NAME,
                        null, null,null, null,null,null)
        );
        try {
            cursor.moveToFirst();
            if (instance != null) {
                while (!cursor.isAfterLast()) {
                    cursor.getSetting();
                    cursor.moveToNext();
                }
            }
        }
        finally {
            cursor.close();
        }
    }

    public void addSetting() {
        ContentValues cv = new ContentValues();
        cv.put(GameSchema.SettingTable.Cols.MAP_HEIGHT, GameData.get().getSetting().getMapHeight());
        cv.put(GameSchema.SettingTable.Cols.MAP_WIDTH, GameData.get().getSetting().getMapWidth());
        cv.put(GameSchema.SettingTable.Cols.STARTING_MONEY, GameData.get().getMoney());
        db.delete(GameSchema.SettingTable.NAME, null,null );
        db.insert(GameSchema.SettingTable.NAME, null, cv);
    }
    public void addStatusMenuData() {
        ContentValues cv = new ContentValues();
        cv.put(GameSchema.StatusScreenTable.Cols.CITY_NAME, GameData.get().getCityName());
        cv.put(GameSchema.StatusScreenTable.Cols.CURR_MONEY, GameData.get().getCurrMoney());
        cv.put(GameSchema.StatusScreenTable.Cols.EMPLOYMENT_RATE, GameData.get().getEmploymentRate());
        cv.put(GameSchema.StatusScreenTable.Cols.GAME_TIME, GameData.get().getGameTime());
        cv.put(GameSchema.StatusScreenTable.Cols.POPULATION, GameData.get().getPopulation());
        cv.put(GameSchema.StatusScreenTable.Cols.NUM_COMM, GameData.get().getnComm());
        cv.put(GameSchema.StatusScreenTable.Cols.NUM_RES, GameData.get().getnRes());
        db.delete(GameSchema.StatusScreenTable.NAME, null,null );
        db.insert(GameSchema.StatusScreenTable.NAME, null, cv);
    }

    public void resetGame() {
        db.delete(GameSchema.StatusScreenTable.NAME, null,null );
        db.delete(GameSchema.SettingTable.NAME, null,null );
    }

    public void addMapData() {
        ContentValues cv = new ContentValues();
        //cv.put(GameSchema.MapTable.Cols.RES_BUILD, GameData.get().getGrid().indexOf());
    }
}
