package com.cmpt276.as3.zombiesseeker.model;

import java.util.Random;


public class Game {
    private int numRow;
    private int numCol;
    private int[][] board;
    private int numZombieLeft;
    private boolean[][] foundZombies;
    private boolean[][] zombies;
    private boolean[][] clicked;
    private int numScan;
    private int numFound;

    public Game(int numRow, int numCol, int numZombie) {
        this.numRow = numRow;
        this.numCol = numCol;
        board = new int[numRow][numCol];
        numZombieLeft = numZombie;
        foundZombies = new boolean[numRow][numCol];
        zombies = new boolean[numRow][numCol];
        clicked = new boolean[numRow][numCol];


        // randomly generate zombies
        Random rand = new Random();
        int temp = numZombie;
        int i,j;
        while (temp > 0){
            i = rand.nextInt(numRow);
            j = rand.nextInt(numCol);
            if (!zombies[i][j]){
                zombies[i][j] = true;
                for (int col = 0; col < numCol; col++){
                    board[i][col]++;
                }
                for (int row = 0; row < numRow; row++){
                    board[row][j]++;
                }
                board[i][j]--;
                temp--;
            }
        }
    }


    // return -1 if (i, j) has a zombie, else, return num of zombie on i and j.
    public int checkPosition(int i, int j){
        if (zombies[i][j]){
            zombies[i][j] = false;
            for (int col = 0; col < numCol; col++){
                board[i][col]--;
            }
            for (int row = 0; row < numRow; row++){
                board[row][j]--;
            }
            board[i][j]++;
            numZombieLeft--;
            foundZombies[i][j] = true;
            numFound++;
            numScan++;
            return -1;
        } else {
            if (!clicked[i][j]) {
                clicked[i][j] = true;
                numScan++;
            }
            return board[i][j];
        }
    }

    public int getNumZombieLeft(){
        return numZombieLeft;
    }

    public int[][] getBoard() {
        return board;
    }

    public boolean[][] getClicked() {
        return clicked;
    }

    public boolean[][] getFoundZombie(){
        return foundZombies;
    }

    public int getNumScan(){
        return  numScan;
    }
    public int getNumFound(){
        return  numFound;
    }
}
