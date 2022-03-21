package com.example.suduko.UI.logic;

import com.example.suduko.Consts.GameState;
import com.example.suduko.Consts.Messages;
import com.example.suduko.Functions.Storage;
import com.example.suduko.Functions.SudokuClass;
import com.example.suduko.UI.UserInterface;

import java.io.IOException;

public class ControlLogic implements UserInterface.EventListener {
    private Storage storage;
    private UserInterface.View view;

    public ControlLogic(Storage storage, UserInterface.View view) {
        this.storage = storage;
        this.view = view;
    }

    @Override
    public void SudukoInput(int x, int y, int input) {
        try {
            SudokuClass gameData = storage.getGameData();
            int[][] newGridState = gameData.getGridState();
            newGridState[x][y] = input;

            gameData = new SudokuClass(
                    GameLogic.checkForCompletion(newGridState),
                    newGridState
            );

            storage.updateGameData(gameData);

            //either way, update the view
            view.updateSquare(x, y, input);

            //if game is complete, show dialog
            if (gameData.getGameState() == GameState.COMPLETE) view.showMessage(Messages.GAME_COMPLETE);
        } catch (IOException e) {
            e.printStackTrace();
            view.showError(Messages.ERROR_MESSAGE);
        }
    }

    @Override
    public void DialogClick() {
        try {
            storage.updateGameData(GameLogic.getNewGame());
            view.updateBoard(storage.getGameData());
        } catch (IOException e) {
            view.showError(Messages.ERROR_MESSAGE);
        }
    }
}
