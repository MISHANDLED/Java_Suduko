package com.example.suduko.Functions;

import com.example.suduko.ComputeLogic.SudokuUtil;
import com.example.suduko.Consts.GameState;

import java.io.Serializable;

public class SudokuClass implements Serializable {
    private final GameState gameState;
    private final int[][] gridState;

    public static final int GRID_BOUNDARY = 9;

    public SudokuClass(GameState gameState, int[][] gridState) {
        this.gameState = gameState;
        this.gridState = gridState;
    }

    public GameState getGameState() {
        return gameState;
    }

    public int[][] getGridState() {
        return SudokuUtil.copyToNewArray(gridState);
    }
}
