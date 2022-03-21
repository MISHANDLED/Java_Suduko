package com.example.suduko.Functions;

import java.io.IOException;

public interface Storage {
    void updateGameData(SudokuClass game) throws IOException;
    SudokuClass getGameData() throws IOException;
}
