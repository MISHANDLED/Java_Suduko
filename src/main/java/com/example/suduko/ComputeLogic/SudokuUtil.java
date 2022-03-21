package com.example.suduko.ComputeLogic;

import com.example.suduko.Functions.SudokuClass;

public class SudokuUtil {

    public static void copySudokuArrayValues(int[][] oldArray, int[][] newArray) {
        for (int i = 0; i < SudokuClass.GRID_BOUNDARY; i++){
            for (int j = 0; j < SudokuClass.GRID_BOUNDARY; j++ ){
                newArray[i][j] = oldArray[i][j];
            }
        }
    }

    public static int[][] copyToNewArray(int[][] oldArray) {
        int[][] newArray = new int[SudokuClass.GRID_BOUNDARY][SudokuClass.GRID_BOUNDARY];
        for (int i = 0; i < SudokuClass.GRID_BOUNDARY; i++){
            for (int j = 0; j < SudokuClass.GRID_BOUNDARY; j++ ){
                newArray[i][j] = oldArray[i][j];
            }
        }
        return newArray;
    }
}
