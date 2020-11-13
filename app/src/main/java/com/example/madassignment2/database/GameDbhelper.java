package com.example.madassignment2.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.madassignment2.object.GameData;

import java.lang.reflect.GenericArrayType;

public class GameDbhelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "game.db";

    public GameDbhelper(Context context) {
        super(context, "game.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + GameSchema.SettingTable.NAME + "(" +
                GameSchema.SettingTable.Cols.MAP_WIDTH + " INTEGER, " +
                GameSchema.SettingTable.Cols.MAP_HEIGHT + " INTEGER, " +
                GameSchema.SettingTable.Cols.STARTING_MONEY + " INTEGER)");

        db.execSQL("CREATE TABLE " + GameSchema.StatusScreenTable.NAME + "(" +
                GameSchema.StatusScreenTable.Cols.CURR_MONEY + " INTEGER, " +
                GameSchema.StatusScreenTable.Cols.GAME_TIME + " INTEGER, " +
                GameSchema.StatusScreenTable.Cols.CITY_NAME + " TEXT, " +
                GameSchema.StatusScreenTable.Cols.POPULATION + " INTEGER, " +
                GameSchema.StatusScreenTable.Cols.EMPLOYMENT_RATE + " REAL, " +
                GameSchema.StatusScreenTable.Cols.NUM_RES + " INTEGER, " +
                GameSchema.StatusScreenTable.Cols.NUM_COMM + " INTEGER)");

        db.execSQL("CREATE TABLE " + GameSchema.MapTable.NAME + "(" +
                GameSchema.MapTable.Cols.TYPE + " TEXT, " +
                GameSchema.MapTable.Cols.POSITION + " INTEGER, " +
                GameSchema.MapTable.Cols.OWNER_NAME + " TEXT, " +
                GameSchema.MapTable.Cols.STRUCTURE + " INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //not needed
    }
}
