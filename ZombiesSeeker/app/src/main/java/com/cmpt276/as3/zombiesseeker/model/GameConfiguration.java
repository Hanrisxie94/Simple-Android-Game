package com.cmpt276.as3.zombiesseeker.model;

import android.content.Context;

import com.cmpt276.as3.zombiesseeker.OptionalActivity;


public class GameConfiguration {
    private int numRow;
    private int numCol;
    private int numZombie;

    // singleton support
    private static GameConfiguration instance;
    private GameConfiguration(Context context){
        // private to prevent anyone else from instantiating
        numRow = OptionalActivity.getBoardRows(context);
        numCol = OptionalActivity.getBoardCols(context);
        numZombie = OptionalActivity.getNumZombieSelected(context);
    }
    public static GameConfiguration getConfiguration(Context context){
        if (instance == null){
            instance = new GameConfiguration(context);
        }
        return instance;
    }


    public int getNumRow() {
        return numRow;
    }

    public int getNumCol() {
        return numCol;
    }

    public int getNumZombie() {
        return numZombie;
    }

    public void setNumRow(int numRow) {
        this.numRow = numRow;
    }

    public void setNumCol(int numCol) {
        this.numCol = numCol;
    }

    public void setNumZombie(int numZombie) {
        this.numZombie = numZombie;
    }
}
