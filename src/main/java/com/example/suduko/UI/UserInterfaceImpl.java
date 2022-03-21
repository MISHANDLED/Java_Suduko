package com.example.suduko.UI;

import com.example.suduko.Consts.GameState;
import com.example.suduko.Functions.Coordinates;
import com.example.suduko.Functions.SudokuClass;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.HashMap;

public class UserInterfaceImpl implements UserInterface.View, EventHandler<KeyEvent> {

    private final Stage stage;
    private final Group root;

    private static final double WINDOW_Y = 732;
    private static final double WINDOW_X = 668;
    private static final double BOARD_PADDING = 50;
    private static final double BOARD_X_AND_Y = 576;

    private static final Color WINDOW_BG_COLOR = Color.rgb(0,150,136);
    private static final Color BOARD_BG_COLOR = Color.rgb(224,242,241);
    private static final String TITLE = "Sudoku";


    private HashMap<Coordinates, SudukoTextField> textCoordinates;
    private UserInterface.EventListener listener;

    public UserInterfaceImpl(Stage stage) {
        this.stage = stage;
        this.root = new Group();
        textCoordinates = new HashMap<>();
        initializeUserInterface();
    }

    private void initializeUserInterface(){
        drawBackground(root);
        drawTitle(root);
        drawSudokuBoard(root);
        drawTextField(root);
        drawGridlines(root);
        stage.show();
    }

    private void drawGridlines(Group root) {
        int XandY = 114;
        int index = 0;
        while(index<8){
            int thickness;
            if(index==2 || index==5){
                thickness=3;
            }else {
                thickness=2;
            }

            Rectangle verticalLine = getLine(XandY+64*index, BOARD_PADDING, BOARD_X_AND_Y, thickness);
            Rectangle horizontalLine = getLine(BOARD_PADDING, XandY+64*index, thickness, BOARD_X_AND_Y);

            root.getChildren().addAll(verticalLine, horizontalLine);
            index++;
        }
    }

    private Rectangle getLine(double x, double y, double height, double width) {
        Rectangle line = new Rectangle();
        line.setX(x);
        line.setY(y);
        line.setHeight(height);
        line.setWidth(width);
        line.setFill(Color.BLACK);
        return line;
    }

    private void drawTextField(Group root) {
        final int xOrigin = 50;
        final int yOrigin = 50;
        final int delta = 64;

        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){
                int x = xOrigin + i * delta;
                int y = yOrigin + j * delta;

                SudukoTextField tile = new SudukoTextField(x,y);
                styleSudokuTile(tile, x, y);
                tile.setOnKeyPressed(this);
                textCoordinates.put(new Coordinates(i,j),tile);
                root.getChildren().add(tile);
            }
        }
    }

    private void styleSudokuTile(SudukoTextField tile, int x, int y) {
        Font numberFont = new Font(32);
        tile.setFont(numberFont);
        tile.setAlignment(Pos.CENTER);
        tile.setLayoutX(x);
        tile.setLayoutY(y);
        tile.setPrefHeight(64);
        tile.setPrefWidth(64);
        tile.setBackground(Background.EMPTY);
    }

    private void drawSudokuBoard(Group root) {
        Rectangle boardBackground = new Rectangle();
        boardBackground.setX(BOARD_PADDING);
        boardBackground.setY(BOARD_PADDING);
        boardBackground.setWidth(BOARD_X_AND_Y);
        boardBackground.setHeight(BOARD_X_AND_Y);
        boardBackground.setFill(BOARD_BG_COLOR);
        root.getChildren().add(boardBackground);
    }

    private void drawTitle(Group root) {
        Text title = new Text(235, 690, TITLE);
        title.setFill(Color.WHITE);
        Font titleFont = new Font(43);
        title.setFont(titleFont);
        root.getChildren().add(title);
    }

    private void drawBackground(Group root){
        Scene scene = new Scene(root, WINDOW_X, WINDOW_Y);
        scene.setFill(WINDOW_BG_COLOR);
        stage.setScene(scene);
    }

    @Override
    public void setListener(UserInterface.EventListener eventListener) {
        this.listener = eventListener;
    }

    @Override
    public void updateSquare(int x, int y, int input) {
        SudukoTextField tile = textCoordinates.get(new Coordinates(x, y));
        String value = Integer.toString(input);
        if (value.equals("0")) value = "";
        tile.textProperty().setValue(value);
    }

    @Override
    public void updateBoard(SudokuClass game) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                TextField tile = textCoordinates.get(new Coordinates(i, j));

                String value = Integer.toString(game.getGridState()[i][j]);

                if (value.equals("0")) value = "";
                tile.setText(value);

                if (game.getGameState() == GameState.NEW){
                    if (value.equals("")) {
                        tile.setStyle("-fx-opacity: 1;");
                        tile.setDisable(false);
                    } else {
                        tile.setStyle("-fx-opacity: 0.8;");
                        tile.setDisable(true);
                    }
                }
            }
        }
    }

    @Override
    public void showMessage(String message) {
        Alert dialog = new Alert(Alert.AlertType.CONFIRMATION, message, ButtonType.OK);
        dialog.showAndWait();

        if (dialog.getResult() == ButtonType.OK) listener.DialogClick();
    }

    @Override
    public void showError(String message) {
        Alert dialog = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
        dialog.showAndWait();
    }

    @Override
    public void handle(KeyEvent event) {
        if (event.getEventType() == KeyEvent.KEY_PRESSED) {
            if (event.getText().equals("0")
                    || event.getText().equals("1")
                    || event.getText().equals("2")
                    || event.getText().equals("3")
                    || event.getText().equals("4")
                    || event.getText().equals("5")
                    || event.getText().equals("6")
                    || event.getText().equals("7")
                    || event.getText().equals("8")
                    || event.getText().equals("9")
            ) {
                int value = Integer.parseInt(event.getText());
                handleInput(value, event.getSource());
            } else if (event.getCode() == KeyCode.BACK_SPACE) {
                handleInput(0, event.getSource());
            } else {
                ((TextField)event.getSource()).setText("");
            }
        }

        event.consume();
    }

    private void handleInput(int value, Object source) {
        listener.SudukoInput(
                ((SudukoTextField) source).getX(),
                ((SudukoTextField) source).getY(),
                value
        );
    }
}
