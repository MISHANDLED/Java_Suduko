package com.example.suduko.UI;

import com.example.suduko.Functions.SudokuClass;

public interface UserInterface {
    interface EventListener{
        void SudukoInput(int x, int y, int input);
        void DialogClick();
    }

    interface View{
        void setListener(UserInterface.EventListener eventListener);
        void updateSquare(int x, int y, int input);
        void updateBoard(SudokuClass game);
        void showMessage(String message);
        void showError(String message);
    }
}
